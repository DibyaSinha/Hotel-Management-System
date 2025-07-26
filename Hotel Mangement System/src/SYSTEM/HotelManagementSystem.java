package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HotelManagementSystem extends JFrame implements ActionListener {
    JLabel text;
    Timer timer;

    public HotelManagementSystem() {
        setBounds(100, 100, 1366, 565);
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/first.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 1366, 565);
        add(image);

        text = new JLabel("HOTEL MANAGEMENT SYSTEM");
        text.setBounds(20, 460, 1000, 50);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("serif", Font.BOLD, 50));
        image.add(text);

        JButton next = new JButton("NEXT");
        next.setBounds(1150, 450, 100, 40);
        next.setFont(new Font("serif", Font.PLAIN, 15));
        next.setBackground(Color.WHITE);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        image.add(next);

        // Start blinking using Timer
        timer = new Timer(500, e -> text.setVisible(!text.isVisible()));
        timer.start();

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        timer.stop(); // Stop blinking before switching screen
        setVisible(false);
        new Login();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelManagementSystem::new);
    }
}
