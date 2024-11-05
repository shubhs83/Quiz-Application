package Online.Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class PLevel1 extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton nextButton, backButton, viewProgressButton; // Added view progress button
    private JLabel feedbackLabel;
    private JLabel timerLabel;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private Timer timer;
    private int timeLeft = 30; // 30 seconds per question

    private Progress progress; // Instance of Progress class

    // Python questions for Level 1
    private String[] questions = {
        "What is Python?",
        "Which of the following is a valid Python data type?",
        "What is the output of print(10 + 5)?",
        "Which symbol is used for comments in Python?",
        "What keyword is used to declare a variable in Python?"
    };

    private String[][] optionsData = {
        {"A) A type of snake", "B) A programming language", "C) A type of food", "D) A car brand"},
        {"A) List", "B) Dictionary", "C) Tuple", "D) All of the above"},
        {"A) 10", "B) 15", "C) 105", "D) 10.5"},
        {"A) //", "B) #", "C) /*", "D) <!"}, 
        {"A) var", "B) let", "C) define", "D) None"}
    };

    private int[] correctAnswers = {1, 3, 1, 1, 3}; // Indices of correct answers

    public PLevel1() {
        // Initialize Progress tracking
        progress = new Progress(); // Initialize Progress object

        setTitle("Python Quiz Application - Level 1");
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
        JPanel bottomPanel = new JPanel(new GridLayout(5, 1)); // Changed to 5 rows for the new button
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

        // View Progress button
        viewProgressButton = new JButton("View Progress"); // New button for viewing progress
        viewProgressButton.setPreferredSize(new Dimension(120, 40));
        viewProgressButton.addActionListener(new ViewProgressButtonListener()); // Listener for progress
        buttonPanel.add(viewProgressButton);

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
        // Record attempt for this question
        progress.recordAttempt("Python Level 1", score); // Record the score in Progress

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

    // Listener for Next Button
    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel();
            checkAnswer();
            moveToNextQuestion();
        }
    }

    // Listener for Back Button
    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel();
            moveToPreviousQuestion();
        }
    }

    // Listener for View Progress Button
    private class ViewProgressButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ProgressPage(progress).setVisible(true); // Open progress page
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PLevel1().setVisible(true);
        });
    }
}
