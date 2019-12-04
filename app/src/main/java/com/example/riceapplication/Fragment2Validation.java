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

import java.io.Serializable;

public class Fragment2Validation extends AppCompatActivity implements Serializable{

    private static final String TAG = "validateAc2";
    private static final String URL = "http://10.0.2.2:8084/kak/insert2.jsp?";

    TextView round_va,goods_name_va,type_va,thickness_va,
             temperature_va,rpm_va,temperature2_va,rpm2_va,
             time_start_va,time_done_va,how_many_va,remaining_flour_va,
             supervisor_va;

    String  insert_url,round2,good2,r_type2,thick2,temp_e,rpm_e,
            temp2_e,rpm2_e,time_s,time_do,how,rema,superv,lot_no,status,message,user;

    Button va2_btn;
    Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2_validation);

        va2_btn            = findViewById(R.id.validate2_btn);
        toolbar2           = findViewById(R.id.toolbar2_va);
        round_va           = findViewById(R.id.round_va);
        goods_name_va      = findViewById(R.id.goods_name_va);
        type_va            = findViewById(R.id.type_va);
        thickness_va       = findViewById(R.id.thickness_va);
        temperature_va     = findViewById(R.id.temperature_va);
        rpm_va             = findViewById(R.id.rpm_va);
        temperature2_va    = findViewById(R.id.temperature2_va);
        rpm2_va            = findViewById(R.id.rpm2_va);
        time_start_va      = findViewById(R.id.time_start_va);
        time_done_va       = findViewById(R.id.time_done_va);
        how_many_va        = findViewById(R.id.how_many_va);
        remaining_flour_va = findViewById(R.id.remaining_flour_va);
        supervisor_va      = findViewById(R.id.supervisor_va);

        toolbar2.setTitle("Validation");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                round2  = null;
                good2   = null;
                r_type2 = null;
                thick2  = null;
                temp_e  = null;
                rpm_e   = null;
                temp2_e = null;
                rpm2_e  = null;
                time_s  = null;
                time_do = null;
                how     = null;
                rema    = null;
                superv  = null;
                lot_no  = null;
                user    = null;

            } else {
                round2  = extras.getString("B_ROUND");
                good2   = extras.getString("B_PRO_NAME");
                r_type2 = extras.getString("B_TYPE");
                thick2  = extras.getString("B_BOLD");
                temp_e  = extras.getString("B_TEMP_STEAM");
                rpm_e   = extras.getString("B_SPEED_STEAM");
                temp2_e = extras.getString("B_TEMP_DRY");
                rpm2_e  = extras.getString("B_SPEED_DRY");
                time_s  = extras.getString("B_REC_START");
                time_do = extras.getString("B_REC_END");
                how     = extras.getString("B_QUAN");
                rema    = extras.getString("B_PIECE");
                superv  = extras.getString("B_EMP_NAME");
                lot_no  = extras.getString("LOT_NO");
                user    = extras.getString("username");
            }
        } else {
            round2  = (String) savedInstanceState.getSerializable("B_ROUND");
            good2   = (String) savedInstanceState.getSerializable("B_PRO_NAME");
            r_type2 = (String) savedInstanceState.getSerializable("B_TYPE");
            thick2  = (String) savedInstanceState.getSerializable("B_BOLD");
            temp_e  = (String) savedInstanceState.getSerializable("B_TEMP_STEAM");
            rpm_e   = (String) savedInstanceState.getSerializable("B_SPEED_STEAM");
            temp2_e = (String) savedInstanceState.getSerializable("B_TEMP_DRY");
            rpm2_e  = (String) savedInstanceState.getSerializable("B_SPEED_DRY");
            time_s  = (String) savedInstanceState.getSerializable("B_REC_START");
            time_do = (String) savedInstanceState.getSerializable("B_REC_END");
            how     = (String) savedInstanceState.getSerializable("B_QUAN");
            rema    = (String) savedInstanceState.getSerializable("B_PIECE");
            superv  = (String) savedInstanceState.getSerializable("B_EMP_NAME");
            lot_no  = (String) savedInstanceState.getSerializable("LOT_NO");
            user    = (String) savedInstanceState.getSerializable("username");
        }

        round_va.setText(round2);
        goods_name_va.setText(good2);
        type_va.setText(r_type2);
        thickness_va.setText(thick2);
        temperature_va.setText(temp_e);
        rpm_va.setText(rpm_e);
        temperature2_va.setText(temp2_e);
        rpm2_va.setText(rpm2_e);
        time_start_va.setText(time_s);
        time_done_va.setText(time_do);
        how_many_va.setText(how);
        remaining_flour_va.setText(rema);
        supervisor_va.setText(superv);

        va2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick();
            }
        });
    }

    private void onButtonClick(){

        insert_url = URL
                + "B_ROUND=" + round2
                + "&B_PRO_NAME=" + good2
                + "&B_TYPE=" + r_type2
                + "&B_BOLD=" + thick2
                + "&B_TEMP_STEAM=" + temp_e
                + "&B_SPEED_STEAM=" + rpm_e
                + "&B_TEMP_DRY=" + temp2_e
                + "&B_SPEED_DRY=" + rpm2_e
                + "&B_REC_START=" + time_s
                + "&B_REC_END=" + time_do
                + "&B_QUAN=" + how
                + "&B_PIECE=" + rema
                + "&B_EMP_NAME=" + superv
                + "&LOT_NO=" + lot_no
                + "&username=" + user;

        Log.d(TAG, "onButtonClick: Final URL : "+insert_url);
        RequestQueue requestQueue = Volley.newRequestQueue(Fragment2Validation.this);
        StringRequest request = new StringRequest(Request.Method.GET, insert_url, new Response.Listener<String>(){
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
                        Toast.makeText(Fragment2Validation.this,message,Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                Fragment2Validation.this.finish();
                                Intent intent = new Intent(Fragment2Validation.this,MainActivity.class);
                                intent.putExtra("from_fg","2");
                                intent.putExtra("username",user);
                                startActivity(intent);
                            }
                        }, 1500);
                    }else{
                        Toast.makeText(Fragment2Validation.this,message,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError",error.toString());
                Toast.makeText(Fragment2Validation.this,"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }
}
