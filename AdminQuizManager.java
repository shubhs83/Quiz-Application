package Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class ProgressDashboard extends JFrame {

    public ProgressDashboard() {
        setTitle("User Progress Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("User Progress", SwingConstants.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(heading, BorderLayout.NORTH);

        // Scroll pane for showing quiz history
        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        add(scrollPane, BorderLayout.CENTER);

        // Retrieve quiz history and display it
        displayQuizHistory(historyArea);

        setVisible(true);
    }

    private void displayQuizHistory(JTextArea historyArea) {
        Map<String, ArrayList<QuizAttempt>> quizHistory = QuizProgressTracker.getQuizHistory();
        
        for (String level : quizHistory.keySet()) {
            historyArea.append("Level: " + level + "\n");
            for (QuizAttempt attempt : quizHistory.get(level)) {
                historyArea.append("Score: " + attempt.getScore() + ", Date: " + attempt.getAttemptDate() + "\n");
            }
            historyArea.append("\n");
        }
    }

    public static void main(String[] args) {
        new ProgressDashboard();
    }
}
