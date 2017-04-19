package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StrafeLeft extends Command {

	double speed;
	double time;
	double startingyaw;
	
	public StrafeLeft(double time)
	{
		this(1, time);
	}
	
    public StrafeLeft(double speed, double time) {
        requires(Robot.drivetrain);
        this.speed = Math.abs(speed);
        this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	new ActivateMecanum();
    	startingyaw = Robot.gyro.getYaw();
    	setTimeout(time);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double differenceinyaw = Robot.gyro.getYaw() - startingyaw;
    	Robot.drivetrain.mecanumDrive(-speed, 0, differenceinyaw);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.stop();
    }
}
