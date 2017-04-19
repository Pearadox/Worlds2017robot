package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMidNoVision extends CommandGroup {

    public AutoMidNoVision() {
    	
    	addSequential(new DriveEncDist(7, .5));
    	
    	Timer.delay(.25);
    	
    	addSequential(new DriveEncDist(2));
		addParallel(new DriveEncDist(2));
		addSequential(new ScoringGearCommandGroup());
		
		if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue)
		{
			addSequential(new RotateLeft(105));
		}
		else
		{
			addSequential(new RotateRight(105));
		}
		
		addSequential(new DriveEncDist(2));
		
		addSequential(new AutoShootOnly());
    }
}
