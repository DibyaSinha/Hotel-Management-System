package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddDriver extends JFrame implements ActionListener {
    JTextField tfName, tfAge, tfCompany, tfModel, tfLocation;
    JComboBox<String> genderCombo, availCombo;
    JButton add, cancel;

    AddDriver() {
        setTitle("Add Driver");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        Font labelFont = new Font("Tahoma" , Font.PLAIN , 16);

        JLabel heading = new JLabel("ADD DRIVER");
        heading.setFont(new Font("Tahoma", Font.BOLD, 18));
        heading.setBounds(150, 20, 200, 20);
        add(heading);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(labelFont);
        lblName.setBounds(60, 80, 120, 30);
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(200, 80, 120, 30);
        add(tfName);

        JLabel lblAge = new JLabel("Age");
        lblAge.setFont(labelFont);
        lblAge.setBounds(60, 120, 120, 30);
        add(lblAge);

        tfAge = new JTextField();
        tfAge.setBounds(200, 120, 120, 30);
        add(tfAge);

        JLabel lblGender = new JLabel("Gender");
        lblGender.setFont(labelFont);
        lblGender.setBounds(60, 160, 120, 30);
        add(lblGender);

        String[] genderOptions = { "Male", "Female", "Others" };
        genderCombo = new JComboBox<>(genderOptions);
        genderCombo.setBounds(200, 160, 120, 30);
        genderCombo.setBackground(Color.WHITE);
        add(genderCombo);

        JLabel lblCompany = new JLabel("Car Company");
        lblCompany.setFont(labelFont);
        lblCompany.setBounds(60, 200, 120, 30);
        add(lblCompany);

        tfCompany = new JTextField();
        tfCompany.setBounds(200, 200, 120, 30);
        add(tfCompany);

        JLabel lblModel = new JLabel("Car Model");
        lblModel.setFont(labelFont);
        lblModel.setBounds(60, 240, 120, 30);
        add(lblModel);

        tfModel = new JTextField();
        tfModel.setBounds(200, 240, 120, 30);
        add(tfModel);

        JLabel lblAvailability = new JLabel("Available");
        lblAvailability.setFont(labelFont);
        lblAvailability.setBounds(60, 280, 120, 30);
        add(lblAvailability);

        String[] availOptions = { "Available", "Not Available" };
        availCombo = new JComboBox<>(availOptions);
        availCombo.setBounds(200, 280, 120, 30);
        availCombo.setBackground(Color.WHITE);
        add(availCombo);

        JLabel lblLocation = new JLabel("Location");
        lblLocation.setFont(labelFont);
        lblLocation.setBounds(60, 320, 120, 30);
        add(lblLocation);

        tfLocation = new JTextField();
        tfLocation.setBounds(200, 320, 120, 30);
        add(tfLocation);

        add = new JButton("ADD");
        add.setForeground(Color.WHITE);
        add.setBackground(Color.BLACK);
        add.setBounds(60, 360, 130, 30);
        add.addActionListener(this);
        add(add);

        cancel = new JButton("CANCEL");
        cancel.setForeground(Color.WHITE);
        cancel.setBackground(Color.GRAY);
        cancel.setBounds(220, 360, 130, 30);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/eleven.jpg"));
        ImageIcon i2 = new ImageIcon(i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT));
        JLabel l15 = new JLabel(i2);
        l15.setBounds(400, 30, 500, 370);
        add(l15);

        setBounds(300, 200, 980, 470);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String name = tfName.getText().trim();
            String age = tfAge.getText().trim();
            String gender = (String) genderCombo.getSelectedItem();
            String company = tfCompany.getText().trim();
            String model = tfModel.getText().trim();
            String availability = (String) availCombo.getSelectedItem();
            String location = tfLocation.getText().trim();

            // Validation
            if (name.isEmpty() || age.isEmpty() || company.isEmpty() || model.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                return;
            }

            if (!age.matches("\\d{1,2}")) {
                JOptionPane.showMessageDialog(null, "Enter valid numeric age.");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO driver VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, age);
                pst.setString(3, gender);
                pst.setString(4, company);
                pst.setString(5, model);
                pst.setString(6, availability);
                pst.setString(7, location);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Driver added successfully!");

                // Reset Fields
                tfName.setText("");
                tfAge.setText("");
                tfCompany.setText("");
                tfModel.setText("");
                tfLocation.setText("");
                genderCombo.setSelectedIndex(0);
                availCombo.setSelectedIndex(0);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while adding driver.");
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(AddDriver::new);
//    }
}
