package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetAngleUnitCircleBased extends CommandGroup {

    public SetAngleUnitCircleBased(double desiredAngle) {

    	double currentAngle = Robot.gyro.getTrueYaw();
    	
    	desiredAngle %= 360;
    	desiredAngle += 360;
    	desiredAngle %= 360;
    	
    	currentAngle %= 360;
    	currentAngle += 360;
    	currentAngle %= 360;
    	
    	if(currentAngle < desiredAngle)
    	{
    		if(Math.abs(currentAngle - desiredAngle) > 180)
    		{
    			addSequential(new RotateRight(Math.abs(currentAngle - desiredAngle) - 180));
    		}
    		else
    		{
    			addSequential(new RotateLeft(Math.abs(currentAngle - desiredAngle)));
    		}
    	}
    	else if(currentAngle > desiredAngle)
    	{
    		if(Math.abs(currentAngle - desiredAngle) > 180)
    		{
    			addSequential(new RotateLeft(Math.abs(currentAngle - desiredAngle) - 180));
    		}
    		else
    		{
    			addSequential(new RotateRight(Math.abs(currentAngle - desiredAngle)));
    		}
    	}
    	
    }
}
