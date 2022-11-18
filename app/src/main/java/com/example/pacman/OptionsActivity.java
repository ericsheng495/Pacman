package com.example.pacman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button deleteScores = (Button) findViewById(R.id.deleteScoreButton);
        Button exitOption = (Button) findViewById(R.id.exitOptions);

        deleteScores.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
            builder.setMessage("Delete scores?");
            builder.setTitle("Confirm delete");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                // When the user click yes button then app will close
                deleteScoreboard();
            });

            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                // If user click no then dialog box is canceled.
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            // Show the Alert Dialog box
            alertDialog.show();
        });

        exitOption.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

    }

    private void deleteScoreboard() {
        File scoreFile = new File(getFilesDir(), "scores.scrs");

        if (scoreFile.exists()) {
            scoreFile.delete();
        }
        Toast.makeText(this, "Scores reset", Toast.LENGTH_SHORT).show();

    }
}