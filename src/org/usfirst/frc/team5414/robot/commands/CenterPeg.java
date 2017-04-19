package org.usfirst.frc.team5414.robot.commands;

import org.usfirst.frc.team5414.robot.Robot;
import org.usfirst.frc.team5414.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CenterPeg extends Command {

	double[] CenterArray;
	double[] AreaArray;
	final double cameraViewCenter = 180;
	boolean centered;
	
    public CenterPeg() {
        requires(Robot.drivetrain);
        setTimeout(3);
    }

    // Called just before this Command runs the first time
    protected void initialize(){
    	
    }
    
    int threshold = 10;

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	CenterArray = Robot.table.getNumberArray("centerX", new double[0]);
    	AreaArray = Robot.table.getNumberArray("area", new double[0]);
    	
    	int largestindex = 0;
    	int secondlargestindex = 0;
    	double largestarea = 0;
    	double secondlargestarea = 0;
    	for(int i = 0; i < AreaArray.length; i++)
    	{
    		if(largestarea < AreaArray[i])
    		{
    			largestindex = i;
    			largestarea = AreaArray[i];
    		}
    	}
    	for(int i = 0; i < AreaArray.length; i++)
    	{
    		if(secondlargestarea < AreaArray[i] && i != largestindex)
    		{
    			secondlargestindex = i;
    			secondlargestarea = AreaArray[i];
    		}
    	}
    	
    	double CenterPanels = AreaArray[largestindex] + AreaArray[secondlargestindex];
    	CenterPanels /= 2.;
    	
    	try {
			if(CenterPanels >= cameraViewCenter + 10) { //Centering Right
				DriverStation.reportWarning("Centering Right", true);
				Robot.drivetrain.drive(-.6, 0.0);
			}
			else if(CenterPanels <= cameraViewCenter - 10) { //Centering left
				DriverStation.reportWarning("Centering Left", true);
				Robot.drivetrain.drive(0.0, -.6); 
			}
			else
			{
				centered = true;
				Robot.drivetrain.stop();
			}
		}catch (Exception e) {
					DriverStation.reportWarning("Error at PivotToCenter (Rotate To Center)", true);
		} //end try/catch
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return centered;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.stop();
    }
}
