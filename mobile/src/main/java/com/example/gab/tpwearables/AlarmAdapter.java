package com.example.gab.tpwearables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null){
            LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(iLayout, null, true);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)rowView.findViewById(R.id.textView_Title_alarm_layout);
            viewHolder.type = (TextView)rowView.findViewById(R.id.textView_Type_alarm_layout);
            viewHolder.date = (TextView)rowView.findViewById(R.id.textView_Date_alarm_layout);
            viewHolder.notif = (ImageView)rowView.findViewById(R.id.imageView_notif_alarm_layout);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)rowView.getTag();
        }
        MyAlarms item = alarms.get(pos);
        viewHolder.id.setText(item.getId());
        viewHolder.title.setText(item.getTitle());
        viewHolder.type.setText(item.getType());
        viewHolder.date.setText(item.getDate());
        if(item.getNotif() == 0){
            viewHolder.notif.setImageResource(android.R.drawable.ic_lock_idle_alarm);
        } else {
            viewHolder.notif.setImageResource(android.R.drawable.ic_menu_add);
        }

        return rowView;
    }

}
