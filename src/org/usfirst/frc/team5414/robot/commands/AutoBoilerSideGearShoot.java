package org.usfirst.frc.team5414.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBoilerSideGearShoot extends CommandGroup {

    public AutoBoilerSideGearShoot() {
    	
    	if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) 
		{
    		addSequential(new DriveEncDist(-5.6505414));
    		addSequential(new RotateRight(50));
		}
		else		{   
			addSequential(new DriveEncDist(-5.2118));
    		addSequential(new RotateLeft(50));
		}
    	
    	addSequential(new DriveEncDist(-2.3, .6, 2));
//    	addSequential(new Brake(-.74, 2));
    	addSequential(new Delay(1));
		if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) 
		{
			addSequential(new DriveEncDist(3.9));
    		addSequential(new RotateRight(137, .785));
		}
		else 
		{   
			addSequential(new DriveEncDist(3.9));
    		addSequential(new RotateLeft(154.14, .785));
    		
		}
		addSequential(new Delay(.3));
    	addSequential(new AutoShootOnly());
    	
    }
}
