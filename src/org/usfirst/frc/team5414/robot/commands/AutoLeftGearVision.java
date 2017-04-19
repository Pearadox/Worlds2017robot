package org.usfirst.frc.team5414.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLeftGearVision extends CommandGroup {

    public AutoLeftGearVision() {
    	
    	addSequential(new ActivateTract());
    	
    	addSequential(new RaiseArm());
    	
    	addSequential(new DriveEncDist(9));
        
    	addSequential(new RotateRight(45));
    	
    	addSequential(new DriveEncDistUntilVision(1.5));
    	
    	addSequential(new VisionScoringGroup());
    	
    }
}
