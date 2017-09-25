package com.example.gab.tpwearables;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HandheldMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handheld_main);

        Log.d(null, "lancement de l'application");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateActivity();
            }
        });

        // TODO: 25/09/2017 gerer l'affichage pour que ce soit stylé t'as vu 

        tryDb();
    }

    protected void tryDb(){
        Log.d(null, "testing db");
        DataBaseHandler db = DataBaseHandler.getInstance(this);
        ArrayList<MyAlarms> allAlarms = db.getAlarms();
        db.close();

        ArrayList<String> allAlarmsString = new ArrayList<String>();
        for(MyAlarms a : allAlarms){
            allAlarmsString.add(a.toString());
        }

        ListView alarmsListView = (ListView)findViewById(R.id.activList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allAlarmsString);
        alarmsListView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        tryDb();
    }

    protected void CreateActivity(){


        Intent CreateIntent = new Intent(this, CreationActivity.class);
        startActivity(CreateIntent);



    }
}
