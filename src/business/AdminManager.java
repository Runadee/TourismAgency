package business;

import dao.AdminDao;
import entity.User;

import java.util.ArrayList;

// Business logic layer for admin-related operations on user entities.
public class AdminManager {

    private final AdminDao adminDao; // Assuming AdminDao is the actual implementation

    // Default constructor initializing AdminManager with a default AdminDao instance.
    public AdminManager() {
        this.adminDao = new AdminDao(); // Assuming AdminDao is the actual implementation
    }

    // Adds a new user.
    public boolean addUser(String tcNo, String username, String password, String name, String surname, String userType) {
        try {
            return adminDao.addUser(tcNo, username, password, name, surname, userType);
        } catch (Exception e) {
            // Log the exception or print a detailed error message
            e.printStackTrace();
            return false;
        }
    }


    // Updates an existing user's information.
    public boolean updateUser(int userID, String tcNo, String username, String password, String name, String surname, String userType) {
        try{
            return adminDao.updateUser(userID, tcNo, username, password, name, surname, userType);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // Deletes a user.
    public boolean deleteUser(int userID) {
        return adminDao.deleteUser(userID);
    }

    // Retrieves a list of all users.
    public ArrayList<User> getUserList() {
        return adminDao.getUserList();
    }
}