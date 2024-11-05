
package Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class JLevel2 extends JFrame {
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

    // Advanced Java-related quiz questions (Level 2)
    private String[] questions = {
        "What is the purpose of the transient keyword in Java?",
        "Which method is used to create a new instance of a class in Java?",
        "What is the default value of a boolean variable in Java?",
        "Which of the following is a checked exception?",
        "What is the use of the synchronized keyword in Java?",
        "How many bits are used to represent a char value in Java?",
        "Which interface does java.util.HashMap implement?",
        "What is the result of 10 / 0 in Java?",
        "What does the 'super' keyword do?",
        "What is an anonymous class in Java?",
        "What is the function of the 'this' keyword?",
        "Which of these methods belong to the Object class?",
        "How do you start a thread in Java?",
        "What is a final class in Java?",
        "What is the purpose of a static block in Java?"
    };

    private String[][] optionsData = {
        {"Used for serialization", "Used for polymorphism", "Used for threading", "Used for inheritance"},
        {"new()", "Class.forName()", "newInstance()", "Object()"},
        {"true", "false", "null", "undefined"},
        {"NullPointerException", "IOException", "ArithmeticException", "ClassCastException"},
        {"To control access to a block of code by multiple threads", "To indicate inheritance", "To control serialization", "To prevent overriding"},
        {"8", "16", "32", "64"},
        {"List", "Map", "Set", "Queue"},
        {"Infinity", "Exception", "0", "Runtime Error"},
        {"Access superclass members", "Create a superclass", "Override superclass methods", "Prevent inheritance"},
        {"A class with no name", "A private inner class", "A subclass with no name", "A class with only static methods"},
        {"Refers to the current instance", "Refers to a static variable", "Refers to a superclass variable", "Creates a new instance"},
        {"toString()", "finalize()", "clone()", "All of the above"},
        {"By calling run()", "By calling start()", "By creating an instance", "By calling init()"},
        {"Cannot be extended", "Cannot be instantiated", "Cannot have methods", "Cannot have fields"},
        {"To initialize static variables", "To call instance methods", "To create a new class", "To prevent instantiation"}
    };

    private int[] correctAnswers = {0, 2, 1, 1, 0, 1, 1, 3, 0, 0, 0, 3, 1, 0, 0}; // Indices of correct answers

    public JLevel2() {
        setTitle("Java Quiz Application - Level 2");
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
        optionsPanel.setBackground(new Color(245, 245, 220));
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
            new JLevel2().setVisible(true);
        });
    }
}
