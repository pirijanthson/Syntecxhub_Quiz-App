package com.example.quiz_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    private TextView userName;
    private GridView languageGrid;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    
    private List<Language> languages;
    private List<Language> filteredList;
    private LanguageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userName = findViewById(R.id.user_name);
        languageGrid = findViewById(R.id.language_grid);
        ImageView logoutBtn = findViewById(R.id.logout_btn);
        SearchView searchBar = findViewById(R.id.search_bar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Logout logic
        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(home.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(home.this, login.class));
            finish();
        });

        // Fetch user data
        fetchUserData();

        // Header animation
        Animation slideDown = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slideDown.setDuration(1000);
        findViewById(R.id.header).startAnimation(slideDown);
        findViewById(R.id.search_bar).startAnimation(slideDown);

        // Setup Grid
        setupLanguageGrid();

        // Search logic
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        filteredList.clear();
        for (Language item : languages) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void fetchUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("fullName");
                            userName.setText(name != null ? name : "User");
                        } else {
                            userName.setText("User");
                        }
                    })
                    .addOnFailureListener(e -> {
                        userName.setText("Welcome");
                        Toast.makeText(home.this, "Error fetching name", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Not logged in, redirect to login
            startActivity(new Intent(home.this, login.class));
            finish();
        }
    }

    private void setupLanguageGrid() {
        languages = new ArrayList<>();
        languages.add(new Language("Java", R.drawable.java));
        languages.add(new Language("Python", R.drawable.python));
        languages.add(new Language("Kotlin", R.drawable.kotlin));
        languages.add(new Language("C++", R.drawable.plus));
        languages.add(new Language("C", R.drawable.c));
        languages.add(new Language("JavaScript", R.drawable.js));
        languages.add(new Language("TypeScript", R.drawable.ts));
        languages.add(new Language("PHP", R.drawable.php));
        languages.add(new Language("C#", R.drawable.cha));
        languages.add(new Language("Swift", R.drawable.swift));
        languages.add(new Language("GO", R.drawable.go));
        languages.add(new Language("Ruby", R.drawable.ruby));
        languages.add(new Language("R", R.drawable.r));
        languages.add(new Language("SQL", R.drawable.sql));
        languages.add(new Language("Dart", R.drawable.dart));
        languages.add(new Language("Rust", R.drawable.rust));
        languages.add(new Language("Scala", R.drawable.scala));
        languages.add(new Language("MATLAB", R.drawable.matlab));
        languages.add(new Language("Perl", R.drawable.perl));
        languages.add(new Language("Bash", R.drawable.bash));
        languages.add(new Language("Objective-C", R.drawable.objective_c));

        filteredList = new ArrayList<>(languages);
        adapter = new LanguageAdapter(this, filteredList);
        languageGrid.setAdapter(adapter);

        languageGrid.setOnItemClickListener((parent, view, position, id) -> {
            // Click animation
            Animation clickAnim = AnimationUtils.loadAnimation(home.this, R.anim.btn_click);
            view.startAnimation(clickAnim);

            String selectedLang = filteredList.get(position).name;
            
            // Delay navigation slightly to allow animation to play
            view.postDelayed(() -> {
                Intent intent = new Intent(home.this, QuizActivity.class);
                intent.putExtra("language", selectedLang);
                startActivity(intent);
            }, 200);
        });
    }

    // Language Model
    static class Language {
        final String name;
        final int logo;

        Language(String name, int logo) {
            this.name = name;
            this.logo = logo;
        }
    }

    // Grid Adapter
    static class LanguageAdapter extends BaseAdapter {
        private final Context context;
        private final List<Language> languages;

        LanguageAdapter(Context context, List<Language> languages) {
            this.context = context;
            this.languages = languages;
        }

        @Override
        public int getCount() { return languages.size(); }

        @Override
        public Object getItem(int position) { return languages.get(position); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.language_item, parent, false);
            }

            Language current = languages.get(position);
            TextView nameText = view.findViewById(R.id.lang_name);
            ImageView logoImage = view.findViewById(R.id.lang_logo);

            nameText.setText(current.name);
            logoImage.setImageResource(current.logo);

            return view;
        }
    }
}
