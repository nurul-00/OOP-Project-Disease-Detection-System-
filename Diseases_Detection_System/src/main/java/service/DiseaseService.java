package service;

import database.FileHandler;
import model.Disease;
import model.DiseaseInfo;

import java.util.*;

public class DiseaseService {
    
    private final List<Disease> diseaseList ;
    private final Map<String, DiseaseInfo> diseaseInfoMap ;
    
    public DiseaseService(){
        this.diseaseList = new ArrayList<>();
        this.diseaseInfoMap = new HashMap<>();
    }

    public DiseaseService(List<Disease> diseaseList, Map<String, DiseaseInfo> diseaseInfoMap) {
        this.diseaseList = diseaseList;
        this.diseaseInfoMap = diseaseInfoMap;
        
        //initializeDiseases();
        //initializeDiseaseInfo();
    }
    
    public List<Disease> getDiseaseList() {
        return diseaseList;
    }

    public Map<String, DiseaseInfo> getDiseaseInfoMap() {
         return diseaseInfoMap;
    }
    
    public void addDisease(Disease disease, DiseaseInfo diseaseInfo){
        diseaseList.add(disease);
        diseaseInfoMap.put(disease.getName(), diseaseInfo);
    }
    
    public Map<Disease, Double> detectDisease(List<String> userSymptoms) {
        Map<Disease, Double> matchResults = new HashMap<>();

        for (Disease disease : diseaseList) {
            Map<String, Double> symptomsWithWeight = disease.getSymptomsWithWeights();

            double totalWeight = symptomsWithWeight.values().stream().mapToDouble(Double::doubleValue).sum();
            double matchedWeight = 0.0;
            

            for (String symptom : userSymptoms) {
                if (symptomsWithWeight.containsKey(symptom)) {
                    matchedWeight += symptomsWithWeight.get(symptom);
                }
            }
            
            if (totalWeight > 0) {
                double matchPercent = (matchedWeight / totalWeight) * 100;
                if (matchPercent > 0) {
                    matchResults.put(disease, matchPercent);
                }
            }
        }

        if (matchResults.isEmpty()) {
            return Collections.emptyMap();
        }
        
        Map.Entry<Disease, Double> maxEntry = Collections.max(matchResults.entrySet(), Map.Entry.comparingByValue());
        Map<Disease, Double> bestMatch = new HashMap<>();
        bestMatch.put(maxEntry.getKey(), maxEntry.getValue());

        return bestMatch;
    }

    
    public DiseaseInfo getDiseaseInfo(String diseaseName) {
        return diseaseInfoMap.get(diseaseName);
    }

    /*public void editDisease(String name, Map<String, Double> newSymptoms, String description, String precautions, List<String> prescription, String severity) {
        // Update symptoms and diseaseInfo if disease exists
        for (Disease disease : diseaseList) {
            if (disease.getName().equalsIgnoreCase(name)) {
                disease.setSymptomsWithWeights(newSymptoms);
                DiseaseInfo info = new DiseaseInfo(name, description, precautions, prescription, severity);
                diseaseInfoMap.put(name, info);
                break;
            }
        }
    }*/
    
