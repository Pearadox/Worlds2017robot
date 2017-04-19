package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;
import org.usfirst.frc.team5414.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RevDBDisabledAuto extends Command {
	
	

    public RevDBDisabledAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.currentAutoMode = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.revdigitboard.getButtonA() && !Robot.btnA)
		{
			if(Robot.currentAutoMode < RobotMap.numberOfModes)
			{
				Robot.currentAutoMode++;
			}
		}

		if(Robot.revdigitboard.getButtonB() && !Robot.btnB)	
		{
			if(Robot.currentAutoMode > 0)
			{
				Robot.currentAutoMode--;
			}
		}
		Robot.btnA = Robot.revdigitboard.getButtonA();
		Robot.btnB =Robot.revdigitboard.getButtonB();
		SmartDashboard.putNumber("Current Auto Mode", Robot.currentAutoMode);
		Robot.revdigitboard.display(Robot.currentAutoMode);
		DriverStation.reportWarning("CURRENT AUTO MODE: " + Robot.currentAutoMode, true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.isDisabled;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
