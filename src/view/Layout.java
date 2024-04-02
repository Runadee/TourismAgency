package view;
import core.Config;
import javax.swing.*;

public abstract class Layout extends JFrame {
    public void setGUILayout(int width,int height){
        setSize(width,height);
        setLocationRelativeTo(null);
        //setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        //setResizable(false);
        setVisible(true);
    }
    public int getTableSelectedRow(JTable table, int index){
        return Integer.parseInt(table.getValueAt(table.getSelectedRow(),index).toString());
    }



}