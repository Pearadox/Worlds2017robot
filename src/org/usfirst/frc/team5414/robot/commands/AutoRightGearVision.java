package org.usfirst.frc.team5414.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRightGearVision extends CommandGroup {

    public AutoRightGearVision() {
    	
    	addSequential(new ActivateTract());
    	
    	addSequential(new RaiseArm());
    
    	addSequential(new DriveEncDist(9));
    	
    	addSequential(new RotateLeft(45));
    	
    	addSequential(new DriveEncDistUntilVision(1.5));
    	
    	addSequential(new VisionScoringGroup());
    	
    }
}
