package com.example.gab.tpwearables;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
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
        typeList.add("Autres...");

        ArrayAdapter typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(typeAdapter);

    }
}
