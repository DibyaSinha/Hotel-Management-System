package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private JTextField username;
    private JPasswordField password;
    private JButton login, cancel;

    Login() {
        setTitle("Login");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setBounds(40, 20, 100, 30);
        userLabel.setForeground(Color.BLACK);
        userLabel.setFont(labelFont);
        add(userLabel);

        username = new JTextField("ADMIN");
        username.setBounds(150, 20, 150, 30);
        add(username);

        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setBounds(40, 70, 100, 30);
        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(labelFont);
        add(passLabel);

        password = new JPasswordField();
        password.setBounds(150, 70, 150, 30);
        add(password);

        login = new JButton("Login");
        login.setBounds(40, 150, 120, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        cancel = new JButton("Cancel");
        cancel.setBounds(180, 150, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(350, 10, 200, 200);
        add(image);

        setBounds(500, 200, 600, 300);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String user = username.getText().trim();
            String pass = new String(password.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;

            try {
                Conn c = new Conn();
                conn = c.c;

                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, user);
                pst.setString(2, pass);
                rs = pst.executeQuery();

                if (rs.next()) {
                    setVisible(false);
                    new Dashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception e) {}
                try { if (pst != null) pst.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
            new HotelManagementSystem();
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Login::new); // Safer UI thread launch
//    }
}
