package com.example.quiz_app;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private TextView scoreView, questionCount, questionText;
    private Button opt1, opt2, option3, opt4, nextBtn;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int correctAnswersCount = 0;
    private String selectedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        selectedLanguage = getIntent().getStringExtra("language");
        if (selectedLanguage == null) selectedLanguage = "General";

        TextView langTitle = findViewById(R.id.selected_lang_title);
        scoreView = findViewById(R.id.score_view);
        questionCount = findViewById(R.id.question_count);
        questionText = findViewById(R.id.question_text);

        opt1 = findViewById(R.id.option1);
        opt2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        opt4 = findViewById(R.id.option4);
        nextBtn = findViewById(R.id.next_btn);

        langTitle.setText(String.format(Locale.getDefault(), "%s Quiz", selectedLanguage));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadQuestions();
        displayQuestion();

        View.OnClickListener optionClickListener = v -> {
            Button selectedOption = (Button) v;
            checkAnswer(selectedOption);
        };

        opt1.setOnClickListener(optionClickListener);
        opt2.setOnClickListener(optionClickListener);
        option3.setOnClickListener(optionClickListener);
        opt4.setOnClickListener(optionClickListener);

        nextBtn.setOnClickListener(v -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                resetOptions();
                displayQuestion();
            } else {
                showResult();
            }
        });
    }

    private void loadQuestions() {
        questionList = new ArrayList<>();
        if ("Java".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What is the default value of boolean?", "true", "false", "0", "null", "false"));
            questionList.add(new Question("Which of these is not a keyword?", "class", "interface", "extends", "Integer", "Integer"));
            questionList.add(new Question("Size of int in Java?", "16 bit", "32 bit", "64 bit", "8 bit", "32 bit"));
            questionList.add(new Question("What will be the output? (System.out.println(10 + 20 + \"Java\");)","30Java","Java30","1020Java","Error","30Java"));
            questionList.add(new Question("Which data type is used to store decimal numbers?","int","float","char","boolean","float"));
            questionList.add(new Question("What is the result of:(int x = 5;\n" + "System.out.println(x++);)","5","6","Error","4","5"));
            questionList.add(new Question("Which keyword is used for decision making in Java?","loop","if","check","select","if"));
            questionList.add(new Question("Which loop is guaranteed to execute at least once?","for loop","while loop","do-while loop","none","do-while loop"));
            questionList.add(new Question("Which OOP concept is used to hide data in Java?","Inheritance","Encapsulation","Polymorphism","Abstraction","Encapsulation"));
            questionList.add(new Question("Which of the following is used to create an object in Java?","class","new keyword","object","create","new keyword"));
        } else if ("Python".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("How do you start a comment in Python?", "//", "/*", "#", "--", "#"));
            questionList.add(new Question("Which data type is immutable?", "List", "Set", "Dictionary", "Tuple", "Tuple"));
            questionList.add(new Question("What will be the output? (print(10 + 20, \"Python\"))","30 Python","1020 Python","Python 30","Error","30 Python"));
            questionList.add(new Question("Which data type is used to store text?","int","str","float","bool","str"));
            questionList.add(new Question("What is the output? (x = 5\n" + "print(x ** 2))","10","25","5","Error","25"));
            questionList.add(new Question("Which keyword is used for condition checking?","check","if","when","condition","if"));
            questionList.add(new Question("Which loop is used when the number of iterations is known?","while","loop","do-while","for","for"));
            questionList.add(new Question("Which symbol is used to create a list?","{}","()","<>","[]","[]"));
            questionList.add(new Question("Which keyword is used to define a function in Python?","function","define","def","fun","def"));
            questionList.add(new Question("What will be the output? (print(\"Hello\" * 3))","HelloHelloHello","Hello 3","Error","HelloHello","HelloHelloHello"));
        } else if ("Kotlin".equalsIgnoreCase(selectedLanguage)){
            questionList.add(new Question("What keyword is used to declare a variable in Kotlin?", "var", "int", "let", "define", "var"));
            questionList.add(new Question("Which keyword is used to declare a constant in Kotlin?", "val", "var", "const", "final", "val"));
            questionList.add(new Question("Kotlin is developed by which company?", "Google", "JetBrains", "Microsoft", "Apple", "JetBrains"));
            questionList.add(new Question("Which function is the entry point of a Kotlin program?", "start()", "main()", "run()", "init()", "main()"));
            questionList.add(new Question("What is the default visibility modifier in Kotlin?", "private", "public", "protected", "internal", "public"));
            questionList.add(new Question("Which symbol is used for string templates in Kotlin?", "$", "#", "@", "&", "$"));
            questionList.add(new Question("Which keyword is used to create a class in Kotlin?", "class", "object", "define", "new", "class"));
            questionList.add(new Question("Which keyword is used for null safety in Kotlin?", "nullsafe", "nullable", "?", "!", "?"));
            questionList.add(new Question("Which function is used to print output in Kotlin?", "print()", "echo()", "System.out.println()", "display()", "print()"));
            questionList.add(new Question("Kotlin is mainly used for which platform?", "iOS", "Web only", "Android", "Game consoles", "Android"));
        } else if ("C++".equalsIgnoreCase(selectedLanguage)){
            questionList.add(new Question("What is the output of: int x = 10; std::cout << x++ + ++x;", "21", "22", "20", "Undefined Behavior", "Undefined Behavior"));
            questionList.add(new Question("Which concept allows the same function name with different implementations at compile time?", "Encapsulation", "Runtime Polymorphism", "Function Overloading", "Abstraction", "Function Overloading"));
            questionList.add(new Question("What does the 'mutable' keyword do in C++?", "Makes variable constant", "Allows modification in const object", "Makes variable static", "Prevents modification", "Allows modification in const object"));
            questionList.add(new Question("Which of the following is NOT a type of inheritance in C++?", "Single", "Multiple", "Hierarchical", "Sequential", "Sequential"));
            questionList.add(new Question("What is the purpose of 'virtual' keyword in C++?", "Compile-time binding", "Runtime polymorphism", "Memory allocation", "Function hiding", "Runtime polymorphism"));
            questionList.add(new Question("What will happen if a base class destructor is not virtual?", "Memory leak", "Compile error", "Runtime error always", "No issue", "Memory leak"));
            questionList.add(new Question("Which operator cannot be overloaded in C++?", "::", "+", "[]", "()", "::"));
            questionList.add(new Question("What is the size of an empty class in C++?", "0", "1", "2", "Depends on compiler", "1"));
            questionList.add(new Question("What is 'RAII' in C++?", "Run-time Allocation and Initialization", "Resource Acquisition Is Initialization", "Random Access Initialization Interface", "Recursive Allocation Inheritance", "Resource Acquisition Is Initialization"));
            questionList.add(new Question("Which feature ensures only one instance of a class exists?", "Factory", "Singleton", "Adapter", "Observer", "Singleton"));
        } else if ("C".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What is the output of: printf(\"%d\", 5/2);", "2.5", "2", "3", "Error", "2"));
            questionList.add(new Question("Which header file is required for printf() function?", "stdlib.h", "stdio.h", "string.h", "math.h", "stdio.h"));
            questionList.add(new Question("What is the correct way to declare a pointer?", "int ptr;", "int *ptr;", "pointer ptr;", "int &ptr;", "int *ptr;"));
            questionList.add(new Question("What will be the output? int x = 5; printf(\"%d\", x++);", "6", "5", "Error", "0", "5"));
            questionList.add(new Question("Which loop is best when the number of iterations is unknown?", "for", "while", "do-while", "loop", "while"));
            questionList.add(new Question("What is the size of int in C (commonly)?", "2 bytes", "4 bytes", "8 bytes", "Depends on compiler", "4 bytes"));
            questionList.add(new Question("Which symbol is used to access value at address?", "&", "*", "#", "@", "*"));
            questionList.add(new Question("What is the output? printf(\"%d\", sizeof(char));", "0", "1", "2", "Depends", "1"));
            questionList.add(new Question("Which function is used to read a string in C?", "scanf()", "gets()", "fgets()", "All of the above", "All of the above"));
            questionList.add(new Question("What is the default return type of main() in C?", "void", "int", "char", "float", "int"));
        } else if ("JavaScript".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What is the output of: console.log(typeof null);", "null", "object", "undefined", "number", "object"));
            questionList.add(new Question("Which keyword is used to declare a block-scoped variable?", "var", "let", "define", "int", "let"));
            questionList.add(new Question("What will be the output? console.log(2 + '2');", "4", "22", "NaN", "Error", "22"));
            questionList.add(new Question("Which method is used to convert JSON to a JavaScript object?", "JSON.parse()", "JSON.stringify()", "JSON.convert()", "JSON.toObject()", "JSON.parse()"));
            questionList.add(new Question("What is the output of: console.log(0 == false);", "true", "false", "Error", "undefined", "true"));
            questionList.add(new Question("Which function is used to delay execution in JavaScript?", "setDelay()", "wait()", "setTimeout()", "delay()", "setTimeout()"));
            questionList.add(new Question("What will be the output? console.log(typeof NaN);", "NaN", "number", "undefined", "object", "number"));
            questionList.add(new Question("Which symbol is used for strict equality comparison?", "=", "==", "===", "!=", "==="));
            questionList.add(new Question("What is the output? console.log([] + []);", "[]", "0", "\"\"", "undefined", "\"\""));
            questionList.add(new Question("Which method adds an element to the end of an array?", "push()", "pop()", "shift()", "unshift()", "push()"));
        }
    }

    private void displayQuestion() {
        if (questionList.isEmpty()) return;
        
        Question q = questionList.get(currentQuestionIndex);
        questionText.setText(q.getQuestion());
        opt1.setText(q.getOpt1());
        opt2.setText(q.getOpt2());
        option3.setText(q.getOpt3());
        opt4.setText(q.getOpt4());
        
        String progress = String.format(Locale.getDefault(), "Question %d/%d", (currentQuestionIndex + 1), questionList.size());
        questionCount.setText(progress);
        nextBtn.setVisibility(View.GONE);
        
        // Entry Animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        findViewById(R.id.question_card).startAnimation(fadeIn);
    }

    private void checkAnswer(Button selected) {
        String answer = selected.getText().toString();
        String correct = questionList.get(currentQuestionIndex).getAnswer();

        if (answer.equals(correct)) {
            selected.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score += 10; // 10 points per correct answer
            correctAnswersCount++;
            scoreView.setText(String.format(Locale.getDefault(), "Score: %d", score));
        } else {
            selected.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            highlightCorrectAnswer(correct);
        }

        disableOptions();
        nextBtn.setVisibility(View.VISIBLE);
    }

    private void highlightCorrectAnswer(String correct) {
        if (opt1.getText().toString().equals(correct)) opt1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        else if (opt2.getText().toString().equals(correct)) opt2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        else if (option3.getText().toString().equals(correct)) option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        else if (opt4.getText().toString().equals(correct)) opt4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
    }

    private void disableOptions() {
        opt1.setEnabled(false);
        opt2.setEnabled(false);
        option3.setEnabled(false);
        opt4.setEnabled(false);
    }

    private void resetOptions() {
        opt1.setEnabled(true);
        opt2.setEnabled(true);
        option3.setEnabled(true);
        opt4.setEnabled(true);
        ColorStateList defaultColor = ColorStateList.valueOf(Color.parseColor("#3A4F63"));
        opt1.setBackgroundTintList(defaultColor);
        opt2.setBackgroundTintList(defaultColor);
        option3.setBackgroundTintList(defaultColor);
        opt4.setBackgroundTintList(defaultColor);
    }

    private void showResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("total", questionList.size());
        intent.putExtra("correct", correctAnswersCount);
        intent.putExtra("score", score);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    static class Question {
        private final String question, opt1, opt2, opt3, opt4, answer;
        public Question(String q, String o1, String o2, String o3, String o4, String a) {
            this.question = q; this.opt1 = o1; this.opt2 = o2; this.opt3 = o3; this.opt4 = o4; this.answer = a;
        }
        public String getQuestion() { return question; }
        public String getOpt1() { return opt1; }
        public String getOpt2() { return opt2; }
        public String getOpt3() { return opt3; }
        public String getOpt4() { return opt4; }
        public String getAnswer() { return answer; }
    }
}
