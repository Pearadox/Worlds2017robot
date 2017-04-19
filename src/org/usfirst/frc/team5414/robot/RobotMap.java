package org.usfirst.frc.team5414.robot;

import java.util.*;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = s1;
	// public static int rightMotor = 2;

	//Buttons for Joystick
	public static int BtnButterfly= 1;			//activates all traction wheels
	public static int BtnClimber = 2;//climber
	public static int ServoClose = 3;//climber
	public static int BtnOpen = 4;//climber
	public static int BtnShooter = 5; //shooter
	public static int BtnStop = 6;//climber	
	public static int BtnLowerManual = 7;//geararm
	public static final int BtnRaiseManual = 8;	//geararm
	public static int BtnLower = 9; //geararm
	public static int BtnRaise = 10; //geararm
	
	public static int BtnCollectGear = 11; 		
	public static int BtnCollectGearSpit = 12; 	
	//***clean****
	
	public static final double gearArmShutoffCurrent = 27;
	
	public static final int numberOfModes = 6;
	
	public static double wheelDiameter = 4. / 12;
	public static final double compBotEnc = 128;
	public static final int practiceBotEnc = 1440;
	public static final double SlowArmSpeed = 0.5;
	public static final double AreaStopSize = 2440;
	public static final double AutoStraightDist = 8;
	public static int LightSolenoid = 0;
	public static double EncoderTicks = 128;
	public static double Circumference = wheelDiameter * Math.PI;
	public static double LengthPerTick = Circumference / EncoderTicks; // this is only roughly true for traction wheels
	
	//arm & collector speeds
	public static double ArmPositionDown = .38;
	public static double ArmPositionUp = .0314159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706798214808651328230664709;
	public static double armTargetRotations = .3;
	public static double armRaiseSpeed= 0.3;
	public static double armLowerSpeed = -0.2;
	public static double intakeSpeed = -1;
	public static double outtakeSpeed = .5;
	//shooter PID loop
	public static int shooterTalon = 1;
	public static double ShooterkP = 7.7;
	public static double ShooterkI = 0;
	public static double ShooterkD = 850;
	public static double ShooterF = 1.58;
	public static int ShooterRPM = 3440;
	
	//Closed-position loop for arm
	public static int ArmError = 80;
	public static int ArmProfile = 0;
	public static double ArmkF = 0;
	public static double ArmkP = 5;
	public static double ArmkI = 0.0002;
	public static double ArmkD = 130;
	
	//GyroPort
	public static int GyroPort = 0;
	
	
	//Driving ports
	public static int PWMRightFrontMotor = 2;
	public static int PWMRightBackMotor = 3;
	public static int PWMLeftFrontMotor = 0;
	public static int PWMLeftBackMotor = 1;
	
	//Shooter port
	public static int PDPShooter = 2;
	
	//pwm for collector wheels & arms
	public static int PWMGearWheels = 4;
	
	//climber stuffs
	public static int PWMlifter = 6;
	
	public static int PDPclimber = 3;
	public static int climberCurrentSpike = 50;
	public static double lifterholdspeed = -0.35;
	public static double lifterLiftSpeed = -1.0;
	public static double lifterLimitSwitchSpeed = .75;
	
	//Autonomous settings
	public static double  goToPegSpeed= .6;
	
	//Servo Ports
	public static int ServoPort = 8;  		//Set to correct Port for servos for climber
	public static int ServoPort2 = 9; 		//Set to correct Port for servos for climber
	public static int Servo1AngleOpen = 150;	//set range of motion of the servos
	public static int Servo2AngleOpen = 55;
	public static int Servo1AngleClose = 45; 
	public static int Servo2AngleClose = 158;
	
	//Solenoid ports
	
	public static int SolenoidFLa = 3;
	public static int SolenoidFLb = 4;
	public static int SolenoidFRa = 2;
	public static int SolenoidFRb = 5;
	public static int SolenoidBRa = 0;
	public static int SolenoidBRb = 7;
	public static int SolenoidBLa = 1;
	public static int SolenoidBLb = 6;
	
	
	//Limit switch port
	public static int LimitInput = 10;  //Set limit to the digitalInput
	
	//DIO ports
	public static int DIOencoderFRa = 8;
	public static int DIOencoderFRb = 9;
	public static int DIOencoderFLa = 4;
	public static int DIOencoderFLb = 5;
	public static int DIOencoderBRa = 6;
	public static int DIOencoderBRb = 7;
	public static int DIOencoderBLa = 2;
	public static int DIOencoderBLb = 3;
	public static int TalonGearArm = 0; //????
	public static int DIOClimberLimit = 1;
	
	public static double GyrokP = 0.00005;
	public static double GyrokI = 0.00003;
	public static double GyrokD = 0.02;
	
	//Shooter Lookup Table
	public static ArrayList<Double> area = new ArrayList<Double>();
	public static ArrayList<Integer> rpm = new ArrayList<Integer>();
}
