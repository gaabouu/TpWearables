package com.example.gab.tpwearables;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

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

        FloatingActionButton btnReturn1 = (FloatingActionButton) findViewById(R.id.BackFab);
        btnReturn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    /// TODO: 24/09/2017 gerer la sauvegarde de la nouvelle activité 
        //Implémenter la sauvegarde ici
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.SaveFab);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  Toast t = Toast.makeText(getApplicationContext(), "Sauvegarde de la nouvelle activité", Toast.LENGTH_LONG);
               // t.show();
                try {
                    saveActivity();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    protected void saveActivity() throws ParseException {
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

        Date date = this.toDate(dateS, timeS);

        Toast t = Toast.makeText(getApplicationContext(), title + desc + type + date , Toast.LENGTH_LONG);
        t.show();

        // TODO: 25/09/17 Tester si ça marche 

        // TODO: 25/09/17 Enregistrer tout ça dans une bdd


    }

    protected Date toDate(String dateS, String timeS) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("E dd MMM yyyy HH:mm");
        return formatter.parse(dateS + " " + timeS);
    }


    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

}
