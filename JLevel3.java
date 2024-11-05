
package Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class JLevel3 extends JFrame {
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

    // Expert-level Java quiz questions (Level 3)
    private String[] questions = {
        "Which of the following is true about the volatile keyword in Java?",
        "What is the difference between HashMap and ConcurrentHashMap?",
        "What does the fork/join framework in Java do?",
        "Which feature was introduced in Java 8 to support functional programming?",
        "What is the purpose of the CountDownLatch class?",
        "What does the finalize() method do in Java?",
        "Which interface is implemented by the lambda expressions?",
        "What does the method reference operator (::) do in Java?",
        "Which type of memory is used for storing static variables?",
        "How does the 'compareTo' method work in Java?",
        "What is the effect of using the transient keyword with a field?",
        "What is the default access modifier for interface methods?",
        "What does the yield() method in Java do?",
        "How is the Stream API beneficial for data processing?",
        "What is the purpose of the 'volatile' keyword in Java?"
    };

    private String[][] optionsData = {
        {"Ensures atomicity", "Enables thread-safe lazy initialization", "Ensures visibility of changes across threads", "Prevents serialization"},
        {"HashMap is thread-safe", "ConcurrentHashMap is synchronized", "HashMap allows null keys", "ConcurrentHashMap is faster than HashMap"},
        {"Manages CPU resources", "Splits tasks into subtasks for parallel processing", "Improves I/O efficiency", "Enhances JVM garbage collection"},
        {"Streams API", "Method overloading", "Concurrency utilities", "Annotations"},
        {"To delay execution", "To synchronize threads until they all reach a common point", "To perform parallel tasks", "To create synchronized blocks"},
        {"Reclaims memory", "Terminates JVM", "Executes on object creation", "Is a constructor"},
        {"Serializable", "Comparable", "Comparator", "FunctionalInterface"},
        {"Accesses method references", "Copies method implementations", "Creates lambda expressions", "Clones objects"},
        {"Heap", "Stack", "Static", "Cache"},
        {"Compares based on hashcodes", "Uses bitwise comparison", "Lexicographically compares values", "Sorts values in descending order"},
        {"Prevents thread access", "Enables serialization", "Excludes a field from serialization", "Increases JVM memory"},
        {"Public", "Protected", "Private", "No modifier"},
        {"Starts a new thread", "Causes a thread to temporarily pause", "Terminates a thread", "Prioritizes a thread"},
        {"Facilitates single-threaded operations", "Simplifies complex, sequential data transformations", "Provides concurrent processing", "Optimizes memory usage"},
        {"Allows threads to bypass JVM locking", "Ensures safe publishing", "Enables lazy instantiation", "Optimizes loop execution"}
    };

    private int[] correctAnswers = {2, 1, 1, 0, 1, 0, 3, 0, 2, 2, 2, 0, 1, 1, 1}; // Indices of correct answers

    public JLevel3() {
        setTitle("Java Quiz Application - Level 3");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question:");
        add(questionLabel, BorderLayout.NORTH);

        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionsPanel.setBackground(new Color(240, 248, 255));
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
        bottomPanel.setBackground(new Color(240, 248, 255));

        feedbackLabel = new JLabel("Feedback:");
        bottomPanel.add(feedbackLabel);

        timerLabel = new JLabel("Time left: 30 seconds");
        bottomPanel.add(timerLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));

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
            new JLevel3().setVisible(true);
        });
    }
}
