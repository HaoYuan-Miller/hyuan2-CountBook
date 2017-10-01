/*
 * CreateNewActivity
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Activity that displays a page for creating new counters
 * @author Hao Yuan
 * @version 1.0
 * @since 1.0
 * Created by hyuan2 on 9/27/17.
 */
public class CreateNewActivity extends AppCompatActivity {
    private EditText nameText;            /* used for the user to input name */
    private EditText commentText;        /* used for the user to input comment */
    private EditText initialValue;       /* used for the user to input initial value */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        nameText = (EditText) findViewById(R.id.Name);
        initialValue = (EditText) findViewById(R.id.Init);
        commentText = (EditText) findViewById(R.id.Comm);
        Button createButton = (Button) findViewById(R.id.Create);   /* Button that allows the user to finish creating process*/
        Button cancelButton = (Button) findViewById(R.id.Cancel);   /* Button that allows the user to cancel creating process*/

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                String name = nameText.getText().toString();        /* Converts user input to string*/
                String comment = commentText.getText().toString();  /* Converts user input to string*/
                if (name.equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a proper name! ", Toast.LENGTH_SHORT).show();  /* Check if the name equals "", if true, refuse user input*/
                } else {
                    if (initialValue.getText().toString().equals("")){
                        Toast.makeText(getBaseContext(), "Please enter a valid initial value! ", Toast.LENGTH_SHORT).show(); /* Check if the initial value equals "", if true, refuse user input*/
                    }else{
                        int initial = Integer.parseInt(initialValue.getText().toString()); /* Converts user input to int*/
                        try {
                            Counter newCounter = new Counter(name,initial,comment);         /* Create new counter using information user provided */
                            MainActivity.counterList.add(newCounter);                       /* Add to counter list*/
                            MainActivity.adapter.notifyDataSetChanged();                    /* Tell the program to refresh */
                            saveInFile();                                                   /* Save it */
                            MainActivity.numOfCounters.setText("  # of counters: "+String.valueOf(MainActivity.counters.getAdapter().getCount())); /* Get total number of counters */
                            finish();                                                       /* finish */
                        } catch (NegativeValueException e) {
//                            e.printStackTrace();
                        }

                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }); /* finish */
    }

    /**
     * Save
     *
     * reference https://github.com/joshua2ua/lonelyTwitter
     */
    protected void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(MainActivity.counterList, out);
            out.flush();
            fos.close();
        }  catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
