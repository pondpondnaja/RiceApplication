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

public class Fragment4 extends Fragment{
    public static final int DATE_REQUEST_CODE_4 = 14;
    public static final int DATE_REQUEST_CODE_4_1 = 15;
    EditText deditText;
    EditText teditText;
    String selectedDate;
    String selectedDate1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment4,container,false);
        deditText = view.findViewById(R.id.date_edit_4);
        teditText = view.findViewById(R.id.date_edit_4_lot);

        final FragmentManager fm = getActivity().getSupportFragmentManager();

        deditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deditText.setHint("");
                AppCompatDialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(Fragment4.this,DATE_REQUEST_CODE_4);
                newFragment.show(fm, "datePicker");
            }
        });

        teditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teditText.setHint("");
                AppCompatDialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(Fragment4.this,DATE_REQUEST_CODE_4_1);
                newFragment.show(fm, "datePicker");
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DATE_REQUEST_CODE_4 && resultCode == Activity.RESULT_OK) {
            selectedDate = data.getStringExtra("selectedDate");
            deditText.setText(selectedDate);
        }

        if (requestCode == DATE_REQUEST_CODE_4_1 && resultCode == Activity.RESULT_OK) {
            selectedDate1 = data.getStringExtra("selectedDate1");
            teditText.setText(selectedDate1);
        }
    }
}
