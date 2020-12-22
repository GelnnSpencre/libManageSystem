
package commons;

import java.io.Serializable;

public class Admin implements Serializable {
    
    private String adminID;
    private String adminName;
    private char[] admin_passkey;
    private boolean nowLogged;
    
    public Admin(String adminID, String adminName, char[] admin_passkey) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.admin_passkey = admin_passkey;
        this.nowLogged = false;
    }
    
    /*
    Setters/Mutators
    */
    
    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }
    
    public void setAdminPassKey(char[] admin_passkey) {
        this.admin_passkey = admin_passkey;
    }
    
    public void setNowLogged(boolean b) {
        this.nowLogged = b;
    }
    
    /*
    Getters/Accessors
    */
    
    public String getAdminID() {
        return adminID;
    }
    
    public String getAdminName() {
        return adminName;
    }
    
    public char[] getAdminPassKey() {
        return admin_passkey;
    }
    
    public boolean isCurrentlyLogged() {
        return nowLogged;
    }
    
}
