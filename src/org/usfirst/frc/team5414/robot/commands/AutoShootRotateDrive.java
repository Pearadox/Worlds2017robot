 package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShootRotateDrive extends CommandGroup {

    public AutoShootRotateDrive() {
    	
    	
    	addSequential(new Shoot(5));
    	if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) 
		{
//			addSequential(new RotateLeft(90));
		}
		else 
		{   
//			addSequential(new RotateRight(90));
		}
//    	addSequential(new DriveEncDist(10));
    }
}
