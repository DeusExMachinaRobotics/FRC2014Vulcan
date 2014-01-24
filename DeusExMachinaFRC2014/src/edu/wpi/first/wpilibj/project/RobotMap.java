/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.project;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.project.MetaTCPVariables;
import dem.world.Robot;
import dem.world.Scoop;
/**
 *
 * @author Evan Coleman
 */
public class RobotMap {
    public static Robot Vulcan = new Robot(0,0,0);
    //stickLeft controls Movement while stickRight controls Shooter.
    public static Joystick stickLeft = new Joystick(1);
    public static Joystick stickRight = new Joystick(2);
    
    /**********************************************************************
    Victor 1 is bLeft side whilst Jaguar 2 is bRight 
    
    IMPORTANT: Jaguars must be connected to PWM 2 through logic splitters
    Victors go on PWM 1. ROBOTDRIVE WILL NOT WORK IF PWMs ARE IN WRONG ORDER.
    ************************************************************************/
    //static Jaguar bLeft = new Jaguar(1);
    static Victor bLeft = new Victor(1);
    static Jaguar bRight = new Jaguar(2);
    
    //"handler" is our RobotDriveHandler intended for use in ArcadeDrive
    public static RobotDrive handler = new RobotDrive(bLeft, bRight);
    
    //MetaTCPVariables is the handler for breaking down intercepted packets from main computer
    public static MetaTCPVariables mdu = new MetaTCPVariables();
    
    ////
    public static Scoop scoop = new Scoop(3,4);
}
