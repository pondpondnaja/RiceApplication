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

public class Fragment1Validation extends AppCompatActivity implements Serializable {

    private static final String TAG = "validateAc";
    private static final String URL = "http://10.0.2.2:8080/rice_app/insert_new.jsp?";

    TextView lotno_va,date_va,time_va,type_va,
            recipe_a_edit,recipe_b_edit,recipe_c_edit,recipe_d_edit,
            grinding_edit,color_edit,smell_edit,radioButton_clean,
            radioButton_engin,radioButton_issue,
            radioButton_ta_krang,getRadioButton_ta_krang_broke,
            radioButton_contamination_edit;

    String lo,date,time,type,a,b,c,d,rc,re,ri,tc,tb,con,ge,ce,se,insert_url,username , status , message;

    Button va_btn;
    Toolbar toolbar;

    boolean new_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment1_validation);
        va_btn   = findViewById(R.id.validate_btn);
        toolbar  = findViewById(R.id.toolbar_va);
        lotno_va = findViewById(R.id.lotno_va);
        date_va  = findViewById(R.id.date_va);
        time_va  = findViewById(R.id.time_va);
        type_va  = findViewById(R.id.type_va);
        recipe_a_edit = findViewById(R.id.recipe_a_va);
        recipe_b_edit = findViewById(R.id.recipe_b_va);
        recipe_c_edit = findViewById(R.id.recipe_c_va);
        recipe_d_edit = findViewById(R.id.recipe_d_va);
        radioButton_clean = findViewById(R.id.clean_va);
        radioButton_engin = findViewById(R.id.engin_status_va);
        radioButton_issue = findViewById(R.id.notcorrect_va);
        radioButton_ta_krang = findViewById(R.id.ta_krang_clean_va);
        getRadioButton_ta_krang_broke  = findViewById(R.id.broken_va);
        radioButton_contamination_edit = findViewById(R.id.contamination_va);
        grinding_edit = findViewById(R.id.grinding_va);
        color_edit    = findViewById(R.id.color_va);
        smell_edit    = findViewById(R.id.smell_va);

        toolbar.setTitle("Validation");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                lo       = null;
                date     = null;
                time     = null;
                type     = null;
                a        = null;
                b        = null;
                c        = null;
                d        = null;
                rc       = null;
                re       = null;
                ri       = null;
                tc       = null;
                tb       = null;
                con      = null;
                ge       = null;
                ce       = null;
                se       = null;
                username = null;
                new_form = false;
            } else {
                lo       = extras.getString("LOT_NO");
                date     = extras.getString("DMY");
                time     = extras.getString("A_REC_TIME");
                type     = extras.getString("A_PRO_TYPE");
                a        = extras.getString("A_RICE_A");
                b        = extras.getString("A_RICE_B");
                c        = extras.getString("A_RICE_C");
                d        = extras.getString("A_RICE_D");
                rc       = extras.getString("A_MO_CLEAN");
                re       = extras.getString("A_MO_OPER");
                ri       = extras.getString("A_MO_AB");
                tc       = extras.getString("A_GRILL_CLEAN");
                tb       = extras.getString("A_GRILL_LOSS");
                con      = extras.getString("A_GRILL_RES");
                ge       = extras.getString("A_FLOUR_CONT");
                ce       = extras.getString("A_FLOUR_COL");
                se       = extras.getString("A_FLOUR_SMELL");
                username = extras.getString("username");
                new_form = extras.getBoolean("new_form");
            }
        } else {
            lo       = (String)  savedInstanceState.getSerializable("LOT_NO");
            date     = (String)  savedInstanceState.getSerializable("DMY");
            time     = (String)  savedInstanceState.getSerializable("A_REC_TIME");
            type     = (String)  savedInstanceState.getSerializable("A_PRO_TYPE");
            a        = (String)  savedInstanceState.getSerializable("A_RICE_A");
            b        = (String)  savedInstanceState.getSerializable("A_RICE_B");
            c        = (String)  savedInstanceState.getSerializable("A_RICE_C");
            d        = (String)  savedInstanceState.getSerializable("A_RICE_D");
            rc       = (String)  savedInstanceState.getSerializable("A_MO_CLEAN");
            re       = (String)  savedInstanceState.getSerializable("A_MO_OPER");
            ri       = (String)  savedInstanceState.getSerializable("A_MO_AB");
            tc       = (String)  savedInstanceState.getSerializable("A_GRILL_CLEAN");
            tb       = (String)  savedInstanceState.getSerializable("A_GRILL_LOSS");
            con      = (String)  savedInstanceState.getSerializable("A_GRILL_RES");
            ge       = (String)  savedInstanceState.getSerializable("A_FLOUR_CONT");
            ce       = (String)  savedInstanceState.getSerializable("A_FLOUR_COL");
            se       = (String)  savedInstanceState.getSerializable("A_FLOUR_SMELL");
            username = (String)  savedInstanceState.getSerializable("username");
            new_form = (boolean) savedInstanceState.getSerializable("new_form");
        }
        lotno_va.setText(lo);
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
        Log.d(TAG, "onCreate: Username : "+username);

        va_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick();
            }
        });
    }

    private void onButtonClick(){

        insert_url = URL
                + "LOT_NO=" + lo
                + "&DMY=" + date
                + "&A_REC_TIME=" + time
                + "&A_PRO_TYPE=" + type
                + "&A_RICE_A=" + a
                + "&A_RICE_B=" + b
                + "&A_RICE_C=" + c
                + "&A_RICE_D=" + d
                + "&A_MO_CLEAN=" + rc
                + "&A_MO_OPER=" + re
                + "&A_MO_AB=" + ri
                + "&A_GRILL_CLEAN=" + tc
                + "&A_GRILL_LOSS=" + tb
                + "&A_GRILL_RES=" + con
                + "&A_FLOUR_CONT=" + ge
                + "&A_FLOUR_COL=" + ce
                + "&A_FLOUR_SMELL=" + se
                + "&username="+ username
                + "&new_form="+ new_form;

        if(!date.isEmpty() && !time.isEmpty()){
            Log.d(TAG, "onButtonClick: Final URL : "+insert_url);
            RequestQueue requestQueue = Volley.newRequestQueue(Fragment1Validation.this);
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
                            Toast.makeText(Fragment1Validation.this,message,Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run(){
                                    Intent intent = new Intent(Fragment1Validation.this,MainActivity.class);
                                    intent.putExtra("from_fg","1");
                                    intent.putExtra("username",username);
                                    startActivity(intent);
                                }
                            }, 1500);
                        }else{
                            Toast.makeText(Fragment1Validation.this,message,Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
