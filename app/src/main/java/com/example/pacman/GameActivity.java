package com.example.pacman;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] difficultyLevels = {"Easy", "Normal", "Hard"};
    private String[] lives = {"5", "3", "2"};
    private GameView mGameView;
    private static int FPS = 5;
    private static int SPEED = 1;
    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        int difficulty = intent.getIntExtra("Difficulty", 0);
        String sprite = intent.getStringExtra("sprite_path");

        mGameView = findViewById(R.id.game_view);
        mGameView.init();

        findViewById(R.id.up_button).setOnClickListener(v ->
                mGameView.setDirection(Direction.UP));
        findViewById(R.id.down_button).setOnClickListener(v ->
                mGameView.setDirection(Direction.DOWN));
        findViewById(R.id.left_button).setOnClickListener(v ->
                mGameView.setDirection(Direction.LEFT));
        findViewById(R.id.right_button).setOnClickListener(v ->
                mGameView.setDirection(Direction.RIGHT));





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

        startGame();
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

    private void startGame() {
        final int delay = 1000 / FPS;
        new Thread(() -> {
            int count = 0;
            while (!mGameView.isGameOver()) {
                try {
                    Thread.sleep(delay);
                    if (count % SPEED == 0) {
                        mGameView.next();
                        mHandler.post(mGameView::invalidate);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}