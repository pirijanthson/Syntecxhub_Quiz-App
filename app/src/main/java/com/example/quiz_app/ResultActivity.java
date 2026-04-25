package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView finalScoreText = findViewById(R.id.final_score);
        TextView correctCountText = findViewById(R.id.correct_count);
        TextView wrongCountText = findViewById(R.id.wrong_count);
        Button homeBtn = findViewById(R.id.home_btn);
        MaterialCardView resultCard = findViewById(R.id.result_card);

        // Get results from intent
        int totalQuestions = getIntent().getIntExtra("total", 0);
        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = totalQuestions - correct;
        int score = getIntent().getIntExtra("score", 0);

        finalScoreText.setText(String.format(Locale.getDefault(), "Score: %d", score));
        correctCountText.setText(String.valueOf(correct));
        wrongCountText.setText(String.valueOf(wrong));

        // Smart Animations
        Animation slideUp = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slideUp.setDuration(800);
        resultCard.startAnimation(slideUp);

        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1500);
        homeBtn.startAnimation(fadeIn);

        // Navigation with animation
        homeBtn.setOnClickListener(v -> {
            Animation clickAnim = AnimationUtils.loadAnimation(this, R.anim.btn_click);
            v.startAnimation(clickAnim);
            
            v.postDelayed(() -> {
                Intent intent = new Intent(ResultActivity.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }, 200);
        });
    }
}
