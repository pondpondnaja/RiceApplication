package com.example.riceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Fragment2Validation extends AppCompatActivity {
    private TextView date_2_va,round_va,goods_name_va,time_start_va,time_done_va,
            textArea2,temperature_edit,rpm_edit,getTemperature_edit,rpm_2_edit,
            how_many_edit,remaining_flour_edit,supervisor_edit,
            radioButton_type_2,radioButton_thickness;

    public static final String URL = "http://172.22.0.203/insertData2.php";
    String date2,round2,good2,time_s,time_do,
            note2,temp_e,rpm_e,temp2_e,rpm2_e,how,rema,superv,
            r_type2,thick2;

    Button va2_btn;
    Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2_validation);
        va2_btn = findViewById(R.id.validate_2_btn);
        toolbar2 = findViewById(R.id.toolbar_2_va);
        date_2_va = findViewById(R.id.date_2_va);
        round_va = findViewById(R.id.round_va);
        goods_name_va = findViewById(R.id.goods_name_va);
        time_start_va = findViewById(R.id.time_start_va);
        time_done_va = findViewById(R.id.time_done_va);
        temperature_edit = findViewById(R.id.temperature_va);
        rpm_edit = findViewById(R.id.rpm_va);
        getTemperature_edit = findViewById(R.id.temperature_2_va);
        rpm_2_edit = findViewById(R.id.rpm_2_va);
        how_many_edit = findViewById(R.id.how_many_va);
        remaining_flour_edit = findViewById(R.id.remaining_flour_va);
        supervisor_edit = findViewById(R.id.supervisor_va);
        radioButton_type_2 = findViewById(R.id.type_2_va);
        radioButton_thickness = findViewById(R.id.thickness_va);
        textArea2 = findViewById(R.id.note2_va);

        toolbar2.setTitle("Validation");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                date2 = null;
                round2 = null;
                good2 = null;
                time_s = null;
                time_do = null;
                note2 = null;
                temp_e = null;
                rpm_e = null;
                temp2_e = null;
                rpm2_e = null;
                how = null;
                rema = null;
                superv= null;
                r_type2 = null;
                thick2 = null;
            } else {
                date2= extras.getString("DMY");
                round2= extras.getString("ROUND");
                good2= extras.getString("PRO_NAME");
                r_type2 = extras.getString("TYPE");
                thick2 = extras.getString("BOLD");
                temp_e = extras.getString("TEMP_STEAM");
                rpm_e = extras.getString("SPEED_STEAM");
                temp2_e = extras.getString("TEMP_DRY");
                rpm2_e = extras.getString("SREED_DRY");
                time_s = extras.getString("REC_START");
                time_do = extras.getString("REC_END");
                how = extras.getString("QUAN");
                rema = extras.getString("PIECE");
                superv = extras.getString("EMP_NAME");
                note2 = extras.getString("REC_PS");
            }
        } else {
            date2= (String) savedInstanceState.getSerializable("DMY");
            round2= (String) savedInstanceState.getSerializable("ROUND");
            good2= (String) savedInstanceState.getSerializable("PRO_NAME");
            r_type2 = (String) savedInstanceState.getSerializable("TYPE");
            thick2 = (String) savedInstanceState.getSerializable("BOLD");
            temp_e = (String) savedInstanceState.getSerializable("TEMP_STEAM");
            rpm_e = (String) savedInstanceState.getSerializable("SPEED_STEAM");
            temp2_e = (String) savedInstanceState.getSerializable("TEMP_DRY");
            rpm2_e = (String) savedInstanceState.getSerializable("SREED_DRY");
            time_s = (String) savedInstanceState.getSerializable("REC_START");
            time_do = (String) savedInstanceState.getSerializable("REC_END");
            how = (String) savedInstanceState.getSerializable("QUAN");
            rema = (String) savedInstanceState.getSerializable("PIECE");
            superv= (String) savedInstanceState.getSerializable("EMP_NAME");
            note2 = (String) savedInstanceState.getSerializable("REC_PS");
        }

        date_2_va.setText(date2);
        round_va.setText(round2);
        goods_name_va.setText(good2);
        radioButton_type_2.setText(r_type2);
        radioButton_thickness.setText(thick2);
        temperature_edit.setText(temp_e);
        rpm_edit.setText(rpm_e);
        getTemperature_edit.setText(temp2_e);
        rpm_2_edit.setText(rpm2_e);
        time_start_va.setText(time_s);
        time_done_va.setText(time_do);
        how_many_edit.setText(how);
        remaining_flour_edit.setText(rema);
        supervisor_edit.setText(superv);
        textArea2.setText(note2);

        va2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick();
            }
        });
    }

    private void onButtonClick(){
        if(!date2.isEmpty() && !time_s.isEmpty()){
            RequestQueue requestQueue = Volley.newRequestQueue(Fragment2Validation.this);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse",response.toString());
                    Toast.makeText(Fragment2Validation.this,"เพิ่มข้อมูลเข้าสู่ระบบแล้ว",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Fragment2Validation.this,MainActivity.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onError",error.toString());
                    Toast.makeText(Fragment2Validation.this,"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    String t = time_s.trim().toString();
                    params.put("DMY", date2);
                    params.put("ROUND", round2);
                    params.put("PRO_NAME", good2);
                    params.put("TYPE", r_type2);
                    params.put("BOLD", thick2);
                    params.put("TEMP_STEAM", temp_e);
                    params.put("SPEED_STEAM", rpm_e);
                    params.put("TEMP_DRY", temp_e);
                    params.put("SPEED_DRY", rpm2_e);
                    params.put("REC_START", t);
                    params.put("REC_END", time_do);
                    params.put("QUAN", how);
                    params.put("PIECE", rema);
                    params.put("EMP_NAME", superv);
                    params.put("REC_PS", note2);

                    return params;
                }
            };
            requestQueue.add(request);
        }

    }
}
