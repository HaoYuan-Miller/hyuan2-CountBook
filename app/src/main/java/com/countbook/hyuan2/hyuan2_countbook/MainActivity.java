/*
 * MainActivity
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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Activity that shows information for counters,
 * allows the user to create, or do minor changes to counter objects
 * @author Hao Yuan
 * @version 1.0
 * @since 1.0
 * Created by hyuan2 on 9/27/17.
 */

public class MainActivity extends AppCompatActivity {
    protected static final String FILENAME = "file.sav";    /* used for data storing */
    protected static ListView counters;                         /* used for display counters */
    protected static ArrayList<Counter> counterList = new ArrayList<Counter>();     /* used for counter storing */
    protected static myCustomAdapter adapter;                   /* self-defined adapter */
    protected static TextView numOfCounters;                    /* used for display number of counters */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counters = (ListView) findViewById(R.id.counters);
        numOfCounters = (TextView) findViewById(R.id.numOfCounters);
    }

    public void createNew(View view){
        Intent intent = new Intent(this, CreateNewActivity.class);      /* Button that used to create a new counter */
        startActivity(intent);
    }

    @Override
    public void onStart(){
        super.onStart();
        loadFromFile();
        adapter = new myCustomAdapter(counterList,this);                /* initialize adapter */
        counters.setAdapter(adapter);                                    /* set adapter */
        numOfCounters.setText("  # of counters: "+String.valueOf(counters.getAdapter().getCount()));    /* set text for number of counters */

    }

    /**
     * loadFromFile
     *
     * reference https://github.com/joshua2ua/lonelyTwitter
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            counterList = new ArrayList<Counter>();
        }
    }

}
