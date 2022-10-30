package com.example.pacman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] difficultyLevels = {"Easy", "Normal", "Hard"};
    private String[] lives = {"5", "3", "2"};
    public Pacman player;
    GestureDetector gestureDetector;

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

        TextView livesText = findViewById(R.id.livesText);
        livesText.setText("Lives: " + lives[difficulty]);

        TextView playerNameText = findViewById(R.id.playerNameText);
        playerNameText.setText(name);

        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText(getString(R.string.score));

        TextView difficultText = findViewById(R.id.difficultText);
        difficultText.setText("Difficulty: " + difficultyLevels[difficulty]);

        TextView roundText = findViewById(R.id.roundText);
        roundText.setText(getString(R.string.round));

        ImageView spriteView = (ImageView) findViewById(R.id.spriteInGame);
        spriteView.setImageResource(getResources().getIdentifier("@android:drawable/" + sprite, null, getPackageName()));

        player = new Pacman(this, sprite);

        //adapted from https://stackoverflow.com/a/4098447/19170967
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() - e2.getX() > 220 && Math.abs(velocityX) > 240) {
                    Toast.makeText(GameActivity.this, "left", Toast.LENGTH_SHORT).show();
                    player.setDirection(2); //swipe left
                    return false;
                } else if (e2.getX() - e1.getX() > 220 && Math.abs(velocityX) > 240) {
                    player.setDirection(1); //swipe right
                    return false;
                }

                if (e1.getY() - e2.getY() > 200 && Math.abs(velocityY) > 240) {
                    player.setDirection(0); //swipe up
                    return false;
                } else if (e2.getY() - e1.getY() > 200 && Math.abs(velocityY) > 240) {
                    player.setDirection(3); //swipe down
                    return false;
                }
                return false;
            }
        });
        player = new Pacman(this, sprite);
        /*player.getImageView().setOnTouchListener((view, event) -> {
            gestureDetector.onTouchEvent(event);
            return false;
        });*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
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