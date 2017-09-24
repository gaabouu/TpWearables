package com.example.gab.tpwearables;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
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


        //Implémenter la sauvegarde ici
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.SaveFab);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast t = Toast.makeText(getApplicationContext(), "Sauvegarde de la nouvelle activité", Toast.LENGTH_LONG);
                t.show();
            }
        });

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
