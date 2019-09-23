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

public class Fragment2 extends Fragment {
    public static final String TAG = "serveradddata";
    public static final int DATE_REQUEST_CODE_2 = 12;
    public static final int TIME_REQUEST_CODE_START = 13;
    public static final int TIME_REQUEST_CODE_DONE = 14;
    private Button submit_button_2;

    private RadioGroup type_edit,thickness_edit;

    private  EditText editText_Date,editText_Time_start,editText_Time_done,round_edit,goods_name_edit,temperature_edit,
             rpm_edit,temperature_edit_2,rpm_2_edit,how_many_edit,remaining_flour_edit,supervisor_edit,textArea;

    private String DMY,ROUND,PRO_NAME,TEMP_STEAM,SPEED_STEAM,TEMP_DRY,SREED_DRY,REC_START,REC_END,QUAN,PIECE,EMP_NAME,REC_PS;


    String selectedDate_2;
    String selectedTime_start;
    String selectedTime_done;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment2,container,false);

        final FragmentManager use_fm = getActivity().getSupportFragmentManager();
        //Edittext
        editText_Date = view.findViewById(R.id.date_edit_2);
        editText_Time_start = view.findViewById(R.id.time_start_edit);
        editText_Time_done = view.findViewById(R.id.time_done_edit);
        textArea = view.findViewById(R.id.note_2_edit);
        round_edit = view.findViewById(R.id.round_edit);
        goods_name_edit = view.findViewById(R.id.goods_name_edit);
        temperature_edit = view.findViewById(R.id.temperature_edit);
        rpm_edit = view.findViewById(R.id.rpm_edit);
        temperature_edit_2 = view.findViewById(R.id.temperature_edit_2);
        rpm_2_edit = view.findViewById(R.id.rpm_2_edit);
        how_many_edit = view.findViewById(R.id.how_many_edit);
        remaining_flour_edit = view.findViewById(R.id.remaining_flour_edit);
        supervisor_edit = view.findViewById(R.id.supervisor_edit);

        //Radio
        type_edit = view.findViewById(R.id.type_edit);
        thickness_edit = view.findViewById(R.id.thickness_edit);

        //buuton
        submit_button_2 = view.findViewById(R.id.submit_button_2);

        //
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


        editText_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDialogFragment datefragment_2 = new DatePickerFragment();
                datefragment_2.setTargetFragment(Fragment2.this,DATE_REQUEST_CODE_2);
                datefragment_2.show(use_fm, "datePicker_2");
            }
        });

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
            String mo_type2,mo_thickness;
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

                DMY                = editText_Date.getText().toString();
                ROUND              = round_edit.getText().toString();
                PRO_NAME           = goods_name_edit.getText().toString();
                TEMP_STEAM         = temperature_edit.getText().toString();
                SPEED_STEAM        = rpm_edit.getText().toString();
                TEMP_DRY           = temperature_edit_2.getText().toString();
                SREED_DRY          = rpm_2_edit.getText().toString();
                REC_START          = editText_Time_start.getText().toString();
                REC_END            = editText_Time_done.getText().toString();
                QUAN               = how_many_edit.getText().toString();
                PIECE              = remaining_flour_edit.getText().toString();
                EMP_NAME           = supervisor_edit.getText().toString();
                REC_PS             = textArea.getText().toString();

                Intent intent = new Intent(getContext(),Fragment2Validation.class);
                intent.putExtra("DMY", DMY);
                intent.putExtra("ROUND", ROUND);
                intent.putExtra("PRO_NAME", PRO_NAME);
                intent.putExtra("TYPE",mo_type2);
                intent.putExtra("BOLD",mo_thickness);
                intent.putExtra("TEMP_STEAM", TEMP_STEAM);
                intent.putExtra("SPEED_STEAM", SPEED_STEAM);
                intent.putExtra("TEMP_DRY", TEMP_DRY);
                intent.putExtra("SREED_DRY", SREED_DRY);
                intent.putExtra("REC_START", REC_START);
                intent.putExtra("REC_END", REC_END);
                intent.putExtra("QUAN", QUAN);
                intent.putExtra("PIECE", PIECE);
                intent.putExtra("EMP_NAME", EMP_NAME);
                intent.putExtra("REC_PS", REC_PS);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DATE_REQUEST_CODE_2 && resultCode == Activity.RESULT_OK) {
            selectedDate_2 = data.getStringExtra("selectedDate");
            editText_Date.setText(selectedDate_2);
        }
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
