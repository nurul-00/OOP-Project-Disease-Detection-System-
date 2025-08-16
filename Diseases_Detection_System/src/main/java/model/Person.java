
package model;

public abstract class Person {
    private String name;
    private int age;
    private String gender;
    private String email;
    
    public Person(String name, int age,String gender, String email){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }
    
    public String getGender(){
        return gender;
    }
    public void setGerder(String gender){
        this.gender = gender;
    }
   
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    public abstract String getRole();
 
    @Override
    public String toString(){
        return "Name: "+name+"\nAge: "+age+"\nGender: "+gender+"\nEmail: "+email;
    }
}

