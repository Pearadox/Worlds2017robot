package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberLimitSwitch extends Command {

	boolean isPressed;
	boolean ignoreNextSwitch;
	
    public ClimberLimitSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isPressed = false;
    	if(Robot.climber.isLimitSet())
    	{
    		ignoreNextSwitch = true;
    	}
    	else ignoreNextSwitch = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(ignoreNextSwitch)
    	{
    		if(Robot.climber.isLimitSet()) Robot.climber.startPos();
    		else ignoreNextSwitch = false;
    	}
    	else 
    	{
	    	if(Robot.climber.isLimitSet()){
	    		Robot.climber.stop();
	    		isPressed = true;
	    	}else{
	    		Robot.climber.startPos();
	    	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(isPressed){
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
