package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateRoom extends JFrame implements ActionListener {
    Choice cCustomer;
    JTextField tfRoom , tfAvailable , tfCleanSts;
    JButton check , update , back;

    public UpdateRoom(){
        setTitle("Update Room");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("Update Room Status");
        text.setFont(new Font("Tahoma" , Font.BOLD , 18));
        text.setBounds(30 , 20 , 250 , 30);
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

        JLabel lblroom = addLabel("Room Number" , 30 , 130);
        add(lblroom);
        tfRoom = createTextField(200 , 130);
        add(tfRoom);

        JLabel lblAvailable = addLabel("Availability" , 30 , 180);
        add(lblAvailable);
        tfAvailable = createTextField(200 , 180);
        add(tfAvailable);

        JLabel lblCleanSts = addLabel("Cleaning Status" , 30 , 230);
        add(lblCleanSts);
        tfCleanSts = createTextField(200 , 230);
        add(tfCleanSts);


        check = createBtn("CHECK" , 30 , 320);
        check.addActionListener(this);
        add(check);

        update = createBtn("UPDATE" , 150 , 320);
        update.addActionListener(this);
        add(update);

        back = createBtn("BACK" , 270 , 320);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/seventh.jpg"));
        ImageIcon i2 = new ImageIcon(i1.getImage().getScaledInstance(500 , 300 , Image.SCALE_DEFAULT));
        JLabel image = new JLabel(i2);

        image.setBounds(400 , 50 , 500 , 300);
        add(image);

        setBounds(300 , 200 , 980 , 450);
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
                }
                ResultSet rs2 = conn.s.executeQuery("SELECT * FROM room WHERE roomNumber = '" + tfRoom.getText() + "'");
                while (rs2.next()){
                    tfAvailable.setText(rs2.getString("availabilty"));
                    tfCleanSts.setText(rs2.getString("cleaningStatus"));

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (ae.getSource() == update){
            String number = cCustomer.getSelectedItem();
            String room = tfRoom.getText();
            String available = tfAvailable.getText();
            String clnStatus = tfCleanSts.getText();

            try{
                Conn conn = new Conn();
                String sql = "UPDATE room SET availabilty = ?, cleaningStatus = ? WHERE roomNumber = ?";
                PreparedStatement pst = conn.c.prepareStatement(sql);
                pst.setString(1, available);
                pst.setString(2, clnStatus);
                pst.setString(3, room);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null , "Data Updated");
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
//        SwingUtilities.invokeLater(UpdateRoom::new);
//    }
}
