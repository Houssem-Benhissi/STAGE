package com.example.stage; // Replace with your package

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button technicianButton = findViewById(R.id.technicianButton);
        technicianButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, TechnicianActivity.class);
            startActivity(intent);
        });

        Button machineButton = findViewById(R.id.machineButton);
        machineButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MachineActivity.class);
            startActivity(intent);
        });

        Button maintenanceButton = findViewById(R.id.maintenanceButton);
        maintenanceButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MaintenanceActivity.class);
            startActivity(intent);
        });
    }
}