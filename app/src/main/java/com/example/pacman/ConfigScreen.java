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
    TextView difficultyDescription;
    String[] diffArr = {"3 Ghosts and Slow Speed", "4 Ghosts and Normal Speed", "5 Ghosts and High Speed"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_screen);

        difficultyLevel = (TextView) findViewById(R.id.difficultyLevel);
        moreDifficulty = (ImageButton) findViewById(R.id.moreDifficulty);
        lessDifficulty = (ImageButton) findViewById(R.id.lessDifficulty);
        difficultyDescription = (TextView) findViewById(R.id.difficultyDescription);
        difficultyDescription.setText(diffArr[difficulty]);
        moreDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (difficulty < 2) {
                    difficulty++;
                    difficultyLevel.setText(String.format("%d", difficulty));
                    difficultyDescription.setText(diffArr[difficulty]);
                }

            }
        });

        lessDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (difficulty > 0) {
                    difficulty--;
                    difficultyLevel.setText(String.format("%d", difficulty));
                    difficultyDescription.setText(diffArr[difficulty]);
                }

            }
        });
    }
}