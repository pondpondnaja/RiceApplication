package com.example.riceapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Fragment1 extends Fragment{
    public static final String TAG = "serveradddata";
    public static final int DATE_REQUEST_CODE = 11;
    public static final int TIME_REQUEST_CODE = 12;
    private Button submit_button_1;
    private RadioGroup clean_edit,engin_status_edit,notcorrect_edit,
                       ta_krang_clean_edit,broken_edit,contamination_edit;

    private EditText deditText,teditText,textArea,typeEdit,
                     recipe_a_edit,recipe_b_edit,recipe_c_edit,recipe_d_edit,
                     grinding_edit,color_edit,smell_edit;

    private String วัน,เวลา,ประเภทสินค้า,A,B,C,D,การโม่_ความสะอาด,
                   การโม่_การเดินเครื่อง,การโม่_สิ่งผิดปกติ,ตะแกรง_ความสะอาด,
                   ตะแกรง_รอยชำรุด,ตะแกรง_ความละเอียด,น้ำแป้ง_สิ่งเจือปน,
                   น้ำแป้ง_สี,น้ำแป้ง_กลิ่น,หมายเหตุ;

    String selectedDate;
    String selectedTime;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_fragment1,container,false);
        final FragmentManager fm = getActivity().getSupportFragmentManager();
        //EditText
        deditText = view.findViewById(R.id.date_edit);
        teditText = view.findViewById(R.id.time_edit);
        textArea  = view.findViewById(R.id.note_edit);
        typeEdit = view.findViewById(R.id.type_edit);
        grinding_edit = view.findViewById(R.id.grinding_edit);
        color_edit = view.findViewById(R.id.color_edit);
        smell_edit = view.findViewById(R.id.smell_edit);
        recipe_a_edit = view.findViewById(R.id.recipe_a_edit);
        recipe_b_edit = view.findViewById(R.id.recipe_b_edit);
        recipe_c_edit = view.findViewById(R.id.recipe_c_edit);
        recipe_d_edit = view.findViewById(R.id.recipe_d_edit);

        //Radio Group.
        clean_edit = view.findViewById(R.id.clean_edit);
        engin_status_edit = view.findViewById(R.id.engin_status_edit);
        notcorrect_edit = view.findViewById(R.id.notcorrect_edit);
        ta_krang_clean_edit = view.findViewById(R.id.ta_krang_clean_edit);
        broken_edit = view.findViewById(R.id.broken_edit);
        contamination_edit = view.findViewById(R.id.contamination_edit);

        //Button
        submit_button_1 = view.findViewById(R.id.submit_button_1);

        textArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        deditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deditText.setHint("");
                AppCompatDialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(Fragment1.this,DATE_REQUEST_CODE);
                newFragment.show(fm, "datePicker");
            }
        });

        teditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teditText.setHint("");
                AppCompatDialogFragment timeFragment = new TimePickerFragment();
                timeFragment.setTargetFragment(Fragment1.this,TIME_REQUEST_CODE);
                timeFragment.show(fm,"timePicker");
            }
        });

        submit_button_1.setOnClickListener(new View.OnClickListener() {
            String mo_clean,mo_engin,mo_issue,ta_clean,ta_broke,flour_con;
            @Override
            public void onClick(final View view){
                if(clean_edit.getCheckedRadioButtonId()!= -1){
                    int id= clean_edit.getCheckedRadioButtonId();
                    View radioButton = clean_edit.findViewById(id);
                    int radioId = clean_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) clean_edit.getChildAt(radioId);
                    mo_clean = (String) btn.getText();
                }
                if(engin_status_edit.getCheckedRadioButtonId()!= -1){
                    int id= engin_status_edit.getCheckedRadioButtonId();
                    View radioButton = engin_status_edit.findViewById(id);
                    int radioId = engin_status_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) engin_status_edit.getChildAt(radioId);
                    mo_engin = (String) btn.getText();
                }
                if(notcorrect_edit.getCheckedRadioButtonId()!= -1){
                    int id= notcorrect_edit.getCheckedRadioButtonId();
                    View radioButton = notcorrect_edit.findViewById(id);
                    int radioId = notcorrect_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) notcorrect_edit.getChildAt(radioId);
                    mo_issue = (String) btn.getText();
                }
                if(ta_krang_clean_edit.getCheckedRadioButtonId()!= -1){
                    int id= ta_krang_clean_edit.getCheckedRadioButtonId();
                    View radioButton = ta_krang_clean_edit.findViewById(id);
                    int radioId = ta_krang_clean_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) ta_krang_clean_edit.getChildAt(radioId);
                    ta_clean = (String) btn.getText();
                }
                if(broken_edit.getCheckedRadioButtonId()!= -1){
                    int id= broken_edit.getCheckedRadioButtonId();
                    View radioButton = broken_edit.findViewById(id);
                    int radioId = broken_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) broken_edit.getChildAt(radioId);
                    ta_broke = (String) btn.getText();
                }
                if(contamination_edit.getCheckedRadioButtonId()!= -1){
                    int id= contamination_edit.getCheckedRadioButtonId();
                    View radioButton = contamination_edit.findViewById(id);
                    int radioId = contamination_edit.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) contamination_edit.getChildAt(radioId);
                    flour_con = (String) btn.getText();
                }

                Log.d(TAG, "onClick: "+mo_clean);
                Log.d(TAG, "onClick: "+mo_engin);
                Log.d(TAG, "onClick: "+mo_issue);
                Log.d(TAG, "onClick: "+ta_clean);
                Log.d(TAG, "onClick: "+ta_broke);
                Log.d(TAG, "onClick: "+flour_con);

                วัน                = deditText.getText().toString();
                เวลา              = teditText.getText().toString();
                ประเภทสินค้า        = typeEdit.getText().toString();
                A                 = recipe_a_edit.getText().toString();
                B                 = recipe_b_edit.getText().toString();
                C                 = recipe_c_edit.getText().toString();
                D                 = recipe_d_edit.getText().toString();
                ตะแกรง_ความละเอียด = grinding_edit.getText().toString();
                น้ำแป้ง_สี           = color_edit.getText().toString();
                น้ำแป้ง_กลิ่น         = smell_edit.getText().toString();
                หมายเหตุ           = textArea.getText().toString();

                Intent intent = new Intent(getContext(),Fragment1Validation.class);
                intent.putExtra("วัน", วัน);
                intent.putExtra("เวลา", เวลา);
                intent.putExtra("ประเภทสินค้า", ประเภทสินค้า);
                intent.putExtra("A", A);
                intent.putExtra("B", B);
                intent.putExtra("C", C);
                intent.putExtra("D", D);
                intent.putExtra("การโม่_ความสะอาด", mo_clean);
                intent.putExtra("การโม่_การเดินเครื่อง", mo_engin);
                intent.putExtra("การโม่_สิ่งผิดปกติ", mo_issue);
                intent.putExtra("ตะแกรง_ความสะอาด", ta_clean);
                intent.putExtra("ตะแกรง_รอยชำรุด", ta_broke);
                intent.putExtra("ตะแกรง_ความละเอียด", ตะแกรง_ความละเอียด);
                intent.putExtra("น้ำแป้ง_สิ่งเจือปน", flour_con);
                intent.putExtra("น้ำแป้ง_สี", น้ำแป้ง_สี);
                intent.putExtra("น้ำแป้ง_กลิ่น", น้ำแป้ง_กลิ่น);
                intent.putExtra("หมายเหตุ", หมายเหตุ);

                startActivity(intent);
            }
        });
        
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DATE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedDate = data.getStringExtra("selectedDate");
            deditText.setText(selectedDate);
        }
        if (requestCode == TIME_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            selectedTime = data.getStringExtra("selectedTime");
            teditText.setText(selectedTime);
        }
    }
}
