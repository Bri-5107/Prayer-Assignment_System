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
public class PrayerAssigner {
    
    
    public static Map<PrayerMember,Family> assignFamily(
            List<PrayerMember> allMembers, 
            List<Family> allFamilies){
        
        //Store family assignments
        Map<PrayerMember, Family> assignments = new LinkedHashMap<>();
        
        //Filter present members  
        ArrayList<PrayerMember> presentMembers = new ArrayList<>();
        for (PrayerMember member : allMembers) {
            if (member.getPresent() == true) {
                presentMembers.add(member);
            }
        }
        
        if (allMembers.isEmpty() || allFamilies.isEmpty()) {
            return assignments;
        }
        
        
        //Prepare families by sorting based on priority 
        List<Family> sortedFamilies = new ArrayList<Family>(allFamilies);
        Collections.sort(sortedFamilies);
        
        
        //Assign 1:1
        int familyIndex = 0;
        for (PrayerMember member : presentMembers) {
            while (familyIndex < sortedFamilies.size()) {
                Family family = sortedFamilies.get(familyIndex);

                // STEP 3b: Check if member is part of the same family
                if (isMemberPartOfFamily(member, family)) {
                    // Move this family to the back and skip it
                    sortedFamilies.remove(familyIndex);
                    sortedFamilies.add(family);
                } else {
                    // STEP 3c: Assign family and move to next member
                    assignments.put(member, family);
                    familyIndex++;
                    break;
                }
            }
        }
        
        updateQueuePriorities(sortedFamilies, assignments);
        
        return assignments;
        
    }
    // Helper: Check if member belongs to a family
    private static boolean isMemberPartOfFamily(PrayerMember member, Family family) {
        for (String famMember : family.getFamilyMembers()) {
            if (famMember.equalsIgnoreCase(member.getMemberName())) {
                return true;
            }
        }
        return false;
    }

    // Helper: Rotate queue priorities (assigned go to back)
    private static void updateQueuePriorities(List<Family> sortedFamilies, Map<PrayerMember, Family> assignments) {
        Set<Family> assigned = new HashSet<>(assignments.values());
        int newPriority = 0;

        // Unassigned families first (lower priority number)
        for (Family f : sortedFamilies) {
            if (!assigned.contains(f)) {
                f.setQueuePriority(newPriority++);
            }
        }

        // Assigned families after (higher priority number)
        for (Family f : sortedFamilies) {
            if (assigned.contains(f)) {
                f.setQueuePriority(newPriority++);
            }
        }
    }

}
