package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EndScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        findViewById(R.id.restartButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConfigScreen.class);
            startActivity(intent);
        });
        findViewById(R.id.backToTitle).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}