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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;

public class Fragment1Validation extends AppCompatActivity implements Serializable {
    private static final String TAG = "validateAc";

    private TextView date_va,time_va,textArea,type_va,
                     recipe_a_edit,recipe_b_edit,recipe_c_edit,recipe_d_edit,
                     grinding_edit,color_edit,smell_edit,radioButton_clean,
                     radioButton_engin,radioButton_issue,
                     radioButton_ta_krang,getRadioButton_ta_krang_broke,
                     radioButton_contamination_edit;

    public static final String URL = "http://10.0.2.2:8080/rice_app/insert.jsp?";
    private String insert_url;
    private long final_insert_url ;
    String date,time,type,a,b,c,d,rc,re,ri,tc,tb,con,ge,ce,se,note;

    Button va_btn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment1_validation);
        va_btn = findViewById(R.id.validate_btn);
        toolbar = findViewById(R.id.toolbar_va);
        date_va = findViewById(R.id.date_va);
        time_va = findViewById(R.id.time_va);
        type_va = findViewById(R.id.type_va);
        recipe_a_edit = findViewById(R.id.recipe_a_va);
        recipe_b_edit = findViewById(R.id.recipe_b_va);
        recipe_c_edit = findViewById(R.id.recipe_c_va);
        recipe_d_edit = findViewById(R.id.recipe_d_va);
        radioButton_clean = findViewById(R.id.clean_va);
        radioButton_engin = findViewById(R.id.engin_status_va);
        radioButton_issue = findViewById(R.id.notcorrect_va);
        radioButton_ta_krang = findViewById(R.id.ta_krang_clean_va);
        getRadioButton_ta_krang_broke = findViewById(R.id.broken_va);
        radioButton_contamination_edit = findViewById(R.id.contamination_va);
        grinding_edit = findViewById(R.id.grinding_va);
        color_edit = findViewById(R.id.color_va);
        smell_edit = findViewById(R.id.smell_va);
        textArea = findViewById(R.id.note_va);

        toolbar.setTitle("Validation");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                date = null;
                time = null;
                type = null;
                a = null;
                b = null;
                c = null;
                d = null;
                rc = null;
                re = null;
                ri = null;
                tc = null;
                tb = null;
                con = null;
                ge = null;
                ce = null;
                se = null;
                note = null;
            } else {
                date= extras.getString("วัน");
                time= extras.getString("เวลา");
                type= extras.getString("ประเภทสินค้า");
                a = extras.getString("A");
                b = extras.getString("B");
                c = extras.getString("C");
                d = extras.getString("D");
                rc = extras.getString("การโม่_ความสะอาด");
                re = extras.getString("การโม่_การเดินเครื่อง");
                ri = extras.getString("การโม่_สิ่งผิดปกติ");
                tc = extras.getString("ตะแกรง_ความสะอาด");
                tb = extras.getString("ตะแกรง_รอยชำรุด");
                con = extras.getString("น้ำแป้ง_สิ่งเจือปน");
                ge = extras.getString("ตะแกรง_ความละเอียด");
                ce = extras.getString("น้ำแป้ง_สี");
                se = extras.getString("น้ำแป้ง_กลิ่น");
                note = extras.getString("หมายเหตุ");
            }
        } else {
            date= (String) savedInstanceState.getSerializable("Date");
            time= (String) savedInstanceState.getSerializable("เวลา");
            type= (String) savedInstanceState.getSerializable("ประเภทสินค้า");
            a = (String) savedInstanceState.getSerializable("A");
            b = (String) savedInstanceState.getSerializable("B");
            c = (String) savedInstanceState.getSerializable("C");
            d = (String) savedInstanceState.getSerializable("D");
            rc = (String) savedInstanceState.getSerializable("การโม่_ความสะอาด");
            re = (String) savedInstanceState.getSerializable("การโม่_การเดินเครื่อง");
            ri = (String) savedInstanceState.getSerializable("การโม่_สิ่งผิดปกติ");
            tc = (String) savedInstanceState.getSerializable("ตะแกรง_ความสะอาด");
            tb = (String) savedInstanceState.getSerializable("ตะแกรง_รอยชำรุด");
            con = (String) savedInstanceState.getSerializable("ตะแกรง_ความละเอียด");
            ge = (String) savedInstanceState.getSerializable("น้ำแป้ง_สิ่งเจือปน");
            ce = (String) savedInstanceState.getSerializable("น้ำแป้ง_สี");
            se = (String) savedInstanceState.getSerializable("น้ำแป้ง_กลิ่น");
            note = (String) savedInstanceState.getSerializable("หมายเหตุ");
        }

        date_va.setText(date);
        time_va.setText(time);
        type_va.setText(type);
        recipe_a_edit.setText(a);
        recipe_b_edit.setText(b);
        recipe_c_edit.setText(c);
        recipe_d_edit.setText(d);
        radioButton_clean.setText(rc);
        radioButton_engin.setText(re);
        radioButton_issue.setText(ri);
        radioButton_ta_krang.setText(tc);
        getRadioButton_ta_krang_broke.setText(tb);
        radioButton_contamination_edit.setText(con);
        grinding_edit.setText(ge);
        color_edit.setText(ce);
        smell_edit.setText(se);
        textArea.setText(note);

        va_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick();
            }
        });
    }

    private void onButtonClick(){

        insert_url = URL + "data_va=" + date
                               + "&time_va=" + time
                               + "&type_va=" + type
                               + "&recipe_a=" + a
                               + "&recipe_b=" + b
                               + "&recipe_c=" + c
                               + "&recipe_d=" + d
                               + "&clean=" + rc
                               + "&engine=" + re
                               + "&issue=" + ri
                               + "&ta_krang=" + tc
                               + "&ta_krang_ba=" + tb
                               + "&contamination=" + con
                               + "&grinding=" + ge
                               + "&color=" + ce
                               + "&smell=" + se
                               + "&note=" + note;

        if(!date.isEmpty() && !time.isEmpty()){
            Log.d(TAG, "onButtonClick: Final URL : "+insert_url);
            RequestQueue requestQueue = Volley.newRequestQueue(Fragment1Validation.this);
            StringRequest request = new StringRequest(Request.Method.GET, insert_url, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse",response.toString());
                    Toast.makeText(Fragment1Validation.this,"เพิ่มข้อมูลเข้าสู่ระบบแล้ว",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Fragment1Validation.this,MainActivity.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onError",error.toString());
                    Toast.makeText(Fragment1Validation.this,"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request);
        }

    }
}
