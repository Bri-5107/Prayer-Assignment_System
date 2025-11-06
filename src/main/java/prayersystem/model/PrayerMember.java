/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prayersystem.model;

/**
 *
 * @author briandionisio
 */
public class PrayerMember {
    private String memberName;
    private boolean isPresent;
    
     public PrayerMember() {
        this.isPresent = false;
    }
    
    public PrayerMember(String memberName){
        this.memberName = memberName;
        this.isPresent = false;
    }
    
    public String getMemberName(){
        return memberName;
    }
    
    public void setMemberName(String memberName){
        this.memberName = memberName;
    }
    
    public boolean getPresent(){
        return isPresent;
    }
    
    public void setPresent(boolean present) {
        this.isPresent = present;
    }
    
    @Override
    public String toString() {
        return memberName + (isPresent ? " (Present)" : "");
    }
}
