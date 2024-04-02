package view;

import business.LoginManager;
import core.Helper;

import javax.swing.*;

public class LoginGUI extends Layout {
    private JPanel container;
    private JTextField txt_username_tcno;
    private JPasswordField passwordField1;
    private JPasswordField txt_password;
    private JButton btn_login;

    // We will call business operations from here, such as adding, deleting, etc.
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        this.loginManager = loginManager;

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
            add(container);
            setGUILayout(350, 250);

            // Action listener for the login button
            btn_login.addActionListener(e -> {
                // Check if the username/TC No or password fields are empty
                if (Helper.isFieldEmpty(txt_username_tcno) || Helper.isFieldEmpty(txt_password)) {
                    Helper.showMsg("Error", "Please fill in all fields");
                } else {
                    // Try to log in with the provided credentials
                    if (loginManager.login(txt_username_tcno.getText(), txt_password.getText())) {
                        dispose(); // Close the login window upon successful login
                    } else {
                        Helper.showMsg("Warning", "Incorrect username or password!!!");
                    }
                }
            });
        });
    }
}
