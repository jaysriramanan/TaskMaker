package com.example.test.taskmaker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    Calendar calendar=Calendar.getInstance();
    int year=calendar.get(Calendar.YEAR);
    int month=calendar.get(Calendar.MONTH);
    int day=calendar.get(Calendar.DAY_OF_MONTH);

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener )getContext(),year,month,day);
    }

}
