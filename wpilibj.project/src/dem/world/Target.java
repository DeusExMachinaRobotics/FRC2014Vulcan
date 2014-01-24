/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dem.world;

import dem.data.Vector3f;
import dem.world.Robot;

/**
 *
 * @author Evan Coleman
 */
public class Target {
    public static Robot robot;
    //x,y,z,p,y,r values
    //x,y,x in units of inches
    //p,y,r in units of degrees
    float x,y,z,pitch,yaw,roll;
    
    public Target(float x, float y, float z, float pitch, float yaw, float rol1, Robot r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.linkRobot(r);
    }
    
    public void linkRobot(Robot r) {
        this.robot = r;
    }
    
    public Vector3f robotDistance() {
        Vector3f dist = new Vector3f(this.x - robot.x, this.y - robot.y, this.z - robot.z);
        return dist;
    }
    
}

