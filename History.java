package Online.Quiz.Application;

import javax.swing.*;
import java.awt.*;

public class HistoryPage extends JFrame {
    private Progress progress;

    public HistoryPage(Progress progress) {
        this.progress = progress;

        setTitle("History Page");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Fetching and displaying the history and scores
        historyArea.append("Quiz History:\n");
        for (String record : progress.history) {
            historyArea.append(record + "\n");
        }

        historyArea.append("\nOverall Progress:\n");
        historyArea.append("Java Attempts: " + progress.getJavaAttempts() + ", Score: " + progress.getJavaScore() + "\n");
        historyArea.append("Python Attempts: " + progress.getPythonAttempts() + ", Score: " + progress.getPythonScore() + "\n");
        historyArea.append("Web Development Attempts: " + progress.getWebAttempts() + ", Score: " + progress.getWebScore() + "\n");
        historyArea.append("Cyber Security Attempts: " + progress.getCSAttempts() + ", Score: " + progress.getCSScore() + "\n");

        add(new JScrollPane(historyArea), BorderLayout.CENTER);
        setVisible(true);
    }
}
