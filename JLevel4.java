
package Quiz.Application;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class JLevel4 extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton nextButton, backButton;
    private JLabel feedbackLabel;
    private JLabel timerLabel;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private Timer timer;
    private int timeLeft = 30; // 30 seconds per question

    // Advanced Java questions for Level 4
    private String[] questions = {
        "In Java, which of the following data types can hold the largest value?",
        "What does the 'AtomicInteger' class in Java provide?",
        "How can you create an immutable class in Java?",
        "What is a daemon thread in Java?",
        "Which of these operations is atomic in Java?",
        "What is the purpose of the 'CopyOnWriteArrayList' in Java?",
        "How does the Java Virtual Machine (JVM) manage method calls and local variables?",
        "What is a memory leak in Java?",
        "What is the purpose of the 'ExecutorService' interface?",
        "How does Java achieve polymorphism?",
        "Which class does not allow modifications to its elements?",
        "What is the purpose of the 'java.util.concurrent' package?",
        "How does 'volatile' differ from 'synchronized' in Java?",
        "What is a marker interface in Java?",
        "How does garbage collection work in Java?"
    };

    private String[][] optionsData = {
        {"long", "float", "double", "BigInteger"},
        {"Thread-safety", "Atomic operations on integers", "Non-blocking IO", "Memory management"},
        {"By making all fields final", "By using the 'immutable' keyword", "By making the class final", "By making fields private and no setters"},
        {"A background thread that runs indefinitely", "A thread with no priority", "A low-priority thread", "A thread that terminates when the JVM exits"},
        {"i++", "int x = y;", "long z = 1L;", "int a = 5;"},
        {"A synchronized list", "A thread-safe list that makes a fresh copy on write", "A list that throws exceptions on write", "A list that is read-only"},
        {"Using stack memory", "Using the heap", "Using a separate thread", "Using method references"},
        {"Unused objects taking up memory", "Memory used by Java processes", "Overloaded garbage collection", "Unreachable memory by the program"},
        {"To manage threads", "To handle multiple tasks", "To perform file operations", "To manage shared memory"},
        {"Encapsulation", "Inheritance", "Method overriding", "Interfaces"},
        {"LinkedList", "HashMap", "TreeMap", "Collections.unmodifiableList"},
        {"Concurrency utilities", "Networking tools", "Serialization framework", "Memory management utilities"},
        {"Provides memory consistency", "Provides locking", "Ensures atomicity", "Reduces memory usage"},
        {"An interface with no methods", "An interface with all methods private", "An abstract class", "An interface with default methods"},
        {"Releases unused memory automatically", "Requires manual release of memory", "Has no impact on memory", "Only works with local variables"}
    };

    private int[] correctAnswers = {3, 1, 3, 3, 3, 1, 0, 0, 0, 2, 3, 0, 0, 0, 0}; // Indices of correct answers

    public JLevel4() {
        setTitle("Java Quiz Application - Level 4");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(230, 230, 250)); // Lavender color
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question:");
        add(questionLabel, BorderLayout.NORTH);

        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionsPanel.setBackground(new Color(230, 230, 250));
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
        bottomPanel.setBackground(new Color(230, 230, 250));

        feedbackLabel = new JLabel("Feedback:");
        bottomPanel.add(feedbackLabel);

        timerLabel = new JLabel("Time left: 30 seconds");
        bottomPanel.add(timerLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(230, 230, 250));

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
            new JLevel4().setVisible(true);
        });
    }
}