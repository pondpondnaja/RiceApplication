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

public class Fragment4Validation extends AppCompatActivity {

    private static final String TAG = "validateAc3";
    private static final String URL = "http://10.0.2.2:8084/kak/insert4.jsp?";

    TextView rice_edit,productName_edit_4,manybox_edit,manysong_edit,
             manybox_edit_waiting,manysong_edit_waiting,pang_edit,
             pang_edit_copy,head_edit_too,room_edit_pack;

    String  rice,productName_4,manybox,manysong,
            manybox_waiting,manysong_waiting,pang,
            pang_copy,head_too,room_pack,lot_no,user,Final_URL,
            status,message;

    Button va4_btn;
    Toolbar toolbar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment4_validation);

        toolbar4              = findViewById(R.id.toolbar4_va);
        va4_btn               = findViewById(R.id.form4_btn_va);
        rice_edit             = findViewById(R.id.rice_edit_va);
        productName_edit_4    = findViewById(R.id.ProductName_edit_4_va);
        manybox_edit          = findViewById(R.id.manybox_edit_va);
        manysong_edit         = findViewById(R.id.manysong_edit_va);
        manybox_edit_waiting  = findViewById(R.id.manybox_edit_waiting_va);
        manysong_edit_waiting = findViewById(R.id.manysong_edit_waiting_va);
        pang_edit             = findViewById(R.id.pang_edit_va);
        pang_edit_copy        = findViewById(R.id.pang_edit_copy_va);
        head_edit_too         = findViewById(R.id.head_edit_too_va);
        room_edit_pack        = findViewById(R.id.room_edit_pack_va);

        toolbar4.setTitle("Validation");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                rice             = null;
                productName_4    = null;
                manybox          = null;
                manysong         = null;
                manybox_waiting  = null;
                manysong_waiting = null;
                pang             = null;
                pang_copy        = null;
                head_too         = null;
                room_pack        = null;
                lot_no           = null;
                user             = null;

            } else {
                rice             = extras.getString("D_QUAN_RICE");
                productName_4    = extras.getString("D_PRO_NAME");
                manybox          = extras.getString("D_DONE_QUANBOX");
                manysong         = extras.getString("D_DONE_QUANPACK");
                manybox_waiting  = extras.getString("D_WAIT_QUANBOX");
                manysong_waiting = extras.getString("D_WAIT_QUANPACK");
                pang             = extras.getString("D_STARCH");
                pang_copy        = extras.getString("D_PEELING");
                head_too         = extras.getString("D_CAR_HEAD");
                room_pack        = extras.getString("D_PACK_ROOM");
                lot_no           = extras.getString("LOT_NO");
                user             = extras.getString("username");
            }
        }else{
            rice             = (String) savedInstanceState.getSerializable("D_QUAN_RICE");
            productName_4    = (String) savedInstanceState.getSerializable("D_PRO_NAME");
            manybox          = (String) savedInstanceState.getSerializable("D_DONE_QUANBOX");
            manysong         = (String) savedInstanceState.getSerializable("D_DONE_QUANPACK");
            manybox_waiting  = (String) savedInstanceState.getSerializable("D_WAIT_QUANBOX");
            manysong_waiting = (String) savedInstanceState.getSerializable("D_WAIT_QUANPACK");
            pang             = (String) savedInstanceState.getSerializable("D_STARCH");
            pang_copy        = (String) savedInstanceState.getSerializable("D_PEELING");
            head_too         = (String) savedInstanceState.getSerializable("D_CAR_HEAD");
            room_pack        = (String) savedInstanceState.getSerializable("D_PACK_ROOM");
            lot_no           = (String) savedInstanceState.getSerializable("LOT_NO");
            user             = (String) savedInstanceState.getSerializable("username");
        }

        Log.d(TAG, "onCreate: Rice : "+rice);

        rice_edit.setText(rice);
        productName_edit_4.setText(productName_4);
        manybox_edit.setText(manybox);
        manysong_edit.setText(manysong);
        manybox_edit_waiting.setText(manybox_waiting);
        manysong_edit_waiting.setText(manysong_waiting);
        pang_edit.setText(pang);
        pang_edit_copy.setText(pang_copy);
        head_edit_too.setText(head_too);
        room_edit_pack.setText(room_pack);

        va4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick();
            }
        });
    }

    private void onButtonClick() {
        Final_URL = URL +
                "D_QUAN_RICE=" + rice +
                "&D_PRO_NAME=" + productName_4 +
                "&D_DONE_QUANBOX=" + manybox +
                "&D_DONE_QUANPACK=" + manysong +
                "&D_WAIT_QUANBOX=" + manybox_waiting +
                "&D_WAIT_QUANPACK=" + manysong_waiting +
                "&D_STARCH=" + pang +
                "&D_PEELING=" + pang_copy +
                "&D_CAR_HEAD=" + head_too +
                "&D_PACK_ROOM=" + room_pack +
                "&LOT_NO=" + lot_no +
                "&username=" + user;

        Log.d(TAG, "onButtonClick: Final URL : "+Final_URL);
        RequestQueue requestQueue = Volley.newRequestQueue(Fragment4Validation.this);
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
                        Toast.makeText(Fragment4Validation.this,message,Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                Fragment4Validation.this.finish();
                                Intent intent = new Intent(Fragment4Validation.this,MainActivity.class);
                                intent.putExtra("from_fg","4");
                                intent.putExtra("username",user);
                                startActivity(intent);
                            }
                        }, 1500);
                    }else{
                        Toast.makeText(Fragment4Validation.this,message,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError",error.toString());
                Toast.makeText(Fragment4Validation.this,"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}
