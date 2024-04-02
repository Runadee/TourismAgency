package view;

import business.EmployeeHotelDetailManager;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeHotelDetailGUI extends Layout {
    private Hotel hotel;
    private EmployeeHotelDetailManager detailManager = new EmployeeHotelDetailManager();
    private JPanel container;
    private JTabbedPane tabbedPane1;

    // Seasons - START
    private JTable tbl_season;
    DefaultTableModel mdl_season;
    private Object[] row_season;
    private JTextField txt_season_name;
    private JTextField txt_season_startDate;
    private JTextField txt_season_endDate;
    // Seasons - END

    private JButton btn_addSeason;
    private JButton btn_addPensiontoHotel;

    // Facility Features - START
    private JComboBox cmb_pension;
    private JTable tbl_pension;

    private JTable tbl_hotel_feature;
    private DefaultTableModel mdl_hotelFeature;
    private Object[] row_hotelFeature;

    private JComboBox cmb_hotelFeatures;
    private JButton btn_addHotelFeature;
    private JComboBox cmb_room_season;
    private JComboBox cmb_room_pension;
    private JTextField txt_room_type;
    private JTextField txt_room_bedNumber;
    private JTextField txt_room_stock;
    private JTextField txt_room_price_child;
    private JButton btn_addHotelRoom;
    DefaultTableModel mdl_pension;
    private Object[] row_pension;
    // Facility Features - START
    private JTable tbl_room;
    private JTextField txt_room_price_adult;
    private JPopupMenu tbl_room_popup;
    DefaultTableModel mdl_room;
    private Object[] row_room;

    public EmployeeHotelDetailGUI(Hotel hotel) {
        this.hotel = hotel;
        this.add(container);
        setGUILayout(1000,500);

        // Season Table - START
        mdl_season = new DefaultTableModel();
        mdl_season.setColumnIdentifiers(new Object[]{"ID","Season","Start Date","End Date"});
        row_season = new Object[4];
        tbl_season.setModel(mdl_season);
        tbl_season.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbl_season.setRowSelectionInterval(tbl_season.rowAtPoint(e.getPoint()), tbl_season.rowAtPoint(e.getPoint()));
            }
        });
        JPopupMenu tbl_season_popup = new JPopupMenu();
        // Deleting hotel season
        tbl_season_popup.add("Delete").addActionListener(e ->{
            if(detailManager.deleteSeason(Integer.parseInt(tbl_season.getValueAt(tbl_season.getSelectedRow(),0).toString()))){
                loadSeasonList();
                Helper.showMsg("Successfully","Hotel Season Deleted");
            }else{
                Helper.showMsg("Warning!","Error deleting Hotel Season");}
        });
        tbl_season.setComponentPopupMenu(tbl_season_popup);
        loadSeasonList();
        // Season Table - END

        // Pension Table - START
        mdl_pension = new DefaultTableModel();
        mdl_pension.setColumnIdentifiers(new Object[]{"ID","Pension"});
        row_pension = new Object[2];
        tbl_pension.setModel(mdl_pension);
        tbl_pension.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbl_pension.setRowSelectionInterval(tbl_pension.rowAtPoint(e.getPoint()), tbl_pension.rowAtPoint(e.getPoint()));
            }
        });
        JPopupMenu tbl_pension_popup = new JPopupMenu();
        // Deleting pension
        tbl_pension_popup.add("Delete").addActionListener(e ->{
            if(detailManager.deleteHotelPension(Integer.parseInt(tbl_pension.getValueAt(tbl_pension.getSelectedRow(),0).toString()))){
                loadPensionList();
                Helper.showMsg("Successfully","Pension Deleted");
            }else{
                Helper.showMsg("Warning","Error deleting pension");}
        });
        tbl_pension.setComponentPopupMenu(tbl_pension_popup);
        loadPensionList();
        // Pension Table - END

        // Hotel Features Table - START
        mdl_hotelFeature = new DefaultTableModel();
        mdl_hotelFeature.setColumnIdentifiers(new Object[]{"Feature"});
        row_hotelFeature = new Object[1];
        tbl_hotel_feature.setModel(mdl_hotelFeature);
        tbl_hotel_feature.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbl_hotel_feature.setRowSelectionInterval(tbl_hotel_feature.rowAtPoint(e.getPoint()), tbl_hotel_feature.rowAtPoint(e.getPoint()));
            }
        });
        JPopupMenu tbl_hotelFeature_popup = new JPopupMenu();
        // Deleting hotel feature
        tbl_hotelFeature_popup.add("Delete").addActionListener(e ->{
            if(detailManager.deleteHotelFeature(hotel.getHotelID(),
                    tbl_hotel_feature.getValueAt(tbl_hotel_feature.getSelectedRow(),0).toString())){
                loadHotelFeatureList();
                Helper.showMsg("Successfully","Pension Deleted");
            }else{
                Helper.showMsg("Warning","Error deleting pension");}
        });
        tbl_hotel_feature.setComponentPopupMenu(tbl_hotelFeature_popup);
        loadHotelFeatureList();
        // Hotel Features Table - END

        // Room - START
        mdl_room = new DefaultTableModel();
        mdl_room.setColumnIdentifiers(new Object[]{"ID","Season","Pension","Room Type","Bed Count","Stock","Child Price","Adult Price"});
        row_room = new Object[8];
        tbl_room.setModel(mdl_room);
        loadHotelRoomList();
        // Selecting the row that mouse pressed
        tbl_room.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbl_room.setRowSelectionInterval(tbl_room.rowAtPoint(e.getPoint()), tbl_room.rowAtPoint(e.getPoint()));
            }
        });
        tbl_room_popup = new JPopupMenu();
        // Adding room feature
        tbl_room_popup.add("Add Room Feature").addActionListener(e -> {
            int id = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(),0).toString());
            AddRoomPropertyGUI addRoomProperty = new AddRoomPropertyGUI(detailManager,id);
        });

        // Updating room information
        tbl_room_popup.add("Update").addActionListener(e -> {
            if(tbl_room.getCellEditor() != null){
                tbl_room.getCellEditor().stopCellEditing();
                if(detailManager.updateRoomInfo(
                        tbl_room.getValueAt(tbl_room.getSelectedRow(),3).toString(),
                        Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(),4).toString()),
                        Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(),5).toString()),
                        Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(),6).toString()),
                        Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(),7).toString())
                )){
                    loadHotelRoomList();
                    Helper.showMsg("Successfully","Update process successful");
                }else {
                    Helper.showMsg("Warning","The update process failedu");
                }
            }
        });
        // Deleting a room
        tbl_room_popup.add("Sil").addActionListener(e -> {
            if(detailManager.deleteRoom((int)tbl_room.getValueAt(tbl_room.getSelectedRow(),0))){
                loadHotelRoomList();
                Helper.showMsg("Successfully","Deletion successful");
            }
        });
        tbl_room.setComponentPopupMenu(tbl_room_popup);
        // Room - END



        // Loading Comboboxes for management
        loadPensionCombobox();
        loadHotelFeaturesCombobox();
        loadRoomSeasonCombobox();
        loadRoomPensionCombobox();

        // Adding season to hotel
        btn_addSeason.addActionListener(e -> {
            if(isValidDates(txt_season_startDate.getText(),txt_season_endDate.getText())){
                if(detailManager.addSeason(
                        hotel.getHotelID(),
                        txt_season_name.getText(),
                        formatDate(txt_season_startDate.getText()),
                        formatDate(txt_season_endDate.getText()))){
                    loadSeasonList();
                    loadRoomSeasonCombobox();
                    Helper.showMsg("Successfully","Season Addition Successfully");
                }
            }
        });
        // Adding pension to hotel
        btn_addPensiontoHotel.addActionListener(e -> {
            if(Helper.confirm("sure")){
                detailManager.addPensiontoHotel(
                        hotel.getHotelID(),
                        ((Pension) cmb_pension.getSelectedItem()).getId(),
                        ((Pension) cmb_pension.getSelectedItem()).getPensionName()
                );
                loadPensionList();
                loadRoomPensionCombobox();
                Helper.showMsg("Successfully","Addition successful");
            }


        });
        // Adding feature to hotel
        btn_addHotelFeature.addActionListener(e -> {
            if(Helper.confirm("sure")){
                detailManager.addHotelFeature(
                        hotel.getHotelID(),
                        ((HotelFeature) cmb_hotelFeatures.getSelectedItem()).getId(),
                        ((HotelFeature) cmb_hotelFeatures.getSelectedItem()).getFeatureName()
                );
                loadHotelFeatureList();
                Helper.showMsg("Successfully","Addition successful");
            }

        });

        // Adding hotel room
        btn_addHotelRoom.addActionListener(e -> {
            if(Helper.isFieldEmpty(txt_room_type) || Helper.isFieldEmpty(txt_room_bedNumber) || !isNumeric(txt_room_bedNumber.getText()) || Helper.isFieldEmpty(txt_room_stock)|| !isNumeric(txt_room_stock.getText()) || Helper.isFieldEmpty(txt_room_price_child) || !isNumeric(txt_room_price_child.getText()) || Helper.isFieldEmpty(txt_room_price_adult) || !isNumeric(txt_room_price_adult.getText())){
                Helper.showMsg("Warning!","Fill in the fields completely and correctly");
            }else {//"ID","Season","Pension","Room Type","Bed Count","Stock","Child Price","Adult Price"
                if(detailManager.addHotelRoom(
                        hotel.getHotelID(),
                        ((Season)cmb_room_season.getSelectedItem()).getId(),
                        ((Pension)cmb_room_pension.getSelectedItem()).getId(),
                        txt_room_type.getText(),
                        Integer.parseInt(txt_room_bedNumber.getText()),
                        Integer.parseInt(txt_room_stock.getText()),
                        Integer.parseInt(txt_room_price_child.getText()),
                        Integer.parseInt(txt_room_price_adult.getText())
                )){
                    loadHotelRoomList();
                    Helper.showMsg("Successfully","Addition successful");
                }
            }
        });


    }
    private String formatDate(String input){
        String[] arr = input.split("/");
        return arr[2]+"-"+arr[1]+"-"+arr[0];
    }
    public Boolean isValidDates(String dateToCheck1,String dateToCheck2){
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateToCheck1,DATE_TIME_FORMATTER);
            date = LocalDate.parse(dateToCheck2,DATE_TIME_FORMATTER);
            return true;
        }catch (Exception e){
            Helper.showMsg("Warning!","You Entered Incorrect Date Information");
        }
        return false;
    }
    private void loadSeasonList() {
        DefaultTableModel db = (DefaultTableModel) tbl_season.getModel();
        db.setRowCount(0);
        for(Season season : detailManager.getSeasonListByHotelID(hotel.getHotelID())){
            row_season[0] = season.getId();
            row_season[1] = season.getSeasonName();
            row_season[2] = season.getStartDate();
            row_season[3] = season.getEndDate();
            mdl_season.addRow(row_season);
        }
    }
    private void loadPensionList(){
        DefaultTableModel db = (DefaultTableModel) tbl_pension.getModel();
        db.setRowCount(0);
        for(Pension pension : detailManager.getPensionList(hotel.getHotelID())){
            row_pension[0] = pension.getId();
            row_pension[1] = pension.getPensionName();
            mdl_pension.addRow(row_pension);
        }
    }
    private void loadHotelFeatureList(){
        DefaultTableModel db = (DefaultTableModel) tbl_hotel_feature.getModel();
        db.setRowCount(0);
        for(HotelFeature hotelFeature : detailManager.getHotelFeatureListByID(hotel.getHotelID())){
            row_hotelFeature[0] = hotelFeature.getFeatureName();
            mdl_hotelFeature.addRow(row_hotelFeature);
        }
    }
    private void loadHotelRoomList(){
        DefaultTableModel db = (DefaultTableModel) tbl_room.getModel();
        db.setRowCount(0);
        for(Room room : detailManager.getHotelRoomListByID(hotel.getHotelID())){
            row_room[0] = room.getId();
            row_room[1] =detailManager.getSeasonNameByID(room.getSeasonID());
            row_room[2] =detailManager.getPensionNameByID(room.getPensionID());
            row_room[3] =room.getRoomType();
            row_room[4] =room.getBedNumber();
            row_room[5] =room.getStock();
            row_room[6] =room.getPriceChild();
            row_room[7] =room.getPriceAdult();
            mdl_room.addRow(row_room);
        }

    }
    private void loadPensionCombobox(){
        cmb_pension.removeAllItems();
        for(Pension pension : detailManager.getPensionTypeList()){
            cmb_pension.addItem(pension);
        }
    }
    private void loadHotelFeaturesCombobox(){
        cmb_hotelFeatures.removeAllItems();
        for(HotelFeature hotelFeature : detailManager.getHotelFeaturesList()){
            cmb_hotelFeatures.addItem(hotelFeature);
        }
    }
    private void loadRoomSeasonCombobox(){
        cmb_room_season.removeAllItems();
        for(Season season : detailManager.getSeasonListByHotelID(hotel.getHotelID())){
            cmb_room_season.addItem(season);
        }
    }
    private void loadRoomPensionCombobox(){
        cmb_room_pension.removeAllItems();
        for(Pension pension : detailManager.getPensionList(hotel.getHotelID())){
            cmb_room_pension.addItem(pension);
        }
    }
    public static boolean isNumeric(String maybeNumeric) {
        return maybeNumeric != null && maybeNumeric.matches("[0-9]+");
    }
}

