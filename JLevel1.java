package Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class JLevel1 extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton nextButton, backButton;
    private JLabel feedbackLabel;
    private JLabel timerLabel;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private Timer timer;
    private int timeLeft = 30; // 30 seconds for each question

    // Java-related quiz questions (Level 1)
    private String[] questions = {
        "What is the correct syntax to output \"Hello World\" in Java?",
        "Which of these data types is used to create a variable that should store text?",
        "Which method is used to find the length of a string in Java?",
        "Which keyword is used to create a class in Java?",
        "Which of the following is not a Java keyword?",
        "What is the default value of an int variable in Java?",
        "Which keyword is used for inheritance in Java?",
        "What does 'JVM' stand for?",
        "What is the size of a float variable in Java?",
        "Which of these is not an access modifier in Java?",
        "Which class is used to handle exceptions in Java?",
        "Which of these operators is used to compare two values?",
        "What is the correct way to declare an array in Java?",
        "Which of these statements is used to exit a loop in Java?",
        "What does the 'final' keyword do in Java?"
    };

    private String[][] optionsData = {
        {"System.out.println(\"Hello World\");", "echo(\"Hello World\");", "Console.WriteLine(\"Hello World\");", "print(\"Hello World\");"},
        {"int", "float", "String", "boolean"},
        {"getSize()", "getLength()", "length()", "size()"},
        {"class", "define", "create", "struct"},
        {"class", "public", "void", "main"},
        {"0", "null", "-1", "1"},
        {"inherits", "extends", "derives", "implements"},
        {"Java Virtual Machine", "Java Variable Memory", "Java Value Machine", "Java Version Manager"},
        {"4 bytes", "8 bytes", "2 bytes", "6 bytes"},
        {"public", "protected", "transient", "private"},
        {"Exception", "Error", "Throwable", "Handler"},
        {"=", "==", "!=", "<>"},
        {"int[] arr = new int[5];", "array int arr[5];", "int arr;", "arr[5]"},
        {"stop", "break", "exit", "leave"},
        {"Prevents inheritance", "Prevents overriding", "Both of the above", "None of the above"}
    };

    private int[] correctAnswers = {0, 2, 2, 0, 3, 0, 1, 0, 0, 2, 0, 1, 0, 1, 2}; // Indices of correct answers

    public JLevel1() {
        setTitle("Java Quiz Application - Level 1");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(245, 245, 220)); // Light beige color
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question:");
        add(questionLabel, BorderLayout.NORTH);

        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionsPanel.setBackground(new Color(245, 245, 220)); // Match background color
        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
            optionsPanel.add(options[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        // Bottom panel with feedback, timer, and buttons
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1));
        bottomPanel.setBackground(new Color(245, 245, 220));

        feedbackLabel = new JLabel("Feedback:");
        bottomPanel.add(feedbackLabel);

        timerLabel = new JLabel("Time left: 30 seconds");
        bottomPanel.add(timerLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(245, 245, 220));

        // Back button
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(new BackButtonListener());
        buttonPanel.add(backButton);

        // Next button
        nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(100, 40));
        nextButton.addActionListener(new NextButtonListener());
        buttonPanel.add(nextButton);

        bottomPanel.add(buttonPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        displayQuestion(currentQuestionIndex);
    }

    private void displayQuestion(int questionIndex) {
        questionLabel.setText("Q" + (questionIndex + 1) + ": " + questions[questionIndex]);
        optionGroup.clearSelection();
        feedbackLabel.setText("Feedback:");
        timerLabel.setText("Time left: 30 seconds");

        // Display options
        for (int i = 0; i < options.length; i++) {
            options[i].setText(optionsData[questionIndex][i]);
        }

        // Enable or disable back button
        backButton.setEnabled(questionIndex > 0);

        startTimer();
    }

    private void startTimer() {
        timeLeft = 30;
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + " seconds");
                if (timeLeft <= 0) {
                    timer.cancel();
                    checkAnswer(); // Automatically check answer if time runs out
                    moveToNextQuestion();
                }
            }
        }, 1000, 1000);
    }

    private void checkAnswer() {
        int selectedOption = -1;
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                selectedOption = i;
                break;
            }
        }

        if (selectedOption == correctAnswers[currentQuestionIndex]) {
            feedbackLabel.setText("Correct!");
            score++;
        } else {
            feedbackLabel.setText("Incorrect. The correct answer is " +
                    optionsData[currentQuestionIndex][correctAnswers[currentQuestionIndex]]);
        }
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            displayQuestion(currentQuestionIndex);
        } else {
            // Record the quiz attempt data before showing the score message
            QuizProgressTracker.addQuizAttempt("JLevel1", score, new Date());

            JOptionPane.showMessageDialog(null, "Quiz Over! Your score is: " + score + "/" + questions.length);
            System.exit(0);
        }
    }

    private void moveToPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            displayQuestion(currentQuestionIndex);
        }
    }

    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel();
            checkAnswer();
            moveToNextQuestion();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel();
            moveToPreviousQuestion();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JLevel1().setVisible(true);
        });
    }
}
