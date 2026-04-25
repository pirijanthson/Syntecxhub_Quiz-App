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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private EditText name, email, phone, password, repassword;
    private Button signupBtn;
    private ImageView eyeBtn, eyeBtn2;
    private TextView loginLink;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private boolean isPassVisible = false;
    private boolean isRePassVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find views
        TextView title = findViewById(R.id.signup_title);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.Re_password);
        signupBtn = findViewById(R.id.signupBtn);
        eyeBtn = findViewById(R.id.eyeBtn);
        eyeBtn2 = findViewById(R.id.eyeBtn2);
        loginLink = findViewById(R.id.login_link);
        progressBar = findViewById(R.id.progressBar);

        // Animations
        Animation clickAnim = AnimationUtils.loadAnimation(this, R.anim.btn_click);
        Animation slideIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slideIn.setDuration(700);

        // Apply animations
        title.startAnimation(slideIn);
        name.startAnimation(slideIn);
        email.startAnimation(slideIn);
        phone.startAnimation(slideIn);
        findViewById(R.id.pass_container).startAnimation(slideIn);
        findViewById(R.id.repass_container).startAnimation(slideIn);
        signupBtn.startAnimation(slideIn);
        loginLink.startAnimation(slideIn);

        // Password toggles
        eyeBtn.setOnClickListener(v -> togglePassword(password, eyeBtn, "pass"));
        eyeBtn2.setOnClickListener(v -> togglePassword(repassword, eyeBtn2, "repass"));

        // Signup Button
        signupBtn.setOnClickListener(v -> {
            v.startAnimation(clickAnim);
            validateAndSignup();
        });

        // Login Link
        loginLink.setOnClickListener(v -> {
            v.startAnimation(clickAnim);
            startActivity(new Intent(signup.this, login.class));
            finish();
        });
    }

    private void togglePassword(EditText field, ImageView btn, String type) {
        boolean visible;
        if (type.equals("pass")) {
            isPassVisible = !isPassVisible;
            visible = isPassVisible;
        } else {
            isRePassVisible = !isRePassVisible;
            visible = isRePassVisible;
        }

        if (visible) {
            field.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        } else {
            field.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btn.setImageResource(android.R.drawable.ic_menu_view);
        }
        field.setSelection(field.getText().length());
    }

    private void validateAndSignup() {
        String fullName = name.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String phoneNo = phone.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String repass = repassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) { name.setError("Name required"); return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) { email.setError("Valid email required"); return; }
        if (phoneNo.length() < 10) { phone.setError("Valid phone required"); return; }
        if (pass.length() < 6) { password.setError("Password too short"); return; }
        if (!pass.equals(repass)) { Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show(); return; }

        // Start registration with Firebase
        setLoading(true);
        
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && mAuth.getCurrentUser() != null) {
                        // Store user data in Firestore
                        String userId = mAuth.getCurrentUser().getUid();
                        Map<String, Object> user = new HashMap<>();
                        user.put("fullName", fullName);
                        user.put("email", mail);
                        user.put("phone", phoneNo);
                        user.put("uid", userId);

                        db.collection("users").document(userId)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(signup.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(signup.this, login.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    setLoading(false);
                                    Toast.makeText(signup.this, "Database Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        setLoading(false);
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(signup.this, "Authentication failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            signupBtn.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            signupBtn.setEnabled(true);
        }
    }
}
