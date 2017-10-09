package com.example.gab.tpwearables;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class AlarmAdapter extends ArrayAdapter<MyAlarms> {

    private Context mContext;
    private List<MyAlarms> alarms;
    private int iLayout;

    public AlarmAdapter(Context context,int layout, List<MyAlarms> objects) {
        super(context, layout, objects);
        this.mContext = context;
        this.alarms = objects;
        this.iLayout = layout;
    }

    static class ViewHolder {
        public TextView title;
        public TextView type;
        public TextView date;
        public TextView id;
        public ImageView notif;
        public TextView desc;
        public Button edit_button;
        public Button delete_button;
    }

    protected int getCount(ArrayList<MyAlarms> list){
        return list.size();
    }

   

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){

        final ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null){
            LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(iLayout, null, true);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)rowView.findViewById(R.id.textView_Title_alarm_layout);
            viewHolder.type = (TextView)rowView.findViewById(R.id.textView_Type_alarm_layout);
            viewHolder.date = (TextView)rowView.findViewById(R.id.textView_Date_alarm_layout);
            viewHolder.id = (TextView)rowView.findViewById(R.id.textView_Id_alarm_layout);
            viewHolder.notif = (ImageView)rowView.findViewById(R.id.imageView_notif_alarm_layout);
            viewHolder.desc = (TextView)rowView.findViewById(R.id.text_desc);
            viewHolder.edit_button=(Button)rowView.findViewById(R.id.button_edit);
            viewHolder.delete_button=(Button)rowView.findViewById(R.id.button_delete);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)rowView.getTag();
        }

        final MyAlarms item = alarms.get(pos);
        viewHolder.id.setText(String.valueOf(item.getId()));
        viewHolder.title.setText(item.getTitle());
        viewHolder.type.setText(item.getType());
        viewHolder.date.setText(item.getDate() + " " + item.getTime());
        setNotifImg(viewHolder.notif, item.getNotif());
        viewHolder.desc.setText(item.getDesc());
        viewHolder.desc.setVisibility(View.GONE);
        viewHolder.edit_button.setVisibility(View.GONE);
        viewHolder.delete_button.setVisibility(View.GONE);

        viewHolder.notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(null, "Click on img");
                DataBaseHandler db = DataBaseHandler.getInstance(getContext());
                switchNotifImg(viewHolder.notif, item.getNotif());
                db.switchNotif(item);

                db.close();
            }
        });

        viewHolder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(mContext, "Modification de l'alarme " + item.getTitle(), Toast.LENGTH_SHORT);
                t.show();

                launchModification(item);





            }
        });

        viewHolder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DataBaseHandler db = DataBaseHandler.getInstance(getContext());
                db.delete(item);

                db.close();


                modificationHappened();


            }
        });


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (viewHolder.desc.getVisibility()){
                    case View.GONE :
                        viewHolder.desc.setVisibility(View.VISIBLE);
                        viewHolder.edit_button.setVisibility(View.VISIBLE);
                        viewHolder.delete_button.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE :
                        viewHolder.desc.setVisibility(View.GONE);
                        viewHolder.edit_button.setVisibility(View.GONE);
                        viewHolder.delete_button.setVisibility(View.GONE);
                        break;
                    default:
                        Toast t = Toast.makeText(getContext(), "click on an alarm", Toast.LENGTH_SHORT);
                        t.show();

                }

            }
        });

       // Log.d(null, "dans getView, id: " + viewHolder.id.getText());

        return rowView;
    }


    /**
     * Set Img for notification
     * @param v ImageView to change
     * @param i Value of field notif in the alarm
     */
    public void setNotifImg(ImageView v, int i){
        if(i == 0){
            v.setImageResource(R.drawable.alarm_grey);
        } else {
            v.setImageResource(R.drawable.alarm_red);

        }
    }

    /**
     * switch the img for the notification
     * @param v ImageView to change
     * @param i Int, actual state of notif in the alarm
     */
    public void switchNotifImg(ImageView v, int i){
        if(i == 0){
            v.setImageResource(R.drawable.alarm_red);
            Toast toast = Toast.makeText(getContext(), "Activation de la notification", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            v.setImageResource(R.drawable.alarm_grey);
            Toast toast = Toast.makeText(getContext(), "Désactivation de la notification", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * refresh the adapter changing values
     */
    public void modificationHappened(){
        Log.d(null, "modfificationHappening " + alarms);
        DataBaseHandler db = DataBaseHandler.getInstance(getContext());
        ArrayList<MyAlarms> newAlarms;
        newAlarms = db.getAlarms();
        db.close();
        alarms.clear();
        alarms.addAll(newAlarms);
        notifyDataSetChanged();
        Log.d(null, "modfificationHappened " + alarms);
    }

    /**
     * to launch the modification of the chosen alarm, setting the intent with alarm values
     * @param a the MyAlarms object to modify
     */
    public void launchModification(MyAlarms a){
        Intent modifyIntent = new Intent(getContext(), CreationActivity.class);
        modifyIntent.putExtra("id", Integer.toString(a.getId()));
        modifyIntent.putExtra("title", a.getTitle());
        modifyIntent.putExtra("type", a.typeToPos());
        modifyIntent.putExtra("desc", a.getDesc());
        modifyIntent.putExtra("date", a.getDate());
        modifyIntent.putExtra("time", a.getTime());
        modifyIntent.putExtra("not", Integer.toString(a.getNotif()));
        mContext.startActivity(modifyIntent);


    }







}
