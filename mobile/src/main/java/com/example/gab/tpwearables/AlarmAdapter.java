package com.example.gab.tpwearables;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)rowView.getTag();
        }
        final MyAlarms item = alarms.get(pos);
        viewHolder.id.setText(String.valueOf(item.getId()));
        viewHolder.title.setText(item.getTitle());
        viewHolder.type.setText(item.getType());
        viewHolder.date.setText(item.getDate());
        setNotifImg(viewHolder.notif, item.getNotif());

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



        return rowView;
    }

    // TODO: 03/10/2017 ajouter le moyen de supprimer une alarme 
    
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





}
