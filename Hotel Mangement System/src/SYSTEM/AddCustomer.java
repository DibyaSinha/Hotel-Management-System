package SYSTEM;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;
import java.sql.*;

public class AddCustomer extends JFrame implements ActionListener {
    private JComboBox<String> idCombo;
    private JTextField tfIdNo, tfName, tfCountry, tfDeposit;
    private JRadioButton rMale, rFemale;
    private Choice cRoom;
    private JLabel checkIn;
    private JButton add, back;

    public AddCustomer() {
        setTitle("Add Customer");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        Font textFont = new Font("Raleway", Font.PLAIN, 16);

        addLabel("NEW CUSTOMER FORM", 100, 20, 300, 30, new Font("Raleway", Font.BOLD, 20));
        addLabel("Customer ID", 35, 80, 100, 30, textFont);
        idCombo = createCombo(new String[]{"Adhaar Card", "Passport", "Driving Licence", "Voter-Id", "Govt ID"}, 200, 80);
        add(idCombo);

        addLabel("Id Number", 35, 120, 100, 30, textFont);
        tfIdNo = createTextField(200, 120);
        add(tfIdNo);

        addLabel("Name", 35, 160, 100, 30, textFont);
        tfName = createTextField(200, 160);
        add(tfName);

        addLabel("Gender", 35, 200, 100, 30, textFont);
        rMale = createRadioButton("Male", 200, 200);
        rFemale = createRadioButton("Female", 280, 200);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rMale);
        genderGroup.add(rFemale);

        add(rMale);
        add(rFemale);

        addLabel("Country", 35, 240, 100, 30, textFont);
        tfCountry = createTextField(200, 240);
        add(tfCountry);

        addLabel("Room No", 35, 280, 100, 30, textFont);
        cRoom = new Choice();
        populateAvailableRooms();
        cRoom.setBounds(200, 280, 150, 25);
        add(cRoom);

        addLabel("CheckIn Time", 35, 320, 100, 20, textFont);
        checkIn = new JLabel(" " + new Date());
        checkIn.setBounds(200, 315, 250, 25);
        checkIn.setFont(new Font("Raleway", Font.PLAIN, 14));
        add(checkIn);

        addLabel("Deposit", 35, 360, 100, 30, textFont);
        tfDeposit = createTextField(200, 360);
        add(tfDeposit);

        add = createButton("Add", 50, 420);
        add.addActionListener(this);
        add(add);

        back = createButton("Back", 200, 420);
        back.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(Reception::new);});
        add(back);

        addImage("Icons/fifth.png", 400, 50, 300, 400);

        setBounds(350, 200, 800, 550);
        setVisible(true);
    }

    private void addLabel(String text, int x, int y, int width, int height, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(font);
        add(label);
    }

    private JComboBox<String> createCombo(String[] items, int x, int y) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBounds(x, y, 150, 30);
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JTextField createTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 150, 30);
        return tf;
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setBounds(x, y, 120, 30);
        return btn;
    }

    private JRadioButton createRadioButton(String text, int x, int y) {
        JRadioButton btn = new JRadioButton(text);
        btn.setBounds(x, y, 80, 30);
        btn.setBackground(Color.WHITE);
        return btn;
    }

    private void addImage(String path, int x, int y, int width, int height) {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(img));
        image.setBounds(x, y, width, height);
        add(image);
    }

    private void populateAvailableRooms() {
        try (Conn conn = new Conn();
             ResultSet rs = conn.s.executeQuery("SELECT * FROM room WHERE availabilty = 'Available'")) {
            while (rs.next()) {
                cRoom.add(rs.getString("roomNumber"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String id = (String) idCombo.getSelectedItem();
            String number = tfIdNo.getText().trim();
            String name = tfName.getText().trim();
            String gender = rMale.isSelected() ? "Male" : rFemale.isSelected() ? "Female" : null;
            String country = tfCountry.getText().trim();
            String roomNo = cRoom.getSelectedItem();
            String time = checkIn.getText().trim();
            String deposit = tfDeposit.getText().trim();

            if (id.isEmpty() || number.isEmpty() || name.isEmpty() || gender == null ||
                    country.isEmpty() || roomNo.isEmpty() || time.isEmpty() || deposit.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill all the fields");
                return;
            }

            try {
                double depositValue = Double.parseDouble(deposit);
                saveCustomerDetails(id, number, name, gender, country, roomNo, time, depositValue);
                JOptionPane.showMessageDialog(null, "New Customer Added Successfully");
                idCombo.setSelectedIndex(0);
                tfIdNo.setText("");
                tfName.setText("");
                tfCountry.setText("");
//                checkIn.setText("");
                tfDeposit.setText("");


            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Deposit must be a valid number");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void saveCustomerDetails(String id, String number, String name, String gender, String country, String roomNo, String time, double deposit) {
        Conn conn = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;

        try {
            conn = new Conn();

            String query1 = "INSERT INTO customerDetails VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst1 = conn.c.prepareStatement(query1);
            pst1.setString(1, id);
            pst1.setString(2, number);
            pst1.setString(3, name);
            pst1.setString(4, gender);
            pst1.setString(5, country);
            pst1.setString(6, roomNo);
            pst1.setString(7, time);
            pst1.setDouble(8, deposit);
            pst1.executeUpdate();

            String query2 = "UPDATE room SET availabilty = 'Occupied' WHERE roomNumber = ?";
            pst2 = conn.c.prepareStatement(query2);
            pst2.setString(1, roomNo);
            pst2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst1 != null) pst1.close();
                if (pst2 != null) pst2.close();
                if (conn != null) conn.c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(AddCustomer::new);
//    }
}
