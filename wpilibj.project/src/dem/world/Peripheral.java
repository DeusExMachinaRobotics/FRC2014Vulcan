/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dem.world;

/**
 *
 * @author Evan Coleman
 */
public abstract class Peripheral {
    boolean healthy;
    int STATE;
       
    public boolean getHealth() {
        return healthy;
    }
    
    public abstract void setHealth(boolean h);
    
    public int getState() {
        return STATE;
    }
    
    public abstract void setState(int s);
}
