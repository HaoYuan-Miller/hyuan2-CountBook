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



public class MainActivity extends AppCompatActivity {
    protected static final String FILENAME = "file.sav";
    protected static ListView counters;
    protected static ArrayList<Counter> counterList = new ArrayList<Counter>();
    protected static myCustomAdapter adapter;
    protected static TextView numOfCounters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counters = (ListView) findViewById(R.id.counters);
        numOfCounters = (TextView) findViewById(R.id.numOfCounters);
    }

    public void createNew(View view){
        Intent intent = new Intent(this, CreateNewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart(){
        super.onStart();
        loadFromFile();
        adapter = new myCustomAdapter(counterList,this);
        counters.setAdapter(adapter);
        numOfCounters.setText("  # of counters: "+String.valueOf(counters.getAdapter().getCount()));

    }
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
