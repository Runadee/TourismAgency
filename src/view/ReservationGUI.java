package view;

import business.EmployeeManager;
import core.Helper;

import javax.swing.*;

public class ReservationGUI extends Layout {
    private EmployeeManager employeeManager;
    private JPanel container;
    private JTextField txt_customerName;
    private JTextField txt_customerTcNo;
    private JTextField txt_customerPhone;
    private JTextField txt_customerEmail;
    private JButton btn_reservation;
    private JLabel txt_totalPrice;
    public ReservationGUI(EmployeeGUI gui, EmployeeManager employeeManager, int hotelID, int roomID, int childNumber, int adultNumber,int adultPrice,int childPrice, int totalPrice) {
        add(container);
        setGUILayout(500,500);
        this.employeeManager = employeeManager;
        txt_totalPrice.setText(String.valueOf(totalPrice));

        btn_reservation.addActionListener(e -> {
            if(Helper.isFieldEmpty(txt_customerName) || Helper.isFieldEmpty(txt_customerTcNo) || Helper.isFieldEmpty(txt_customerPhone) || Helper.isFieldEmpty(txt_customerEmail)){
                Helper.showMsg("Warning!","Please fill out the information completely");
            }else{
                if(employeeManager.addReservation(
                        hotelID,
                        roomID,
                        txt_customerName.getText(),
                        txt_customerTcNo.getText(),
                        txt_customerPhone.getText(),
                        txt_customerEmail.getText(),
                        childNumber,
                        adultNumber)){
                    employeeManager.decreaseStock(roomID);
                    gui.loadRezervationList();
                    Helper.showMsg("Successfully","Reservation Made");
                }
            }
        });
    }
}
