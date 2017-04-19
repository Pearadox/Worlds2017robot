package org.usfirst.frc.team5414.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RevDBGroup extends CommandGroup {

    public RevDBGroup() {
        
    	addSequential(new RevDBDisabledAuto());
    }
}
