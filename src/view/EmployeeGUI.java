package view;

import business.EmployeeManager;
import business.LoginManager;
import core.Helper;
import entity.Reservation;
import dao.LoginDao;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class EmployeeGUI extends Layout {
    private final EmployeeManager employeeManager = new EmployeeManager();

    private JTabbedPane tabbedPane1;
    private User employee;

    // Hotel Panel - START
    private JPanel pnl_hotel;
    private JTable tbl_hotel;
    private JTextField txt_hotel_name;
    private JTextField txt_hotel_email;
    private JTextField txt_hotel_phoneNumber;
    private JButton btn_addHotel;
    private JComboBox cmb_hotel_star;
    private JPanel pnl_bottom;
    private JTextField txt_city;
    private JTextField txt_startDate;
    private JTextField txt_endDate;
    private JComboBox cmb_childNumber;
    private JComboBox cmb_adultNumber;
    private JButton btn_search;
    DefaultTableModel mdl_hotel_list;
    private Object[] row_hotel_list;
    private JPopupMenu tbl_hotel_PopupMenu;
    // Hotel Panel - END
    private JTable tbl_search;
    private JTextArea txtA_hotelFeatures;
    private JTextArea txtA_roomFeatures;
    DefaultTableModel mdl_search;
    private Object[] row_search;

    private JTable tbl_rezervations;
    private JTextField txt_addcity;
    private JTextField txt_region;
    private JTextField txt_address;
    private JPanel container;
    private JButton btn_newUserButton;
    DefaultTableModel mdl_rezervations;
    private Object[] row_rezervations;
    private Hotel hotelUpdate;


    public EmployeeGUI(User employee) {
        this.employee = employee;
        add(container);
        setGUILayout(1300, 500);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LoginGUI backToLogin = new LoginGUI(new LoginManager(new LoginDao()));
            }
        });
        // Hotel Table - START
        mdl_hotel_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_hotel_list = {"ID", "Hotel Name", "City", "District", "Address", "Email", "Phone Number", "Star"};
        mdl_hotel_list.setColumnIdentifiers(col_hotel_list);
        row_hotel_list = new Object[col_hotel_list.length];
        tbl_hotel.setModel(mdl_hotel_list);
        //tbl_hotel.getTableHeader().setReorderingAllowed(false);

        tbl_hotel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbl_hotel.setRowSelectionInterval(tbl_hotel.rowAtPoint(e.getPoint()), tbl_hotel.rowAtPoint(e.getPoint()));
            }
        });
        tbl_hotel_PopupMenu = new JPopupMenu();
        tbl_hotel_PopupMenu.add("Manage").addActionListener(e -> {
            int selectedHotelID = Integer.parseInt(tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 0).toString());
            EmployeeHotelDetailGUI detailGUI = new EmployeeHotelDetailGUI(employeeManager.getHotelByID(selectedHotelID));
        });
        // Hotel details (facility feature, hostel type addition/deletion operations) and things like room operations in this gui
        tbl_hotel_PopupMenu.add("Update").addActionListener(e -> {
            int selectedHotelID = this.getTableSelectedRow(tbl_hotel,0);
            Hotel selectedHotel = this.employeeManager.getHotelByID(selectedHotelID);
            setUpdateHotel(selectedHotel);
            this.txt_hotel_name.setText(selectedHotel.getHotelName());
            this.txt_addcity.setText(selectedHotel.getCity());
            this.txt_region.setText(selectedHotel.getRegion());
            this.txt_address.setText(selectedHotel.getAddress());
            this.txt_hotel_email.setText(selectedHotel.getHotelEmail());
            this.txt_hotel_phoneNumber.setText(selectedHotel.getHotelPhoneNumber());
            this.btn_addHotel.setText("Update");

        });

        tbl_hotel_PopupMenu.add("Delete").addActionListener(e -> {
            if (employeeManager.deleteHotel(Integer.parseInt(tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 0).toString()))) {
                loadHotelTable();
                Helper.showMsg("Successfully", "Hotel Deleted");
            }
        });
        tbl_hotel.setComponentPopupMenu(tbl_hotel_PopupMenu);
        loadHotelTable();
        // Hotel Table - END


        // tbl_rezervations - START
        mdl_rezervations = new DefaultTableModel();
        mdl_rezervations.setColumnIdentifiers(new Object[]{"Rezervasyon ID", "Hotel ID", "Hotel", "Room ID", "Name Surname", "Citizen ID", "Phone", "Email", "Child", "Adult"});
        row_rezervations = new Object[10];
        tbl_rezervations.setModel(mdl_rezervations);
        tbl_rezervations.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbl_rezervations.setRowSelectionInterval(tbl_rezervations.rowAtPoint(e.getPoint()), tbl_rezervations.rowAtPoint(e.getPoint()));
            }
        });

        JPopupMenu tbl_rezervations_popup = new JPopupMenu();
        tbl_rezervations_popup.add("Update").addActionListener(e -> {

            int adult_number = Integer.parseInt((String)cmb_adultNumber.getSelectedItem());
            int child_number = Integer.parseInt((String)cmb_childNumber.getSelectedItem());
            int child_price = Integer.parseInt(tbl_search.getValueAt(tbl_search.getSelectedRow(),14).toString());
            int adult_price = Integer.parseInt(tbl_search.getValueAt(tbl_search.getSelectedRow(),15).toString());

            System.out.println(adult_number+", "+child_number+", "+ adult_price + ", "+ child_price);
            tbl_rezervations.getCellEditor().stopCellEditing();
            if (employeeManager.updateReservation(
                    Integer.parseInt(tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 0).toString()),
                    (String) tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 4),
                    (String) tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 5),
                    (String) tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 6),
                    (String) tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 7),
                    Integer.parseInt(tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 8).toString()),
                    Integer.parseInt(tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 9).toString())
            )) {
                loadRezervationList();
                Helper.showMsg("Successfully", "Reservation Updated");
            }
        });
        tbl_rezervations_popup.add("Delete").addActionListener(e -> {
            if (employeeManager.deleteReservation(Integer.parseInt(tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 0).toString()))) {
                employeeManager.increaseStock(Integer.parseInt(tbl_rezervations.getValueAt(tbl_rezervations.getSelectedRow(), 3).toString()));
                loadRezervationList();
                Helper.showMsg("Successfully", "Reservation Deleted");
            }
        });
        tbl_rezervations.setComponentPopupMenu(tbl_rezervations_popup);
        loadRezervationList();
        // tbl_rezervations - END


        // Search Table - Start
        mdl_search = new DefaultTableModel();
        mdl_search.setColumnIdentifiers(new Object[]{"Season", "Start Season", "End Season", "Hotel", "City", "District", "Address", "Email", "Phone Number", "Star", "Pension", "Room Type", "Bed Count", "Available Room", "Child Price", "Adult Price", "Hotel ID", "Room ID"});
        row_search = new Object[18];
        tbl_search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tbl_search.setRowSelectionInterval(tbl_search.rowAtPoint(e.getPoint()), tbl_search.rowAtPoint(e.getPoint()));
                int hotelID = Integer.parseInt(tbl_search.getValueAt(tbl_search.getSelectedRow(), 16).toString());
                int roomID = Integer.parseInt(tbl_search.getValueAt(tbl_search.getSelectedRow(), 17).toString());
                loadHotelFeatures(txtA_hotelFeatures, employeeManager.getHotelFeatures(hotelID));
                loadRoomFeatures(txtA_roomFeatures, employeeManager.getRoomFeatures(roomID));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                tbl_search.setRowSelectionInterval(tbl_search.rowAtPoint(e.getPoint()), tbl_search.rowAtPoint(e.getPoint()));
            }
        });
        tbl_search.setModel(mdl_search);
        tbl_search.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl_search.getColumnModel().getColumn(16).setPreferredWidth(0);
        tbl_search.getColumnModel().getColumn(17).setPreferredWidth(0);


        JPopupMenu reservation = new JPopupMenu();
        reservation.add("Reservation").addActionListener(e -> {

            int adult_number = Integer.parseInt((String)cmb_adultNumber.getSelectedItem());
            int child_number = Integer.parseInt((String)cmb_childNumber.getSelectedItem());
            int child_price = Integer.parseInt(tbl_search.getValueAt(tbl_search.getSelectedRow(),14).toString());
            int adult_price = Integer.parseInt(tbl_search.getValueAt(tbl_search.getSelectedRow(),15).toString());

            if (txt_startDate.getText().isEmpty() || txt_endDate.getText().isEmpty()) {
                Helper.showMsg("Warning", "Enter date ranges to make a reservation");
                return;
            }
            int days = employeeManager.calculateDay(txt_startDate.getText(), txt_endDate.getText());
            int totalPrice = ( adult_number * days * adult_price)+(child_number*child_price*days);
            ReservationGUI res = new ReservationGUI(
                    this,
                    employeeManager,
                    Integer.parseInt(tbl_search.getValueAt(tbl_search.getSelectedRow(), 16).toString()),
                    Integer.parseInt(tbl_search.getValueAt(tbl_search.getSelectedRow(), 17).toString()),
                    Integer.parseInt(cmb_childNumber.getSelectedItem().toString()),
                    Integer.parseInt(cmb_adultNumber.getSelectedItem().toString()),
                    child_price,
                    adult_price,
                    totalPrice



            );
        });

        tbl_search.setComponentPopupMenu(reservation);
        loadSearchTable();
        // Search Table - END


        btn_addHotel.addActionListener(e -> {
            if (Helper.isFieldEmpty(txt_hotel_name) || Helper.isFieldEmpty(txt_hotel_email) || Helper.isFieldEmpty(txt_hotel_phoneNumber)) {
                Helper.showMsg("Warning!", "Please fill in the fields completely");
            } else {
                if(btn_addHotel.getText().equals("Update")){
                    employeeManager.updateHotel(
                            hotelUpdate.getHotelID(),
                            txt_hotel_name.getText(),
                            txt_addcity.getText(),
                            txt_region.getText(),
                            txt_address.getText(),
                            txt_hotel_email.getText(),
                            txt_hotel_phoneNumber.getText(),
                            Integer.parseInt((String) cmb_hotel_star.getSelectedItem()));
                    loadHotelTable();
                    Helper.showMsg("Successfully", "Update successful");

                }
                else {

                    employeeManager.addHotel(
                            txt_hotel_name.getText(),
                            txt_addcity.getText(),
                            txt_region.getText(),
                            txt_address.getText(),
                            txt_hotel_email.getText(),
                            txt_hotel_phoneNumber.getText(),
                            Integer.parseInt((String) cmb_hotel_star.getSelectedItem())) ;
                    loadHotelTable();
                    Helper.showMsg("Successfully", "Addition successful");
                }
            }
        });
        btn_newUserButton.addActionListener(e -> {
            this.txt_hotel_name.setText(null);
            this.txt_addcity.setText(null);
            this.txt_region.setText(null);
            this.txt_address.setText(null);
            this.txt_hotel_email.setText(null);
            this.txt_hotel_phoneNumber.setText(null);
            this.btn_addHotel.setText("Add");

        })
        ;

        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!employeeManager.isValidDates(txt_startDate.getText(), txt_endDate.getText())) {
                    Helper.showMsg("Warning!", "Enter Date in Valid Format (DAY/Month/Year)");
                } else {
                    ArrayList<SearchResult> result = employeeManager.search(
                            employeeManager.searchQuery(
                                    txt_city.getText(),
                                    txt_city.getText(),
                                    txt_city.getText(),
                                    txt_startDate.getText(),
                                    txt_endDate.getText(),
                                    Integer.parseInt(cmb_childNumber.getSelectedItem().toString()) + Integer.parseInt(cmb_adultNumber.getSelectedItem().toString())
                            )
                    );
                    loadSearchTable(result);
                }
            }
        });
    }

    public void loadRezervationList() {
        DefaultTableModel db = (DefaultTableModel) tbl_rezervations.getModel();
        db.setRowCount(0);
        for (Reservation r : employeeManager.getReservationList()) {
            row_rezervations[0] = r.getId();
            row_rezervations[1] = r.getHotelID();
            row_rezervations[2] = employeeManager.getHotelByID(r.getHotelID()).getHotelName();
            row_rezervations[3] = r.getRoomID();
            row_rezervations[4] = r.getCustomerName();
            row_rezervations[5] = r.getCustomerTcNo();
            row_rezervations[6] = r.getCustomerPhone();
            row_rezervations[7] = r.getCustomerEmail();
            row_rezervations[8] = r.getChildNumber();
            row_rezervations[9] = r.getAdultNumber();
            mdl_rezervations.addRow(row_rezervations);
        }
    }

    private void loadHotelFeatures(JTextArea area, ArrayList<HotelFeature> list) {
        area.setText("");
        for (HotelFeature f : list) {
            area.append(f.getFeatureName() + "\n");
        }
    }

    private void loadRoomFeatures(JTextArea area, ArrayList<RoomFeature> list) {
        area.setText("");
        for (RoomFeature r : list) {
            area.append(r.getFeatureName() + "\n");
        }
    }

    private void loadSearchTable(ArrayList<SearchResult> result) {
        DefaultTableModel db = (DefaultTableModel) tbl_search.getModel();
        db.setRowCount(0);
        for (SearchResult r : result) {
            row_search[0] = r.getSeasonName();
            row_search[1] = employeeManager.formatDateBack(r.getSeasonStartDate());
            row_search[2] = employeeManager.formatDateBack(r.getSeasonEndDate());
            row_search[3] = r.getHotelName();
            row_search[4] = r.getHotelCity();
            row_search[5] = r.getHotelRegion();
            row_search[6] = r.getHotelAdress();
            row_search[7] = r.getHotelEmail();
            row_search[8] = r.getHotelPhoneNumber();
            row_search[9] = r.getHotelStars();
            row_search[10] = employeeManager.getPensionNameByID(r.getPensionID());
            row_search[11] = r.getRoomType();
            row_search[12] = r.getRoomBedNumber();
            row_search[13] = r.getRoomStock();
            row_search[14] = r.getRoomPriceChildren();
            row_search[15] = r.getRoomPriceAdult();
            row_search[16] = r.getHotelID();
            row_search[17] = r.getRoomID();
            mdl_search.addRow(row_search);
        }
    }

    private void loadSearchTable() {
        DefaultTableModel db = (DefaultTableModel) tbl_search.getModel();
        db.setRowCount(0);
        for (SearchResult r : employeeManager.search()) {
            row_search[0] = r.getSeasonName();
            row_search[1] = employeeManager.formatDateBack(r.getSeasonStartDate());
            row_search[2] = employeeManager.formatDateBack(r.getSeasonEndDate());
            row_search[3] = r.getHotelName();
            row_search[4] = r.getHotelCity();
            row_search[5] = r.getHotelRegion();
            row_search[6] = r.getHotelAdress();
            row_search[7] = r.getHotelEmail();
            row_search[8] = r.getHotelPhoneNumber();
            row_search[9] = r.getHotelStars();
            row_search[10] = employeeManager.getPensionNameByID(r.getPensionID());
            row_search[11] = r.getRoomType();
            row_search[12] = r.getRoomBedNumber();
            row_search[13] = r.getRoomStock();
            row_search[14] = r.getRoomPriceChildren();
            row_search[15] = r.getRoomPriceAdult();
            row_search[16] = r.getHotelID();
            row_search[17] = r.getRoomID();
            mdl_search.addRow(row_search);
        }
    }

    private void loadHotelTable() {
        DefaultTableModel db = (DefaultTableModel) tbl_hotel.getModel();
        db.setRowCount(0);
        for (Hotel hotel : employeeManager.getHotelList()) {
            row_hotel_list[0] = hotel.getHotelID();
            row_hotel_list[1] = hotel.getHotelName();
            row_hotel_list[2] = hotel.getCity();
            row_hotel_list[3] = hotel.getRegion();
            row_hotel_list[4] = hotel.getAddress();
            row_hotel_list[5] = hotel.getHotelEmail();
            row_hotel_list[6] = hotel.getHotelPhoneNumber();
            row_hotel_list[7] = hotel.getHotelStars();
            mdl_hotel_list.addRow(row_hotel_list);
        }
    }

    private Hotel getUpdateHotel(){

        return hotelUpdate;
    }
    private void setUpdateHotel(Hotel hotel){
        this.hotelUpdate = hotel;
    }

}
