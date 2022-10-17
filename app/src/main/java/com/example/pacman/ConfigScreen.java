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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_screen);
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
    }
}