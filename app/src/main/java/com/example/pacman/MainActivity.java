package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets onClick function for PLAY and QUIT button
        //playButton onClick
        ((Button) findViewById(R.id.playButton)).setOnClickListener(this);
        //quitButton onClick
        ((Button) findViewById(R.id.quitButton)).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.playButton):
                //Switch to Config Screen, Game Screen for now.
                //Change GameActivity.class to Config class
                Intent intent = new Intent(getApplicationContext(), ConfigScreen.class);
                startActivity(intent);

                break;
            case (R.id.quitButton):
                //Quit game function, kills application
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
        }
    }
}
