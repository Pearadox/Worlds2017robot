package org.usfirst.frc.team5414.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.*;

import org.usfirst.frc.team5414.robot.OI;
import org.usfirst.frc.team5414.robot.Robot;
import org.usfirst.frc.team5414.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
/**
 *
 */
public class Shooter extends Subsystem {

	public static CANTalon talon;
	
	
	public Shooter()
	{
		/*
		 BRAZOS CODE
		talon = new CANTalon(RobotMap.shooterTalon);
    	talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	talon.reverseSensor(false);
    	talon.configEncoderCodesPerRev(20);
    	talon.configNominalOutputVoltage(+0.0f, -0.0f);
    	talon.configPeakOutputVoltage(+12.0f, 0.0f);
    	talon.setProfile(0);
    	talon.setF(RobotMap.ShooterF);
    	talon.setP(RobotMap.ShooterkP);
    	talon.setI(RobotMap.ShooterkI);
    	talon.setD(RobotMap.ShooterkD);
    	talon.changeControlMode(TalonControlMode.Speed);
    	*/
		
		//bruteforce
		talon = new CANTalon(RobotMap.shooterTalon);
		talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon.reverseSensor(true);
		talon.configNominalOutputVoltage(+0.0f, -0.0f);
		talon.configEncoderCodesPerRev(20);
    	talon.configPeakOutputVoltage(+12.0f, 0.0f);
    	talon.setProfile(0);
    	talon.setF(1.58);
    	talon.setP(16.2);
    	talon.setI(0);
    	talon.setD(520);
	}
	
    public void initDefaultCommand() {
    	System.out.println("Init Default");
    }
    
    public void shoot()
    {
    	this.shoot(RobotMap.ShooterRPM);
    }
    
    public void shoot(int RPM)
    {
//    	DriverStation.reportWarning("TRYING TO SHOOT", true);
//    	double[] areas = Robot.table.getNumberArray("area", new double[0]);
//    	double area = 200;
//    	talon.changeControlMode(TalonControlMode.Speed);
//    	try{
//    		double a1 = areas[0];
//    		double a2 = areas[1];
//    		area = Math.max(a1, a2);
//    	} catch(Exception e) {}
//    	double speed = getRPM(area);
//    	double speed = Robot.prefs.getDouble("Shooter RPM", RPM);
    	talon.changeControlMode(TalonControlMode.Speed);
    	talon.set(RPM);
//    	SmartDashboard.putNumber("Shooter RPM", getRPM(area));
    	
    }
    
    public double getRPM(double CurrentArea)
    {
    	double RPM = 2500;
    	int index = 0;
    	ArrayList<Double> areas = RobotMap.area;
    	while(CurrentArea > areas.get(index) && index< areas.size()-1)
    	{
    		index++;
    	}
    	if(index == 0)
    	{
    		RPM = RobotMap.rpm.get(0);
    	}
    	else if(index == areas.size()-1)
    	{
    		RPM = RobotMap.rpm.get(areas.size()-1);
    	}
    	else
    	{
    		double low = areas.get(index - 1);
    		double high = areas.get(index);
    		double percent = (high-low)/high;
    		RPM = (percent * (CurrentArea - RobotMap.rpm.get(index-1)) + RobotMap.rpm.get(index-1));
//    		RPM = RobotMap.rpm.get(index);
    	}
    	return RPM;
    }
    
    public void stop()
    {
    	talon.changeControlMode(TalonControlMode.PercentVbus);
    	talon.set(0);
    }
}

