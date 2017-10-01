/*
 * ViewEditActivity
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
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Activity that shows detail information for a counter and allow the user to edit all information except for date
 * @author Hao Yuan
 * @version 1.0
 * @since 1.0
 * Created by hyuan2 on 9/30/17.
 */
public class ViewEditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);
        final int position = this.getIntent().getIntExtra("counter",0);         /* receive passed value */
        final Counter  counter = MainActivity.counterList.get(position);            /* get the counter object */
        /* used to display content of a counter and buttons */
        TextView name;
        TextView initialValue;
        TextView currentValue;
        TextView comment;
        TextView date;
        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date) ;
        initialValue = (TextView) findViewById(R.id.init);
        currentValue = (TextView) findViewById(R.id.curr);
        comment = (TextView) findViewById(R.id.comm);

        name.setText(counter.getItemName());
        date.setText(counter.getDate());
        initialValue.setText(counter.getInitialValue());
        currentValue.setText(counter.getCurrentValue());
        comment.setText(counter.getComment());

        Button buttonInc = (Button) findViewById(R.id.buttonInc);               /* button used for incrementing current value */
        Button buttonDec = (Button) findViewById(R.id.buttonDec);               /* button used for decrementing current value */
        Button buttonReset = (Button) findViewById(R.id.buttonReset);           /* button used for resetting current value */
        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);         /* button used for deleting counter */
        Button buttonBack = (Button) findViewById(R.id.buttonBack);             /* button used for go back */
        Button buttonEditName = (Button) findViewById(R.id.buttonEditName);     /* button used for name editing */
        Button buttonEditInit = (Button) findViewById(R.id.buttonEditInit);     /* button used for editing initial value */
        Button buttonEditCurr = (Button) findViewById(R.id.buttonEditCurr);     /* button used for editing current value */
        Button buttonEditComm = (Button) findViewById(R.id.buttonEditComm);     /* button used for editing comment */

        buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.incrementCounter();
                MainActivity.adapter.notifyDataSetChanged();
                saveInFile();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
        buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    counter.decrementCounter();
                    MainActivity.adapter.notifyDataSetChanged();
                    saveInFile();
                    Intent intent = getIntent();
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                } catch (NegativeValueException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.resetCurrentValue();
                MainActivity.adapter.notifyDataSetChanged();
                saveInFile();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.counterList.remove(position);
                MainActivity.adapter.notifyDataSetChanged();
                saveInFile();
                MainActivity.numOfCounters.setText("  # of counters: "+String.valueOf(MainActivity.counters.getAdapter().getCount()));
                finish();
            }
        });
        buttonEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(view.getContext());
                View promptsView = li.inflate(R.layout.prompts,null);
                TextView textview = promptsView.findViewById(R.id.textView1);
                textview.setText("Enter a new name: ");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = promptsView.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        counter.setItemName(userInput.getText().toString());
                                        MainActivity.adapter.notifyDataSetChanged();
                                        saveInFile();
                                        Intent intent = getIntent();
                                        overridePendingTransition(0, 0);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        buttonEditComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(view.getContext());
                View promptsView = li.inflate(R.layout.prompts,null);
                TextView textview = promptsView.findViewById(R.id.textView1);
                textview.setText("Enter a new comment: ");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = promptsView.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        counter.setComment(userInput.getText().toString());
                                        MainActivity.adapter.notifyDataSetChanged();
                                        saveInFile();
                                        Intent intent = getIntent();
                                        overridePendingTransition(0, 0);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        buttonEditInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(view.getContext());
                View promptsView = li.inflate(R.layout.prompt_integer,null);
                TextView textview = promptsView.findViewById(R.id.textView1);
                textview.setText("Enter a new initial value: ");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = promptsView.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            counter.setInitialValue(Integer.parseInt(userInput.getText().toString()));
                                            MainActivity.adapter.notifyDataSetChanged();
                                            saveInFile();
                                            Intent intent = getIntent();
                                            overridePendingTransition(0, 0);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            finish();
                                            overridePendingTransition(0, 0);
                                            startActivity(intent);
                                        } catch (NegativeValueException e) {
//                                            e.printStackTrace();
                                        }

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        buttonEditCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(view.getContext());
                View promptsView = li.inflate(R.layout.prompt_integer,null);
                TextView textview = promptsView.findViewById(R.id.textView1);
                textview.setText("Enter a new current value: ");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = promptsView.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            counter.setCurrentValue(Integer.parseInt(userInput.getText().toString()));
                                            MainActivity.adapter.notifyDataSetChanged();
                                            saveInFile();
                                            Intent intent = getIntent();
                                            overridePendingTransition(0, 0);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            finish();
                                            overridePendingTransition(0, 0);
                                            startActivity(intent);
                                        } catch (NegativeValueException e) {
//                                            e.printStackTrace();
                                        }

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.numOfCounters.setText("  # of counters: "+String.valueOf(MainActivity.counters.getAdapter().getCount()));
                finish();
            }
        });
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
            throw new RuntimeException();
        }
    }


}
