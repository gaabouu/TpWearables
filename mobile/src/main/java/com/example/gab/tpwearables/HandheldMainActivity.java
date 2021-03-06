package com.example.gab.tpwearables;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HandheldMainActivity extends AppCompatActivity {

    private EditText searchEdit;
    private ArrayList<MyAlarms> alarms;

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

        DataBaseHandler db = DataBaseHandler.getInstance(this);
        alarms = db.getAlarms();
        db.close();

        tryDb();

        searchEdit = (EditText)this.findViewById(R.id.editText_Search);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<MyAlarms> found = searchAlarms(searchEdit.getText().toString());
                if(!found.isEmpty()){
                    displayAlarms(found);
                } else {
                    Toast t = Toast.makeText(getApplicationContext(), "aucune alarme trouvée avec ce(s) terme(s)", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });


    }

    /**
     * Test DataBase to display all alarms
     */
    public final void tryDb(){
        Log.d(null, "testing db");
        DataBaseHandler db = DataBaseHandler.getInstance(this);
        alarms = db.getAlarms();
        //Log.d(null, db.getAlarms().toString());
        db.close();
        displayAlarms(alarms);
    }

    /**
     * create and deploy the adapter for the alarm list
     * @param allAlarms ArrayList of MyAlarms
     */
    protected void displayAlarms(ArrayList<MyAlarms> allAlarms){
        ListView alarmsListView = (ListView)findViewById(R.id.activList);

        AlarmAdapter adapter = new AlarmAdapter(this, R.layout.alarm_layout, allAlarms);
        alarmsListView.setAdapter(adapter);


    }



    /**
     * search in alarms for a string (in all parameters of the alarm)
     * @param w String of the wanted word
     */
    protected ArrayList<MyAlarms> searchAlarms(String w){
        ArrayList<MyAlarms> found = new ArrayList<MyAlarms>();

        for(MyAlarms a : alarms){
            if(a.contains(w)) found.add(a);
        }

        return found;

    }




    @Override
    protected void onResume(){
        super.onResume();
        tryDb();
    }

    /**
     * Create the alarm creation activity
     */
    protected void CreateActivity(){
        Intent CreateIntent = new Intent(this, CreationActivity.class);
        startActivity(CreateIntent);
    }


    // TODO: 09/10/2017 Créer les notifications 


}
