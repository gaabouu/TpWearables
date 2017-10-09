package com.example.gab.tpwearables;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreationActivity extends AppCompatActivity {

    private boolean modifying = false;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        fillSpinner();


       // Log.d(null, paramIntent.getStringExtra("title"));
        existingValues();

        /*
        Back to main activity on click on return button
         */
        FloatingActionButton btnReturn1 = (FloatingActionButton) findViewById(R.id.BackFab);
        btnReturn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        /*
        Save the alarm on click on this button
         */
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.SaveFab);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                        if(hasEmpty()){
                            Toast t2 = Toast.makeText(getApplicationContext(), "Vous n'avez pas rempli tous les champs", Toast.LENGTH_SHORT);
                            t2.show();
                        } else {
                            Toast t = Toast.makeText(getApplicationContext(), "Sauvegarde de la nouvelle activité", Toast.LENGTH_LONG);
                            t.show();
                            MyAlarms a = createAlarm();
                            Log.d(null, a.toString());
                            if (modifying == true) modifyAlarm(a);
                            else saveAlarm(a);
                        }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * fill the spinner with chosen values
     */
    public void fillSpinner(){
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);

        List typeList = new ArrayList();
        typeList.add("RDV");
        typeList.add("Travail");
        typeList.add("Anniversaire");
        typeList.add("Sport");
        typeList.add("Autres...");

        ArrayAdapter typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(typeAdapter);
    }

    /**
     * Create a new MyAlarms object with actual form's values
     * @return The created MyAlarms
     * @throws ParseException
     */
    protected MyAlarms createAlarm() throws ParseException{




        EditText titleEdit = (EditText) findViewById(R.id.nameEdit);
        String title = titleEdit.getText().toString();

        EditText descEdit = (EditText) findViewById(R.id.descEdit);
        String desc = descEdit.getText().toString();

        Spinner typespin = (Spinner)findViewById(R.id.typeSpinner);
        String type = typespin.getSelectedItem().toString();

        TextView dateText = (TextView)findViewById(R.id.dateText);
        String dateS = dateText.getText().toString();


        TextView timeText = (TextView)findViewById(R.id.timeText);
        String timeS = timeText.getText().toString();


        String fullDateS = dateS + " " + timeS;


        CheckBox notifCheck = (CheckBox)findViewById(R.id.notifCheck);
        int not = 0;
        if(notifCheck.isChecked()) not = 1;



        MyAlarms alarmToAdd = new MyAlarms(id, title, type, desc, dateS, timeS, not);
        return alarmToAdd;




    }


    /**
     * save alarm to db with actual fields values
     * @throws ParseException
     */
    protected void saveAlarm(MyAlarms a) throws ParseException {

            DataBaseHandler db = DataBaseHandler.getInstance(this.getApplicationContext());
            db.addAlarm(a);
            db.close();
            finish();


    }

    /**
     * modify the chosen alarm in the db
     * @param a the MyAlarms object to modify
     */
    public void modifyAlarm(MyAlarms a){
        DataBaseHandler db = DataBaseHandler.getInstance(getApplicationContext());
        db.modifyAlarm(a);
        Log.d(null, db.getAlarms().toString());
        db.close();
        finish();
    }

    /**
     * Gives a full Date (format Date) from two String of date and time
     * @param dateS String of a date >> "E dd MMM yyyy"
     * @param timeS String of a time >> "HH:mm"
     * @return Previous date and time in Date format
     * @throws ParseException
     */
    protected Date toDate(String dateS, String timeS) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("E dd MMM yyyy HH:mm");
        return formatter.parse(dateS + " " + timeS);
    }


    /**
     * to show date picker fragment
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * to show time picker fragment
     * @param v
     */
    public void showTimePickerDialog(View v) {
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * if intent came with values it's a modification then fill the form with the right values
     */
    public void existingValues(){
        Intent i = getIntent();
        if(i == null) return;

        if(i.hasExtra("title")){
            modifying = true;
            Log.d(null, "Dans existing values avec intent " + modifying);

            id = Integer.parseInt(i.getStringExtra("id"));

            EditText titleEdit = (EditText) findViewById(R.id.nameEdit);
            titleEdit.setText(i.getStringExtra("title"));

            EditText descEdit = (EditText) findViewById(R.id.descEdit);
            descEdit.setText(i.getStringExtra("desc"));

            Spinner typespin = (Spinner)findViewById(R.id.typeSpinner);
            typespin.setSelection(Integer.parseInt(i.getStringExtra("type")));

            TextView dateText = (TextView)findViewById(R.id.dateText);
            dateText.setText(i.getStringExtra("date"));

            TextView timeText = (TextView)findViewById(R.id.timeText);
            timeText.setText(i.getStringExtra("time"));

            CheckBox notCheck = (CheckBox)findViewById(R.id.notifCheck);
            int not = Integer.parseInt(i.getStringExtra("not"));
            switch(not){
                case 0: notCheck.setChecked(false);
                    break;
                case 1: notCheck.setChecked(true);
                    break;
                default:
            }
        }
    }

    /**
     * test the form's fields to know if one is empty
     * @return true if something is empty, false if nothing is
     */
    public boolean hasEmpty(){
        boolean result = false;
        EditText titleEdit = (EditText) findViewById(R.id.nameEdit);
        String title = titleEdit.getText().toString();
        TextView dateText = (TextView)findViewById(R.id.dateText);
        String date = dateText.getText().toString();
        TextView timeText = (TextView)findViewById(R.id.timeText);
        String time = timeText.getText().toString();

        if(title.isEmpty() || date.isEmpty() || time.isEmpty()) result = true;

        return result;

    }


}
