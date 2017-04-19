

package org.usfirst.frc.team5414.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;

import java.io.IOException;
import java.util.*;
import org.opencv.core.Mat;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;

import org.usfirst.frc.team5414.robot.commands.ActivateMecanum;
import org.usfirst.frc.team5414.robot.commands.AutoBoilerSideGearShoot;
import org.usfirst.frc.team5414.robot.commands.AutoDriveForward;
import org.usfirst.frc.team5414.robot.commands.AutoLeftGearVision;
import org.usfirst.frc.team5414.robot.commands.AutoLoadingLaneGear;
import org.usfirst.frc.team5414.robot.commands.AutoMidGearShoot;
import org.usfirst.frc.team5414.robot.commands.AutoRightGearVision;
import org.usfirst.frc.team5414.robot.commands.AutoShootOnly;
import org.usfirst.frc.team5414.robot.commands.AutoShootRotateDrive;
import org.usfirst.frc.team5414.robot.commands.AutoShootStrafeGear;
import org.usfirst.frc.team5414.robot.commands.AutoShootStrafe;
import org.usfirst.frc.team5414.robot.commands.AutoMidGearVision;
import org.usfirst.frc.team5414.robot.commands.AutoMidNoVision;
import org.usfirst.frc.team5414.robot.commands.EncoderDrives;
import org.usfirst.frc.team5414.robot.commands.RaiseArm;
import org.usfirst.frc.team5414.robot.commands.RevDBGroup;
import org.usfirst.frc.team5414.robot.subsystems.Climber;
import org.usfirst.frc.team5414.robot.subsystems.Drivetrain;
import org.usfirst.frc.team5414.robot.subsystems.Electrical;
import org.usfirst.frc.team5414.robot.subsystems.GearArm;
import org.usfirst.frc.team5414.robot.subsystems.GearCollector;

import org.usfirst.frc.team5414.robot.subsystems.Navx;
import org.usfirst.frc.team5414.robot.subsystems.Servo1;
import org.usfirst.frc.team5414.robot.subsystems.Shooter;
import org.usfirst.frc.team5414.robot.subsystems.WheelEncoder;

public class Robot extends IterativeRobot {
	
	public static Drivetrain drivetrain;
	public static Compressor compressor;
	public static NetworkTable table;
	public static WheelEncoder encoder;
	public static OI oi;
	public static Navx gyro;
	public static Climber climber;
	public static Electrical electrical;
	public static GearArm geararm;
	public static GearCollector gearcollector;
	public static REVDigitBoard revdigitboard;
	public static Servo1 servo1;
	public static Preferences prefs;
	public static Shooter shooter;
	public static boolean isAutonomous;
	public static Minimap map;
	Command autonomousCommand;
//	SendableChooser<CommandGroup> AutoChooser;
	static boolean currentButtonState;
    String test="";
    UsbCamera cam1;
    
    /**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		try{
			currentButtonState= false;
			//Not sure what this is before - if this was just for testing, we should remove it.
			//Need to have the servos in the climbing subsystem
			servo1 = new Servo1();
//			cam1 = new UsbCamera("cam1", 1);
//			cam1.setExposureManual(50);
//			cam1.setBrightness(60);
//			cam1.setResolution(320	, 270);
//			CameraServer.getInstance().startAutomaticCapture(cam1);
//			CameraServer.getInstance().startAutomaticCapture(0);
		} catch(Exception e){}
		//Should probably remove the digiboard as we are unlikely to use it. 
		revdigitboard = new REVDigitBoard();
		table = NetworkTable.getTable("GRIP/myContoursReport");
		geararm = new GearArm();
		gearcollector = new GearCollector();
		compressor = new Compressor(0);
		shooter = new Shooter();
		compressor.start();
		drivetrain = new Drivetrain();
		encoder = new WheelEncoder();
		climber = new Climber();
		electrical = new Electrical();
		gyro = new Navx();
		gyro.initialize();
		map = new Minimap();
		try {
			map.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Robot.drivetrain.FullTraction();
//		shoot=new Wheel();
		oi = new OI();
		new RaiseArm().start();

		/** Should add a command that just drives forward for a set amount of second 
		  * so that we have a auto mode that gets us the 5 pt bonus for driving forward
		  */
//		AutoChooser.addDefault("Left Side", new AutoLeftGearVision());
		
		//These can't all be defaults. It should become AutoChooser.addObject();
		/** Should add a command that just drives forward for a set amount of second 
		  * so that we have a auto mode that gets us the 5 pt bonus for driving forward
		  */
//		AutoChooser.addDefault("Middle", new AutoRightGearVision());
//		AutoChooser.addDefault("Right Side", new AutoMidGearVision());
		
		//Should also add an autonomous mode that does nothing. Just in Case
//		SmartDashboard.putData("Autonomous Mode", AutoChooser);
		prefs = Preferences.getInstance();
		prefs.putString("Areas", "172,272,372,472");
		prefs.putString("RPMs", "4000,3500,3000,2500");
		prefs.putDouble("GyrokP", RobotMap.GyrokP);
		prefs.putDouble("GyrokI", RobotMap.GyrokI);
		prefs.putDouble("GyrokD", RobotMap.GyrokD);
		prefs.putDouble("Shooter RPM", RobotMap.ShooterRPM);
		prefs.putDouble("AUTO_MID_DISTANCE", 4.5);
		prefs.putDouble("AUTO_SPEED", 5.5);
		getAreas();
		getRPMs();
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	
	public static boolean isDisabled;
	
