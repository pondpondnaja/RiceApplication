package com.example.riceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment4 extends Fragment{
    private static final String TAG = "fg4";
    private static String URL = "http://10.0.2.2:8080/rice_app/read_lot_data.jsp?";

    EditText rice_edit,productName_edit_4,manybox_edit,manysong_edit,
             manybox_edit_waiting,manysong_edit_waiting,pang_edit,
             pang_edit_copy,head_edit_too,room_edit_pack;

    String  rice,productName_4,manybox,manysong,
            manybox_waiting,manysong_waiting,pang,
            pang_copy,head_too,room_pack,lot_no,user,Final_URL;

    Button form4_btn;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment4,container,false);

        //*** Session Login
        final LoginHelper usrHelper = new LoginHelper(getActivity().getApplicationContext());

        bundle = getArguments();
        if(bundle != null){
            lot_no = bundle.getString("LOT_NO");
            Log.d(TAG, "onCreateView: Username from Lot Selector : "+usrHelper.getUserName()+" LOT_NO : "+lot_no);
        }

        inintData();

        form4_btn             = view.findViewById(R.id.form4_btn);
        rice_edit             = view.findViewById(R.id.rice_edit);
        productName_edit_4    = view.findViewById(R.id.ProductName_edit_4);
        manybox_edit          = view.findViewById(R.id.manybox_edit);
        manysong_edit         = view.findViewById(R.id.manysong_edit);
        manybox_edit_waiting  = view.findViewById(R.id.manybox_edit_waiting);
        manysong_edit_waiting = view.findViewById(R.id.manysong_edit_waiting);
        pang_edit             = view.findViewById(R.id.pang_edit);
        pang_edit_copy        = view.findViewById(R.id.pang_edit_copy);
        head_edit_too         = view.findViewById(R.id.head_edit_too);
        room_edit_pack        = view.findViewById(R.id.room_edit_pack);

        form4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Fragment4Validation.class);
                intent.putExtra("D_QUAN_RICE",rice_edit.getText().toString());
                intent.putExtra("D_PRO_NAME",productName_edit_4.getText().toString());
                intent.putExtra("D_DONE_QUANBOX",manybox_edit.getText().toString());
                intent.putExtra("D_DONE_QUANPACK",manysong_edit.getText().toString());
                intent.putExtra("D_WAIT_QUANBOX",manybox_edit_waiting.getText().toString());
                intent.putExtra("D_WAIT_QUANPACK",manysong_edit_waiting.getText().toString());
                intent.putExtra("D_STARCH",pang_edit.getText().toString());
                intent.putExtra("D_PEELING",pang_edit_copy.getText().toString());
                intent.putExtra("D_CAR_HEAD",head_edit_too.getText().toString());
                intent.putExtra("D_PACK_ROOM",room_edit_pack.getText().toString());
                intent.putExtra("LOT_NO",lot_no);
                intent.putExtra("username",usrHelper.getUserName());
                startActivity(intent);
            }
        });

        return view;
    }

    private void inintData() {
        Final_URL = URL + "lot_no="+lot_no;
        Log.d(TAG, "initData: Final url : "+Final_URL);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST,Final_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: Respond JSON : "+response.trim());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject item = jsonArray.getJSONObject(0);

                    rice             = item.getString("D_QUAN_RICE");
                    productName_4    = item.getString("D_PRO_NAME");
                    manybox          = item.getString("D_DONE_QUANBOX");
                    manysong         = item.getString("D_DONE_QUANPACK");
                    manybox_waiting  = item.getString("D_WAIT_QUANBOX");
                    manysong_waiting = item.getString("D_WAIT_QUANPACK");
                    pang             = item.getString("D_STARCH");
                    pang_copy        = item.getString("D_PEELING");
                    head_too         = item.getString("D_CAR_HEAD");
                    room_pack        = item.getString("D_PACK_ROOM");

                    Log.d(TAG, "onResponse: QUAN_RICE    : "+rice);
                    Log.d(TAG, "onResponse: PRO_NAME     : "+productName_4);

                    setData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
                Toast.makeText(getActivity(),"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void setData() {
        Log.d(TAG, "setData: SetData : true");
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
    }
}
