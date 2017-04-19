package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;
import org.usfirst.frc.team5414.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ActivateHalf extends Command {

    public ActivateHalf() {
    	requires(Robot.drivetrain);
    }

  
    protected void initialize() {
    	Robot.drivetrain.FullButterfly();		//sets robot to half traction half omni wheels
    }

    
    protected void execute() {

    	Robot.drivetrain.mecanumDrive(Robot.oi.getJoystick1());		//sets drivestrain to mechanum drive
    }

    
    protected boolean isFinished() {
    	return true;
    }

    
    protected void end() {
//    	Robot.drivetrain.FullTraction();							//sets the robot back into all traction wheels
    	Robot.drivetrain.mecanumDrive(Robot.oi.getJoystick1());		// sets drivetrain back to arcade drive
    }

   
    protected void interrupted() {
    }
}
