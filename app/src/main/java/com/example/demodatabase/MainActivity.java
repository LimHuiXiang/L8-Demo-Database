package com.example.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert,btnGetTasks;
    TextView tvResults;
    ListView lvTasksList;
    EditText UserTask;
    DatePicker dp;
    ArrayList<Task> TasksArray;
    ArrayAdapter<String> Adapter;
    boolean asc = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lvTasksList = findViewById(R.id.lv);
        dp = findViewById(R.id.datePicker);
        UserTask = findViewById(R.id.editTextTask);


                TasksArray = new ArrayList<>();

        Adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,TasksArray);
        lvTasksList.setAdapter(Adapter);


        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the
                // activity's Context
                DBHelper db = new DBHelper(MainActivity.this);
                int year = dp.getYear();
                int month = dp.getMonth();
                int day = dp.getDayOfMonth();
                String cdate = day + "-" + month +  "-"  + year;




                // Insert a task
                db.insertTask(UserTask.getText().toString(),cdate);

            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the
                // activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                ArrayList<String> data = db.getTaskContent();


                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i +". "+data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResults.setText(txt);

                //populate ListView
                TasksArray = db.getTasks(asc);
                db.close();
                asc = !asc;
                Adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, TasksArray);
                lvTasksList.setAdapter(Adapter);


            }
        });



    }
}