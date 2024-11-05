package Online.Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class CSLevel3 extends JFrame {
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

    // Cyber Security questions for Level 3
    private String[] questions = {
        "What is the primary purpose of encryption?",
        "What is a zero-day exploit?",
        "What is a common type of social engineering attack?",
        "What is the role of a Security Information and Event Management (SIEM)?",
        "What is multi-factor authentication?",
        "What is a common vulnerability in web applications?",
        "What is the purpose of a vulnerability assessment?",
        "What is the principle of least privilege?",
        "What is a common type of cyber attack?",
        "What does GDPR stand for?"
    };

    private String[][] optionsData = {
        {"To protect data", "To speed up processes", "To increase data size", "To reduce costs"},
        {"An exploit that is known but not patched", "An exploit that is patched", "A type of virus", "None of the above"},
        {"Phishing", "Hacking", "Malware", "Ransomware"},
        {"To analyze security events", "To monitor network traffic", "To prevent attacks", "None of the above"},
        {"Using two or more methods to verify identity", "Using a single password", "An encryption method", "None of the above"},
        {"SQL Injection", "Password complexity", "User education", "Strong passwords"},
        {"To find weaknesses in systems", "To create malware", "To train employees", "None of the above"},
        {"Limiting user access to only what is necessary", "Giving all users admin access", "Monitoring user activities", "None of the above"},
        {"Denial of Service", "Data breaches", "Phishing", "All of the above"},
        {"General Data Protection Regulation", "Global Data Protection Regulation", "General Data Privacy Regulation", "None of the above"}
    };

    private int[] correctAnswers = {0, 0, 0, 0, 0, 0, 0, 0, 3, 0}; // Indices of correct answers

    public CSLevel3() {
        setTitle("Cyber Security Quiz - Level 3");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230, 230, 250)); // Lavender color
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question:");
        add(questionLabel, BorderLayout.NORTH);

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

        JPanel bottomPanel = new JPanel(new GridLayout(4, 1));
        bottomPanel.setBackground(new Color(230, 230, 250));

        feedbackLabel = new JLabel("Feedback:");
        bottomPanel.add(feedbackLabel);

        timerLabel = new JLabel("Time left: 30 seconds");
        bottomPanel.add(timerLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(230, 230, 250));

        backButton = new JButton("Back");
        backButton.addActionListener(new BackButtonListener());
        buttonPanel.add(backButton);

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

        for (int i = 0; i < options.length; i++) {
            options[i].setText(optionsData[questionIndex][i]);
        }

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
                    checkAnswer();
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
            new CSLevel3().setVisible(true);
        });
    }
}
