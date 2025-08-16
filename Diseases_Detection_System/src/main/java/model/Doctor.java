
package model;

public class Doctor {
    
    private final String name;
    private final String specialization;
    private final String contact;
    
    public Doctor(String name, String specialization, String contact) {
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }
    
    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContact() {
        return contact;
    }
    
    @Override
    public String toString() {
        return name + " (" + specialization + ") - " + contact;
    }
}
