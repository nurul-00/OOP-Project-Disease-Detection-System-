
package service;

import model.User;

import database.FileHandler;

import model.Disease;
import model.DiseaseInfo;


import java.util.*;

public class AdminService {
    private final List<Disease> diseaseList ; //= new ArrayList<>() ;
    private final Map<String, DiseaseInfo> diseaseInfoMap ;//= new HashMap<>(); ;
    
    public AdminService(List<Disease> diseaseList, Map<String, DiseaseInfo> diseaseInfoMap){
        this.diseaseList = diseaseList;
        this.diseaseInfoMap = diseaseInfoMap;
    }
    
    public void addDisease(String name, Map<String,Double> symptomsWithWeight, String description, List<String> precautions, List<String> prescriptionList, String severity) {
       
        Disease disease = new Disease(name, symptomsWithWeight);
        DiseaseInfo info = new DiseaseInfo(name, description, precautions, prescriptionList, severity);
        
        diseaseList.add(disease);
        diseaseInfoMap.put(name.toLowerCase().trim(), info);
        FileHandler.saveDiseases(diseaseList, diseaseInfoMap);
        
        System.out.println(" Disease added successfully.");
    }
    
    public void editDisease(Scanner scanner) {
    System.out.print("Enter the name of the disease to edit: ");
    String name = scanner.nextLine().trim();

    Disease diseasetoEdit = null;
    for (Disease d : diseaseList) {
        if (d.getName().equalsIgnoreCase(name)) {
            diseasetoEdit = d;
            break;
        }
    }

    if (diseasetoEdit == null) {
        System.out.println("Disease not found.");
        return;
    }

    System.out.println("Editing " + name);

    // Symptoms input
    Map<String, Double> newSymptoms = new HashMap<>();
    System.out.println("Enter symptoms (type 'done' to finish): ");
    while (true) {
        System.out.print("Symptom: ");
        String symptom = scanner.nextLine().trim();
        if (symptom.equalsIgnoreCase("done")) break;

        System.out.print("Weight (range 0.1 - 1.0): ");
        double weight = Double.parseDouble(scanner.nextLine().trim());
        if (weight < 0.1 || weight > 1.0) {
            System.out.println("Invalid weight! Please enter a value between 0.1 and 1.0");
            continue;
        }

        newSymptoms.put(symptom, weight);
    }

    // ⚠️ Set new symptoms
    diseasetoEdit.setSymptomsWithWeights(newSymptoms);

    // DiseaseInfo update
    System.out.print("Enter new description: ");
    String description = scanner.nextLine();
    
    List<String> precautions = new ArrayList<>();
    System.out.println("Enter precautions (type 'done' to finish):");
    while (true) {
        System.out.print("Precaution: ");
        String presc = scanner.nextLine().trim();
        if (presc.equalsIgnoreCase("done")) break;
        precautions.add(presc);
    }
    System.out.print("Enter new severity: ");
    String severity = scanner.nextLine();

    List<String> prescription = new ArrayList<>();
    System.out.println("Enter prescriptions (type 'done' to finish):");
    while (true) {
        System.out.print("Prescription: ");
        String presc = scanner.nextLine().trim();
        if (presc.equalsIgnoreCase("done")) break;
        prescription.add(presc);
    }

    // ⚠️ DiseaseInfo update
    DiseaseInfo updatedInfo = new DiseaseInfo(name, description, precautions, prescription, severity);
    diseaseInfoMap.put(name, updatedInfo);

    // Save changes
    FileHandler.saveDiseases(diseaseList, diseaseInfoMap);
    System.out.println("Disease updated successfully.");
}
            
        
    
    
    public void deleteDisease(Scanner scanner) {
        System.out.print("Enter the name of the disease to delete: ");
    String name = scanner.nextLine().trim();

    Disease diseaseToDelete = null;
    for (Disease d : diseaseList) {
        if (d.getName().equalsIgnoreCase(name)) {
            diseaseToDelete = d;
            break;
        }
    }
    
    if (diseaseToDelete == null) {
        System.out.println("Disease not found.");
        return;
    }

    diseaseList.remove(diseaseToDelete);
    diseaseInfoMap.remove(name);
    FileHandler.saveDiseases(diseaseList, diseaseInfoMap);
    System.out.println("Disease deleted successfully.");
    
    }
    
    
    
    public void viewAllDisease(){
        if(diseaseList.isEmpty()){
            System.out.println("No disease available.");
            return;
        }
        
        for(Disease disease : diseaseList){
            System.out.println("\n");
            System.out.println("Name: "+disease.getName());
            System.out.println("Symptoms & Weight: ");
            
            for (Map.Entry<String, Double> entry : disease.getSymptomsWithWeights().entrySet()) {
            System.out.println(" - " + entry.getKey() + " : " + entry.getValue());
            }
            
            DiseaseInfo info = diseaseInfoMap.get(disease.getName());
            if(info != null){
                System.out.println("Severity: " + info.getSeverity());
                System.out.println("Description: " + info.getDescription());
                System.out.println("Precautions: " + info.getPrecautions());
                System.out.println("Prescription: " + String.join(", ", info.getPrescription()));
                System.out.println("\n");
            }
            else{
                System.out.println("No detailed info available.");
            }
        }
        System.out.println("\n\n");
    }  
    
    
    public void viewAllUsers() {
        List<User> users = FileHandler.readUsers();

        int count = 0;
        for (User user : users) {
        
            if (user.getEmail().equalsIgnoreCase("admin@dds.com")) {
                continue;
            }

            count++;
            System.out.println("");
            System.out.println("   Name: " + user.getName());
            System.out.println("   Age: " + user.getAge());
            System.out.println("   Gender: " + user.getGender());
            System.out.println("   Email: " + user.getEmail());
            System.out.println("");
        }

        if (count == 0) {
            System.out.println(" No users registered yet.");
        } 
        
        else {
            System.out.println("------------------------");
            System.out.println(" Total Users: " + count);
        }
    }
    
    public void deleteUserByEmail(String email) {
        List<User> users = FileHandler.readUsers();
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
            FileHandler.writeUsers(users);
            System.out.println(" User with email \"" + email + "\" has been deleted successfully.");
        } else {
            System.out.println(" No user found with email \"" + email + "\".");
        }
    }

    
}


    

