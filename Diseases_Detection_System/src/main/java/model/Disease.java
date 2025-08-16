
package model;

import java.io.Serializable;
import java.util.Map;

public class Disease implements Serializable{
    private String name;
    private Map<String, Double> symptomsWithWeights;
    
    public Disease(String name, Map<String, Double> symptomsWithWeights){
        this.name = name;
        this.symptomsWithWeights = symptomsWithWeights;
    }
    
    public String getName(){
        return name;
    }
    
    public Map<String, Double> getSymptomsWithWeights(){
        return symptomsWithWeights;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setSymptomsWithWeights(Map<String, Double> symptomsWithWeights) {
        this.symptomsWithWeights = symptomsWithWeights;
    }
    
    @Override
    public String toString(){
        return "Disease{Name: '"+name+"', symptoms: "+symptomsWithWeights;
    }

}