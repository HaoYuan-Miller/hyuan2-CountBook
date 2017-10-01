/*
 * myCustomAdapter
 *
 * Version 1.0
 *
 * September 30, 2017
 *
 * Copyright (c) 2017 Hao Yuan, CMPUT301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact hyuan2@ualberta.ca
 */

package com.countbook.hyuan2.hyuan2_countbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Represents a self implemented adapter
 * @refrence https://stackoverflow.com/questions/40862154/how-to-create-listview-items-button-in-each-row
 * @version 1.0
 * @since 1.0
 * Created by hyuan2 on 9/27/17.
 */

public class myCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Counter> list = new ArrayList<Counter>();
    private Context context;

    public myCustomAdapter(ArrayList<Counter> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount(){
        return list.size();
    }
    @Override
    public Object getItem(int pos){
        return list.get(pos);
    }
    @Override
    public long getItemId(int pos) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }
        TextView info =  view.findViewById(R.id.info);
        info.setText(list.get(position).toString());

        //Handle buttons and add onClickListeners
        Button buttonView = view.findViewById(R.id.buttonView);
        Button buttonInc = view.findViewById(R.id.buttonInc);
        Button buttonDec = view.findViewById(R.id.buttonDec);

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewEditActivity.class);
                intent.putExtra("counter", position);
                context.startActivity(intent);
            }
        });
        buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(position).incrementCounter();
                notifyDataSetChanged();
                saveInFile();
            }
        });
        buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    list.get(position).decrementCounter();
                    notifyDataSetChanged();
                    saveInFile();
                } catch (NegativeValueException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    /**
     * Save
     *
     * reference https://github.com/joshua2ua/lonelyTwitter
     */
    protected void saveInFile() {
        try {
            FileOutputStream fos = context.openFileOutput(MainActivity.FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(MainActivity.counterList, out);
            out.flush();
            fos.close();
        }  catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
