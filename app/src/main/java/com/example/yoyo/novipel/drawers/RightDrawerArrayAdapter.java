package com.example.yoyo.novipel.drawers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yoyo.novipel.R;

public class RightDrawerArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final int layout;
    private int drawerHeight;

    private boolean connectedToDevice;

    public RightDrawerArrayAdapter(Context context, int layout, String[] values, boolean connectedToDevice, int drawerHeight) {
        super(context, layout, values);
        this.context = context;
        this.values = values;
        this.layout = layout;
        this.connectedToDevice = connectedToDevice;
        this.drawerHeight = drawerHeight;
    }

    // this is used to initialize the view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.drawer_item_text_right);

        LinearLayout v = (LinearLayout) rowView.findViewById(R.id.drawer_list_container_right);
        // calculate the height by dividing by the length of the list
        // to get scalable distance

        int avgHeight = (drawerHeight / values.length);
        Log.i("AVGHEIGHT", avgHeight +"" );
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(v.getLayoutParams());



        v.setLayoutParams((new android.widget.AbsListView.LayoutParams(lp.width, avgHeight)));


        switch(position) {
            case 0:
                ImageView iv = (ImageView) rowView.findViewById(R.id.drawer_item_icon_right);
                iv.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.settings));
                break;
            case 1:
                iv = (ImageView) rowView.findViewById(R.id.drawer_item_icon_right);
                iv.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.faq));
                break;
            case 2:
                iv = (ImageView) rowView.findViewById(R.id.drawer_item_icon_right);
                iv.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.feedback));
                break;
            case 3:
                iv = (ImageView) rowView.findViewById(R.id.drawer_item_icon_right);
                iv.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.instructions));
                break;

        }

        textView.setText(values[position]);

        return rowView;
    }


}
