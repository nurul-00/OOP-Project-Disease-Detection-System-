
package database;

import model.User;
import model.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.Disease;
import model.DiseaseInfo;

import java.util.*;


public class FileHandler {
    private static final String USER_FILE = "users.txt";
    private static final String ADMIN_FILE = "admins.txt";
    
    private static final String DISEASE_LIST_FILE = "diseases.dat";
    private static final String DISEASE_INFO_FILE = "diseaseInfo.dat";
    
    public static boolean saveUser(User user){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))){
            String line = String.format("%s,%d,%s,%s,%s", user.getName(), user.getAge(), user.getGender(), user.getEmail(), user.getPassword());
            bw.write(line);
            bw.newLine();
            return true;
        }
        catch(IOException e){
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean saveAdmin(Admin admin){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("admins.txt", true))){
            String line = String.format("%s,%d,%s,%s,%s", admin.getName(), admin.getAge(), admin.getGender(), admin.getEmail(), admin.getPassword());
            bw.write(line);
            bw.newLine();
            return true;
        }
        catch(IOException e){
            System.out.println("Error saving admin: " + e.getMessage());
            return false;
        }
    }
    
    public static List<User> readUsers(){
        List<User> users = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(new File("users.txt"))){
            while(scanner.hasNextLine()){
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 5){
                User user = new User(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3], parts[4]);
                users.add(user);
                }
            }
        }
    
        catch(FileNotFoundException e){
            System.out.println("No user file found.");
    
        }
        return users;
    }
    
    public static List<Admin> readAdmins(){
        List<Admin> admins = new ArrayList<>();
        
       try (Scanner scanner = new Scanner(new File(ADMIN_FILE))){
            while(scanner.hasNextLine()){
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 5){
                Admin admin = new Admin(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3], parts[4]);
                admins.add(admin);
                }
            }
        }
    
        catch(FileNotFoundException e){
            System.out.println("Admin file not found. Creating new one...");
        }
        return admins;   
    }

    public static void writeUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean deleteUserByEmail(String email) {
    List<User> users = readUsers();
    boolean found = false;

    Iterator<User> iterator = users.iterator();
    while (iterator.hasNext()) {
        User user = iterator.next();
        if (user.getEmail().equalsIgnoreCase(email.trim())) {
            iterator.remove();
            found = true;
            break;
        }
    }

    if (found) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", false))) {
            for (User user : users) {
                String line = String.format("%s,%d,%s,%s,%s", user.getName(), user.getAge(),
                        user.getGender(), user.getEmail(), user.getPassword());
                bw.write(line);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error rewriting user file: " + e.getMessage());
        }
    }

    return false;
}

    

    public static void saveDiseases(List<Disease> diseaseList, Map<String, DiseaseInfo> diseaseInfoMap) {
        saveObject(diseaseList, DISEASE_LIST_FILE);
        saveObject(diseaseInfoMap, DISEASE_INFO_FILE);
    }
    
    @SuppressWarnings("unchecked")
    public static Object[] loadDiseases() {
        List<Disease> diseaseList = (List<Disease>) loadObject(DISEASE_LIST_FILE);
        Map<String, DiseaseInfo> diseaseInfoMap = (Map<String, DiseaseInfo>) loadObject(DISEASE_INFO_FILE);

        if (diseaseList == null) {
            diseaseList = new ArrayList<>();
        }
        if (diseaseInfoMap == null) {
            diseaseInfoMap = new HashMap<>();
        }

        return new Object[] { diseaseList, diseaseInfoMap };
    }
    
    private static void saveObject(Object obj, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Error saving " + filename + ": " + e.getMessage());
        }
    }
    
    private static Object loadObject(String filename) {
        File file = new File(filename);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading " + filename + ": " + e.getMessage());
            return null;
        }
    }
}

    
 /*public static Object[] loadDiseases() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DISEASE_FILE))) {
            List<Disease> diseases = (List<Disease>) ois.readObject();
            Map<String, DiseaseInfo> diseaseInfoMap = (Map<String, DiseaseInfo>) ois.readObject();
            return new Object[]{diseases, diseaseInfoMap};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing disease file found or error reading: " + e.getMessage());
            return new Object[]{new ArrayList<>(), new HashMap<>()};
        }
    }   */
    
    

    

  



