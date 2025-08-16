package model;

import java.io.Serializable;
import java.util.List;

public class DiseaseInfo implements Serializable{
    private  String name;
    private  String description;
    private List<String> precautions;
    private List<String> prescription;
    private String severity;

    public DiseaseInfo(String name, String description, List<String> precautions, List<String> prescription, String severity) {
        this.name = name;
        this.description = description;
        this.precautions = precautions;
        this.prescription = prescription;
        this.severity = severity;
    }


    public String getName(){ 
        return name; 
    }
    
    public String getDescription(){ 
        return description; 
    }
    
    public List<String> getPrecautions(){ 
        return precautions; 
    }
    
    public List<String> getPrescription(){ 
        return prescription; 
    }
    
    public String getSeverity(){
        return severity; 
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPrecautions(List<String> precautions) {
        this.precautions = precautions;
    }
    
    public void setPrescription(List<String> prescription) {
        this.prescription = prescription;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString(){
        return
               "Disease Name: " + name + "\n" +
               "Severity Level: " + severity + "\n" +
               "Description: " + description + "\n" +
               "Precautions: " + precautions + "\n" +
               "Prescription:\n" + prescription.toString();
        
    }

    public String getTreatment() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    
   