    /*public void deleteDisease(String name) {
        diseaseList.removeIf(d -> d.getName().equalsIgnoreCase(name));
        diseaseInfoMap.remove(name);
    }*/
}
    /*public void loadFromFile(){
        List<Disease> diseaseFromFile = FileHandler.loadDiseaseList();
        Map<String, DiseaseInfo> infoFrom = FileHandler.loadDiseaseInfoMap();
    }*/

    
    
    
    /*private void initializeDiseases(){
        diseaseList.add(new Disease("Flu", Map.of("fever",1.0, "cough",0.8, "headache",0.7, "fatigue",0.6, "sore throat", 0.5, "runny nose", 0.4)));
        //diseaseList.add(new Disease("Common Cold", List.of("cought", "sore throat", "runny nose", "sneezing")));
        //diseaseList.add(new Disease("Migraine", List.of("headache", "nausea", "sensitivity to light", "dizziness")));
        diseaseList.add(new Disease("COVID-19", Map.of("fever", 1.0, "cough", 0.9, "shortness of breath", 0.8, "loss of taste", 0.7, "loss of smell", 0.6, "fatigue", 0.5)));
        diseaseList.add(new Disease("Malaria", Map.of("fever", 1.0, "chills", 0.9, "sweating", 0.8, "headache", 0.7, "nausea", 0.6, "vomiting", 0.5)));
        diseaseList.add(new Disease("Dengue", Map.of("fever", 1.0, "rash", 0.9, "joint pain", 0.8, "muscle pain", 0.7, "headache", 0.6, "fatigue", 0.5)));
        /*diseaseList.add(new Disease("Diabetes", List.of("increased thirst", "frequent urination", "fatigue", "blurred vision")));
        diseaseList.add(new Disease("HyperTension", List.of("headache", "chest pain", "fatigue", "irregular heartbeat")));
        diseaseList.add(new Disease("Asthma", List.of("shortness of breath", "coughing", "wheezing", "chest tightness")));
        diseaseList.add(new Disease("Typhoid", List.of("high fever", "weakness", "stomach pain", "loss of appetite")));
        diseaseList.add(new Disease("Tuberculosis", List.of("persistent cough", "weight loss", "night sweats", "fever")));
        diseaseList.add(new Disease("Chickenpox", List.of("rash", "fever", "triedness", "loss of appetite")));
        diseaseList.add(new Disease("Pneumonia", List.of("fever", "chest pain", "shortness of breath", "cough")));
        diseaseList.add(new Disease("Hepatitis B", List.of("fatigue", "nausea", "dark urine", "yellowing of skin")));
        diseaseList.add(new Disease("Allergy", List.of("sneezing", "itchy eyes", "runny nose", "skin rash")));*/
        
    //}
    
    //private void initializeDiseaseInfo(){
        /*diseaseInfoMap.put("flu", new DiseaseInfo(
                "Flu",
                "A contagious respiratory illness caused by influenza viruses.",
                "Get vaccinated yearly, rest, and stay hydrated.",
                List.of("Paracetamol", "Antihistamines", "Rest"),
                "MEDIUM"
        ));*/
        
        /*diseaseInfoMap.put("common Cold", new DiseaseInfo("Common Cold",
        "A viral infection of your nose and throat (upper respiratory tract).",
        "Wash hands frequently, avoid touching face, use tissues.",
        "Rest, hydration, over-the-counter cold remedies."
        ));*/
        
        /*diseaseInfoMap.put("migraine", new DiseaseInfo("Migraine",
        "A neurological condition causing intense headache, nausea, and sensitivity.",
        "Avoid triggers, maintain sleep schedule, reduce stress.",
        "Pain relivers, prescription medeications, lifestyle changes."
        ));*/
        
        /*diseaseInfoMap.put("covid-19", new DiseaseInfo(
                "Covid-19",
                "A respiratory illness caused by the coronavirus SARS-CoV-2.",
                "Isolate, wear masks, maintain hygiene.",
                List.of("Paracetamol", "Zinc", "Vitamin C"),
                "HIGH"
        ));
        
        diseaseInfoMap.put("malaria", new DiseaseInfo(
                "Malaria",
                "A disease caused by a plasmodium parasite, transmitted by the bite of infected mosquitoes.",
                "Avoid mosquito bites, use bed nets.",
                List.of("Chloroquine", "Paracetamol"),
                "HIGH"
        ));
        
        diseaseInfoMap.put("dengue", new DiseaseInfo(
                "Dengue",
                "A mosquito-borne viral disease causing severe flu-like illness.",
                "Avoid mosquito bites, use repellents, eliminate mosquito habitats.",
                List.of("Paracetamol 500mg – 1 tablet every 6 hours", "ORS for hydration", "Complete bed rest for 3–5 days"),
                "HIGH"
        ));
        
        /*diseaseInfoMap.put("diabetes", new DiseaseInfo("Diabetes",
        "A chronic disease that affects how your body turns food into energy.",
        "Healthy diet, regular exercise, monitor blood sugar.",
        "Insulin, oral medication, lifestyle changes."
        ));
        
        diseaseInfoMap.put("hypertension", new DiseaseInfo("HyperTension",
        "High blood pressure that can lead to heart disease and stroke.",
        "Low-sodium diet, regular physical activity, manage stress.",
        "Blood pressure medication, lifestyle change."
        ));
        
        diseaseInfoMap.put("asthma", new DiseaseInfo("Asthma",
        "A condition in which airways narrow and swell and may produce extra mucus.",
        "Avoid triggers, use inhalers as prescribed.",
        "Inhalers, corticosteroids, breathing exercises."
        ));
        
        diseaseInfoMap.put("typhoid", new DiseaseInfo("Typhoid",
        "A bacterial infection due to Salmonella typhi.",
        "Drink safe water, practice proper hygiene, get vaccinated.",
        "Antibiotics prescribed by doctor, rest, fluid intake."
        ));
        
        diseaseInfoMap.put("tuberculosis", new DiseaseInfo("Tuberculosis",
        "A potentially serious infections bacterial disease that mainly affects the lunges.",
        "Avoid close contact with infected people, good ventilation.",
        "Long-term antibiotics, proper medical supervision."
        ));
        
        diseaseInfoMap.put("chickenpox", new DiseaseInfo("Chickenpox",
        "A highly contagious viral infection causing an itchy rash and red spots.",
        "Avoid contact with infected individuals, get vaccinated.",
        "Calamine lotion, antiviral drugs, rest."
        ));
        
        diseaseInfoMap.put("pneumonia", new DiseaseInfo("Pneumonia",
        "Infection that inflames air sacs in one or both lungs.",
        "Get vaccinated, maintain hygiene, avoid smoking.",
        "Antibiotics, cough medicine, fever reducers."
        ));
        
        diseaseInfoMap.put("hepatitis b", new DiseaseInfo("Hepatitis B",
        "A serious liver infection caused by the hepatities B virus.",
        "Avoid sharing needles, practice safe sex, get vaccinated.",
        "Antiviral medication, liver monitoring, rest."
        ));
        
        diseaseInfoMap.put("allergy", new DiseaseInfo("Allergy",
        "A condition in which the immune system reacts abnormally to a foreign substance.",
        "Avoid allergens, use air purifiers, allergy-proof home.",
        "Antihistamines, nasal sprays, allergy shots."
        ));*/
    
    
    /**
     *
     * @param userSymptoms
     * @return
     *
    public Map<Disease, Double> detectDisease(List<String> symptoms) {
        Map<Disease, Double> matched = new HashMap<>();

        for (Disease disease : diseaseList) {
            double matchScore = 0.0;

            Map<String, Double> knownSymptoms = disease.getSymptomsWithWeight();

                for (String symptom : symptoms) {
                if (knownSymptoms.containsKey(symptom)) {
                    matchScore += knownSymptoms.get(symptom);
                }
            }
            
            double totalPossible = knownSymptoms.values().stream().mapToDouble(Double::doubleValue).sum();
            double matchPercentage = (matchScore / totalPossible) * 100.0;
            
            if (matchPercentage >= 50) {
                matched.put(disease, matchPercentage);
            }
        }

        return matched;
    }*/

    

    

    /*private String formatPrescription(List<String> prescription) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prescription.size(); i++) {
            sb.append(i + 1).append(". ").append(prescription.get(i)).append("\n");
        }
        return sb.toString();
    }
}

    
/*    public DiseaseInfo getDiseaseInfo(String diseaseName){
        if(diseaseName == null) return null;
        return diseaseInfoMap.get(diseaseName.toLowerCase());
    }
    
    // তোমার কাঙ্খিত আউটপুট আকারে প্রিন্ট করার জন্য helper method
    public void printDiseaseOutput(Disease disease, double matchPercent) {
        DiseaseInfo info = getDiseaseInfo(disease.getName());
        if (info == null) {
            System.out.println("No detailed info found for " + disease.getName());
            return;
        }
        
        System.out.println("🦠 Detected Disease: " + disease.getName() + " (" + String.format("%.2f", matchPercent) + "% match)");
        System.out.println("⚠️ Severity Level: " + info.getSeverity());
        System.out.println("📄 Description: " + info.getDescription());
        System.out.println("🛡️ Precautions: " + info.getPrecautions());
        System.out.println("💊 Prescription:");
        
        List<String> prescriptions = info.getPrescription();
        for (int i = 0; i < prescriptions.size(); i++) {
            System.out.println((i + 1) + ". " + prescriptions.get(i));
        }
        System.out.println("---------------------------------------");
    }
}
    
*/

