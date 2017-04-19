package org.usfirst.frc.team5414.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ServoCloseCommandGroup extends CommandGroup {

    public ServoCloseCommandGroup() {
    	
    	addSequential(new ServoClose());
    	
    	addParallel(new ClimberLimitSwitch());
    }
}
