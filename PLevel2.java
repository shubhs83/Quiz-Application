package Online.Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class PLevel2 extends JFrame {
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

    // Python questions for Level 2
    private String[] questions = {
        "What is the output of print('Hello' + 'World')?",
        "Which function is used to read input from the user?",
        "How do you create a list in Python?",
        "What is the keyword used to import modules in Python?",
        "Which of the following is used to create a for loop in Python?"
    };

    private String[][] optionsData = {
        {"A) HelloWorld", "B) Hello World", "C) Hello+World", "D) Error"},
        {"A) input()", "B) read()", "C) scan()", "D) get()"},
        {"A) list = {}", "B) list = []", "C) list = ()", "D) list = ''"},
        {"A) include", "B) import", "C) require", "D) load"},
        {"A) for i in range()", "B) foreach i in range()", "C) loop i in range()", "D) for i = range()"}
    };

    private int[] correctAnswers = {0, 0, 1, 1, 0}; // Indices of correct answers

    public PLevel2() {
        setTitle("Python Quiz Application - Level 2");
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
            new PLevel2().setVisible(true);
        });
    }
}
