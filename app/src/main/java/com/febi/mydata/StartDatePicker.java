package com.febi.mydata;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by flock on 17/1/17.
 */

public class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    Calendar calendar   = Calendar.getInstance();
    int startYear       = calendar.get(Calendar.YEAR);
    int startMonth      = calendar.get(Calendar.MONTH);
    int startDay        = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, startYear, startMonth, startDay);
        return dialog;

    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TextView dateTextView = (TextView) getActivity().findViewById(R.id.id_date_edit_text);
        startYear   = year;
        startMonth  = monthOfYear + 1;
        startDay    = dayOfMonth;
        dateTextView.setText(startDay + "/" + startMonth + "/" + startYear);
    }
}
