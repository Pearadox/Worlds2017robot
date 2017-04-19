package org.usfirst.frc.team5414.robot.subsystems;



import org.usfirst.frc.team5414.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Navx extends Subsystem implements PIDSource{

//	AHRS ahrs = new AHRS(SPI.Port.kMXP); //navx
	AnalogGyro gy = new AnalogGyro(RobotMap.GyroPort);
	
	public static double yawOffset;
	
    public void initDefaultCommand() {
    }
    
    public void initialize()
    {
    	gy.initGyro();
    	gy.setSensitivity(.007);
    	zeroYaw();
    	yawOffset = 0;
    }
    
    public void resetOffset()
    {
    	yawOffset = 0;
    }
    
    public double getTrueYaw()
    {
    	return getYaw() + yawOffset;
    }
    
    public double getYaw()
    {
    	return gy.getAngle();			//getting the angle of the gyro
    }
    
    public void zeroYaw()
    {
    	reset();	
    }
    
    public void reset()
    {
    	yawOffset += getYaw() - yawOffset;
    	gy.reset();					//resetting the gyro
    }

    public void setPIDSourceType(PIDSourceType pidSource) {
		
	}

	public PIDSourceType getPIDSourceType() {
		
		return PIDSourceType.kRate;		//returns the source type of the PID loop
	}
	
	int times = 0;
	
	public double pidGet() {
		return getYaw();				//returns the yaw of the AnalogGyro
	}   
}