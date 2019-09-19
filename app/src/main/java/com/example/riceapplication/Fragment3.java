package com.example.riceapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Fragment3 extends Fragment {
    public static final int DATE_REQUEST_CODE_3 = 13;
    public static final int TIME_REQUEST_CODE_START_3 = 14;
    public static final int TIME_REQUEST_CODE_DONE_3 = 15;

    EditText editText_Date;
    EditText editText_Time_start;
    EditText editText_Time_done;

    String selectedDate_3;
    String selectedTime_start;
    String selectedTime_done;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment3,container,false);

        final FragmentManager use_fm = getActivity().getSupportFragmentManager();

        editText_Date = view.findViewById(R.id.date_edit_3);
        editText_Time_start = view.findViewById(R.id.time_start_edit_3);
        editText_Time_done = view.findViewById(R.id.time_done_edit_3);

        editText_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDialogFragment datefragment_3 = new DatePickerFragment();
                datefragment_3.setTargetFragment(Fragment3.this,DATE_REQUEST_CODE_3);
                datefragment_3.show(use_fm, "datePicker_3");
            }
        });

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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DATE_REQUEST_CODE_3 && resultCode == Activity.RESULT_OK) {
            selectedDate_3 = data.getStringExtra("selectedDate");
            editText_Date.setText(selectedDate_3);
        }
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
