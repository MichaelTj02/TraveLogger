package com.example.iat359_final_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewLogsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewLogs;
    private ArrayList<String> travelLogsList; // List of travel logs
    private LogsAdapter logsAdapter; // Create your custom RecyclerView Adapter
    private ArrayList<LogModel> logsList;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);

        dbHelper = new DatabaseHelper(this);
        recyclerViewLogs = findViewById(R.id.recyclerViewLogs);
        logsList = new ArrayList<>(); // Initialize your list of logs

        retrieveLogsFromDatabase();

        // Mock data - replace this with actual data retrieval logic
//        travelLogsList.add("Log 1");
//        travelLogsList.add("Log 2");
//        travelLogsList.add("Log 3");

        logsAdapter = new LogsAdapter(logsList); // Create your custom Adapter
        recyclerViewLogs.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLogs.setAdapter(logsAdapter);
    }

    private void retrieveLogsFromDatabase(){
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM logs", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            double distance = cursor.getDouble(cursor.getColumnIndex("distance"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            int steps = cursor.getInt(cursor.getColumnIndex("steps"));

            LogModel log = new LogModel(id, distance, location, steps);
            logsList.add(log);
        }

        cursor.close();
        database.close();
    }

}