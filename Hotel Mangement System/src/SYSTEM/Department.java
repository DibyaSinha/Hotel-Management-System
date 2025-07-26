package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;
public class Department extends JFrame implements ActionListener {

    JTable table;
    JButton back;

    public Department(){
        setTitle("Department Info");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
//        Font textFont = new Font("Raleway" , Font.PLAIN , 16);


        addLabel("Department" , 180 , 15 , 100 , 20 );
        addLabel("Budget" , 450 , 15 , 100 , 20);

        table = new JTable();
        table.setBounds(0 , 50 , 700 , 350);
        add(table);

        try{
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM department");
            table.setModel(DbUtils.resultSetToTableModel(rs));

        }catch (Exception e){
            e.printStackTrace();
        }

        back = createBtn("BACK" , 280 , 400);
        back.addActionListener(this);
        add(back);

        setBounds(380 , 220 , 700 , 480);
        setVisible(true);
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
//        SwingUtilities.invokeLater(Department::new);
//    }
}
