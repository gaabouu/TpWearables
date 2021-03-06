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
    private String time;
    private int notif;


    public MyAlarms(){

    }

    public MyAlarms(int id, String title, String type, String desc, String date, String time, int not){
        this.id = id;
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.date = date;
        this.time = time;
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

        result = this.getTitle() + " " + this.getType() + " " + this.getDate() + " " + this.getNotif() + " " + this.getId();



        return result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * to get the position of the selected type in the spinner
     * @return the position of the item in the spinner in String
     */
    public String typeToPos(){
        switch(this.type){
            case "RDV": return "0";
            case "Travail": return "1";
            case "Anniversaire": return "2";
            case "Sport": return "3";
            case "Autres": return "4";
            default: return "0";
        }
    }

    /**
     * search if the alarm contains the charSequence in one of his fields
     * @param search the CharSequece to search for
     * @return true if yes false if not
     */
    public boolean contains(String search){
        boolean result = false;
        if(title.contains(search) || type.contains(search) || desc.contains(search) || date.contains(search) || time.contains(search)){
            result = true;
        } else if(search.matches("\\d+(?:\\.\\d+)?")){
            if(id == Integer.parseInt(search)) result = true;
        }

        return result;
    }
}
