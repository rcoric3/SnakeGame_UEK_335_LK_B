package com.mygame.thesnakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thesnakegame.R;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageButton homeScreenButton = findViewById(R.id.myHomeScreenButton);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("highScore", 0);
        TextView highScoreTextView = findViewById(R.id.textViewHighScore);
        highScoreTextView.setText("HIGH SCORE: " + highScore);
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextScreen();
            }
        });
    }

    private void goToNextScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}