package com.example.gab.tpwearables;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    // TODO: 24/09/2017 Comme pour DatePicker 
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c2 = Calendar.getInstance();
        c2.set(c2.HOUR_OF_DAY, hourOfDay);
        c2.set(c2.MINUTE, minute);

        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf2.format(c2.getTime());
        TextView timeText = (TextView) getActivity().findViewById(R.id.timeText);
        timeText.setText(formattedTime);
    }
}
