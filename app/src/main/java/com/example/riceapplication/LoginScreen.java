package com.example.riceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity{

    private static final String TAG = "mainAc";
    Button login_btn;
    EditText user_e,pass_e;
    public static final String URL = "http://10.0.2.2:8084/kak/index.jsp";
    public String username = "";
    private String final_url;
    String user_inp,pass_inp,hash_pass;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast backToast;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        login_btn    = findViewById(R.id.login_btn);
        user_e       = findViewById(R.id.user_edit);
        pass_e       = findViewById(R.id.password_edit);
        progressBar  = findViewById(R.id.progressBar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //*** Session Login
        final LoginHelper usrHelper = new LoginHelper(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_inp = user_e.getText().toString();
                pass_inp = pass_e.getText().toString();

                if(TextUtils.isEmpty(user_inp)) {
                    user_e.setError("Please enter username");
                    user_e.setBackgroundTintList(getResources().getColorStateList(R.color.error, getApplication().getTheme()));
                }else if(TextUtils.isEmpty(pass_inp)){
                    pass_e.setError("Please enter password");
                    pass_e.setBackgroundTintList(getResources().getColorStateList(R.color.error,getApplication().getTheme()));
                    user_e.setBackgroundTintList(null);
                }else {
                    user_e.setBackgroundTintList(null);
                    pass_e.setBackgroundTintList(null);

                    /*try {
                        MessageDigest md = MessageDigest.getInstance("");
                        byte[] digest = md.digest(pass_inp.getBytes());
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < digest.length; i++) {
                            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                        }
                        hash_pass = sb.toString();
                        Log.d(TAG, "onClick: hash password : "+hash_pass);
                    } catch (NoSuchAlgorithmException e) {}*/

                    final_url = URL + "?user=" + user_inp;

                    RequestQueue requestQueue = Volley.newRequestQueue(LoginScreen.this);
                    StringRequest request = new StringRequest(Request.Method.GET,final_url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "onResponse: Respond JSON : "+response.trim());
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject item = jsonArray.getJSONObject(0);

                                String q_password = item.getString("Password");
                                String status     = item.getString("status");
                                String message    = item.getString("message");

                                username = item.getString("Name");

                                Log.d(TAG, "onResponse: Query_password : "+q_password);
                                Log.d(TAG, "onResponse: Status : "+status);
                                Log.d(TAG, "onResponse: Name : "+username);

                                if (q_password.equals(q_password) && status.equals("success")) {
                                    Log.d(TAG, "onResponse: Passsword status : Match");
                                    Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                                    intent.putExtra("username",username);
                                    intent.putExtra("from_fg","1");
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);*/
                                    usrHelper.createSession(username);
                                    login_btn.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    user_e.setEnabled(false);
                                    pass_e.setEnabled(false);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                            progressBar.setVisibility(View.GONE);
                                            finish();
                                        }
                                    },1500);

                                }else{
                                    Log.d(TAG, "onResponse: Passsword status : Didn't match");
                                    Toast.makeText(LoginScreen.this,"Username or Password didn't match.gig",Toast.LENGTH_SHORT).show();
                                    login_btn.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    user_e.setEnabled(true);
                                    pass_e.setEnabled(true);
                                    user_e.setText("");
                                    pass_e.setText("");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("onError",error.toString());
                            Toast.makeText(LoginScreen.this,"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(request);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Called");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {

        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            backToast = Toast.makeText(this,"Back again to exit.", Toast.LENGTH_SHORT);
            backToast.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }

            }, 2000);
        } else {
            backToast.cancel();
            super.onBackPressed();
            finish();
            //System.exit(0);
            return;
        }
    }
}
