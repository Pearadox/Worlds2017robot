package org.usfirst.frc.team5414.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLoadingLaneGear extends CommandGroup {

    public AutoLoadingLaneGear() {

    	if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) 
		{
    		addSequential(new DriveEncDist(-5.6505414));
    		addSequential(new RotateRight(50));
		}
		else 
		{   
			addSequential(new DriveEncDist(-5.2118));
    		addSequential(new RotateLeft(50));
		}
    	addSequential(new DriveEncDist(-2.3, .6, 2));
    	addSequential(new Delay(1));
    	addSequential(new DriveEncDist(2));
    	if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) 
    	{
    		addSequential(new RotateRight(50));
		}
		else 
		{   
    		addSequential(new RotateLeft(50));
		}
    	addSequential(new DriveEncDist(-8));
    }
}
