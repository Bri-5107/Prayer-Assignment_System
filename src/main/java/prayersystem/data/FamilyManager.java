/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prayersystem.data;
import prayersystem.model.Family;
import prayersystem.model.PrayerMember;
import java.util.*;
import java.io.*;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;




/**
 *
 * @author briandionisio
 */
public class FamilyManager {
    private static final String DATA_FILE = "prayer_data.json";
    private List<Family> families;
    private List<PrayerMember> members;
    private Gson gson;
    
    //constructor 
    public FamilyManager() {
         families = new ArrayList<>();
        members = new ArrayList<>();
        
        // Initialize Gson with pretty printing
        gson = new GsonBuilder().setPrettyPrinting().create();
        
        // Try to load existing data, otherwise use defaults
        if (!loadFromFile()) {
            loadDefaultData();
        }
    }
    
    //Load default data
    
    private void loadDefaultData() {
        System.out.println("[INFO] Loading default sample data...");
        
        // Sample families
        families.add(new Family("Cruz Family", Arrays.asList("Maria Cruz", "Jose Cruz")));
        families.add(new Family("Santos Family", Arrays.asList("Juan Santos", "Ana Santos")));
        families.add(new Family("Reyes Family", Arrays.asList("Pedro Reyes", "Rosa Reyes")));
        families.add(new Family("Garcia Family", Arrays.asList("Sofia Garcia", "Miguel Garcia")));
        
        // Sample members
        members.add(new PrayerMember("Maria Cruz"));
        members.add(new PrayerMember("Juan Santos"));
        members.add(new PrayerMember("Pedro Reyes"));
        members.add(new PrayerMember("Sofia Garcia"));
        
        System.out.println("[INFO] Loaded " + families.size() + " families and " + 
                          members.size() + " members");
    }
    
    //CRUD Operations
    
    public void addFamily(Family family) {
        families.add(family);
        System.out.println("[INFO]: Addedd Family " + family.getFamilyName());
    }
    

    public void removeFamily(int index) {
        if (index >= 0 && index < families.size()) { 
            Family removed = families.remove(index);
            System.out.println("[INFO] Removed family: " + removed.getFamilyName());
        }
    }
    
    public void addMember(PrayerMember member) {
        members.add(member);
        System.out.println("[INFO]: Addedd Member " + member.getMemberName());
    }
    

    public void removeMember(int index) {
        if (index >= 0 && index < members.size()) { 
            PrayerMember removed = members.remove(index);
            System.out.println("[INFO] Removed Member: " + removed.getMemberName());
        }
    }
    
    //getters   
    
    public List<Family> getFamilies() {
        return families;
    }
    
    public List<PrayerMember> getMembers() {
        return members;
    }
    
    //inner class for DataWrapper
     private static class DataWrapper {
        List<Family> families;
        List<PrayerMember> members;
        
        public DataWrapper(List<Family> families, List<PrayerMember> members) {
            this.families = families;
            this.members = members;
        }
     }
     
    //save to file
    public boolean saveToFile(){
        try (FileWriter writer = new FileWriter(DATA_FILE)){
            DataWrapper wrapper = new DataWrapper(families, members);
            gson.toJson(wrapper, writer);
            System.out.println("[SUCCESS] Data saved to " + DATA_FILE);
            return true;
            
        } catch (IOException e){
            System.err.println("[ERROR] Failed to save data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
   
    //load from file
    public boolean loadFromFile() {
        File file = new File(DATA_FILE);
        
        if (!file.exists()) {
            System.out.println("[INFO] No saved data file found (" + DATA_FILE + ")");
            return false;
        }
        
        try (FileReader reader = new FileReader(DATA_FILE)) {
          
            DataWrapper wrapper = gson.fromJson(reader, DataWrapper.class);
            
            if (wrapper != null) {
                this.families = wrapper.families != null ? wrapper.families : new ArrayList<>();
                this.members = wrapper.members != null ? wrapper.members : new ArrayList<>();
                
                System.out.println("[SUCCESS] Loaded " + families.size() + 
                                 " families and " + members.size() + " members from " + DATA_FILE);
                return true;
            } else {
                System.out.println("[WARNING] JSON file is empty or invalid");
                return false;
            }
            
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
   
}
