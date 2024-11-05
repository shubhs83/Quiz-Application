package Quiz.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLogin() {
        // Set up the frame
        setTitle("Admin Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create the UI components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JLabel messageLabel = new JLabel("");

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Validate login credentials
                if (username.equals("Admin") && password.equals("12345")) {
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Login Successful!");
                    
                    // Open the AdminQuizManager window
                    new AdminQuizManager(); // Open the quiz manager
                    dispose(); // Close the login window
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Invalid Username or Password");
                }
            }
        });

        // Set up the layout
        setLayout(new FlowLayout());
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(messageLabel);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the login form on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new AdminLogin());
    }
}
