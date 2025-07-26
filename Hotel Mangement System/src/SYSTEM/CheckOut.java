package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Date;

public class CheckOut extends JFrame implements ActionListener {
    Choice cCustomer;
    JLabel lblRoomNumber, lblCheckInTime, lblCheckOutTime;
    JButton btnCheckOut, btnBack, btnCheck;

    public CheckOut() {
        setTitle("Check Out");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel title = new JLabel("Check-Out");
        title.setBounds(100, 20, 150, 30);
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(title);

        JLabel lblId = addLabel("Customer Id", 30, 80);
        add(lblId);
        cCustomer = new Choice();
        cCustomer.setBounds(150, 80, 150, 30);
        cCustomer.setBackground(Color.WHITE);
        add(cCustomer);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM customerDetails");
            while (rs.next()) {
                cCustomer.add(rs.getString("idNumber"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblRoom = addLabel("Room Number", 30, 130);
        add(lblRoom);
        lblRoomNumber = addLabel("", 150, 130);
        add(lblRoomNumber);

        JLabel lblCheckIn = addLabel("Check-In Time", 30, 180);
        add(lblCheckIn);
        lblCheckInTime = addLabel("", 150, 180);
        lblCheckInTime.setBounds(150, 180, 250, 30);
        add(lblCheckInTime);

        JLabel lblCheckOut = addLabel("Check-Out Time", 30, 230);
        add(lblCheckOut);
        Date date = new Date();
        lblCheckOutTime = addLabel("" + date, 150, 230);
        lblCheckOutTime.setBounds(150, 230, 250, 30);
        add(lblCheckOutTime);

        btnCheck = createBtn("CHECK", 30, 310);
        btnCheck.addActionListener(this);
        add(btnCheck);

        btnCheckOut = createBtn("CHECK OUT", 170, 310);
        btnCheckOut.addActionListener(this);
        add(btnCheckOut);

        btnBack = createBtn("BACK", 630, 310);
        btnBack.addActionListener(e -> {
            setVisible(false);
            SwingUtilities.invokeLater(Reception::new);
        });
        add(btnBack);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/sixth.jpg"));
        ImageIcon i2 = new ImageIcon(i1.getImage().getScaledInstance(400, 250, Image.SCALE_DEFAULT));
        JLabel image = new JLabel(i2);
        image.setBounds(350, 50, 400, 250);
        add(image);

        setBounds(300, 200, 800, 400);
        setVisible(true);
    }

    private JLabel addLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 100, 30);
        return lbl;
    }

    private JButton createBtn(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setBounds(x, y, 120, 30);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnCheckOut) {
            String customerId = cCustomer.getSelectedItem();
            String query1 = "DELETE FROM customerDetails WHERE idNumber = '" + customerId + "'";
            String query2 = "UPDATE room SET availabilty = 'Available' WHERE roomNumber = '" + lblRoomNumber.getText() + "'";

            try {
                Conn conn = new Conn();
                conn.s.executeUpdate(query1);
                conn.s.executeUpdate(query2);

                JOptionPane.showMessageDialog(null, "Check Out Done");
                setVisible(false);
                SwingUtilities.invokeLater(Reception::new);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == btnCheck) {
            String customerId = cCustomer.getSelectedItem();
            try {
                Conn conn = new Conn();
                ResultSet rs = conn.s.executeQuery("SELECT * FROM customerDetails WHERE idNumber = '" + customerId + "'");
                if (rs.next()) {
                    lblRoomNumber.setText(rs.getString("roomNo"));
                    lblCheckInTime.setText(rs.getString("checkIn"));
                } else {
                    JOptionPane.showMessageDialog(null, "Customer details not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(CheckOut::new);
//    }
}
