package com.example.pacman;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;

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
    private Handler mHandler = new Handler();
    GestureDetector gestureDetector;
    Pacman pacman;
    Direction nextDirection = Direction.RIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        int difficulty = intent.getIntExtra("Difficulty", 0);
        String sprite = intent.getStringExtra("sprite_path");

        Pacman pacman = new Pacman();

        findViewById(R.id.up_button).setOnClickListener(v ->
                nextDirection = Direction.UP);
        findViewById(R.id.down_button).setOnClickListener(v ->
                nextDirection = Direction.DOWN);
        findViewById(R.id.left_button).setOnClickListener(v ->
                nextDirection = Direction.LEFT);
        findViewById(R.id.right_button).setOnClickListener(v ->
                nextDirection = Direction.RIGHT);


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


        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() - e2.getX() > 220 && Math.abs(velocityX) > 240) {
                    //Toast.makeText(GameActivity.this, "left", Toast.LENGTH_SHORT).show();
                    nextDirection = Direction.LEFT; //swipe left
                    //difficultText.setText("" +2);
                    return false;
                } else if (e2.getX() - e1.getX() > 220 && Math.abs(velocityX) > 240) {
                    nextDirection = Direction.RIGHT; //swipe right
                    //difficultText.setText("" +1);
                    return false;
                }

                if (e1.getY() - e2.getY() > 200 && Math.abs(velocityY) > 240) {
                    nextDirection = Direction.UP; //swipe up
                    //difficultText.setText("" +0);
                    return false;
                } else if (e2.getY() - e1.getY() > 200 && Math.abs(velocityY) > 240) {
                    //difficultText.setText("" +3);
                    nextDirection = Direction.DOWN; //swipe down
                    return false;
                }
                return false;
            }
        });

         /*mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    scoreText.setText("Score: " + pacman.score);
                }
            }
        };*/
        mGameView = findViewById(R.id.game_view);
        mGameView.init(pacman);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private void startGame() {
        final int delay = 1000 / FPS;
        new Thread(() -> {
            int count = 0;
            while (!mGameView.isGameOver()) {
                try {
                    Thread.sleep(delay);
                    if (count % SPEED == 0) {
                        //pacman.setNext_direction(nextDirection);
                        mGameView.next(nextDirection);
                        mHandler.post(mGameView::invalidate);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}