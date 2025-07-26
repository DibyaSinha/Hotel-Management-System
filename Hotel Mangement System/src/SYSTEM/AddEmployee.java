package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployee extends JFrame implements ActionListener {
    JTextField tfName, tfAge, tfSalary, tfContact, tfEmail, tfAdhaar;
    JRadioButton rbMale, rbFemale, rbOthers;
    JButton submit, cancel;
    JComboBox<String> cbJob;
    ButtonGroup bg;

    public AddEmployee() {
        setTitle("Add Employee");
        setLayout(null);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(60, 30, 120, 30);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(200, 30, 150, 30);
        add(tfName);

        JLabel lblAge = new JLabel("Age");
        lblAge.setBounds(60, 80, 120, 30);
        lblAge.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblAge);

        tfAge = new JTextField();
        tfAge.setBounds(200, 80, 150, 30);
        add(tfAge);

        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(60, 130, 120, 30);
        lblGender.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblGender);

        rbMale = new JRadioButton("Male");
        rbMale.setBounds(200, 130, 70, 30);
        rbMale.setFont(new Font("Tahoma", Font.PLAIN, 14));
        rbMale.setBackground(Color.WHITE);
        add(rbMale);

        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(280, 130, 80, 30);
        rbFemale.setBackground(Color.WHITE);
        add(rbFemale);

        rbOthers = new JRadioButton("Others");
        rbOthers.setBounds(370, 130, 80, 30);
        rbOthers.setBackground(Color.WHITE);
        add(rbOthers);

        bg = new ButtonGroup();
        bg.add(rbMale);
        bg.add(rbFemale);
        bg.add(rbOthers);

        JLabel lblJob = new JLabel("Job");
        lblJob.setBounds(60, 180, 120, 30);
        lblJob.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblJob);

        String[] jobs = { "Front Office", "Food & Beverages", "Food Production", "House Keeping", "Management" };
        cbJob = new JComboBox<>(jobs);
        cbJob.setBounds(200, 180, 150, 30);
        cbJob.setBackground(Color.WHITE);
        add(cbJob);

        JLabel lblSalary = new JLabel("Salary");
        lblSalary.setBounds(60, 230, 120, 30);
        lblSalary.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblSalary);

        tfSalary = new JTextField();
        tfSalary.setBounds(200, 230, 150, 30);
        add(tfSalary);

        JLabel lblContact = new JLabel("Contact");
        lblContact.setBounds(60, 280, 120, 30);
        lblContact.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblContact);

        tfContact = new JTextField();
        tfContact.setBounds(200, 280, 150, 30);
        add(tfContact);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(60, 330, 120, 30);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(200, 330, 150, 30);
        add(tfEmail);

        JLabel lblAdhaar = new JLabel("Adhaar No.");
        lblAdhaar.setBounds(60, 380, 120, 30);
        lblAdhaar.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblAdhaar);

        tfAdhaar = new JTextField();
        tfAdhaar.setBounds(200, 380, 150, 30);
        add(tfAdhaar);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setBounds(380, 430, 130, 30);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBounds(200, 430, 130, 30);
        cancel.addActionListener(e -> setVisible(false));
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/tenth.jpg"));
        Image i2 = i1.getImage().getScaledInstance(450, 450, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(400, 60, 450, 380);
        add(image);

        getContentPane().setBackground(Color.WHITE);
        setBounds(350, 200, 900, 540);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String name = tfName.getText();
        String age = tfAge.getText();
        String salary = tfSalary.getText();
        String phone = tfContact.getText();
        String email = tfEmail.getText();
        String adhaar = tfAdhaar.getText();

        String gender = null;
        if (rbMale.isSelected()) gender = "Male";
        else if (rbFemale.isSelected()) gender = "Female";
        else if (rbOthers.isSelected()) gender = "Others";

        String job = (String) cbJob.getSelectedItem();

        if (name.isEmpty() || age.isEmpty() || gender == null || job == null || salary.isEmpty() || phone.isEmpty()
                || email.isEmpty() || adhaar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
            return;
        }

        // Validation
        if (!age.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Enter valid numeric age.");
            return;
        }

        if (!salary.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(null, "Enter valid salary.");
            return;
        }

        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Enter a valid 10-digit contact number.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(null, "Enter a valid email address.");
            return;
        }

        if (!adhaar.matches("\\d{12}")) {
            JOptionPane.showMessageDialog(null, "Enter a valid 12-digit Aadhaar number.");
            return;
        }

        try {
            Conn conn = new Conn();

            // Check for duplicate Aadhaar
            String checkQuery = "SELECT * FROM employee WHERE adhaar = ?";
            PreparedStatement checkPst = conn.c.prepareStatement(checkQuery);
            checkPst.setString(1, adhaar);
            ResultSet rs = checkPst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Employee with this Aadhaar number already exists.");
                return;
            }

            String query = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.c.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, age);
            pst.setString(3, gender);
            pst.setString(4, job);
            pst.setString(5, salary);
            pst.setString(6, phone);
            pst.setString(7, email);
            pst.setString(8, adhaar);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Employee Added Successfully");

            // Reset fields
            tfName.setText("");
            tfAge.setText("");
            tfSalary.setText("");
            tfContact.setText("");
            tfEmail.setText("");
            tfAdhaar.setText("");
            bg.clearSelection();
            cbJob.setSelectedIndex(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(AddEmployee::new);
//    }
}
