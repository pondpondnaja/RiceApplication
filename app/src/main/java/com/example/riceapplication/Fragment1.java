package com.example.riceapplication;

import android.app.Activity;
import android.content.Context;
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

import java.io.Serializable;

public class Fragment1 extends Fragment implements Serializable {

    private static final String TAG = "fg1";
    private static String URL = "http://10.0.2.2:8084/kak/read_lot_data.jsp?";
    private static final int DATE_REQUEST_CODE = 11;
    private static final int TIME_REQUEST_CODE = 12;

    private Button submit_button_1;
    private RadioGroup clean_edit, engin_status_edit, notcorrect_edit,
            ta_krang_clean_edit, broken_edit, contamination_edit, grinding_edit;

    private EditText lotno_edit, deditText, teditText, typeEdit,
            recipe_a_edit, recipe_b_edit, recipe_c_edit, recipe_d_edit,
            color_edit, smell_edit, note_edit;

    private String LOT_NO, DMY, A_REC_TIME, A_PRO_TYPE, A_RICE_A,
            A_RICE_B, A_RICE_C, A_RICE_D, A_FLOUR_COL,
            A_FLOUR_SMELL, user, selectedDate, selectedTime, Final_URL, lot_no,
            A_MO_CLEAN, A_MO_OPER, A_MO_AB, A_GRILL_CLEAN, A_GRILL_LOSS, A_GRILL_RES, A_FLOUR_CONT, A_REC_PS;

