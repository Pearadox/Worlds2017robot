package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGearRobotnauts extends CommandGroup {

    public AutoGearRobotnauts() {
        
    	addSequential(new DriveEncDist(7.5));
    	
    	if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) 
		{
    		addSequential(new RotateRight(45));
    		addSequential(new DriveEncDist(2));
    		addParallel(new DriveEncDist(2));
    		addSequential(new ScoringGearCommandGroup());
    		addSequential(new RotateLeft(45));
    		addSequential(new StrafeLeft(1, 2.5));
		}
		else 
		{   
    		addSequential(new RotateLeft(45));
    		addSequential(new DriveEncDist(2));
    		addParallel(new DriveEncDist(2));
    		addSequential(new ScoringGearCommandGroup());
    		addSequential(new RotateRight(45));
    		addSequential(new StrafeRight(1, 2.5));
		}
    	
    	addSequential(new AutoShootOnly());
    	
    }
}