	@Override
	public void disabledInit() {
		isAutonomous = false;
		revdigitboard.clear();
		isDisabled = true;
		CommandGroup rev = new RevDBGroup();
		rev.setRunWhenDisabled(true);
		rev.start();
		
		climber.stop();
	}

	public static int currentAutoMode = 0;
	public static boolean btnA = false;
	public static boolean btnB = false;
	public static int rpmdisplayloops = 0;
	
	@Override
	public void disabledPeriodic() {
		
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Potentiometer", revdigitboard.getScaledPot());
		SmartDashboard.putNumber("Gyro Angle", gyro.getYaw());
	}
	
	@Override
	public void autonomousInit() {
		isAutonomous = true;
		gyro.resetOffset();
		gyro.reset();
		switch(currentAutoMode)
		{
		default: autonomousCommand = new AutoBoilerSideGearShoot(); break;
		case 0: autonomousCommand = new AutoBoilerSideGearShoot(); break;
		case 1: autonomousCommand = new AutoMidGearShoot(); break;
		case 2: autonomousCommand = new AutoLoadingLaneGear(); break;
		case 3: autonomousCommand = new AutoShootStrafe(4, .9); break;
		case 4: autonomousCommand = new AutoDriveForward(10); break;
		case 5: autonomousCommand = new AutoShootOnly(); break;
		case 6: autonomousCommand = null; break;
		}
//		autonomousCommand = new AutoLoadingLaneGear();
//		autonomousCommand = new AutoBoilerSideGearShoot();
//		autonomousCommand = new AutoMidGearShoot();
		try { autonomousCommand.start(); } catch(Exception e){}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Gyro Angle", gyro.getYaw());
	}

	@Override
	public void teleopInit() {
		isAutonomous = false;
//		geararm.setPosition(RobotMap.ArmPositionUp);
		isDisabled = false;
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		new ActivateMecanum().start();
//		table.shutdown();
		if (autonomousCommand != null)
			autonomousCommand.cancel();
//		servo1.Zero();
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	int loops = 0;
	
	@Override
	public void teleopPeriodic() {

		SmartDashboard.putNumber("Gear Arm", geararm.currentposition());
		System.out.println(shooter.talon.getError());
//		if(loops == 6) { revdigitboard.display(DriverStation.getInstance().getBatteryVoltage()*100); loops = 0; }
//		loops++;
		SmartDashboard.putNumber("Geararm Current", geararm.getCurrent());
		SmartDashboard.putNumber("JS Potentiometer", OI.stick.getRawAxis(3));
		double[] areas = table.getNumberArray("area", new double[0]);
		SmartDashboard.putString("Area", Arrays.toString(areas));
		Scheduler.getInstance().run();
		currentButtonState = oi.getJoystick1().getRawButton(5);
    	
    		try {
				SmartDashboard.putString("Area: ", Arrays.toString(table.getNumberArray("area", new double[0])));
				SmartDashboard.putBoolean("Errored", false);
				SmartDashboard.putString("CenterX: ", Arrays.toString(table.getNumberArray("centerX", new double[0]))); 
				SmartDashboard.putString("CenterY", Arrays.toString(table.getNumberArray("centerY",new double[0])));
			} catch (Exception e) {
				SmartDashboard.putBoolean("Errored", true);
			}
    		
    		SmartDashboard.putNumber("Gyro Angle", gyro.getYaw());
    		SmartDashboard.putNumber("Encoder Ticks", drivetrain.getEncoderBL());
    		
//    	if(geararm.getCurrent() > RobotMap.gearArmShutoffCurrent)
//    	{
//    		geararm.stop();
//    	}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	public static boolean containsArea(){
		double[] thing  = table.getNumberArray(""
				+ "", new double[0]);
//		SmartDashboard.putString("areaThing", Arrays.toString(thing));
//		SmartDashboard.putNumber("Thing length", thing.length);
		return thing.length==0;
	}
	public static double centerX(){
		double x = 0;
		double[] center = table.getNumberArray("centerX", new double[0]);
		if(center.length==1){
			x = center[0];
		}
		return x;
	}
	
	public void getAreas()
	{
		prefs = Preferences.getInstance();
		String raw = prefs.getString("Areas", "");
		ArrayList<Double> toReturn = new ArrayList<Double>();
		StringTokenizer st = new StringTokenizer(raw, ",");
		while(st.hasMoreTokens())
		{
			toReturn.add(Double.parseDouble(st.nextToken().trim()));
		}
		
		RobotMap.area = toReturn;
	}

	public void getRPMs()
	{
		prefs = Preferences.getInstance();
		String raw = prefs.getString("RPMs", "");
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer(raw, ",");
		while(st.hasMoreTokens())
		{
			toReturn.add(Integer.parseInt(st.nextToken().trim()));
		}
		RobotMap.rpm = toReturn;
	}
}