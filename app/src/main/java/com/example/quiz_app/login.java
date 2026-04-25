package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText email, password;
    private Button loginBtn, signupBtn;
    private ImageView eyeBtn;
    private ProgressBar progressBar;
    private boolean isPasswordVisible = false;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Handle window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        TextView title = findViewById(R.id.login_title);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        eyeBtn = findViewById(R.id.eyeBtn);
        progressBar = findViewById(R.id.progressBar);

        // Load animations
        Animation clickAnim = AnimationUtils.loadAnimation(this, R.anim.btn_click);
        Animation slideIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slideIn.setDuration(800);

        // Apply entry animations
        title.startAnimation(slideIn);
        email.startAnimation(slideIn);
        findViewById(R.id.pass_container).startAnimation(slideIn);
        loginBtn.startAnimation(slideIn);
        signupBtn.startAnimation(slideIn);

        // Password Visibility Toggle
        eyeBtn.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                eyeBtn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            } else {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyeBtn.setImageResource(android.R.drawable.ic_menu_view);
            }
            password.setSelection(password.getText().length());
        });

        // Login Button Click
        loginBtn.setOnClickListener(v -> {
            v.startAnimation(clickAnim);
            performLogin();
        });

        // Signup Button Click
        signupBtn.setOnClickListener(v -> {
            v.startAnimation(clickAnim);
            Intent intent = new Intent(login.this, signup.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void performLogin() {
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (TextUtils.isEmpty(mail)) {
            email.setError("Email is required");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Enter a valid email");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            password.setError("Password is required");
            return;
        }

        setLoading(true);

        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        setLoading(false);
                        String error = task.getException() != null ? task.getException().getMessage() : "Authentication failed";
                        Toast.makeText(login.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            loginBtn.setEnabled(true);
        }
    }
}
