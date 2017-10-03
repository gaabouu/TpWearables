package com.example.gab.tpwearables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gab on 25/09/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    //singleton pour utiliser la base de données dans toutes les activités
    private static DataBaseHandler dBInstance;

    private static final String DATABASE_NAME = "AlarmsManager";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Alarmes";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "Titre";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_DESC = "Description";
    private static final String KEY_DATE = "DateA";
    private static final String KEY_NOTIF = "Notif";


    /**
     * Implementing singleton
     * @param context app context
     * @return instance of DataBaseHandler
     */
    public static synchronized DataBaseHandler getInstance(Context context) {
        Log.d(null, "getting db instance");
        if (dBInstance == null) {

            Log.d(null, "giving db instance in DataBaseHandler");
            dBInstance = new DataBaseHandler(context.getApplicationContext());

        }
        Log.d(null, dBInstance.toString());
        return dBInstance;
    }

    private DataBaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    /**
     * create table in the db
     * @param db
     */
     @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d(null, "Creating table");

            String CREATE_ALARM_TABLE = "CREATE TABLE " + TABLE_NAME
                    + "("
                    + KEY_ID + " INTEGER PRIMARY KEY, "
                    + KEY_TITLE + " TEXT, "
                    + KEY_TYPE + " TEXT, "
                    + KEY_DESC + " TEXT, "
                    + KEY_DATE + " TEXT, "
                    + KEY_NOTIF + " INTEGER"
                    + ")";
            db.execSQL(CREATE_ALARM_TABLE);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(null, "Upgrading db");
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);

        onCreate(db);
    }


    /**
     * add alarm in the db
     * @param a alarm to add in the db
     */
    public void addAlarm(MyAlarms a){
        Log.d(null, "creating alarm values before inserting...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, a.getTitle());
        values.put(KEY_TYPE, a.getType());
        values.put(KEY_DESC, a.getDesc());
        values.put(KEY_DATE, a.getDate());
        values.put(KEY_NOTIF, a.getNotif());

        Log.d(null, "inserting alarm in db");
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public void switchNotif(MyAlarms alarm) {

        Log.d(null, "Switching notif");
        if(alarm != null) {
            int id = alarm.getId();
            int notif = alarm.getNotif();
            int newNot = 0;


            if (notif == 0) {
                alarm.setNotif(1);
                newNot = 1;
            } else {
                alarm.setNotif(0);
                newNot = 0;
            }

            Log.d(null, String.valueOf(id));
            SQLiteDatabase db = this.getWritableDatabase();
            String changeNotifQuery = "UPDATE " + TABLE_NAME +
                    " SET " + KEY_NOTIF + " = " + newNot
                    + " WHERE " + KEY_ID + " = " + id + " ; ";

            db.execSQL(changeNotifQuery);
       /* ContentValues cv = new ContentValues();
        cv.put(KEY_NOTIF, newNot);
        db.beginTransaction();
        db.update(TABLE_NAME, cv, KEY_ID + " = " + id, null);
        db.setTransactionSuccessful();
        db.endTransaction();*/
            db.close();
        }
        Log.d(null, getAlarms().toString());
    }

    public void removeAlarm(MyAlarms alarm){

    }


    /**
     * give all alarms
     * @return All alarms from the DB in an ArrayList
     */
    public ArrayList<MyAlarms> getAlarms(){
        ArrayList<MyAlarms> alarmsList = new ArrayList<MyAlarms>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                MyAlarms alarm = new MyAlarms();
                alarm.setId(Integer.parseInt(cursor.getString(0)));
                alarm.setTitle(cursor.getString(1));
                alarm.setType(cursor.getString(2));
                alarm.setDesc(cursor.getString(3));
                alarm.setDate(cursor.getString(4));
                alarm.setNotif(Integer.parseInt(cursor.getString(5)));

                alarmsList.add(alarm);
            }while(cursor.moveToNext());
        }
        Log.d(null, "getAlarms giving arrayList");
        return alarmsList;
    }
}
