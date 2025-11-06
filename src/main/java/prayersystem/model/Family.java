/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prayersystem.model;
import java.util.*;

/**
 *
 * @author briandionisio
 */
public class Family implements Comparable<Family>{
    private String familyName;
    private ArrayList<String> familyMembers;
    private int queuePriority;
    
    public Family() {
        this.familyMembers = new ArrayList<>();
        this.queuePriority = 0;
    }
    //constructor for initialization of family name and members
    public Family(String familyName, List<String> familyMembers){
        this.familyName = familyName;
        this.familyMembers = new ArrayList<String>(familyMembers);
        queuePriority = 0;
        
    }
    
    //getter 
    public String getFamilyName(){
        return familyName;
    }
    
    public void setFamilyName(String familyName){
        this.familyName = familyName;
    }
    
    public ArrayList<String> getFamilyMembers(){
        return familyMembers;
    }
    
    public void setFamilyMembers(ArrayList<String> familyMembers) {
        this.familyMembers = new ArrayList<>(familyMembers);
    }
    
    public int getQueuePriority(){
        return queuePriority;
    }
    
    public void setQueuePriority (int queuePriority){
        this.queuePriority = queuePriority;
    }
    
    //to display family members as string without []
    public String convertArrayList(){
        return String.join(", ", familyMembers);
    }
    
    //to display family members 
    public String toString() {
        return "Family: " + familyName + "|" + convertArrayList();
    }

    @Override
    public int compareTo(Family other) {
        return Integer.compare(this.queuePriority, other.queuePriority);
    }
}
    

