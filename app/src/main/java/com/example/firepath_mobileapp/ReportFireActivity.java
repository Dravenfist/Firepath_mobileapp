package com.example.firepath_mobileapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReportFireActivity extends AppCompatActivity {

    private EditText inputLocation, inputDescription;
    private Button btnSubmitReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_fire);

        inputLocation = findViewById(R.id.inputLocation);
        inputDescription = findViewById(R.id.inputDescription);
        btnSubmitReport = findViewById(R.id.btnSubmitReport);

        btnSubmitReport.setOnClickListener(v -> submitFireReport());
    }

    private void submitFireReport() {
        String location = inputLocation.getText().toString().trim();
        String description = inputDescription.getText().toString().trim();

        if (location.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> report = new HashMap<>();
        report.put("location", location);
        report.put("description", description);
        report.put("timestamp", FieldValue.serverTimestamp());

        db.collection("fire_reports")
                .add(report)
                .addOnSuccessListener(docRef -> Toast.makeText(this, "Report submitted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
