
package model;

public class Admin extends Person{
    private String password;
    
    public Admin(String name, int age, String gender, String email, String password ){
        super(name, age, gender, email);
        this.password = password;
    }
    
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    @Override
    public String getRole() {
        return "Admin";
    }
    
    @Override
    public String toString(){
        return super.toString()+", Role: Admin";
    }
}