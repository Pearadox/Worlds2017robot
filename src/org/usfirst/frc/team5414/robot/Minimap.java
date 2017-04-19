package org.usfirst.frc.team5414.robot;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.usfirst.frc.team5414.robot.Robot;
import org.usfirst.frc.team5414.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Minimap {
	
	String IP = "10.54.14.5";
	int port = 5800;
	byte[] sendData = new byte[0];
    InetAddress IPAddress;
    double changeInAngle = 0;
    double lastAngle = 0;
    double lastY;
    String alliance = "b";
   
    public void start() throws IOException
    { 
    	Robot.gyro.zeroYaw();
    	Robot.gyro.resetOffset();
    	try {
			IPAddress = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	Robot.gyro.reset();
    	Thread t = new Thread(() -> 
    	{
    		while(true)
    		{
    			if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) alliance = "r";
    			else alliance = "b";
    			try {
					Thread.sleep(50);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			double currentYaw = Robot.gyro.getTrueYaw();
    			changeInAngle = currentYaw-lastAngle;
    			lastAngle = currentYaw;
	    		double averageEncoder = (Robot.drivetrain.getEncoderBL() + Robot.drivetrain.getEncoderBR()) / 2.;
	    		double inchY = (averageEncoder - getLastY()) * RobotMap.LengthPerTick * 12;
	    		int displacementY = (int)Math.round(inchY * 287 / 284.8);     // ratio: 287 px : 284.8 in
	        	try {
	        		moveY(displacementY);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    setLastY(averageEncoder);}
    	});
    	t.start(); 
    }
    
    public double getLastY() { return lastY; }
    public void setLastY(double d) { lastY = d; }
    
    public void zero(int key) throws IOException
    {
    	DatagramSocket clientSocket = new DatagramSocket();
//    	Robot.gyro.zeroDisplacement();
    	clientSocket = new DatagramSocket();
        sendData = ("ZERO " + key).getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5800);
        clientSocket.send(sendPacket);
        clientSocket.close();
    }
    
    public void moveY(int d) throws IOException
    {
    	DatagramSocket clientSocket = new DatagramSocket();
        clientSocket = new DatagramSocket();
        String toSend = "0 "+d+" "+changeInAngle+" " + alliance;
        System.out.println(toSend);
        sendData = (toSend).getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5800);
        clientSocket.send(sendPacket);
        clientSocket.close();
    }

    public void moveX(int d) throws IOException
    {
    	DatagramSocket clientSocket = new DatagramSocket();
        clientSocket = new DatagramSocket();
        sendData = (d+" 0 "+changeInAngle+" "+ changeInAngle + " " + alliance).getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5800);
        clientSocket.send(sendPacket);
        clientSocket.close();
    }
    
    public void move(int x, int y) throws IOException
    {
    	DatagramSocket clientSocket = new DatagramSocket();
        clientSocket = new DatagramSocket();
        String command = (x+" " + y + " "+changeInAngle+" " + alliance); 
//        System.out.println(command);
        sendData = command.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5800);
        clientSocket.send(sendPacket);
//        System.out.println("sent");
        clientSocket.close();
    }
 
}

