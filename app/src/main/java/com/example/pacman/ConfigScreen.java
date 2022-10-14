package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ConfigScreen extends AppCompatActivity {

    int difficulty = 0;
    ImageButton moreDifficulty;
    ImageButton lessDifficulty;
    TextView difficultyLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_screen);

        difficultyLevel = (TextView) findViewById(R.id.difficultyLevel);
        moreDifficulty = (ImageButton) findViewById(R.id.moreDifficulty);
        lessDifficulty = (ImageButton) findViewById(R.id.lessDifficulty);

        moreDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (difficulty == 3) {
                    difficulty = 3;
                } else {
                    difficulty++;
                }
                difficultyLevel.setText(String.format("%d", difficulty));
            }
        });

        lessDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (difficulty == 0) {
                    difficulty = 0;
                } else {
                    difficulty--;
                }
                difficultyLevel.setText(String.format("%d", difficulty));
            }
        });
    }
}