    private boolean new_form;
    private Context context;

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_fragment1, container, false);
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        //*** Session Login
        context = view.getContext();
        final LoginHelper usrHelper = new LoginHelper(getActivity().getApplicationContext());

        bundle = getArguments();
        if (bundle != null) {
            new_form = bundle.getBoolean("new_lot");
            lot_no = bundle.getString("LOT_NO");
            Log.d(TAG, "onCreateView: Username in fg1 : " + user + " New form : " + new_form);
        }

        if (!new_form) {
            initData();
        }

        //EditText
        lotno_edit = view.findViewById(R.id.lotno_edit);
        deditText = view.findViewById(R.id.date_edit);
        teditText = view.findViewById(R.id.time_edit);
        typeEdit = view.findViewById(R.id.type_edit);
        color_edit = view.findViewById(R.id.color_edit);
        smell_edit = view.findViewById(R.id.smell_edit);
        recipe_a_edit = view.findViewById(R.id.recipe_a_edit);
        recipe_b_edit = view.findViewById(R.id.recipe_b_edit);
        recipe_c_edit = view.findViewById(R.id.recipe_c_edit);
        recipe_d_edit = view.findViewById(R.id.recipe_d_edit);
        note_edit = view.findViewById(R.id.note_edit);

        //Radio Group.
        clean_edit = view.findViewById(R.id.clean_edit);
        engin_status_edit = view.findViewById(R.id.engin_status_edit);
        notcorrect_edit = view.findViewById(R.id.notcorrect_edit);
        ta_krang_clean_edit = view.findViewById(R.id.ta_krang_clean_edit);
        broken_edit = view.findViewById(R.id.broken_edit);
        contamination_edit = view.findViewById(R.id.contamination_edit);
        grinding_edit = view.findViewById(R.id.grinding_edit);

        //Button
        submit_button_1 = view.findViewById(R.id.submit_button_1);

        deditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deditText.setHint("");
                AppCompatDialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(Fragment1.this, DATE_REQUEST_CODE);
                newFragment.show(fm, "datePicker");
            }
        });

        teditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teditText.setHint("");
                AppCompatDialogFragment timeFragment = new TimePickerFragment();
                timeFragment.setTargetFragment(Fragment1.this, TIME_REQUEST_CODE);
                timeFragment.show(fm, "timePicker");
            }
        });

        submit_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (clean_edit.getCheckedRadioButtonId() != -1) {
                    int id = clean_edit.getCheckedRadioButtonId();
                    View radioButton = clean_edit.findViewById(id);
                    int radioId = clean_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) clean_edit.getChildAt(radioId);
                    A_MO_CLEAN = (String) btn.getText();
                }
                if (engin_status_edit.getCheckedRadioButtonId() != -1) {
                    int id = engin_status_edit.getCheckedRadioButtonId();
                    View radioButton = engin_status_edit.findViewById(id);
                    int radioId = engin_status_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) engin_status_edit.getChildAt(radioId);
                    A_MO_OPER = (String) btn.getText();
                }
                if (notcorrect_edit.getCheckedRadioButtonId() != -1) {
                    int id = notcorrect_edit.getCheckedRadioButtonId();
                    View radioButton = notcorrect_edit.findViewById(id);
                    int radioId = notcorrect_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) notcorrect_edit.getChildAt(radioId);
                    A_MO_AB = (String) btn.getText();
                }
                if (ta_krang_clean_edit.getCheckedRadioButtonId() != -1) {
                    int id = ta_krang_clean_edit.getCheckedRadioButtonId();
                    View radioButton = ta_krang_clean_edit.findViewById(id);
                    int radioId = ta_krang_clean_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) ta_krang_clean_edit.getChildAt(radioId);
                    A_GRILL_CLEAN = (String) btn.getText();
                }
                if (broken_edit.getCheckedRadioButtonId() != -1) {
                    int id = broken_edit.getCheckedRadioButtonId();
                    View radioButton = broken_edit.findViewById(id);
                    int radioId = broken_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) broken_edit.getChildAt(radioId);
                    A_GRILL_LOSS = (String) btn.getText();
                }
                if (grinding_edit.getCheckedRadioButtonId() != -1) {
                    int id = grinding_edit.getCheckedRadioButtonId();
                    View radioButton = grinding_edit.findViewById(id);
                    int radioId = grinding_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) grinding_edit.getChildAt(radioId);
                    A_GRILL_RES = (String) btn.getText();
                }
                if (contamination_edit.getCheckedRadioButtonId() != -1) {
                    int id = contamination_edit.getCheckedRadioButtonId();
                    View radioButton = contamination_edit.findViewById(id);
                    int radioId = contamination_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) contamination_edit.getChildAt(radioId);
                    A_FLOUR_CONT = (String) btn.getText();
                }

                Log.d(TAG, "onClick: " + A_MO_CLEAN);
                Log.d(TAG, "onClick: " + A_MO_OPER);
                Log.d(TAG, "onClick: " + A_MO_AB);
                Log.d(TAG, "onClick: " + A_GRILL_CLEAN);
                Log.d(TAG, "onClick: " + A_GRILL_LOSS);
                Log.d(TAG, "onClick: " + A_GRILL_RES);
                Log.d(TAG, "onClick: " + A_FLOUR_CONT);

                LOT_NO = lotno_edit.getText().toString();
                DMY = deditText.getText().toString();
                A_REC_TIME = teditText.getText().toString();
                A_PRO_TYPE = typeEdit.getText().toString();
                A_RICE_A = recipe_a_edit.getText().toString();
                A_RICE_B = recipe_b_edit.getText().toString();
                A_RICE_C = recipe_c_edit.getText().toString();
                A_RICE_D = recipe_d_edit.getText().toString();
                A_FLOUR_COL = color_edit.getText().toString();
                A_FLOUR_SMELL = smell_edit.getText().toString();
                A_REC_PS = note_edit.getText().toString();
                user = usrHelper.getUserName();

                Intent intent = new Intent(getContext(), Fragment1Validation.class);
                intent.putExtra("LOT_NO", LOT_NO);
                intent.putExtra("DMY", DMY);
                intent.putExtra("A_REC_TIME", A_REC_TIME);
                intent.putExtra("A_PRO_TYPE", A_PRO_TYPE);
                intent.putExtra("A_RICE_A", A_RICE_A);
                intent.putExtra("A_RICE_B", A_RICE_B);
                intent.putExtra("A_RICE_C", A_RICE_C);
                intent.putExtra("A_RICE_D", A_RICE_D);
                intent.putExtra("A_MO_CLEAN", A_MO_CLEAN);
                intent.putExtra("A_MO_OPER", A_MO_OPER);
                intent.putExtra("A_MO_AB", A_MO_AB);
                intent.putExtra("A_GRILL_CLEAN", A_GRILL_CLEAN);
                intent.putExtra("A_GRILL_LOSS", A_GRILL_LOSS);
                intent.putExtra("A_GRILL_RES", A_GRILL_RES);
                intent.putExtra("A_FLOUR_CONT", A_FLOUR_CONT);
                intent.putExtra("A_FLOUR_COL", A_FLOUR_COL);
                intent.putExtra("A_FLOUR_SMELL", A_FLOUR_SMELL);
                intent.putExtra("REC_PS", A_REC_PS);
                intent.putExtra("username", user);
                intent.putExtra("new_form", new_form);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initData() {
        Final_URL = URL + "lot_no=" + lot_no;
        Log.d(TAG, "initData: Final url : " + Final_URL);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, Final_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: Respond JSON : " + response.trim());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject item = jsonArray.getJSONObject(0);

                    LOT_NO        = item.getString("LOT_NO");
                    DMY           = item.getString("DMY");
                    A_REC_TIME    = item.getString("A_REC_TIME");
                    A_PRO_TYPE    = item.getString("A_PRO_TYPE");
                    A_RICE_A      = item.getString("A_RICE_A");
                    A_RICE_B      = item.getString("A_RICE_B");
                    A_RICE_C      = item.getString("A_RICE_C");
                    A_RICE_D      = item.getString("A_RICE_D");
                    A_MO_CLEAN    = item.getString("A_MO_CLEAN");
                    A_MO_OPER     = item.getString("A_MO_OPER");
                    A_MO_AB       = item.getString("A_MO_AB");
                    A_GRILL_CLEAN = item.getString("A_GRILL_CLEAN");
                    A_GRILL_LOSS  = item.getString("A_GRILL_LOSS");
                    A_GRILL_RES   = item.getString("A_GRILL_RES");
                    A_FLOUR_CONT  = item.getString("A_FLOUR_CONT");
                    A_FLOUR_COL   = item.getString("A_FLOUR_COL");
                    A_FLOUR_SMELL = item.getString("A_FLOUR_SMELL");
                    A_REC_PS = item.getString("REC_PS");

                    Log.d(TAG, "onResponse: LOT_NO  : " + LOT_NO);
                    Log.d(TAG, "onResponse: DATE    : " + DMY);
                    Log.d(TAG, "onResponse: A       : " + A_RICE_A);

                    setData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                Toast.makeText(getActivity(), "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void setData() {
        //EditText
        lotno_edit.setText(LOT_NO);
        deditText.setText(DMY);
        teditText.setText(A_REC_TIME);
        typeEdit.setText(A_PRO_TYPE);
        recipe_a_edit.setText(A_RICE_A);
        recipe_b_edit.setText(A_RICE_B);
        recipe_c_edit.setText(A_RICE_C);
        recipe_d_edit.setText(A_RICE_D);
        color_edit.setText(A_FLOUR_COL);
        smell_edit.setText(A_FLOUR_SMELL);
        note_edit.setText(A_REC_PS);

        //RadioButton
        if (A_MO_CLEAN.equals("สะอาด")) {
            clean_edit.check(R.id.radio_btn_clean);
        } else {
            clean_edit.check(R.id.radio_btn_unclean);
        }

        if (A_MO_OPER.equals("ปกติ")) {
            engin_status_edit.check(R.id.radio_btn_nomal);
        } else {
            engin_status_edit.check(R.id.radio_btn_abnomal);
        }

        if (A_MO_AB.equals("มี")) {
            notcorrect_edit.check(R.id.have);
        } else {
            notcorrect_edit.check(R.id.have_not);
        }

        if (A_GRILL_CLEAN.equals("สะอาด")) {
            ta_krang_clean_edit.check(R.id.radio_ta_krang_btn_clean);
        } else {
            ta_krang_clean_edit.check(R.id.radio_ta_krang_btn_unclean);
        }

        if (A_GRILL_LOSS.equals("มี")) {
            broken_edit.check(R.id.have_broke);
        } else {
            broken_edit.check(R.id.have_not_broke);
        }

        if (A_GRILL_RES.equals("ละเอียด")) {
            grinding_edit.check(R.id.grinding_edit_g);
        } else {
            grinding_edit.check(R.id.grinding_edit_ug);
        }

        if (A_FLOUR_CONT.equals("มี")) {
            contamination_edit.check(R.id.have_contamination);
        } else {
            contamination_edit.check(R.id.have_not_contamination);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DATE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedDate = data.getStringExtra("selectedDate");
            deditText.setText(selectedDate);
        }
        if (requestCode == TIME_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedTime = data.getStringExtra("selectedTime");
            teditText.setText(selectedTime);
        }
    }
}
