package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;
public class SearchRoom extends JFrame implements ActionListener {
    JTable table;
    JButton back , submit;
    JComboBox<String> bedType;
    JCheckBox available;

    public SearchRoom(){
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
//        Font textFont = new Font("Raleway" , Font.PLAIN , 16);

        JLabel text = new JLabel("Search For Room");
        text.setFont(new Font("Tahoma" , Font.BOLD , 20));
        text.setBounds(420 , 30 , 200 , 30);
        add(text);

        JLabel lblBed = new JLabel("Bed Type");
        lblBed.setBounds(50 , 100 , 100 , 20);
        add(lblBed);
        bedType = new JComboBox<String>(new String[]{"Single Bed" , "Double Bed"});
        bedType.setBounds(150 , 100 , 150 , 25);
        bedType.setBackground(Color.WHITE);
        bedType.setForeground(Color.BLACK);
        add(bedType);

        available = new JCheckBox("Only display Available");
        available.setBounds(650 , 100 , 150 , 25);
        available.setBackground(Color.WHITE);
        add(available);

        addLabel("Room Number" , 50 , 170 , 100 , 20 );
        addLabel("Availability" , 270 , 170 , 100 , 20);
        addLabel("Status" , 450 , 170 , 100 , 20);
        addLabel("Price" , 670 , 170 , 100 , 20);
        addLabel("BedType" , 870 , 170 , 100 , 20);

        table = new JTable();
        table.setBounds(0 , 200 , 1000 , 300);
        add(table);

        try{
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM room");
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

                String selectedBedType = (String) bedType.getSelectedItem();

                if(available.isSelected()){
                    String query = "SELECT * FROM room WHERE availabilty = 'Available' AND bedType = ?";
                    PreparedStatement pst = conn.c.prepareStatement(query);
                    pst.setString(1, selectedBedType);
                    rs = pst.executeQuery();
                } else {
                    String query = "SELECT * FROM room WHERE bedType = ?";
                    PreparedStatement pst = conn.c.prepareStatement(query);
                    pst.setString(1, selectedBedType);
                    rs = pst.executeQuery();
                }

                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(SearchRoom::new);
//    }
}
