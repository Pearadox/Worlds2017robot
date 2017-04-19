// Pearadox 5414's REVDigitboard Library
package org.usfirst.frc.team5414.robot; // library for revdigitboard

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class REVDigitBoard {
	/*
	 * DOCUMENTATION::
	 * 
	 * REVDigitBoard() : constructor
	 * void display(String str) : displays the first four characters of the string (only alpha (converted to uppercase), numbers, and spaces)
	 * void display(double number) : displays the thousands to the ones places.
	 * void clear() : clears the display
	 * boolean getButtonA() : button A on the board
	 * boolean getButtonB() : button B on the board
	 * double getRawPot() : potentiometer value
	 * double getScaledPot() : potentiometer value between [0, 1]
	 */
	
	I2C i2c;
	DigitalInput buttonA, buttonB;
	AnalogInput pot;
	byte[][] charreg;
	
	public REVDigitBoard() {
		
	charreg = new byte[36][2]; //charreg is short for character registry
    	charreg[0][0] = (byte)0b00000110; charreg[0][1] = (byte)0b00000000; //1
    	charreg[1][0] = (byte)0b11011011; charreg[1][1] = (byte)0b00000000; //2
    	charreg[2][0] = (byte)0b11001111; charreg[2][1] = (byte)0b00000000; //3
    	charreg[3][0] = (byte)0b11100110; charreg[3][1] = (byte)0b00000000; //4
    	charreg[4][0] = (byte)0b11101101; charreg[4][1] = (byte)0b00000000; //5
    	charreg[5][0] = (byte)0b11111101; charreg[5][1] = (byte)0b00000000; //6
    	charreg[6][0] = (byte)0b00000111; charreg[6][1] = (byte)0b00000000; //7
    	charreg[7][0] = (byte)0b11111111; charreg[7][1] = (byte)0b00000000; //8
    	charreg[8][0] = (byte)0b11101111; charreg[8][1] = (byte)0b00000000; //9
    	charreg[9][0] = (byte)0b00111111; charreg[9][1] = (byte)0b00000000; //0
    	charreg[10][0] = (byte)0b11110111; charreg[10][1] = (byte)0b00000000; //A
    	charreg[11][0] = (byte)0b10001111; charreg[11][1] = (byte)0b00010010; //B
    	charreg[12][0] = (byte)0b00111001; charreg[12][1] = (byte)0b00000000; //C
    	charreg[13][0] = (byte)0b00001111; charreg[13][1] = (byte)0b00010010; //D
    	charreg[14][0] = (byte)0b11111001; charreg[14][1] = (byte)0b00000000; //E
    	charreg[15][0] = (byte)0b11110001; charreg[15][1] = (byte)0b00000000; //F
    	charreg[16][0] = (byte)0b10111101; charreg[16][1] = (byte)0b00000000; //G
    	charreg[17][0] = (byte)0b11110110; charreg[17][1] = (byte)0b00000000; //H
    	charreg[18][0] = (byte)0b00001001; charreg[18][1] = (byte)0b00010010; //I
    	charreg[19][0] = (byte)0b00011110; charreg[19][1] = (byte)0b00000000; //J
    	charreg[20][0] = (byte)0b01110000; charreg[20][1] = (byte)0b00001100; //K
    	charreg[21][0] = (byte)0b00111000; charreg[21][1] = (byte)0b00000000; //L
    	charreg[22][0] = (byte)0b00110110; charreg[22][1] = (byte)0b00000101; //M
    	charreg[23][0] = (byte)0b00110110; charreg[23][1] = (byte)0b00001001; //N
    	charreg[24][0] = (byte)0b00111111; charreg[24][1] = (byte)0b00000000; //O
    	charreg[25][0] = (byte)0b11110011; charreg[25][1] = (byte)0b00000000; //P
    	charreg[26][0] = (byte)0b00111111; charreg[26][1] = (byte)0b00001000; //Q
    	charreg[27][0] = (byte)0b11110011; charreg[27][1] = (byte)0b00001000; //R
    	charreg[28][0] = (byte)0b10001101; charreg[28][1] = (byte)0b00000001; //S
    	charreg[29][0] = (byte)0b00000001; charreg[29][1] = (byte)0b00010010; //T
    	charreg[30][0] = (byte)0b00111110; charreg[30][1] = (byte)0b00000000; //U
    	charreg[31][0] = (byte)0b00110000; charreg[31][1] = (byte)0b00100100; //V
    	charreg[32][0] = (byte)0b00110110; charreg[32][1] = (byte)0b00101000; //W
    	charreg[33][0] = (byte)0b00000000; charreg[33][1] = (byte)0b00101101; //X
    	charreg[34][0] = (byte)0b00000000; charreg[34][1] = (byte)0b00010101; //Y
    	charreg[35][0] = (byte)0b00001001; charreg[35][1] = (byte)0b00100100; //Z
		
    	pot = new AnalogInput(7);
    	
		i2c = new I2C(Port.kMXP, 0x70);
		buttonA = new DigitalInput(19);
		buttonB = new DigitalInput(20);
		
		byte[] osc = new byte[1];
	 	byte[] blink = new byte[1];
	 	byte[] bright = new byte[1];
	 	osc[0] = (byte)0x21;
	 	blink[0] = (byte)0x81;
	 	bright[0] = (byte)0xEF;

		i2c.writeBulk(osc);
		Timer.delay(.01);
		i2c.writeBulk(bright);
		Timer.delay(.01);
		i2c.writeBulk(blink);
		Timer.delay(.01);
	}
	
	public void display(String str) { // only displays first 4 chars
		int[] charz = new int[4];
		// uppercase and map it
		str = str.toUpperCase();
		try {
		if(str.charAt(0) == '0') charz[0] = 0;
		else if(str.charAt(0) == '1') charz[0] = 1;
		else if(str.charAt(0) == '2') charz[0] = 2;
		else if(str.charAt(0) == '3') charz[0] = 3;
		else if(str.charAt(0) == '4') charz[0] = 4;
		else if(str.charAt(0) == '5') charz[0] = 5;
		else if(str.charAt(0) == '6') charz[0] = 6;
		else if(str.charAt(0) == '7') charz[0] = 7;
		else if(str.charAt(0) == '8') charz[0] = 8;
		else if(str.charAt(0) == '9') charz[0] = 9;
		else charz[0] = str.charAt(0)-54;
		
		if(str.charAt(1) == '0') charz[1] = 0;
		else if(str.charAt(1) == '1') charz[1] = 1;
		else if(str.charAt(1) == '2') charz[1] = 2;
		else if(str.charAt(1) == '3') charz[1] = 3;
		else if(str.charAt(1) == '4') charz[1] = 4;
		else if(str.charAt(1) == '5') charz[1] = 5;
		else if(str.charAt(1) == '6') charz[1] = 6;
		else if(str.charAt(1) == '7') charz[1] = 7;
		else if(str.charAt(1) == '8') charz[1] = 8;
		else if(str.charAt(1) == '9') charz[1] = 9;
		else charz[1] = str.charAt(1)-54;
		
		if(str.charAt(2) == 0) charz[2] = 0;
		else if(str.charAt(2) == '1') charz[2] = 1;
		else if(str.charAt(2) == '2') charz[2] = 2;
		else if(str.charAt(2) == '3') charz[2] = 3;
		else if(str.charAt(2) == '4') charz[2] = 4;
		else if(str.charAt(2) == '5') charz[2] = 5;
		else if(str.charAt(2) == '6') charz[2] = 6;
		else if(str.charAt(2) == '7') charz[2] = 7;
		else if(str.charAt(2) == '8') charz[2] = 8;
		else if(str.charAt(2) == '9') charz[2] = 9;
		else charz[2] = str.charAt(2)-54;
		
		if(str.charAt(3) == '0') charz[3] = 0;
		else if(str.charAt(3) == '1') charz[3] = 1;
		else if(str.charAt(3) == '2') charz[3] = 2;
		else if(str.charAt(3) == '3') charz[3] = 3;
		else if(str.charAt(3) == '4') charz[3] = 4;
		else if(str.charAt(3) == '5') charz[3] = 5;
		else if(str.charAt(3) == '6') charz[3] = 6;
		else if(str.charAt(3) == '7') charz[3] = 7;
		else if(str.charAt(3) == '8') charz[3] = 8;
		else if(str.charAt(3) == '9') charz[3] = 9;
		else charz[3] = str.charAt(3)-54;
		
		} catch(Exception e) {}
		this._display(charz);
	}
	
	public void display(double number) {
		int[] charz = {36,36,36,36};
		// idk how to decimal point
		
		int thousand = (int)(number/1000);
		int hundred = (int)(number/100 % 10);
		int ten = (int)((number/10) % 10);
		int one = (int)((number%10));

		charz[0] = thousand;
		charz[1] = hundred;
		charz[2] = ten;
		charz[3] = one;
		this._display(charz);
	}
	
	 public void clear() {
		 int[] charz = {36,36,36,36};
		 this._display(charz);
	 }
	 
	 public boolean getButtonA() {
		 return !buttonA.get();
	 }
	 
	 public boolean getButtonB() {
		 return !buttonB.get();
	 }

	 public double getRawPot()
	 {
		 return pot.getAverageVoltage();
	 }
	 
	 public double getScaledPot()
	 { 
		 
		 //You will have to tune and change these numbers. These are our values
		 double lowestPotentiometerReading = 0.004882; 
		 double highestPotentiometerReading = 4.92;
		 
		 DecimalFormat df = new DecimalFormat("#.###");
		 df.setRoundingMode(RoundingMode.HALF_UP);
		 
		 double scaled = (pot.getAverageVoltage() - lowestPotentiometerReading) / highestPotentiometerReading;
		 
		 return Double.parseDouble(df.format(scaled));
	 }

	public void _display(int[] charz) {
		byte[] byte1 = new byte[10];
		byte1[0] = (byte)(0b0000111100001111);

		//I know I probably should've used switch statements or a hashmap, but I'm too lazy. Add else in front of the if statements if you want.
		if(charz[0] == 1) { byte1[9] = charreg[0][1]; byte1[8] = charreg[0][0]; }
		if(charz[0] == 2) { byte1[9] = charreg[1][1]; byte1[8] = charreg[1][0]; }
		if(charz[0] == 3) { byte1[9] = charreg[2][1]; byte1[8] = charreg[2][0]; }
		if(charz[0] == 4) { byte1[9] = charreg[3][1]; byte1[8] = charreg[3][0]; }
		if(charz[0] == 5) { byte1[9] = charreg[4][1]; byte1[8] = charreg[4][0]; }
		if(charz[0] == 6) { byte1[9] = charreg[5][1]; byte1[8] = charreg[5][0]; }
		if(charz[0] == 7) { byte1[9] = charreg[6][1]; byte1[8] = charreg[6][0]; }
		if(charz[0] == 8) { byte1[9] = charreg[7][1]; byte1[8] = charreg[7][0]; }
		if(charz[0] == 9) { byte1[9] = charreg[8][1]; byte1[8] = charreg[8][0]; }
		if(charz[0] == 0) { byte1[9] = charreg[9][1]; byte1[8] = charreg[9][0]; }
		if(charz[0] == 11) { byte1[9] = charreg[10][1]; byte1[8] = charreg[10][0]; }
		if(charz[0] == 12) { byte1[9] = charreg[11][1]; byte1[8] = charreg[11][0]; }
		if(charz[0] == 13) { byte1[9] = charreg[12][1]; byte1[8] = charreg[12][0]; }
		if(charz[0] == 14) { byte1[9] = charreg[13][1]; byte1[8] = charreg[13][0]; }
		if(charz[0] == 15) { byte1[9] = charreg[14][1]; byte1[8] = charreg[14][0]; }
		if(charz[0] == 16) { byte1[9] = charreg[15][1]; byte1[8] = charreg[15][0]; }
		if(charz[0] == 17) { byte1[9] = charreg[16][1]; byte1[8] = charreg[16][0]; }
		if(charz[0] == 18) { byte1[9] = charreg[17][1]; byte1[8] = charreg[17][0]; }
		if(charz[0] == 19) { byte1[9] = charreg[18][1]; byte1[8] = charreg[18][0]; }
		if(charz[0] == 20) { byte1[9] = charreg[19][1]; byte1[8] = charreg[19][0]; }
		if(charz[0] == 21) { byte1[9] = charreg[20][1]; byte1[8] = charreg[20][0]; }
		if(charz[0] == 22) { byte1[9] = charreg[21][1]; byte1[8] = charreg[21][0]; }
		if(charz[0] == 23) { byte1[9] = charreg[22][1]; byte1[8] = charreg[22][0]; }
		if(charz[0] == 24) { byte1[9] = charreg[23][1]; byte1[8] = charreg[23][0]; }
		if(charz[0] == 25) { byte1[9] = charreg[24][1]; byte1[8] = charreg[24][0]; }
		if(charz[0] == 26) { byte1[9] = charreg[25][1]; byte1[8] = charreg[25][0]; }
		if(charz[0] == 27) { byte1[9] = charreg[26][1]; byte1[8] = charreg[26][0]; }
		if(charz[0] == 28) { byte1[9] = charreg[27][1]; byte1[8] = charreg[27][0]; }
		if(charz[0] == 29) { byte1[9] = charreg[28][1]; byte1[8] = charreg[28][0]; }
		if(charz[0] == 30) { byte1[9] = charreg[29][1]; byte1[8] = charreg[29][0]; }
		if(charz[0] == 31) { byte1[9] = charreg[30][1]; byte1[8] = charreg[30][0]; }
		if(charz[0] == 32) { byte1[9] = charreg[31][1]; byte1[8] = charreg[31][0]; }
		if(charz[0] == 33) { byte1[9] = charreg[32][1]; byte1[8] = charreg[32][0]; }
		if(charz[0] == 34) { byte1[9] = charreg[33][1]; byte1[8] = charreg[33][0]; }
		if(charz[0] == 35) { byte1[9] = charreg[34][1]; byte1[8] = charreg[34][0]; }
		if(charz[0] == 36) { byte1[9] = charreg[35][1]; byte1[8] = charreg[35][0]; }


		if(charz[1] == 1) { byte1[7] = charreg[0][1]; byte1[6] = charreg[0][0]; }
		if(charz[1] == 2) { byte1[7] = charreg[1][1]; byte1[6] = charreg[1][0]; }
		if(charz[1] == 3) { byte1[7] = charreg[2][1]; byte1[6] = charreg[2][0]; }
		if(charz[1] == 4) { byte1[7] = charreg[3][1]; byte1[6] = charreg[3][0]; }
		if(charz[1] == 5) { byte1[7] = charreg[4][1]; byte1[6] = charreg[4][0]; }
		if(charz[1] == 6) { byte1[7] = charreg[5][1]; byte1[6] = charreg[5][0]; }
		if(charz[1] == 7) { byte1[7] = charreg[6][1]; byte1[6] = charreg[6][0]; }
		if(charz[1] == 8) { byte1[7] = charreg[7][1]; byte1[6] = charreg[7][0]; }
		if(charz[1] == 9) { byte1[7] = charreg[8][1]; byte1[6] = charreg[8][0]; }
		if(charz[1] == 0) { byte1[7] = charreg[9][1]; byte1[6] = charreg[9][0]; }
		if(charz[1] == 11) { byte1[7] = charreg[10][1]; byte1[6] = charreg[10][0]; }
		if(charz[1] == 12) { byte1[7] = charreg[11][1]; byte1[6] = charreg[11][0]; }
		if(charz[1] == 13) { byte1[7] = charreg[12][1]; byte1[6] = charreg[12][0]; }
		if(charz[1] == 14) { byte1[7] = charreg[13][1]; byte1[6] = charreg[13][0]; }
		if(charz[1] == 15) { byte1[7] = charreg[14][1]; byte1[6] = charreg[14][0]; }
		if(charz[1] == 16) { byte1[7] = charreg[15][1]; byte1[6] = charreg[15][0]; }
		if(charz[1] == 17) { byte1[7] = charreg[16][1]; byte1[6] = charreg[16][0]; }
		if(charz[1] == 18) { byte1[7] = charreg[17][1]; byte1[6] = charreg[17][0]; }
		if(charz[1] == 19) { byte1[7] = charreg[18][1]; byte1[6] = charreg[18][0]; }
		if(charz[1] == 20) { byte1[7] = charreg[19][1]; byte1[6] = charreg[19][0]; }
		if(charz[1] == 21) { byte1[7] = charreg[20][1]; byte1[6] = charreg[20][0]; }
		if(charz[1] == 22) { byte1[7] = charreg[21][1]; byte1[6] = charreg[21][0]; }
		if(charz[1] == 23) { byte1[7] = charreg[22][1]; byte1[6] = charreg[22][0]; }
		if(charz[1] == 24) { byte1[7] = charreg[23][1]; byte1[6] = charreg[23][0]; }
		if(charz[1] == 25) { byte1[7] = charreg[24][1]; byte1[6] = charreg[24][0]; }
		if(charz[1] == 26) { byte1[7] = charreg[25][1]; byte1[6] = charreg[25][0]; }
		if(charz[1] == 27) { byte1[7] = charreg[26][1]; byte1[6] = charreg[26][0]; }
		if(charz[1] == 28) { byte1[7] = charreg[27][1]; byte1[6] = charreg[27][0]; }
		if(charz[1] == 29) { byte1[7] = charreg[28][1]; byte1[6] = charreg[28][0]; }
		if(charz[1] == 30) { byte1[7] = charreg[29][1]; byte1[6] = charreg[29][0]; }
		if(charz[1] == 31) { byte1[7] = charreg[30][1]; byte1[6] = charreg[30][0]; }
		if(charz[1] == 32) { byte1[7] = charreg[31][1]; byte1[6] = charreg[31][0]; }
		if(charz[1] == 33) { byte1[7] = charreg[32][1]; byte1[6] = charreg[32][0]; }
		if(charz[1] == 34) { byte1[7] = charreg[33][1]; byte1[6] = charreg[33][0]; }
		if(charz[1] == 35) { byte1[7] = charreg[34][1]; byte1[6] = charreg[34][0]; }
		if(charz[1] == 36) { byte1[7] = charreg[35][1]; byte1[6] = charreg[35][0]; }

		if(charz[2] == 1) { byte1[5] = charreg[0][1]; byte1[4] = charreg[0][0]; }
		if(charz[2] == 2) { byte1[5] = charreg[1][1]; byte1[4] = charreg[1][0]; }
		if(charz[2] == 3) { byte1[5] = charreg[2][1]; byte1[4] = charreg[2][0]; }
		if(charz[2] == 4) { byte1[5] = charreg[3][1]; byte1[4] = charreg[3][0]; }
		if(charz[2] == 5) { byte1[5] = charreg[4][1]; byte1[4] = charreg[4][0]; }
		if(charz[2] == 6) { byte1[5] = charreg[5][1]; byte1[4] = charreg[5][0]; }
		if(charz[2] == 7) { byte1[5] = charreg[6][1]; byte1[4] = charreg[6][0]; }
		if(charz[2] == 8) { byte1[5] = charreg[7][1]; byte1[4] = charreg[7][0]; }
		if(charz[2] == 9) { byte1[5] = charreg[8][1]; byte1[4] = charreg[8][0]; }
		if(charz[2] == 0) { byte1[5] = charreg[9][1]; byte1[4] = charreg[9][0]; }
		if(charz[2] == 11) { byte1[5] = charreg[10][1]; byte1[4] = charreg[10][0]; }
		if(charz[2] == 12) { byte1[5] = charreg[11][1]; byte1[4] = charreg[11][0]; }
		if(charz[2] == 13) { byte1[5] = charreg[12][1]; byte1[4] = charreg[12][0]; }
		if(charz[2] == 14) { byte1[5] = charreg[13][1]; byte1[4] = charreg[13][0]; }
		if(charz[2] == 15) { byte1[5] = charreg[14][1]; byte1[4] = charreg[14][0]; }
		if(charz[2] == 16) { byte1[5] = charreg[15][1]; byte1[4] = charreg[15][0]; }
		if(charz[2] == 17) { byte1[5] = charreg[16][1]; byte1[4] = charreg[16][0]; }
		if(charz[2] == 18) { byte1[5] = charreg[17][1]; byte1[4] = charreg[17][0]; }
		if(charz[2] == 19) { byte1[5] = charreg[18][1]; byte1[4] = charreg[18][0]; }
		if(charz[2] == 20) { byte1[5] = charreg[19][1]; byte1[4] = charreg[19][0]; }
		if(charz[2] == 21) { byte1[5] = charreg[20][1]; byte1[4] = charreg[20][0]; }
		if(charz[2] == 22) { byte1[5] = charreg[21][1]; byte1[4] = charreg[21][0]; }
		if(charz[2] == 23) { byte1[5] = charreg[22][1]; byte1[4] = charreg[22][0]; }
		if(charz[2] == 24) { byte1[5] = charreg[23][1]; byte1[4] = charreg[23][0]; }
		if(charz[2] == 25) { byte1[5] = charreg[24][1]; byte1[4] = charreg[24][0]; }
		if(charz[2] == 26) { byte1[5] = charreg[25][1]; byte1[4] = charreg[25][0]; }
		if(charz[2] == 27) { byte1[5] = charreg[26][1]; byte1[4] = charreg[26][0]; }
		if(charz[2] == 28) { byte1[5] = charreg[27][1]; byte1[4] = charreg[27][0]; }
		if(charz[2] == 29) { byte1[5] = charreg[28][1]; byte1[4] = charreg[28][0]; }
		if(charz[2] == 30) { byte1[5] = charreg[29][1]; byte1[4] = charreg[29][0]; }
		if(charz[2] == 31) { byte1[5] = charreg[30][1]; byte1[4] = charreg[30][0]; }
		if(charz[2] == 32) { byte1[5] = charreg[31][1]; byte1[4] = charreg[31][0]; }
		if(charz[2] == 33) { byte1[5] = charreg[32][1]; byte1[4] = charreg[32][0]; }
		if(charz[2] == 34) { byte1[5] = charreg[33][1]; byte1[4] = charreg[33][0]; }
		if(charz[2] == 35) { byte1[5] = charreg[34][1]; byte1[4] = charreg[34][0]; }
		if(charz[2] == 36) { byte1[5] = charreg[35][1]; byte1[4] = charreg[35][0]; }

		if(charz[3] == 1) { byte1[3] = charreg[0][1]; byte1[2] = charreg[0][0]; }
		if(charz[3] == 2) { byte1[3] = charreg[1][1]; byte1[2] = charreg[1][0]; }
		if(charz[3] == 3) { byte1[3] = charreg[2][1]; byte1[2] = charreg[2][0]; }
		if(charz[3] == 4) { byte1[3] = charreg[3][1]; byte1[2] = charreg[3][0]; }
		if(charz[3] == 5) { byte1[3] = charreg[4][1]; byte1[2] = charreg[4][0]; }
		if(charz[3] == 6) { byte1[3] = charreg[5][1]; byte1[2] = charreg[5][0]; }
		if(charz[3] == 7) { byte1[3] = charreg[6][1]; byte1[2] = charreg[6][0]; }
		if(charz[3] == 8) { byte1[3] = charreg[7][1]; byte1[2] = charreg[7][0]; }
		if(charz[3] == 9) { byte1[3] = charreg[8][1]; byte1[2] = charreg[8][0]; }
		if(charz[3] == 0) { byte1[3] = charreg[9][1]; byte1[2] = charreg[9][0]; }
		if(charz[3] == 11) { byte1[3] = charreg[10][1]; byte1[2] = charreg[10][0]; }
		if(charz[3] == 12) { byte1[3] = charreg[11][1]; byte1[2] = charreg[11][0]; }
		if(charz[3] == 13) { byte1[3] = charreg[12][1]; byte1[2] = charreg[12][0]; }
		if(charz[3] == 14) { byte1[3] = charreg[13][1]; byte1[2] = charreg[13][0]; }
		if(charz[3] == 15) { byte1[3] = charreg[14][1]; byte1[2] = charreg[14][0]; }
		if(charz[3] == 16) { byte1[3] = charreg[15][1]; byte1[2] = charreg[15][0]; }
		if(charz[3] == 17) { byte1[3] = charreg[16][1]; byte1[2] = charreg[16][0]; }
		if(charz[3] == 18) { byte1[3] = charreg[17][1]; byte1[2] = charreg[17][0]; }
		if(charz[3] == 19) { byte1[3] = charreg[18][1]; byte1[2] = charreg[18][0]; }
		if(charz[3] == 20) { byte1[3] = charreg[19][1]; byte1[2] = charreg[19][0]; }
		if(charz[3] == 21) { byte1[3] = charreg[20][1]; byte1[2] = charreg[20][0]; }
		if(charz[3] == 22) { byte1[3] = charreg[21][1]; byte1[2] = charreg[21][0]; }
		if(charz[3] == 23) { byte1[3] = charreg[22][1]; byte1[2] = charreg[22][0]; }
		if(charz[3] == 24) { byte1[3] = charreg[23][1]; byte1[2] = charreg[23][0]; }
		if(charz[3] == 25) { byte1[3] = charreg[24][1]; byte1[2] = charreg[24][0]; }
		if(charz[3] == 26) { byte1[3] = charreg[25][1]; byte1[2] = charreg[25][0]; }
		if(charz[3] == 27) { byte1[3] = charreg[26][1]; byte1[2] = charreg[26][0]; }
		if(charz[3] == 28) { byte1[3] = charreg[27][1]; byte1[2] = charreg[27][0]; }
		if(charz[3] == 29) { byte1[3] = charreg[28][1]; byte1[2] = charreg[28][0]; }
		if(charz[3] == 30) { byte1[3] = charreg[29][1]; byte1[2] = charreg[29][0]; }
		if(charz[3] == 31) { byte1[3] = charreg[30][1]; byte1[2] = charreg[30][0]; }
		if(charz[3] == 32) { byte1[3] = charreg[31][1]; byte1[2] = charreg[31][0]; }
		if(charz[3] == 33) { byte1[3] = charreg[32][1]; byte1[2] = charreg[32][0]; }
		if(charz[3] == 34) { byte1[3] = charreg[33][1]; byte1[2] = charreg[33][0]; }
		if(charz[3] == 35) { byte1[3] = charreg[34][1]; byte1[2] = charreg[34][0]; }
		if(charz[3] == 36) { byte1[3] = charreg[35][1]; byte1[2] = charreg[35][0]; }
 		
 		//send the array to the board
 		i2c.writeBulk(byte1);
 		Timer.delay(0.01);
	}
	
	String repeat(char c, int n) {
	    char[] arr = new char[n];
	    Arrays.fill(arr, c);
	    return new String(arr);
	}
	
}

