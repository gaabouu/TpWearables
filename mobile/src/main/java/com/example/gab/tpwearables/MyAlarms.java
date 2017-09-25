package com.example.gab.tpwearables;

/**
 * Created by Gab on 25/09/2017.
 */

public class MyAlarms {

    private int id;
    private String title;
    private String type;
    private String desc;
    private String date;
    private int notif;


    public MyAlarms(){

    }

    public MyAlarms(int id, String title, String type, String desc, String date, int not){
        this.id = id;
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.date = date;
        this.notif = not;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotif() {
        return notif;
    }

    public void setNotif(int notif) {
        this.notif = notif;
    }

    public String toString(){
        String result;

        result = this.getTitle() + " " + this.getType() + " " + this.getDate() + " " + this.getNotif();



        return result;
    }
}