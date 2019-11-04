package com.example.riceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginScreen extends AppCompatActivity{

    private static final String TAG = "mainAc";
    Button login_btn;
    EditText user_e,pass_e;
    public static final String URL = "http://10.0.2.2:8080/rice_app/index.jsp";
    private String final_url;
    String user_inp,pass_inp,hash_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        login_btn = findViewById(R.id.login_btn);
        user_e = findViewById(R.id.user_edit);
        pass_e = findViewById(R.id.password_edit);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_inp = user_e.getText().toString();
                pass_inp = pass_e.getText().toString();

                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    byte[] digest = md.digest(pass_inp.getBytes());
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < digest.length; i++) {
                        sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                    }
                    hash_pass = sb.toString();
                    Log.d(TAG, "onClick: hash password : "+hash_pass);
                } catch (NoSuchAlgorithmException e) {}

                final_url = URL + "?user=" + user_inp;

                RequestQueue requestQueue = Volley.newRequestQueue(LoginScreen.this);
                StringRequest request = new StringRequest(Request.Method.GET,final_url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse",response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject item = jsonArray.getJSONObject(0);
                            String q_password = item.getString("Password");
                            Log.d(TAG, "onResponse: Query_password : "+q_password);

                            if(hash_pass.equals(q_password)){
                                Log.d(TAG, "onResponse: Passsword status : Match");
                                Toast.makeText(LoginScreen.this,"Login complete",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                                startActivity(intent);
                            }else{
                                Log.d(TAG, "onResponse: Passsword status : Didn't match");
                                Toast.makeText(LoginScreen.this,"Something went wrong",Toast.LENGTH_LONG).show();
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
        });
    }
}
