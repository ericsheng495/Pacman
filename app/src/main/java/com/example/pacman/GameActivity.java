package com.example.pacman;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] difficultyLevels = {"Easy", "Normal", "Hard"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        int difficulty = intent.getIntExtra("Difficulty", 0);
        String sprite = intent.getStringExtra("sprite_path");



        Button mainMenu = findViewById(R.id.mainButton);
        mainMenu.setOnClickListener(this);

        //TextView livesText = findViewById(R.id.livesText);
        //livesText.setText(getString(R.string.lives));

        TextView playerNameText = findViewById(R.id.playerNameText);
        playerNameText.setText(name);

        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText(getString(R.string.score));

        TextView difficultText = findViewById(R.id.difficultText);
        difficultText.setText("Difficulty: " + difficultyLevels[difficulty]);

        TextView roundText = findViewById(R.id.roundText);
        roundText.setText(getString(R.string.round));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.mainButton):
                //Switches back to MainActivity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                break;
            case (10):
                break;
        }
    }
}