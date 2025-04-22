package com.example.stage;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class REGISTER extends AppCompatActivity {

    EditText REGNAME, REGPASSWORD, REPASSWORD;
    Button REGISTER, BTNBACK;
    DBConnect dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


        dbHelper = new DBConnect(this);
        REGNAME = findViewById(R.id.REGNAME);
        REGPASSWORD = findViewById(R.id.REGPASSWORD);
        REPASSWORD = findViewById(R.id.REPASSWORD);
        REGISTER = findViewById(R.id.REGISTER);
        BTNBACK = findViewById(R.id.BTNBACK);


        REGISTER.setOnClickListener(v -> {
            String username = REGNAME.getText().toString().trim();
            String password = REGPASSWORD.getText().toString().trim();
            String rePassword = REPASSWORD.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(rePassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
                    dbHelper.adduser(db, new USER(0 ,username, password));
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        BTNBACK.setOnClickListener(v -> {
            // Use an Intent to go back to MainActivity
            Intent intent = new Intent(this, MainActivity.class); // Assuming your main activity is called MainActivity
            startActivity(intent);
            finish(); // Optional: close the current activity after navigating back.
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}