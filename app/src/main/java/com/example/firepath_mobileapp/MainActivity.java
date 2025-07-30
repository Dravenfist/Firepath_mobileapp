package com.example.firepath_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



public class MainActivity extends AppCompatActivity {

    private Button btnStartNavigation, btnReportIncident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartNavigation = findViewById(R.id.btnStartNavigation);
        btnReportIncident = findViewById(R.id.btnReportIncident);

        btnStartNavigation.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.firepath_mobileapp.NavigationActivity.class);
            startActivity(intent);
        });

        btnReportIncident.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.firepath_mobileapp.ReportFireActivity.class);
            startActivity(intent);
        });

        fetchLatestAlert();
    }

    private void fetchLatestAlert() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView fireStatus = findViewById(R.id.fireStatus);

        db.collection("fire_alerts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String location = doc.getString("location");
                        fireStatus.setText("🔥 Fire Alert: Ongoing at " + location);
                    }
                })
                .addOnFailureListener(e -> fireStatus.setText("Unable to fetch alerts."));
    }
}
