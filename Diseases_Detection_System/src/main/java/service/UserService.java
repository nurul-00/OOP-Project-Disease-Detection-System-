
package service;

import model.User;
import model.Admin;
import database.FileHandler;

import java.util.List;

public class UserService {
    public boolean registerUser(User newUser){
        List<User> users =FileHandler.readUsers();
        for(User user : users){
            if(user.getEmail().equalsIgnoreCase(newUser.getEmail())){
                return false;
            }
        }
        return FileHandler.saveUser(newUser);
    }

    
    public boolean registerAdmin(Admin newAdmin){
        List<Admin> admins =FileHandler.readAdmins();
        
        if(admins.size() >= 6){
            System.out.println("Cannot register more than 6 admins.");
            return false;
        }
        for(Admin admin : admins){
            if(admin.getEmail().equalsIgnoreCase(newAdmin.getEmail())){
                System.out.println("Admin with this email already exists.");
                return false;
            }
        }
        return FileHandler.saveAdmin(newAdmin);
    }
    
    public User loginUser(String email, String password){
        List<User> users =FileHandler.readUsers();
        for(User user : users){
            if(user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)){
                return user;
            }
        }
        return null;
    }
    
    public Admin loginAdmin(String email, String password){
        List<Admin> admins =FileHandler.readAdmins();
        for(Admin admin : admins){
            if(admin.getEmail().equalsIgnoreCase(email) && admin.getPassword().equalsIgnoreCase(password)){
                return admin;
            }
        }
        return null;
    }
}
