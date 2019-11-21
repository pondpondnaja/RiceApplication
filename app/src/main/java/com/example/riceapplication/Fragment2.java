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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Fragment2 extends Fragment {
    private static final String TAG = "fg2";
    private static String URL = "http://10.0.2.2:8080/rice_app/read_lot_data.jsp?";
    public static final int DATE_REQUEST_CODE_2 = 12;
    public static final int TIME_REQUEST_CODE_START = 13;
    public static final int TIME_REQUEST_CODE_DONE = 14;
    private Button submit_button_2;

    private RadioGroup type_edit,thickness_edit;

    private  EditText editText_Time_start,editText_Time_done,round_edit,goods_name_edit,temperature_edit,
            rpm_edit,temperature_edit_2,rpm_2_edit,how_many_edit,remaining_flour_edit,supervisor_edit;

    private String ROUND,PRO_NAME,TEMP_STEAM,SPEED_STEAM,TEMP_DRY,SPEED_DRY,REC_START,REC_END,QUAN,PIECE,EMP_NAME,mo_type2,mo_thickness,Final_URL;

    String selectedTime_start;
    String selectedTime_done,user,lot_no;

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment2,container,false);
        final FragmentManager use_fm = getActivity().getSupportFragmentManager();

        //*** Session Login
        final LoginHelper usrHelper = new LoginHelper(getActivity().getApplicationContext());

        bundle = getArguments();
        if(bundle != null){
            lot_no = bundle.getString("LOT_NO");
            Log.d(TAG, "onCreateView: Username from Lot Selector : "+user+" LOT_NO : "+lot_no);
        }

        initData();


        //Edittext
        editText_Time_start = view.findViewById(R.id.time_start_edit);
        editText_Time_done = view.findViewById(R.id.time_done_edit);
        round_edit = view.findViewById(R.id.round_edit);
        goods_name_edit = view.findViewById(R.id.goods_name_edit);
        temperature_edit = view.findViewById(R.id.temperature_edit);
        rpm_edit = view.findViewById(R.id.rpm_edit);
        temperature_edit_2 = view.findViewById(R.id.temperature2_edit);
        rpm_2_edit = view.findViewById(R.id.rpm2_edit);
        how_many_edit = view.findViewById(R.id.how_many_edit);
        remaining_flour_edit = view.findViewById(R.id.remaining_flour_edit);
        supervisor_edit = view.findViewById(R.id.supervisor_edit);

        //Radio
        type_edit = view.findViewById(R.id.type_edit);
        thickness_edit = view.findViewById(R.id.thickness_edit);

        //buuton
        submit_button_2 = view.findViewById(R.id.submit_button_2);

        editText_Time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDialogFragment timefragment_2_start = new TimePickerFragment();
                timefragment_2_start.setTargetFragment(Fragment2.this,TIME_REQUEST_CODE_START);
                timefragment_2_start.show(use_fm,"timePicker_start");
            }
        });

        editText_Time_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDialogFragment timefragment_2_done = new TimePickerFragment();
                timefragment_2_done.setTargetFragment(Fragment2.this,TIME_REQUEST_CODE_DONE);
                timefragment_2_done.show(use_fm,"timePicker_done");
            }
        });

        submit_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view){
                if(type_edit.getCheckedRadioButtonId()!= -1){
                    int id= type_edit.getCheckedRadioButtonId();
                    View radioButton = type_edit.findViewById(id);
                    int radioId = type_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) type_edit.getChildAt(radioId);
                    mo_type2 = (String) btn.getText();
                }
                if(thickness_edit.getCheckedRadioButtonId()!= -1){
                    int id= thickness_edit.getCheckedRadioButtonId();
                    View radioButton = thickness_edit.findViewById(id);
                    int radioId = thickness_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) thickness_edit.getChildAt(radioId);
                    mo_thickness = (String) btn.getText();
                }

                Log.d(TAG, "onClick: "+mo_type2);
                Log.d(TAG, "onClick: "+mo_thickness);

                ROUND              = round_edit.getText().toString();
                PRO_NAME           = goods_name_edit.getText().toString();
                TEMP_STEAM         = temperature_edit.getText().toString();
                SPEED_STEAM        = rpm_edit.getText().toString();
                TEMP_DRY           = temperature_edit_2.getText().toString();
                SPEED_DRY          = rpm_2_edit.getText().toString();
                REC_START          = editText_Time_start.getText().toString();
                REC_END            = editText_Time_done.getText().toString();
                QUAN               = how_many_edit.getText().toString();
                PIECE              = remaining_flour_edit.getText().toString();
                EMP_NAME           = supervisor_edit.getText().toString();
                user               = usrHelper.getUserName();

                Intent intent = new Intent(getContext(),Fragment2Validation.class);
                intent.putExtra("B_ROUND", ROUND);
                intent.putExtra("B_PRO_NAME", PRO_NAME);
                intent.putExtra("B_TYPE",mo_type2);
                intent.putExtra("B_BOLD",mo_thickness);
                intent.putExtra("B_TEMP_STEAM", TEMP_STEAM);
                intent.putExtra("B_SPEED_STEAM", SPEED_STEAM);
                intent.putExtra("B_TEMP_DRY", TEMP_DRY);
                intent.putExtra("B_SPEED_DRY", SPEED_DRY);
                intent.putExtra("B_REC_START", REC_START);
                intent.putExtra("B_REC_END", REC_END);
                intent.putExtra("B_QUAN", QUAN);
                intent.putExtra("B_PIECE", PIECE);
                intent.putExtra("B_EMP_NAME", EMP_NAME);
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

                    ROUND        = item.getString("B_ROUND");
                    PRO_NAME     = item.getString("B_PRO_NAME");
                    mo_type2     = item.getString("B_TYPE");
                    mo_thickness = item.getString("B_BOLD");
                    TEMP_STEAM   = item.getString("B_TEMP_STEAM");
                    SPEED_STEAM  = item.getString("B_SPEED_STEAM");
                    TEMP_DRY     = item.getString("B_TEMP_DRY");
                    SPEED_DRY    = item.getString("B_SPEED_DRY");
                    REC_START    = item.getString("B_REC_START");
                    REC_END      = item.getString("B_REC_END");
                    QUAN         = item.getString("B_QUAN");
                    PIECE        = item.getString("B_PIECE");
                    EMP_NAME     = item.getString("B_EMP_NAME");

                    Log.d(TAG, "onResponse: ROUND    : "+ROUND);
                    Log.d(TAG, "onResponse: PRO_NAME : "+PRO_NAME);

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
        Log.d(TAG, "setData: Called");
        //EditText
        round_edit.setText(ROUND);
        goods_name_edit.setText(PRO_NAME);
        temperature_edit.setText(TEMP_STEAM);
        rpm_edit.setText(SPEED_STEAM);
        temperature_edit_2.setText(TEMP_DRY);
        rpm_2_edit.setText(SPEED_DRY);
        editText_Time_start.setText(REC_START);
        editText_Time_done.setText(REC_END);
        how_many_edit.setText(QUAN);
        remaining_flour_edit.setText(PIECE);
        supervisor_edit.setText(EMP_NAME);

        //RadioButton
        if(mo_type2.equals("ธรรมดา")){
            type_edit.check(R.id.radio_btn_nomal);
        }else{
            type_edit.check(R.id.radio_btn_abnomal);
        }

        if(mo_thickness.equals("บาง")){
            thickness_edit.check(R.id.thickness_edit_1);
        }else if(mo_thickness.equals("ปกติ")){
            thickness_edit.check(R.id.thickness_edit_2);
        }else{
            thickness_edit.check(R.id.thickness_edit_3);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIME_REQUEST_CODE_START && resultCode == Activity.RESULT_OK) {
            selectedTime_start = data.getStringExtra("selectedTime");
            editText_Time_start.setText(selectedTime_start);
        }
        if (requestCode == TIME_REQUEST_CODE_DONE && resultCode == Activity.RESULT_OK) {
            selectedTime_done = data.getStringExtra("selectedTime");
            editText_Time_done.setText(selectedTime_done);
        }
    }
}
