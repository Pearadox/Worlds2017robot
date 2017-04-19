package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shoot extends Command {

	double time;
	int RPM;

	Timer t = new Timer();
    
    public Shoot(double time)
    {
    	this(time, 3440);
    }
    
    public Shoot(double time, int RPM)
    {
    	requires(Robot.shooter);
    	this.time = time;
    	this.RPM = RPM;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(time);
    	t.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.shoot(RPM);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(t.get()>time)
        	return true;
        else{
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.stop();
    }
}
