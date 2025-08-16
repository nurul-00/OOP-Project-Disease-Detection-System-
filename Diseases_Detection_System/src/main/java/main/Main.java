package main;

import database.FileHandler;
import model.User;
import model.Admin;
import model.Disease;
import model.DiseaseInfo;
import service.UserService;
import service.DiseaseService;
import service.AdminService;
import java.util.Map;
import java.util.HashMap;


import java.util.*;
import java.util.Scanner;
import model.Doctor;
import service.DoctorService;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static AdminService adminService;
    private static DiseaseService diseaseService ;
    private static DoctorService doctorService = new DoctorService();
    
    public static void main(String[] args) {
        System.out.println("\n\t=====================================");
        System.out.println("\tWELCOME TO DISEASES DETECTION SYSTEM");
        System.out.println("\t=====================================");
        
        preloadAdmin();
        
        Object[] data = FileHandler.loadDiseases();
        List<Disease> diseaseList = (List<Disease>) data[0];
        Map<String, DiseaseInfo> diseaseInfoMap = (Map<String, DiseaseInfo>) data[1];
        diseaseService = new DiseaseService(diseaseList, diseaseInfoMap);


        //adminService = new AdminService(diseaseList, diseaseInfoMap);
        diseaseService = new DiseaseService(diseaseList, diseaseInfoMap);
        
        while(true){
            
            showMainMenu();
            
            int choice = getChoice();
            
            switch(choice){
                case 1 -> registerUser();
                
                case 2 -> loginUser();
                
                case 3 -> registerAdmin();
                
                case 4 -> loginAdmin();
                
                case 5 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }  
                    
                default -> System.out.println("Invalid choice! Try again.");       
            }
        
        }
    }
    
    private static void showMainMenu(){
        System.out.println("\nMain Menu:");
        System.out.println("1. Register as User");
        System.out.println("2. Login as User");
        System.out.println("3. Register as Admin");
        System.out.println("4. Login as Admin");
        System.out.println("5. Exit");
        System.out.println("--------------------");
        System.out.print("Enter your choice: ");
    }
    
    private static int getChoice(){
        int choice = -1;
        try{
            choice = Integer.parseInt(scanner.nextLine());
        }
        catch(NumberFormatException ignored){
            
        }
        return choice;
    }
    
    private static void registerUser(){
        System.out.println("\n--- User Registration ---");
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        
        int age = getIntInput("Enter your age: ");
        
        System.out.print("Enter your gender (Male/Female/Other): ");
        String gender = scanner.nextLine();
        
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        
        User newUser = new User(name, age, gender, email, password);
        if (userService.registerUser(newUser)){
            System.out.println("Registration successful! You can now login.");
        }
        else{
            System.out.println("Email already exists! Please try logging in or use another email.");
        }
    }

    private static void loginUser(){
        System.out.println("\n--- User Login ---");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
    
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        User user = userService.loginUser(email, password);
        if(user != null){
            System.out.println("Login successful! Welcome, "+user.getName());
            
            boolean backToMainMenu = false;
            while(!backToMainMenu){
                System.out.println("\nUser Menu:");
                System.out.println("1. View Profile");
                System.out.println("2. Detect Disease");
                System.out.println("3. Emergency Doctor Call");
                System.out.println("4. Logout");
                System.out.println("Enter your choice: ");
                
                int choice = getChoice();
            
                switch(choice){
                    case 1 -> System.out.println(user);
                    
                    case 2 -> detectDiseaseFlow();
                    
                    case 3 -> emergencyDoctorCall();
                    
                    case 4 ->{
                        
                        FileHandler.saveDiseases(diseaseService.getDiseaseList(), diseaseService.getDiseaseInfoMap());
                        backToMainMenu = true;
                    }
                    
                    default -> System.out.println("Invalide choice. try again.");
                }
            }
        }
        
        else{
            System.out.println("Invalid email or password.");
        } 
    }
    
    public static void detectDiseaseFlow(){
        System.out.println("\n---Disease Detection---");
        System.out.println("Please enter your symptoms one by one (type 'done' when finished):");
        List<String> symptoms = new ArrayList<>();
        
        while(true){
           
            String symptom = scanner.nextLine().trim().toLowerCase();
            
            if(symptom.equalsIgnoreCase("done")){
                break;
            }
            if(!symptom.isEmpty()){
                symptoms.add(symptom);
            }
                    
        }
        
        Map<Disease, Double> result = diseaseService.detectDisease(symptoms);
        if (result.isEmpty()) {
            System.out.println("\n No disease matched your symptoms.\n");
        } else {
            result.forEach((disease, percent)->{
            System.out.printf("\n\nDetected Disease: %s (%.2f%% match)\n", disease.getName(), percent);    
            
            DiseaseInfo info = diseaseService.getDiseaseInfo(disease.getName());
                
            if (info != null) {
                    
                System.out.println("️\nSeverity Level: " + info.getSeverity());
                System.out.println("\nDescription: " + info.getDescription());
                System.out.println("️\nPrecautions: " + info.getPrecautions());                    
                System.out.println("\nPrescription:");
                    

                List<String> presc = info.getPrescription();
                if (presc != null && !presc.isEmpty()) {
                    for (int i = 0; i < presc.size(); i++) {
                        System.out.println((i + 1) + ". " + presc.get(i));
                    }
                } 
            }

            List<Doctor> doctors = doctorService.getDoctorByDisease(disease.getName());
            if (doctors != null && !doctors.isEmpty()) {
                System.out.println("\n Recommended Doctors:");
                for (Doctor doc : doctors) {
                    System.out.println("- " + doc);
                }
            } else {
                System.out.println("\n No doctor recommendation available.");
            }

            //break;    
                
                
            });
        }
    } 
    

    
    
    
    private static void emergencyDoctorCall() {
        System.out.println("\n\nEmergency Doctor Contact List:");
        System.out.println("1. Dr. Amina Rahman     - +880123456789 (Medicine Specialist)");
        System.out.println("2. Dr. Faisal Kabir     - +880198765432 (Chest Specialist)");
        System.out.println("3. Dr. Tanvir Hossain   - +880176543210 (Infectious Disease)");
        System.out.println("4. Dr. Shirin Akter     - +8801555533311 (Pediatrician)");
        System.out.println("5. Dr. Rakibul Islam    - +8801999888777 (General Physician)");
        System.out.println("");
    }
    
    private static void registerAdmin(){
        System.out.println("\n--- Admin Registration ---");
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        
        int age = getIntInput("Enter your age: ");
        //System.out.println("Enter your age: ");
        //int age = scanner.nextInt();
        
        System.out.print("Enter your gender (Male/Female/Other): ");
        String gender = scanner.nextLine();
        
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        
        Admin newAdmin = new Admin(name, age, gender, email, password);
        if (userService.registerAdmin(newAdmin)){
            System.out.println("Registration successful! You can now login.");
        }
        else{
            System.out.println("Admin already exists! Please try logging in or registration failed.");
        }
    }
    
    private static int getIntInput(String prompt){
        int val = -1;
        while (val<0){
            System.out.println(prompt);
            try{
                val = Integer.parseInt(scanner.nextLine());
                if(val<0){
                    System.out.println("Please enter a valid positive integer.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Invalid input! Please enter a number.");
            }
        }
        return val;
        
    }

    private static void loginAdmin(){
        System.out.println("\n--- Admin Login ---");
        System.out.print("Enter admin email: ");
        String email = scanner.nextLine();
    
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        
        Admin admin = userService.loginAdmin(email, password);
            if(admin != null){
                System.out.println("\nAdmin login successful! Welcome, "+admin.getName());
        
            //System.out.println(admin);
                //AdminService adminService;
                adminService = new AdminService(
                    diseaseService.getDiseaseList(),
                    diseaseService.getDiseaseInfoMap()
            );
        
             while(true){
            
                System.out.println("\n----- Admin Panel -----");
                System.out.println("\n1. View All Users");
                System.out.println("2. Delete User");
                System.out.println("3. Add Disease");
                System.out.println("4. Edit Disease");
                System.out.println("5. Delete Disease");
                System.out.println("6. View All Diseases");
                System.out.println("7. Logout");
                System.out.println("-------------------");
                System.out.print("Enter yout choice: ");
            
                int choice = getChoice();

                switch (choice) {
                
                    case 1 -> {
                        adminService.viewAllUsers();
                    
                    }
                
                    case 2 -> {
                        System.out.print("Enter user email to delete: ");
                        String emailToDelete = scanner.nextLine();
                        adminService.deleteUserByEmail(emailToDelete);
                    
                    }
                
                
                    case 3-> addDisease();
                        
                

                    case 4 -> adminService.editDisease(scanner);
                       
                     

                    case 5 -> adminService.deleteDisease(scanner);

                    case 6 -> adminService.viewAllDisease();

                    case 7 -> {
                        System.out.println(" Logging out from Admin Panel.");
                        return;
                    }

                    default -> System.out.println(" Invalid choice.");
                }
            }

        }
    }
    
    public static void addDisease(){
        System.out.println("\n-----Add New Disease-----");
        System.out.print("Enter Disease Name: ");
        String name = scanner.nextLine().trim();
        
        Map<String, Double> symptoms = new HashMap<>();
        System.out.println("Enter symptoms with weight (0.0 to 1.0) and type 'done' to finish: ");
        
        while(true){
            System.out.print("Symptom: ");
            String symptom = scanner.nextLine().trim().toLowerCase();
            if(symptom.equalsIgnoreCase("done")){
                break;
            }
            
            double weight = -1;
            while(weight < 0 || weight>1){
                System.out.print("Enter weight for "+symptom+" (0.0 to 1.0): ");
                try{
                    weight = Double.parseDouble(scanner.nextLine());
                    if(weight < 0 || weight > 1){
                        System.out.print("Please enter a valid weight between 0.0 to 1.0 .");
                    }
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid input! Please enter a number.");
                }
            }
            symptoms.put(symptom, weight);
        }
        
        System.out.print("Enter Description: ");
        String description = scanner.nextLine().trim();
        
        System.out.print("Enter Precautions (type 'done' to finish): ");
        List<String> precautions = new ArrayList<>();
        
        while (true) {
            
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("done")) {
                break;
            }
            precautions.add(line);
        }
        
        System.out.println("Enter Prescription (type 'done' to finish):");
        List<String> prescription = new ArrayList<>();
        
        while (true) {
            
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("done")) {
                break;
            }
            prescription.add(line);
        }
        
        System.out.print("Severity (LOW/MEDIUM/HIGH): ");
        String severity = scanner.nextLine().toLowerCase();
        
        Disease disease = new Disease(name, symptoms);
        DiseaseInfo diseaseInfo = new DiseaseInfo(name, description, precautions, prescription, severity);
        
        diseaseService.addDisease(disease, diseaseInfo);
        FileHandler.saveDiseases(diseaseService.getDiseaseList(), diseaseService.getDiseaseInfoMap());
        
        System.out.println("\nDisease added successfully!\n\n");
        
    }
    
    private static void preloadAdmin(){
        if(userService.loginAdmin("admin@dds.com", "admin123") == null){
            Admin defaultAdmin = new Admin("Mr. Admin", 30, "Other", "admin@dds.com", "admin123");
            userService.registerAdmin(defaultAdmin);
        }
    }
}
