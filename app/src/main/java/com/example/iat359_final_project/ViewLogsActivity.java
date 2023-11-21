package com.example.iat359_final_project;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ViewLogsActivity extends Activity {
    private RecyclerView myRecycler;
    private Database db;
    private CustomAdapter customAdapter;
    private DatabaseHelper helper;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);
        myRecycler = (RecyclerView) findViewById(R.id.recyclerViewLogs);

        db = new Database(this);
        helper = new DatabaseHelper(this);

        Cursor cursor = db.getData();

        int index1 = cursor.getColumnIndex(Constants.LOCATION);
        int index2 = cursor.getColumnIndex(Constants.STEPS_AMOUNT);

        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String logLocation = cursor.getString(index1);
            String logSteps = cursor.getString(index2);
            String s = logLocation +"," + logSteps;
            mArrayList.add(s);
            cursor.moveToNext();
        }

        ArrayList<String> queryResults = getIntent().getStringArrayListExtra("queryResults");

        if(queryResults != null){
            customAdapter = new CustomAdapter(queryResults);
        } else{
            customAdapter = new CustomAdapter((mArrayList));
        }
        myRecycler.setAdapter(customAdapter);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        myRecycler.setLayoutManager(mLayoutManager);
    }
}
