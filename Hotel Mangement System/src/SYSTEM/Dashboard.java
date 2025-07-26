package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {
    JButton logOut;

    Dashboard() {
        setBounds(0, 0, 1550, 1000);
        setLayout(null);

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/third.jpg"));
        ImageIcon i2 = new ImageIcon(i1.getImage().getScaledInstance(1550, 1000, Image.SCALE_DEFAULT));
        JLabel image = new JLabel(i2);
        image.setBounds(0, 0, 1550, 1000);
        add(image);

        // Title Label
        JLabel text = new JLabel("THE TAJ GROUP OF HOTELS");
        text.setBounds(550, 80, 1000, 50);
        text.setFont(new Font("Tahoma", Font.PLAIN, 40));
        text.setForeground(Color.WHITE);
        image.add(text);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu hotel = new JMenu("HOTEL MANAGEMENT");
        hotel.setForeground(Color.BLACK);
        menuBar.add(hotel);

        JMenuItem reception = new JMenuItem("Reception");
        reception.setForeground(Color.BLACK);
        reception.addActionListener(e -> new Reception());
        hotel.add(reception);

        JMenu admin = new JMenu("Admin");
        admin.setForeground(Color.BLACK);
        menuBar.add(admin);

        JMenuItem addEmployee = new JMenuItem("ADD EMPLOYEE");
        addEmployee.addActionListener(e -> new AddEmployee());
        admin.add(addEmployee);

        JMenuItem addRooms = new JMenuItem("ADD ROOMS");
        addRooms.addActionListener(e -> new AddRoom());
        admin.add(addRooms);

        JMenuItem addDriver = new JMenuItem("ADD DRIVER");
        addDriver.addActionListener(e -> new AddDriver());
        admin.add(addDriver);

        // Log Out Button
        logOut = new JButton("LOG OUT");
        logOut.setBounds(1400, 770, 100, 30);
        logOut.setBackground(Color.WHITE);
        logOut.setActionCommand("LOG OUT");
        logOut.addActionListener(this);
        image.add(logOut);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("LOG OUT")){
            setVisible(false);
            SwingUtilities.invokeLater(Login::new);
        }
    }

//    public static void main(String[] args) {
//        new Dashboard();
//    }
}
