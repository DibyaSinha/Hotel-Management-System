package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddRoom extends JFrame implements ActionListener {
    private JTextField tfRoom, tfPrice;
    private JButton add, cancel;
    private JComboBox<String> availCombo, cleanCombo, bedCombo;

    public AddRoom() {
        setTitle("Add Room");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Add Rooms");
        heading.setFont(new Font("Tahoma", Font.BOLD, 18));
        heading.setBounds(150, 20, 200, 20);
        add(heading);

        addLabel("Room Number", 60, 80);
        tfRoom = createTextField(200, 80);
        add(tfRoom);

        addLabel("Available", 60, 130);
        availCombo = createComboBox(new String[]{"Available", "Occupy"}, 200, 130);
        add(availCombo);

        addLabel("Status", 60, 180);
        cleanCombo = createComboBox(new String[]{"Cleaned", "Dirty"}, 200, 180);
        add(cleanCombo);

        addLabel("Price", 60, 230);
        tfPrice = createTextField(200, 230);
        add(tfPrice);

        addLabel("Bed Type", 60, 280);
        bedCombo = createComboBox(new String[]{"Single Bed", "Double Bed"}, 200, 280);
        add(bedCombo);

        add = createButton("ADD ROOM", 60, 350);
        add.addActionListener(this);
        add(add);

        cancel = createButton("CANCEL", 220, 350);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/twelve.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(400, 30, 500, 300);
        add(image);

        setBounds(330, 220, 940, 470);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 120, 30);
        add(label);
    }

    private JTextField createTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 150, 30);
        return tf;
    }

    private JComboBox<String> createComboBox(String[] options, int x, int y) {
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setBounds(x, y, 150, 30);
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        btn.setBounds(x, y, 130, 30);
        return btn;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String roomNumber = tfRoom.getText().trim();
            String availability = (String) availCombo.getSelectedItem();
            String cleanStatus = (String) cleanCombo.getSelectedItem();
            String priceStr = tfPrice.getText().trim();
            String bedType = (String) bedCombo.getSelectedItem();

            if (roomNumber.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields.");
                return;
            }

            // Validate room number and price as numeric
            if (!roomNumber.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Room Number must be numeric.");
                return;
            }

            if (!priceStr.matches("\\d+(\\.\\d{1,2})?")) {
                JOptionPane.showMessageDialog(this, "Price must be a valid number.");
                return;
            }

            // Save to database
            Connection conn = null;
            PreparedStatement pst = null;
            try {
                Conn c = new Conn();
                conn = c.c;
                String query = "INSERT INTO room VALUES(?, ?, ?, ?, ?)";
                pst = conn.prepareStatement(query);
                pst.setString(1, roomNumber);
                pst.setString(2, availability);
                pst.setString(3, cleanStatus);
                pst.setString(4, priceStr);
                pst.setString(5, bedType);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Room Added Successfully");

                tfRoom.setText("");
                availCombo.setSelectedIndex(0);
                cleanCombo.setSelectedIndex(0);
                tfPrice.setText("");
                bedCombo.setSelectedIndex(0);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            } finally {
                try { if (pst != null) pst.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(AddRoom::new);
//    }
}
