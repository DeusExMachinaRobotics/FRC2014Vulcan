/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.project;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.project.RobotMap;
import dem.world.Target;
import edu.wpi.first.wpilibj.project.MetaTCPVariables;

//import edu.wpi.first.wpilibj.Joystick;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    //DriverStationLCD lcd = new DriverStationLCD();
    public void robotInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, ""+RobotMap.mdu.dataMessage.toString());
        RobotMap.handler.arcadeDrive(RobotMap.stickLeft);
        
        if(RobotMap.stickRight.getRawButton(2)) {
            RobotMap.scoop.setState(RobotMap.scoop.READY);
        } else if(RobotMap.stickRight.getRawButton(3)) {
            RobotMap.scoop.setState(RobotMap.scoop.CARRY);            
        } else if(RobotMap.stickRight.getRawButton(4)) {
            RobotMap.scoop.setState(RobotMap.scoop.DUMP);               
        } else if(RobotMap.stickRight.getRawButton(5)) {
            RobotMap.scoop.setState(RobotMap.scoop.STOW);            
        } else if(Math.abs(RobotMap.stickRight.getY()) > .1) {
            RobotMap.scoop.setPosition(RobotMap.scoop.getMainPosition(),RobotMap.scoop.getEndPosition() + 0.01 * RobotMap.stickRight.getY());
        } else {
            
        }
        /*****************************************************
        ** Mdu is a MetaTCPVariables object introduced in RobotMap. 
        ** It controls packet reception on the cRIO side.
        ******************************************************/
        //Target t = new Target((float) RobotMap.mdu.dataMessage.elementAt(0),(float)  RobotMap.mdu.dataMessage.elementAt(1),(float)  RobotMap.mdu.dataMessage.elementAt(2), 0, 0, 0, RobotMap.Vulcan);
        
        //    RobotMap.mdu.update();
        
        /*****************************************************
        ** Update the Driver Station with our edits
        ** It's important to call this at the end of the method.
        ******************************************************/
        DriverStationLCD.getInstance().updateLCD();
    }
     
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
