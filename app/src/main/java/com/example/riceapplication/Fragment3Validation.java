package com.example.riceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment3Validation extends AppCompatActivity {

    private static final String TAG = "validateAc2";
    private static final String URL = "http://10.0.2.2:8084/kak/insert3.jsp?";

    TextView editText_Time_start,editText_Time_done,
             repond_person_edit,pro_name_edit,size_edit,
             weight_edit,total_edit;

    String  Time_start,Time_done,repond_person,
            pro_name,size,weight,total,user,lot_no,Final_URL,
            status,message;

    Button va3_btn;
    Toolbar toolbar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment3_validation);

        toolbar3            = findViewById(R.id.toolbar3_va);
        va3_btn             = findViewById(R.id.form3_btn);
        repond_person_edit  = findViewById(R.id.Responsible_person_edit_va);
        pro_name_edit       = findViewById(R.id.ProductName_edit_va);
        size_edit           = findViewById(R.id.Size_edit_va);
        weight_edit         = findViewById(R.id.Weight_edit_va);
        editText_Time_start = findViewById(R.id.time_start_edit_3_va);
        editText_Time_done  = findViewById(R.id.time_done_edit_3_va);
        total_edit          = findViewById(R.id.total_edit_va);

        toolbar3.setTitle("Validation");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                repond_person = null;
                pro_name      = null;
                size          = null;
                weight        = null;
                Time_start    = null;
                Time_done     = null;
                total         = null;
                lot_no        = null;
                user          = null;
            } else {
                repond_person = extras.getString("C_EMP_NAME");
                pro_name      = extras.getString("C_PRO_NAME");
                size          = extras.getString("C_REC_SIZE");
                weight        = extras.getString("C_REC_WEIGHT");
                Time_start    = extras.getString("C_REC_START");
                Time_done     = extras.getString("C_REC_END");
                total         = extras.getString("C_REC_QUAN");
                lot_no  = extras.getString("LOT_NO");
                user    = extras.getString("username");
            }
        }else{
            repond_person  = (String) savedInstanceState.getSerializable("C_EMP_NAME");
            pro_name       = (String) savedInstanceState.getSerializable("C_PRO_NAME");
            size           = (String) savedInstanceState.getSerializable("C_REC_SIZE");
            weight         = (String) savedInstanceState.getSerializable("C_REC_WEIGHT");
            Time_start     = (String) savedInstanceState.getSerializable("C_REC_START");
            Time_done      = (String) savedInstanceState.getSerializable("C_REC_END");
            total          = (String) savedInstanceState.getSerializable("C_REC_QUAN");
            lot_no         = (String) savedInstanceState.getSerializable("LOT_NO");
            user           = (String) savedInstanceState.getSerializable("username");
        }

        repond_person_edit.setText(repond_person);
        pro_name_edit.setText(pro_name);
        size_edit.setText(size);
        weight_edit.setText(weight);
        editText_Time_start.setText(Time_start);
        editText_Time_done.setText(Time_done);
        total_edit.setText(total);

        va3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick();
            }
        });
    }

    private void onButtonClick() {

        Final_URL = URL +
                "C_EMP_NAME=" + repond_person +
                "&C_PRO_NAME=" + pro_name +
                "&C_REC_SIZE=" + size +
                "&C_REC_WEIGHT=" + weight +
                "&C_REC_START=" + Time_start +
                "&C_REC_END=" + Time_done +
                "&C_REC_QUAN=" + total +
                "&LOT_NO=" + lot_no +
                "&username=" + user;

        Log.d(TAG, "onButtonClick: Final URL : "+Final_URL);
        RequestQueue requestQueue = Volley.newRequestQueue(Fragment3Validation.this);
        StringRequest request = new StringRequest(Request.Method.GET, Final_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject item = jsonArray.getJSONObject(0);
                    status  = item.getString("status");
                    message = item.getString("message");

                    Log.d(TAG, "onResponse: status  : "+status);
                    Log.d(TAG, "onResponse: message : "+message);

                    if(status.equals("success")){
                        Toast.makeText(Fragment3Validation.this,message,Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                Fragment3Validation.this.finish();
                                Intent intent = new Intent(Fragment3Validation.this,MainActivity.class);
                                intent.putExtra("from_fg","3");
                                intent.putExtra("username",user);
                                startActivity(intent);
                            }
                        }, 1500);
                    }else{
                        Toast.makeText(Fragment3Validation.this,message,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError",error.toString());
                Toast.makeText(Fragment3Validation.this,"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}
