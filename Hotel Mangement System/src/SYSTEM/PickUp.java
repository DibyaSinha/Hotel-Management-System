package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;
public class PickUp extends JFrame implements ActionListener {
    JTable table;
    JButton back , submit;
    Choice carType;
    JCheckBox available;

    public PickUp(){
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
//        Font textFont = new Font("Raleway" , Font.PLAIN , 16);

        JLabel text = new JLabel("Pick Up Service");
        text.setFont(new Font("Tahoma" , Font.BOLD , 20));
        text.setBounds(420 , 30 , 200 , 30);
        add(text);

        JLabel lblCarType = new JLabel("Car Name");
        lblCarType.setBounds(50 , 100 , 100 , 20);
        add(lblCarType);
        carType = new Choice();
        carType.setBounds(150 , 100 , 200 , 25);
        add(carType);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT DISTINCT company FROM driver");
            while(rs.next()){
                carType.add(rs.getString("company"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        available = new JCheckBox("Only display Available");
        available.setBounds(650 , 100 , 150 , 25);
        available.setBackground(Color.WHITE);
        add(available);

        addLabel("Name" , 30 , 170 , 100 , 20 );
        addLabel("Age" , 200 , 170 , 100 , 20);
        addLabel("Gender" , 330 , 170 , 100 , 20);
        addLabel("Company" , 460 , 170 , 100 , 20);
        addLabel("Brand" , 630 , 170 , 100 , 20);
        addLabel("Availability" , 740 , 170 , 100 , 20);
        addLabel("Location" , 890 , 170 , 100 , 20);

        table = new JTable();
        table.setBounds(0 , 200 , 1000 , 300);
        add(table);

        try{
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM driver");
            table.setModel(DbUtils.resultSetToTableModel(rs));

        }catch (Exception e){
            e.printStackTrace();
        }
        submit = createBtn("SUBMIT" , 300 , 520);
        submit.addActionListener(this);
        add(submit);

        back = createBtn("BACK" , 500 , 520);
        back.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(Reception::new);});
        add(back);

        setBounds(300 , 180 , 1000 , 600);
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
        if (ae.getSource() == submit){
            try {
                Conn conn = new Conn();
                ResultSet rs;

                String typeOfCar = (String) carType.getSelectedItem();

                if(available.isSelected()){
                    String query = "SELECT * FROM driver WHERE availabilty = 'Available' AND company = ?";
                    PreparedStatement pst = conn.c.prepareStatement(query);
                    pst.setString(1, typeOfCar);
                    rs = pst.executeQuery();
                } else {
                    String query = "SELECT * FROM driver WHERE company = ?";
                    PreparedStatement pst = conn.c.prepareStatement(query);
                    pst.setString(1, typeOfCar);
                    rs = pst.executeQuery();
                }

                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(PickUp::new);
//    }
}
