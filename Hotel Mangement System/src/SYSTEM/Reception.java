package SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Reception extends JFrame implements ActionListener {
    JButton newCustomerBtn, roomBtn , deptBtn, allEmpBtn,customInfoBtn , managerInfoBtn , checkoutBtn ,
            updateStatusBtn , updateRoomBtn , pickupBtn , searchRoomBtn , logOutBtn;

    public Reception(){
        setTitle("Reception");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        newCustomerBtn = createButton("New Customer Form" , 20 , 30);
        newCustomerBtn.addActionListener(e ->{setVisible(false); SwingUtilities.invokeLater(AddCustomer::new);});
        add(newCustomerBtn);

        roomBtn = createButton("Rooms" , 20 , 70);
        roomBtn.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(Room::new);});
        add(roomBtn);

        deptBtn = createButton("Department" , 20 , 110);
        deptBtn.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(Department::new);});
        add(deptBtn);

        allEmpBtn = createButton("Employees Info" , 20 , 150);
        allEmpBtn.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(EmployeeInfo::new);});
        add(allEmpBtn);

        customInfoBtn = createButton("Customer Info" , 20 , 190);
        customInfoBtn.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(CustomerInfo::new);});
        add(customInfoBtn);

        managerInfoBtn = createButton("Manager Info" , 20 , 230);
        managerInfoBtn.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(ManagerInfo::new);});
        add(managerInfoBtn);

        checkoutBtn = createButton("CheckOut" , 20 , 270);
        checkoutBtn.addActionListener(e -> {setVisible(false);SwingUtilities.invokeLater(CheckOut::new);});
        add(checkoutBtn);

        updateStatusBtn = createButton("Update Status" , 20 , 310);
        updateStatusBtn.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(UpdateCheckOut::new);});
        add(updateStatusBtn);

        updateRoomBtn = createButton("Update Room Status" , 20 , 350);
        updateRoomBtn.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(UpdateRoom::new);});
        add(updateRoomBtn);

        pickupBtn = createButton("Pickup Services" , 20 , 390);
        pickupBtn.addActionListener(e -> {setVisible(false); SwingUtilities.invokeLater(PickUp::new);});
        add(pickupBtn);

        searchRoomBtn = createButton("Search Room" , 20 , 430);
        searchRoomBtn.addActionListener(e -> {setVisible(false);SwingUtilities.invokeLater(SearchRoom::new);});
        add(searchRoomBtn);

        logOutBtn = createButton("LogOut" , 20 , 470);
        logOutBtn.addActionListener(e -> setVisible(false));
        add(logOutBtn);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/fourth.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(250 , 30 , 500 , 470);
        add(image);

        setBounds(350 , 200 , 800 ,570);
        setVisible(true);
    }

    private JButton createButton(String text , int x , int y){
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        btn.setBounds(x , y , 200 , 30);
        return btn;
    }

    public void actionPerformed(ActionEvent ae){

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Reception::new);
//    }
}
