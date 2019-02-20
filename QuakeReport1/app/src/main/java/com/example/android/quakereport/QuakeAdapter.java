package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Created by srujana on 28/12/17.
 */

public class QuakeAdapter extends ArrayAdapter<Earthquake> {
    public QuakeAdapter(@NonNull Context context, ArrayList<Earthquake> objects) {
        super(context,0, objects);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Earthquake current = getItem(position);

        TextView mag = (TextView) listItemView.findViewById(R.id.magnitude);
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        TextView lo = (TextView) listItemView.findViewById(R.id.locationOffset);
        TextView time = (TextView)  listItemView.findViewById(R.id.time);
        TextView po = (TextView) listItemView.findViewById(R.id.primarylocation);

        mag.setText(current.getMagnitude());
        date.setText(current.getDate());
        time.setText(current.getTym());

        String locationstring = current.getPlace();

        if(locationstring.contains("of")){
            String[] location = locationstring.split("of");
            String locationOffset = location[0]+"of";
            String primaryLocation = location[1];

            lo.setText(locationOffset);
            po.setText(primaryLocation);
        }
        else{
            String locationOffset = "Near the";
            String primarylocation = locationstring;

            lo.setText(locationOffset);
            po.setText(primarylocation);
        }



        return listItemView;
}}
