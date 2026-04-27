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
            questionList.add(new Question("What will be the output?\n System.out.println(10 + 20 + \"Java\");","30Java","Java30","1020Java","Error","30Java"));
            questionList.add(new Question("Which data type is used to store decimal numbers?","int","float","char","boolean","float"));
            questionList.add(new Question("What is the result of:\nint x = 5;\n" + "System.out.println(x++);","5","6","Error","4","5"));
            questionList.add(new Question("Which keyword is used for decision making in Java?","loop","if","check","select","if"));
            questionList.add(new Question("Which loop is guaranteed to execute at least once?","for loop","while loop","do-while loop","none","do-while loop"));
            questionList.add(new Question("Which OOP concept is used to hide data in Java?","Inheritance","Encapsulation","Polymorphism","Abstraction","Encapsulation"));
            questionList.add(new Question("Which of the following is used to create an object in Java?","class","new keyword","object","create","new keyword"));
        } else if ("Python".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("How do you start a comment in Python?", "//", "/*", "#", "--", "#"));
            questionList.add(new Question("Which data type is immutable?", "List", "Set", "Dictionary", "Tuple", "Tuple"));
            questionList.add(new Question("What will be the output? \nprint(10 + 20, \"Python\")","30 Python","1020 Python","Python 30","Error","30 Python"));
            questionList.add(new Question("Which data type is used to store text?","int","str","float","bool","str"));
            questionList.add(new Question("What is the output? \nx = 5\n" + "print(x ** 2)","10","25","5","Error","25"));
            questionList.add(new Question("Which keyword is used for condition checking?","check","if","when","condition","if"));
            questionList.add(new Question("Which loop is used when the number of iterations is known?","while","loop","do-while","for","for"));
            questionList.add(new Question("Which symbol is used to create a list?","{}","()","<>","[]","[]"));
            questionList.add(new Question("Which keyword is used to define a function in Python?","function","define","def","fun","def"));
            questionList.add(new Question("What will be the output? \nprint(\"Hello\" * 3)","HelloHelloHello","Hello 3","Error","HelloHello","HelloHelloHello"));
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
            questionList.add(new Question("What is the output of: \nint x = 10; std::cout << x++ + ++x;", "21", "22", "20", "Undefined Behavior", "Undefined Behavior"));
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
            questionList.add(new Question("What is the output of: \nprintf(\"%d\", 5/2);", "2.5", "2", "3", "Error", "2"));
            questionList.add(new Question("Which header file is required for printf() function?", "stdlib.h", "stdio.h", "string.h", "math.h", "stdio.h"));
            questionList.add(new Question("What is the correct way to declare a pointer?", "int ptr;", "int *ptr;", "pointer ptr;", "int &ptr;", "int *ptr;"));
            questionList.add(new Question("What will be the output? \nint x = 5; printf(\"%d\", x++);", "6", "5", "Error", "0", "5"));
            questionList.add(new Question("Which loop is best when the number of iterations is unknown?", "for", "while", "do-while", "loop", "while"));
            questionList.add(new Question("What is the size of int in C (commonly)?", "2 bytes", "4 bytes", "8 bytes", "Depends on compiler", "4 bytes"));
            questionList.add(new Question("Which symbol is used to access value at address?", "&", "*", "#", "@", "*"));
            questionList.add(new Question("What is the output? \nprintf(\"%d\", sizeof(char);", "0", "1", "2", "Depends", "1"));
            questionList.add(new Question("Which function is used to read a string in C?", "scanf()", "gets()", "fgets()", "All of the above", "All of the above"));
            questionList.add(new Question("What is the default return type of main() in C?", "void", "int", "char", "float", "int"));
        } else if ("JavaScript".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What is the output of: \nconsole.log(typeof null);", "null", "object", "undefined", "number", "object"));
            questionList.add(new Question("Which keyword is used to declare a block-scoped variable?", "var", "let", "define", "int", "let"));
            questionList.add(new Question("What will be the output? \nconsole.log(2 + '2');", "4", "22", "NaN", "Error", "22"));
            questionList.add(new Question("Which method is used to convert JSON to a JavaScript object?", "JSON.parse()", "JSON.stringify()", "JSON.convert()", "JSON.toObject()", "JSON.parse()"));
            questionList.add(new Question("What is the output of: \nconsole.log(0 == false);", "true", "false", "Error", "undefined", "true"));
            questionList.add(new Question("Which function is used to delay execution in JavaScript?", "setDelay()", "wait()", "setTimeout()", "delay()", "setTimeout()"));
            questionList.add(new Question("What will be the output? \nconsole.log(typeof NaN);", "NaN", "number", "undefined", "object", "number"));
            questionList.add(new Question("Which symbol is used for strict equality comparison?", "=", "==", "===", "!=", "==="));
            questionList.add(new Question("What is the output? \nconsole.log([] + []);", "[]", "0", "\"\"", "undefined", "\"\""));
            questionList.add(new Question("Which method adds an element to the end of an array?", "push()", "pop()", "shift()", "unshift()", "push()"));
        }else if ("TypeScript".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What is TypeScript mainly used for?", "Database management", "Static typing for JavaScript", "Game development only", "Networking", "Static typing for JavaScript"));
            questionList.add(new Question("Which company developed TypeScript?", "Google", "Microsoft", "Facebook", "Apple", "Microsoft"));
            questionList.add(new Question("What file extension does TypeScript use?", ".js", ".ts", ".java", ".html", ".ts"));
            questionList.add(new Question("Which command compiles TypeScript to JavaScript?", "tsc", "npm run", "node compile", "js run", "tsc"));
            questionList.add(new Question("Which keyword is used to define a variable in TypeScript?", "var only", "let and const", "int", "define", "let and const"));
            questionList.add(new Question("TypeScript is a superset of which language?", "Java", "Python", "JavaScript", "C++", "JavaScript"));
            questionList.add(new Question("Which feature is NOT in TypeScript?", "Interfaces", "Type annotations", "Static typing", "DOM only access", "DOM only access"));
            questionList.add(new Question("What is used to define data types in TypeScript?", "Interfaces", "Type annotations", "Classes", "Objects", "Type annotations"));
            questionList.add(new Question("Which symbol is used for type assertion in TypeScript?", "as", ":", "=>", "#", "as"));
            questionList.add(new Question("Which of the following is a valid TypeScript type?", "number", "integer", "decimal", "digit", "number"));
        }else if ("PHP".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What does PHP stand for?", "Personal Home Page", "Private Home Page", "PHP: Hypertext Preprocessor", "Pre Hypertext Processor", "PHP: Hypertext Preprocessor"));
            questionList.add(new Question("Which symbol is used to start a PHP variable?", "#", "$", "@", "&", "$"));
            questionList.add(new Question("Which function is used to print output in PHP?", "echo", "print()", "write()", "display()", "echo"));
            questionList.add(new Question("What is the correct way to end a PHP statement?", ".", ";", ":", ",", ";"));
            questionList.add(new Question("Which extension is used for PHP files?", ".html", ".php", ".js", ".css", ".php"));
            questionList.add(new Question("Which superglobal is used to collect form data?", "$_GET", "$_POST", "$_REQUEST", "All of the above", "All of the above"));
            questionList.add(new Question("Which operator is used for concatenation in PHP?", "+", ".", "&", "++", "."));
            questionList.add(new Question("Which function is used to count array elements?", "count()", "sizeof()", "length()", "array_count()", "count()"));
            questionList.add(new Question("PHP runs on which side?", "Client side", "Server side", "Database side", "Browser side", "Server side"));
            questionList.add(new Question("Which tag is used to start PHP code?", "<?php", "<php>", "<script>", "<?", "<?php"));
        }else if ("C#".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Who developed C# language?", "Google", "Microsoft", "Apple", "Oracle", "Microsoft"));
            questionList.add(new Question("Which framework is mainly used with C#?", "Django", "Spring", ".NET", "Laravel", ".NET"));
            questionList.add(new Question("What is the file extension of C# files?", ".java", ".cs", ".csharp", ".cpp", ".cs"));
            questionList.add(new Question("Which keyword is used to define a class in C#?", "class", "define", "struct", "object", "class"));
            questionList.add(new Question("Which method is the entry point in C#?", "start()", "main()", "Main()", "run()", "Main()"));
            questionList.add(new Question("Which symbol is used for single-line comments in C#?", "//", "#", "/* */", "<!-- -->", "//"));
            questionList.add(new Question("Which data type is used for text in C#?", "int", "char", "string", "float", "string"));
            questionList.add(new Question("Which keyword is used to create an object in C#?", "new", "create", "object", "make", "new"));
            questionList.add(new Question("Which operator is used for equality comparison in C#?", "=", "==", "===", "!=", "=="));
            questionList.add(new Question("C# is developed for which platform mainly?", "iOS", "Windows", "Linux only", "Android only", "Windows"));
        }else if ("Swift".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Who developed Swift language?", "Google", "Apple", "Microsoft", "Oracle", "Apple"));
            questionList.add(new Question("Swift is mainly used for?", "Web development", "iOS app development", "Game engines only", "Database design", "iOS app development"));
            questionList.add(new Question("Which keyword is used to declare a constant in Swift?", "var", "let", "const", "final", "let"));
            questionList.add(new Question("Which keyword is used to declare a variable in Swift?", "let", "var", "define", "int", "var"));
            questionList.add(new Question("What is the file extension for Swift files?", ".java", ".swift", ".sw", ".ios", ".swift"));
            questionList.add(new Question("Which symbol is used for optional values in Swift?", "?", "!", "#", "&", "?"));
            questionList.add(new Question("Which keyword is used to define a function in Swift?", "func", "function", "def", "method", "func"));
            questionList.add(new Question("Swift is mainly used with which IDE?", "Android Studio", "Xcode", "Eclipse", "NetBeans", "Xcode"));
            questionList.add(new Question("Which keyword is used for inheritance in Swift?", "extends", ":", "inherits", "super", ":"));
            questionList.add(new Question("Which type system does Swift use?", "Weak typing", "Dynamic typing", "Static typing", "No typing", "Static typing"));
        }else if ("Go".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Who developed Go language?", "Microsoft", "Apple", "Google", "IBM", "Google"));
            questionList.add(new Question("What is Go mainly used for?", "Mobile apps only", "System and backend development", "Game design only", "Frontend design", "System and backend development"));
            questionList.add(new Question("What is the file extension of Go files?", ".go", ".golang", ".g", ".goLang", ".go"));
            questionList.add(new Question("Which keyword is used to define a function in Go?", "function", "def", "func", "method", "func"));
            questionList.add(new Question("Which symbol is used for comments in Go?", "//", "#", "/* */", "<!-- -->", "//"));
            questionList.add(new Question("What is used to declare variables in Go?", "var", "let", "const only", "define", "var"));
            questionList.add(new Question("Which command is used to run a Go program?", "go run", "run go", "execute go", "start go", "go run"));
            questionList.add(new Question("Go is a ______ language.", "Interpreted", "Compiled", "Markup", "Query", "Compiled"));
            questionList.add(new Question("Which keyword is used for package declaration in Go?", "module", "package", "import", "main", "package"));
            questionList.add(new Question("Which function is the entry point in Go?", "start()", "Main()", "main()", "run()", "main()"));
        }else if ("Ruby".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Who created Ruby language?", "Yukihiro Matsumoto", "Guido van Rossum", "James Gosling", "Dennis Ritchie", "Yukihiro Matsumoto"));
            questionList.add(new Question("What is Ruby mainly used for?", "System programming", "Web development", "Mobile apps only", "Database design", "Web development"));
            questionList.add(new Question("What is the file extension of Ruby files?", ".rb", ".ruby", ".ru", ".rby", ".rb"));
            questionList.add(new Question("Which framework is commonly used with Ruby?", "Django", "Laravel", "Rails", "Spring", "Rails"));
            questionList.add(new Question("Which symbol is used for single-line comments in Ruby?", "//", "#", "/* */", "<!-- -->", "#"));
            questionList.add(new Question("Which keyword is used to define a method in Ruby?", "function", "def", "method", "func", "def"));
            questionList.add(new Question("Which keyword is used to end a method in Ruby?", "end", "stop", "finish", "close", "end"));
            questionList.add(new Question("Ruby is which type of language?", "Compiled", "Interpreted", "Machine language", "Assembly", "Interpreted"));
            questionList.add(new Question("Which data type is NOT in Ruby?", "Integer", "String", "Boolean", "CharType", "CharType"));
            questionList.add(new Question("Which keyword is used to create a class in Ruby?", "class", "define", "object", "struct", "class"));
        }else if ("R".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What is R mainly used for?", "Web development", "Data analysis and statistics", "Game development", "Mobile apps", "Data analysis and statistics"));
            questionList.add(new Question("Who developed R language?", "Google", "Microsoft", "Ross Ihaka and Robert Gentleman", "Oracle", "Ross Ihaka and Robert Gentleman"));
            questionList.add(new Question("What is the file extension of R scripts?", ".r", ".R", ".rs", "Both .r and .R", "Both .r and .R"));
            questionList.add(new Question("Which symbol is used for assignment in R?", "=", "<-", "->", ":=", "<-"));
            questionList.add(new Question("Which function is used to display output in R?", "print()", "echo()", "show()", "display()", "print()"));
            questionList.add(new Question("R is mainly used in which field?", "Web design", "Data science", "Game design", "Networking", "Data science"));
            questionList.add(new Question("Which data structure is commonly used in R?", "Array", "Data Frame", "Stack", "Queue", "Data Frame"));
            questionList.add(new Question("Which command is used to install packages in R?", "install()", "install.packages()", "get.packages()", "load()", "install.packages()"));
            questionList.add(new Question("R is a ______ language.", "Low-level", "Statistical computing", "Assembly", "Markup", "Statistical computing"));
            questionList.add(new Question("Which keyword is used to create a function in R?", "function", "def", "func", "method", "function"));
        }else if ("SQL".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What does SQL stand for?", "Structured Query Language", "Simple Query Language", "Structured Question Language", "System Query Language", "Structured Query Language"));
            questionList.add(new Question("Which SQL statement is used to fetch data?", "GET", "SELECT", "FETCH", "SHOW", "SELECT"));
            questionList.add(new Question("Which keyword is used to insert data into a table?", "ADD", "INSERT INTO", "UPDATE", "APPEND", "INSERT INTO"));
            questionList.add(new Question("Which SQL clause is used to filter records?", "ORDER BY", "GROUP BY", "WHERE", "HAVING", "WHERE"));
            questionList.add(new Question("Which command is used to delete all records in a table?", "REMOVE", "DROP", "DELETE", "CLEAR", "DELETE"));
            questionList.add(new Question("Which SQL keyword is used to sort results?", "SORT BY", "ORDER BY", "GROUP BY", "ARRANGE", "ORDER BY"));
            questionList.add(new Question("Which symbol is used to select all columns?", "#", "*", "&", "@", "*"));
            questionList.add(new Question("Which command removes a table completely?", "DELETE TABLE", "DROP TABLE", "REMOVE TABLE", "CLEAR TABLE", "DROP TABLE"));
            questionList.add(new Question("Which SQL statement is used to update data?", "MODIFY", "CHANGE", "UPDATE", "SET", "UPDATE"));
            questionList.add(new Question("Which clause is used to group rows?", "ORDER BY", "GROUP BY", "SORT BY", "COLLECT BY", "GROUP BY"));
        }else if ("Dart".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Who developed Dart language?", "Google", "Microsoft", "Apple", "Oracle", "Google"));
            questionList.add(new Question("Dart is mainly used for?", "Backend development", "Flutter app development", "System programming", "Database design", "Flutter app development"));
            questionList.add(new Question("What is the file extension of Dart files?", ".dart", ".drt", ".d", ".flutter", ".dart"));
            questionList.add(new Question("Which framework uses Dart language?", "React Native", "Flutter", "Angular", "Spring", "Flutter"));
            questionList.add(new Question("Which keyword is used to define a variable in Dart?", "var", "let", "define", "int only", "var"));
            questionList.add(new Question("Which keyword is used to define a constant in Dart?", "let", "const", "finalize", "static", "const"));
            questionList.add(new Question("Dart is a ______ language.", "Interpreted only", "Compiled to native code", "Markup", "Query language", "Compiled to native code"));
            questionList.add(new Question("Which function is the entry point in Dart?", "start()", "main()", "run()", "execute()", "main()"));
            questionList.add(new Question("Which symbol is used for single-line comments in Dart?", "//", "#", "<!-- -->", "/* */", "//"));
            questionList.add(new Question("Dart is mainly optimized for which type of apps?", "Desktop apps", "Mobile apps", "Database apps", "Game servers only", "Mobile apps"));
        }else if ("Rust".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Who developed Rust language?", "Google", "Mozilla", "Microsoft", "Apple", "Mozilla"));
            questionList.add(new Question("Rust is mainly used for?", "Web design", "System programming", "Mobile apps only", "Database queries", "System programming"));
            questionList.add(new Question("What is the file extension of Rust files?", ".rs", ".rust", ".rt", ".ru", ".rs"));
            questionList.add(new Question("Which keyword is used to define a variable in Rust?", "var", "let", "define", "int", "let"));
            questionList.add(new Question("Which keyword is used for mutable variables in Rust?", "mut", "var", "change", "mutable", "mut"));
            questionList.add(new Question("Rust is known for which main feature?", "Garbage collection", "Memory safety without GC", "Dynamic typing", "Weak typing", "Memory safety without GC"));
            questionList.add(new Question("Which symbol is used for comments in Rust?", "//", "#", "/* */", "<!-- -->", "//"));
            questionList.add(new Question("Which function is the entry point in Rust?", "start()", "main()", "run()", "execute()", "main()"));
            questionList.add(new Question("Rust is a ______ language.", "Interpreted", "Compiled", "Markup", "Query", "Compiled"));
            questionList.add(new Question("Which keyword is used to define a function in Rust?", "function", "def", "fn", "func", "fn"));
        }else if ("Scala".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Scala runs on which platform?", "JVM", "CLR", "Node.js", "Android only", "JVM"));
            questionList.add(new Question("Who created Scala?", "James Gosling", "Martin Odersky", "Guido van Rossum", "Dennis Ritchie", "Martin Odersky"));
            questionList.add(new Question("Scala is a blend of which two programming styles?", "Procedural and Assembly", "OOP and Functional", "Logic and Query", "Markup and Script", "OOP and Functional"));
            questionList.add(new Question("What is the file extension of Scala files?", ".sc", ".scala", ".scl", ".java", ".scala"));
            questionList.add(new Question("Which keyword is used to define a variable in Scala?", "var", "let", "define", "int", "var"));
            questionList.add(new Question("Which keyword is used to define a constant in Scala?", "const", "val", "final", "static", "val"));
            questionList.add(new Question("Scala is mainly used with which framework?", "Spring Boot", "Apache Spark", "Django", "Laravel", "Apache Spark"));
            questionList.add(new Question("Which symbol is used for single-line comments in Scala?", "//", "#", "/* */", "<!-- -->", "//"));
            questionList.add(new Question("Which function is the entry point in Scala?", "start()", "main()", "run()", "execute()", "main()"));
            questionList.add(new Question("Scala supports which type of programming?", "Only OOP", "Only Functional", "Both OOP and Functional", "Only Procedural", "Both OOP and Functional"));
        }else if ("MATLAB".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What does MATLAB stand for?", "Matrix Laboratory", "Math Lab Tool", "Mathematical Language", "Matrix Language Tool", "Matrix Laboratory"));
            questionList.add(new Question("MATLAB is mainly used for?", "Web development", "Numerical computing and simulations", "Mobile apps", "Game development", "Numerical computing and simulations"));
            questionList.add(new Question("What is the file extension of MATLAB scripts?", ".mat", ".m", ".ml", ".matlab", ".m"));
            questionList.add(new Question("Which symbol is used for comments in MATLAB?", "//", "#", "%", "/* */", "%"));
            questionList.add(new Question("Which function is used to display output in MATLAB?", "echo()", "print()", "disp()", "show()", "disp()"));
            questionList.add(new Question("MATLAB is developed by which company?", "Google", "Microsoft", "MathWorks", "Oracle", "MathWorks"));
            questionList.add(new Question("Which data type is mainly used in MATLAB?", "Strings only", "Matrices and arrays", "Linked lists", "Trees", "Matrices and arrays"));
            questionList.add(new Question("Which command is used to clear the command window?", "clear", "clc", "reset", "clean", "clc"));
            questionList.add(new Question("MATLAB is a ______ language.", "Low-level", "High-level technical computing", "Markup", "Assembly", "High-level technical computing"));
            questionList.add(new Question("Which operator is used for matrix multiplication in MATLAB?", "*", ".*", "x", "mul", "*"));
        }else if ("Perl".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Who created Perl language?", "Dennis Ritchie", "Larry Wall", "Guido van Rossum", "James Gosling", "Larry Wall"));
            questionList.add(new Question("Perl is mainly used for?", "Mobile apps", "Text processing and scripting", "Game development", "Database design only", "Text processing and scripting"));
            questionList.add(new Question("What is the file extension of Perl files?", ".pl", ".perl", ".prl", ".p", ".pl"));
            questionList.add(new Question("Which symbol is used for variables in Perl?", "$", "#", "@", "&", "$"));
            questionList.add(new Question("Which symbol is used for arrays in Perl?", "$", "@", "%", "&", "@"));
            questionList.add(new Question("Which symbol is used for hashes in Perl?", "%", "@", "$", "#", "%"));
            questionList.add(new Question("Which keyword is used to define a subroutine in Perl?", "function", "def", "sub", "method", "sub"));
            questionList.add(new Question("Which symbol is used for comments in Perl?", "//", "#", "/* */", "<!-- -->", "#"));
            questionList.add(new Question("Perl is known as a ______ language.", "Compiled only", "Scripting language", "Markup language", "Assembly language", "Scripting language"));
            questionList.add(new Question("Which command is used to run a Perl script?", "perl script.pl", "run perl script", "execute script.pl", "start perl", "perl script.pl"));
        }else if ("Bash".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("What does Bash stand for?", "Basic Shell", "Bourne Again Shell", "Binary Shell", "Basic Application Shell", "Bourne Again Shell"));
            questionList.add(new Question("Bash is mainly used for?", "Web design", "Command-line scripting and automation", "Game development", "Mobile apps", "Command-line scripting and automation"));
            questionList.add(new Question("What is the file extension of Bash scripts?", ".bash", ".sh", ".bsh", ".script", ".sh"));
            questionList.add(new Question("Which symbol is used for comments in Bash?", "//", "#", "/* */", "<!-- -->", "#"));
            questionList.add(new Question("Which command is used to list files in Bash?", "show", "list", "ls", "dir", "ls"));
            questionList.add(new Question("Which command is used to print text in Bash?", "print", "echo", "write", "display", "echo"));
            questionList.add(new Question("Which symbol is used for variables in Bash?", "$", "#", "@", "&", "$"));
            questionList.add(new Question("Which command is used to change directory in Bash?", "cd", "chdir", "move", "dir", "cd"));
            questionList.add(new Question("Bash runs on which system?", "Windows only", "Linux/Unix", "Android only", "iOS only", "Linux/Unix"));
            questionList.add(new Question("Which command is used to give execute permission?", "chmod", "run", "exec", "permit", "chmod"));
        }else if ("Objective-C".equalsIgnoreCase(selectedLanguage)) {
            questionList.add(new Question("Objective-C is mainly used for?", "Android development", "iOS/macOS development", "Web development", "Game engines only", "iOS/macOS development"));
            questionList.add(new Question("Which company developed Objective-C?", "Google", "Apple", "Microsoft", "Oracle", "Apple"));
            questionList.add(new Question("What is the file extension of Objective-C files?", ".oc", ".objc", ".m", ".cobj", ".m"));
            questionList.add(new Question("Which symbol is used for method calls in Objective-C?", "->", "[ ]", "::", ".", "[ ]"));
            questionList.add(new Question("Objective-C is built on top of which language?", "Java", "C", "C++", "Python", "C"));
            questionList.add(new Question("Which keyword is used to define a class in Objective-C?", "class", "@interface", "define", "struct", "@interface"));
            questionList.add(new Question("Which keyword is used to implement a class in Objective-C?", "@implementation", "@class", "implement", "class", "@implementation"));
            questionList.add(new Question("Which symbol is used for comments in Objective-C?", "//", "#", "/* */", "<!-- -->", "//"));
            questionList.add(new Question("Objective-C is mainly used in which ecosystem?", "Android", "Apple ecosystem", "Windows", "Linux only", "Apple ecosystem"));
            questionList.add(new Question("Which keyword is used for importing headers in Objective-C?", "#include", "#import", "using", "import", "#import"));
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
