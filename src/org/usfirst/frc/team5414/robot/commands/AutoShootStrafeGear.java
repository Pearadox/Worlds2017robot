package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShootStrafeGear extends CommandGroup {

    public AutoShootStrafeGear() {
        
    	addSequential(new AutoShootStrafe(4, .9)); //need to tune 2. strafe time

//		if(DriverStation.Alliance.Blue == DriverStation.getInstance().getAlliance()) //to 315
//		{
//			addSequential(new SetAngleUnitCircleBased(315));
//		}
//		else //to 45
//		{
//			addSequential(new SetAngleUnitCircleBased(45));
//		}
		
//		addSequential(new VisionScoringGroup());
		
    }
}
