package Online.Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class WLevel4 extends JFrame {
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

    // Full-Stack Development questions for Level 4
    private String[] questions = {
        "What is the primary function of a RESTful API?",
        "Which of the following is a NoSQL database?",
        "What does the 'npm' command stand for?",
        "Which HTML5 feature allows for offline web applications?",
        "What is the role of a server in web development?",
    };

    private String[][] optionsData = {
        {"To transfer data between the client and server", "To store data", "To render HTML", "None of these"},
        {"MySQL", "MongoDB", "SQLite", "PostgreSQL"},
        {"Node Package Manager", "Node Program Manager", "No Package Manager", "None of these"},
        {"Local Storage", "Session Storage", "Web Workers", "None of these"},
        {"To host files", "To handle user requests", "To process data", "All of the above"},
    };

    private int[] correctAnswers = {0, 1, 0, 0, 3}; // Indices of correct answers

    public WLevel4() {
        setTitle("Web Development Quiz - Level 4");
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
        optionsPanel.setLayout(new GridLayout(5, 1));
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
        backButton.addActionListener(new BackButtonListener());
        buttonPanel.add(backButton);

        // Next button
        nextButton = new JButton("Next");
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
            new WLevel4().setVisible(true);
        });
    }
}
