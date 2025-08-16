
package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Doctor;
import java.util.*;

public class DoctorService {
    
    private final Map<String, List<Doctor>> doctorMap = new HashMap<>();
    
    public DoctorService(){
    
        initializeDoctors();
    }

    private void initializeDoctors() {
        doctorMap.put("Influenza (Flu)", List.of(
        new Doctor("Dr. Ayesha Rahman", "General Physician", "01712345678"),
        new Doctor("Dr. Kamal Hossain", "Pulmonologist", "01987654321")
    ));
        
    doctorMap.put("Chickenpox", List.of(
        new Doctor("Dr. Nusrat Sultana", "Dermatologist", "01812345678")
    ));
    
    doctorMap.put("Hypertension", List.of(
        new Doctor("Dr. Mehedi Hasan", "Cardiologist", "01611223344")
    ));
    
    }
    
    public List<Doctor> getDoctorByDisease(String diseaseName){
        return doctorMap.getOrDefault(diseaseName, new ArrayList<>());
    }
}
