package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.proteanit.sql.*;
public class CustomerInfo extends JFrame implements ActionListener {
    JTable table;
    JButton back;

    public CustomerInfo(){
        setTitle("Employee Info");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
//        Font textFont = new Font("Raleway" , Font.PLAIN , 16);


        addLabel("ID Type" , 10, 15 , 100 , 20 );
        addLabel("ID Number" , 130 , 15 , 100 , 20);
        addLabel("Name" , 290 , 15 , 100 , 20);
        addLabel("Gender" , 410 , 15 , 100 , 20);
        addLabel("Country" , 540 , 15 , 100 , 20);
        addLabel("Room Allotted" , 640 , 15 , 100 , 20);
        addLabel("Check In Time" , 760 , 15 , 100 , 20);
        addLabel("Deposit" , 900 , 15 , 100 , 20);

        table = new JTable();
        table.setBounds(0 , 40 , 1000 , 400);
        add(table);

        try{
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM customerDetails");
            table.setModel(DbUtils.resultSetToTableModel(rs));

        }catch (Exception e){
            e.printStackTrace();
        }

        back = createBtn("BACK" , 420 , 500);
        back.addActionListener(this);
        add(back);

        setBounds(300 , 200 , 1000 , 600);
        setVisible(true);
    }

    private void addImage(String path , int x , int y , int width , int height){
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        Image img = icon.getImage().getScaledInstance(width , height , Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(img));
        image.setBounds(x , y , width , height);
        add(image);
    }

    private void addLabel(String text , int x , int y , int width , int height ){
        JLabel label = new JLabel(text);
        label.setBounds(x , y , width , height);
        add(label);
    }

    private JButton createBtn (String text , int x , int y){
        JButton btn = new JButton(text);
        btn.setBounds(x , y , 120 , 30);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    public void actionPerformed(ActionEvent ae){
        setVisible(false);
        SwingUtilities.invokeLater(Reception::new);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(CustomerInfo::new);
//    }
}
