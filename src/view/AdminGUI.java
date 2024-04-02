
// Import necessary packages
package view;

import business.AdminManager;
import business.LoginManager;
import core.Helper;
import dao.LoginDao;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

// AdminGUI class for managing admin-related tasks
public class AdminGUI extends Layout {
    private AdminManager adminManager = new AdminManager();
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JTable tbl_personel;
    private DefaultTableModel mdl_personel;
    private Object[] row_personel;
    private JTextField txt_tc;
    private JTextField txt_userName;
    private JTextField txt_name;
    private JTextField txt_surname;
    private JComboBox cmb_userType;
    private JButton btn_addUser;
    private JTextField txt_password;
    private User admin;

    // Constructor for AdminGUI
    public AdminGUI(User admin){
        this.admin = admin;
        add(wrapper);
        setGUILayout(800,500);

        // Add window listener to handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LoginGUI backToLogin = new LoginGUI(new LoginManager(new LoginDao()));
            }
        });

        // Initialize table model
        mdl_personel = new DefaultTableModel();
        mdl_personel.setColumnIdentifiers(new Object[]{"User ID","TC","Username","Password","Name","Surname","User Type"});
        row_personel = new Object[7];
        tbl_personel.setModel(mdl_personel);

        // Add mouse listener to table
        tbl_personel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbl_personel.setRowSelectionInterval(tbl_personel.rowAtPoint(e.getPoint()), tbl_personel.rowAtPoint(e.getPoint()));
            }
        });

        // Load initial data into the table
        loadPersonelTable();

        // Create popup menu for table
        JPopupMenu tbl_personel_popup = new JPopupMenu();
        tbl_personel_popup.add("Update").addActionListener(e ->{
            if(adminManager.updateUser(
                    Integer.parseInt(tbl_personel.getValueAt(tbl_personel.getSelectedRow(),0).toString()),
                    tbl_personel.getValueAt(tbl_personel.getSelectedRow(),1).toString(),
                    tbl_personel.getValueAt(tbl_personel.getSelectedRow(),2).toString(),
                    tbl_personel.getValueAt(tbl_personel.getSelectedRow(),3).toString(),
                    tbl_personel.getValueAt(tbl_personel.getSelectedRow(),4).toString(),
                    tbl_personel.getValueAt(tbl_personel.getSelectedRow(),5).toString(),
                    cmb_userType.getSelectedItem().toString()
            )){
                loadPersonelTable();
                Helper.showMsg("Success","User information updated");
            } else {
                Helper.showMsg("Error","An error occurred while updating user information");
            }
        });
        tbl_personel_popup.add("Delete").addActionListener(e ->{
            if(adminManager.deleteUser(Integer.parseInt(tbl_personel.getValueAt(tbl_personel.getSelectedRow(),0).toString()))){
                loadPersonelTable();
                Helper.showMsg("Success","User deleted");
            } else {
                Helper.showMsg("Error","An error occurred while deleting user");
            }
        });
        tbl_personel.setComponentPopupMenu(tbl_personel_popup);

        // Add action listener for btn_addUser button
// Add action listener for btn_addUser button
        btn_addUser.addActionListener(e -> {
            if (Helper.isFieldEmpty(txt_tc) || Helper.isFieldEmpty(txt_userName) || Helper.isFieldEmpty(txt_password) ||
                    Helper.isFieldEmpty(txt_name) || Helper.isFieldEmpty(txt_surname)) {
                Helper.showMsg("Warning", "Fill in all fields");
            } else {
                if (adminManager.addUser(
                        txt_tc.getText(),
                        txt_userName.getText(),
                        txt_password.getText(),
                        txt_name.getText(),
                        txt_surname.getText(),
                        cmb_userType.getSelectedItem().toString()
                )) {
                    loadPersonelTable();
                    Helper.showMsg("Success", "User added");
                }
            }
        });

    }

    // Method to load user data into the table
    private void loadPersonelTable(){
        DefaultTableModel db = (DefaultTableModel) tbl_personel.getModel();
        db.setRowCount(0);
        for(User user : adminManager.getUserList()){
            row_personel[0] = user.getUserID();
            row_personel[1] = user.getTcNo();
            row_personel[2] = user.getUsername();
            row_personel[3] = user.getPassword();
            row_personel[4] = user.getName();
            row_personel[5] = user.getSurname();
            row_personel[6] = user.getUserType();
            mdl_personel.addRow(row_personel);
        }
    }
}