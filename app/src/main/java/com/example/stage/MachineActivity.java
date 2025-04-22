package com.example.stage;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MachineActivity extends AppCompatActivity {

    private DBConnect dbConnect;
    private ListView machineListView;
    private ArrayAdapter<String> adapter;
    private List<Machine> machines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);  // Create this layout

        dbConnect = new DBConnect(this);
        machineListView = findViewById(R.id.machineListView);

        loadMachines();

        Button addButton = findViewById(R.id.addMachineButton);
        addButton.setOnClickListener(v -> showAddMachineDialog());

        machineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Machine selectedMachine = machines.get(position);
                showDeleteMachineDialog(selectedMachine);
            }
        });
    }

    private void loadMachines() {
        Cursor cursor = dbConnect.getAllMachines();
        machines.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                machines.add(new Machine(id, name));
            } while (cursor.moveToNext());
            cursor.close();
        }
        updateListView();
    }

    private void updateListView() {
        List<String> machineNames = new ArrayList<>();
        for (Machine machine : machines) {
            machineNames.add(machine.getName());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, machineNames);
        machineListView.setAdapter(adapter);
    }

    private void showAddMachineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Machine");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_machine, null); // Create this layout
        EditText nameEditText = view.findViewById(R.id.machineNameEditText);
        builder.setView(view);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameEditText.getText().toString().trim();
            if (!name.isEmpty()) {
                Machine newMachine = new Machine(0, name); // ID auto-generated
                long newId = dbConnect.addMachine(newMachine);
                if (newId != -1) {
                    newMachine.setId((int) newId);
                    machines.add(newMachine);
                    updateListView();
                    Toast.makeText(this, "Machine added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to add machine", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter machine name", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteMachineDialog(Machine machineToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Machine");
        builder.setMessage("Are you sure you want to delete " + machineToDelete.getName() + "?");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            int rowsAffected = dbConnect.deleteMachine(machineToDelete.getId());
            if (rowsAffected > 0) {
                machines.remove(machineToDelete);
                updateListView();
                Toast.makeText(this, "Machine deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to delete machine", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
