package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShootStrafe extends CommandGroup {

	public AutoShootStrafe() {
        
    	this(4, .9);
    }
	
	public AutoShootStrafe(int shootingTime)
	{
		this(shootingTime, .9);
	}
	
	public AutoShootStrafe(double strafeTime)
	{
		this(4, strafeTime);
	}

	public AutoShootStrafe(int shootingTime, double strafetime) {
	    
		addSequential(new ActivateMecanum());
		
//		if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) addSequential(new RotateLeft(10));
//		else addSequential(new RotateRight(10));
		
		addSequential(new Shoot(shootingTime));
		
//		if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) addSequential(new RotateRight(10));
//		else addSequential(new RotateLeft(10));
		
		if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) 
		{
			DriverStation.reportWarning("BLUE", true);
		 	addSequential(new StrafeLeft(1, Robot.prefs.getDouble("AUTO_STRAFE_TIME", strafetime)));
		}
		else 
		{
			DriverStation.reportWarning("RED", true);
		 	addSequential(new StrafeRight(1, Robot.prefs.getDouble("AUTO_STRAFE_TIME", strafetime)));   
	
		}
		
		
	}
}
