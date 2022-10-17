<<<<<<< HEAD
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
=======
  package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

  public class ConfigScreen extends AppCompatActivity {
    int currentSprite = 0;
    String[] sprites = {
            "ic_menu_edit",
            "ic_menu_delete",
            "ic_menu_directions"
    };
>>>>>>> feature/config-screen-sprite
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_screen);
<<<<<<< HEAD

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
=======
        ImageView spritePrevious = (ImageView) findViewById(R.id.spritePrevious);
        ImageView spriteNext = (ImageView) findViewById(R.id.spriteNext);
        spriteNext.setClickable(true);
        spritePrevious.setClickable(true);
        spriteNext.setOnClickListener((view) -> setPlayerSprite(1));
        spritePrevious.setOnClickListener((view) -> setPlayerSprite(-1));
    }

    void setPlayerSprite(int next) {
        currentSprite += next;
        if (currentSprite < 0) {
            currentSprite = 2;
        } else {
            currentSprite = currentSprite%3;
        }

        ImageView sprite = (ImageView) findViewById(R.id.playerSprite);
        sprite.setImageResource(getResources().getIdentifier("@android:drawable/" + sprites[currentSprite], null, getPackageName()));
>>>>>>> feature/config-screen-sprite
    }
}