package org.usfirst.frc.team5414.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionScoringGroup extends CommandGroup {

    public VisionScoringGroup() {

    	addSequential(new GoToPeg());
    	
    	addSequential(new DriveEncDist(.5, .6));
    	
    	addSequential(new ScoringGearCommandGroupAuto());
    	
    }
}
