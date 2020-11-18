package ui;

import delegates.LoginDelegate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginOra extends JFrame{
    private JPanel panel1;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton button1;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JLabel statusLabel;

    private LoginDelegate delegate;
    private int attempts;
    private int MAX_ATTEMPTS = 3;
    

    public LoginOra() {
        super("Oracle Login");

    }

    public void show(LoginDelegate delegate) {
        this.delegate = delegate;
        attempts = 0;
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delegate.oraLogin(usernameTextField.getText(), String.valueOf(passwordField.getPassword()));
            }
        });

        this.setVisible(true);
    }


    public void loginFail() {
        attempts++;
        passwordField.setText("");
        statusLabel.setText("Login Failed");
    }

    public boolean hasReachedAttemptLimit() {
        return (attempts >= MAX_ATTEMPTS);
    }
}
