package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateCheckOut extends JFrame implements ActionListener {
    Choice cCustomer;
    JTextField tfRoom , tfName , tfCheckIn , tfPaid , tfPending;
    JButton check , update , back;

    public UpdateCheckOut(){
        setTitle("Update CheckOut");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("Update CheckOut");
        text.setFont(new Font("Tahoma" , Font.BOLD , 18));
        text.setBounds(90 , 20 , 200 , 30);
        text.setForeground(Color.BLACK);
        add(text);

        JLabel lblid = addLabel("Customer Id" , 30 , 80);
        add(lblid);
        cCustomer = new Choice();
        cCustomer.setBounds(200 , 80 , 150 , 25);
        cCustomer.setBackground(Color.WHITE);
        add(cCustomer);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM customerDetails");
            while (rs.next()){
                cCustomer.add(rs.getString("idNumber"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        JLabel lblroom = addLabel("Room Number" , 30 , 120);
        add(lblroom);
        tfRoom = createTextField(200 , 120);
        add(tfRoom);

        JLabel lblname = addLabel("Name" , 30 , 160);
        add(lblname);
        tfName = createTextField(200 , 160);
        add(tfName);

        JLabel lblCheckIn = addLabel("Check In Time" , 30 , 200);
        add(lblCheckIn);
        tfCheckIn = createTextField(200 , 200);
        add(tfCheckIn);

        JLabel lblPaid = addLabel("Paid Amount" , 30 , 240);
        add(lblPaid);
        tfPaid = createTextField(200 , 240);
        add(tfPaid);

        JLabel lblPending = addLabel("Pending Amount" , 30 , 280);
        add(lblPending);
        tfPending = createTextField(200 , 280);
        add(tfPending);

        check = createBtn("CHECK" , 30 , 340);
        check.addActionListener(this);
        add(check);

        update = createBtn("UPDATE" , 150 , 340);
        update.addActionListener(this);
        add(update);

        back = createBtn("BACK" , 270 , 340);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/nine.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(400 , 50 , 500 , 300);
        add(image);

        setBounds(300 , 200 , 980 , 500);
        setVisible(true);
    }

    private JLabel addLabel(String text , int x , int y){
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x , y , 100 , 20);
        return lbl;
    }

    private JTextField createTextField(int x, int y){
        JTextField tf = new JTextField();
        tf.setBounds(x , y , 150 , 25);
        return tf;
    }

    private JButton createBtn(String text , int x , int y){
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        btn.setBounds(x , y , 100 , 30);
        return btn;
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == check){
            String id = cCustomer.getSelectedItem();
            Conn conn = new Conn();
            ResultSet rs;

            try {
                String query = "SELECT * FROM customerDetails WHERE idNumber = ?";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1 , id);
                rs = pst.executeQuery();

                while (rs.next()){
                    tfRoom.setText(rs.getString("roomNo"));
                    tfName.setText(rs.getString("name"));
                    tfCheckIn.setText(rs.getString("checkIn"));
                    tfPaid.setText(rs.getString("deposite"));
                }
                ResultSet rs2 = conn.s.executeQuery("SELECT * FROM room WHERE roomNumber = '" + tfRoom.getText() + "'");
                while (rs2.next()){
                    String price = rs2.getString("price");
                    double amountPaid = Double.parseDouble(price) - Double.parseDouble(tfPaid.getText());
                    tfPending.setText(" " + amountPaid);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (ae.getSource() == update){
                String number = cCustomer.getSelectedItem();
                String room = tfRoom.getText();
                String name = tfName.getText();
                String checkIn = tfCheckIn.getText();
                String deposit = tfPaid.getText();

                try{
                    Conn conn = new Conn();
                    String sql = "UPDATE customerDetails SET roomNo = ?, name = ?, checkIn = ?, deposite = ? WHERE idNumber = ?";
                    PreparedStatement pst = conn.c.prepareStatement(sql);
                    pst.setString(1, room);
                    pst.setString(2, name);
                    pst.setString(3, checkIn);
                    pst.setString(4, deposit);
                    pst.setString(5, number);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null , "Data Updated");

                    tfRoom.setText("");
                    tfName.setText("");
                    tfCheckIn.setText("");
                    tfPaid.setText("");

                    setVisible(false);
                    SwingUtilities.invokeLater(Reception::new);
                }catch (Exception e){
                    e.printStackTrace();
                }
        }else if (ae.getSource() == back){
            setVisible(false);
            SwingUtilities.invokeLater(Reception::new);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(UpdateCheckOut::new);
//    }
}
