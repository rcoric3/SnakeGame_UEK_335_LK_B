package com.mygame.thesnakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.thesnakegame.R;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageButton exitButton = findViewById(R.id.buttonExit);
        ImageButton restartButton = findViewById(R.id.buttonRestart);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitGame(v);
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame(v);
            }
        });
    }

    private void exitGame(View v) {
        finish();
    }

    private void restartGame(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}