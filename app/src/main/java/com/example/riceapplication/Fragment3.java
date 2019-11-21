package com.example.riceapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment3 extends Fragment {
    private static final String TAG = "fg3";
    private static String URL = "http://10.0.2.2:8080/rice_app/read_lot_data.jsp?";
    private static final int TIME_REQUEST_CODE_START_3 = 14;
    private static final int TIME_REQUEST_CODE_DONE_3 = 15;

    EditText editText_Time_start,editText_Time_done,
             repond_person_edit,pro_name_edit,size_edit,weight_edit,total_edit;

    String  selectedTime_start,selectedTime_done,
            Time_start,Time_done,repond_person,
            pro_name,size,weight,total,user,lot_no,Final_URL;

    Button form3_btn;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment3,container,false);
        final FragmentManager use_fm = getActivity().getSupportFragmentManager();

        //*** Session Login
        final LoginHelper usrHelper = new LoginHelper(getActivity().getApplicationContext());

        bundle              = getArguments();
        if(bundle != null){
            lot_no = bundle.getString("LOT_NO");
            Log.d(TAG, "onCreateView: Username from Lot Selector : "+user+" LOT_NO : "+lot_no);
        }

        initData();

        bundle              = getArguments();
        repond_person_edit  = view.findViewById(R.id.Responsible_person_edit);
        pro_name_edit       = view.findViewById(R.id.ProductName_edit);
        size_edit           = view.findViewById(R.id.Size_edit);
        weight_edit         = view.findViewById(R.id.Weight_edit);
        editText_Time_start = view.findViewById(R.id.time_start_edit_3);
        editText_Time_done  = view.findViewById(R.id.time_done_edit_3);
        total_edit          = view.findViewById(R.id.total_edit);
        form3_btn           = view.findViewById(R.id.form3_btn);

        editText_Time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDialogFragment timefragment_3_start = new TimePickerFragment();
                timefragment_3_start.setTargetFragment(Fragment3.this,TIME_REQUEST_CODE_START_3);
                timefragment_3_start.show(use_fm,"timePicker_start_3");
            }
        });

        editText_Time_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDialogFragment timefragment_3_done = new TimePickerFragment();
                timefragment_3_done.setTargetFragment(Fragment3.this,TIME_REQUEST_CODE_DONE_3);
                timefragment_3_done.show(use_fm,"timePicker_done_3");
            }
        });

        form3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repond_person = repond_person_edit.getText().toString();
                pro_name      = pro_name_edit.getText().toString();
                size          = size_edit.getText().toString();
                weight        = weight_edit.getText().toString();
                Time_start    = editText_Time_start.getText().toString();
                Time_done     = editText_Time_done.getText().toString();
                total         = total_edit.getText().toString();
                user          = usrHelper.getUserName();

                Intent intent = new Intent(getContext(),Fragment3Validation.class);
                intent.putExtra("C_EMP_NAME",repond_person);
                intent.putExtra("C_PRO_NAME",pro_name);
                intent.putExtra("C_REC_SIZE",size);
                intent.putExtra("C_REC_WEIGHT",weight);
                intent.putExtra("C_REC_START",Time_start);
                intent.putExtra("C_REC_END",Time_done);
                intent.putExtra("C_REC_QUAN",total);
                intent.putExtra("LOT_NO",lot_no);
                intent.putExtra("username",user);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initData() {
        Final_URL = URL + "lot_no="+lot_no;
        Log.d(TAG, "initData: Final url : "+Final_URL);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET,Final_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: Respond JSON : "+response.trim());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject item = jsonArray.getJSONObject(0);

                    repond_person = item.getString("C_EMP_NAME");
                    pro_name      = item.getString("C_PRO_NAME");
                    size          = item.getString("C_REC_SIZE");
                    weight        = item.getString("C_REC_WEIGHT");
                    Time_start    = item.getString("C_REC_START");
                    Time_done     = item.getString("C_REC_END");
                    total         = item.getString("C_REC_QUAN");

                    Log.d(TAG, "onResponse: Employee name    : "+repond_person);
                    Log.d(TAG, "onResponse: PRO_NAME         : "+pro_name);

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
        repond_person_edit.setText(repond_person);
        pro_name_edit.setText(pro_name);
        size_edit.setText(size);
        weight_edit.setText(weight);
        editText_Time_start.setText(Time_start);
        editText_Time_done.setText(Time_done);
        total_edit.setText(total);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIME_REQUEST_CODE_START_3 && resultCode == Activity.RESULT_OK) {
            selectedTime_start = data.getStringExtra("selectedTime");
            editText_Time_start.setText(selectedTime_start);
        }
        if (requestCode == TIME_REQUEST_CODE_DONE_3 && resultCode == Activity.RESULT_OK) {
            selectedTime_done = data.getStringExtra("selectedTime");
            editText_Time_done.setText(selectedTime_done);
        }
    }
}
