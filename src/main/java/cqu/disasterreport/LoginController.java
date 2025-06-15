package cqu.disasterreport;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;

import java.security.MessageDigest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.IOException;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization if needed
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both fields.");
            return;
        }

        String hashedPassword = hashPassword(password);
        User user = new UserDAO().authenticate(username, hashedPassword);

        if (user != null) {
            messageLabel.setText("Login successful! Role: " + user.getRole());
            navigateToRole(user.getRole());
        } else {
            messageLabel.setText("Invalid credentials.");
        }
    }

    private void navigateToRole(String role) {
        try {
            switch (role) {
                case "Manager":
                    App.setRoot("manager");
                    break;
                case "Responder":
                    App.setRoot("responder");
                    break;
                case "Admin":
                    App.setRoot("admin"); // optional
                    break;
                default:
                    messageLabel.setText("Unknown role.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Navigation error.");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @FXML
    private void goToCivilian() throws IOException {
        App.setRoot("civilian");
    }



}
