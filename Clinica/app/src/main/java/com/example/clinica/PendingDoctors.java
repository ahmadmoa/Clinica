package com.example.clinica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class PendingDoctors extends AppCompatActivity {
    private static PendingListAdapter PendingListAdapter;
    static public com.example.clinica.PendingListAdapter getPendingListAdapter() {
        return PendingListAdapter;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_doctors);
        PeopleDatabase dataBaseObject = new PeopleDatabase(getApplicationContext());
        ArrayList<Doctors> adapter = new ArrayList<>();
        Cursor c = dataBaseObject.getAllPendingDoctors();
        adapter.addAll(dataBaseObject.fetchDoctorsData(c));
        RecyclerView RV = findViewById(R.id.pendingDoctorsRecyclerView);
        RV.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        PendingListAdapter = new PendingListAdapter(adapter);
        RV.setAdapter(PendingListAdapter);
    }
}