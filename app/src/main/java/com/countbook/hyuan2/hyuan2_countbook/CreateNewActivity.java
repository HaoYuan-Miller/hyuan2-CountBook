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

public class CreateNewActivity extends AppCompatActivity {
    private EditText nameText;
    private EditText commentText;
    private EditText initialValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        nameText = (EditText) findViewById(R.id.Name);
        initialValue = (EditText) findViewById(R.id.Init);
        commentText = (EditText) findViewById(R.id.Comm);
        Button createButton = (Button) findViewById(R.id.Create);
        Button cancelButton = (Button) findViewById(R.id.Cancel);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                String name = nameText.getText().toString();
                String comment = commentText.getText().toString();
                if (name.equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a proper name! ", Toast.LENGTH_SHORT).show();
                } else {
                    if (initialValue.getText().toString().equals("")){
                        Toast.makeText(getBaseContext(), "Please enter a valid initial value! ", Toast.LENGTH_SHORT).show();
                    }else{
                        int initial = Integer.parseInt(initialValue.getText().toString());
                        try {
                            Counter newCounter = new Counter(name,initial,comment);
                            MainActivity.counterList.add(newCounter);
                            MainActivity.adapter.notifyDataSetChanged();
                            saveInFile();
                            MainActivity.numOfCounters.setText("  # of counters: "+String.valueOf(MainActivity.counters.getAdapter().getCount()));
                            finish();
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
        });
    }

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
