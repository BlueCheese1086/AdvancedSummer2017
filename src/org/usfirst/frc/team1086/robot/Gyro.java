package org.usfirst.frc.team1086.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro {
	static enum GyroType {
		NAVX, GYRO;
	}
	AHRS navX;
	AnalogGyro gyro;
	GyroType type = GyroType.GYRO;
	static Gyro instance = null;
	private Gyro() {
    	try {
        	navX = new AHRS(SerialPort.Port.kMXP);
        	type = GyroType.NAVX;
    	} catch(Exception e){
    		gyro = new AnalogGyro(0);
    	}
    	instance = this;
    }
	public static Gyro getGyro(){
		if(instance == null)
			return new Gyro();
		return instance;
	}
	public double getAngle() {
		double ang = 0;
		if(type == GyroType.NAVX) {
			ang = (navX.getYaw() % 360 + 360) % 360;
		} else {
			ang = (gyro.getAngle() % 360 + 360) % 360;
		}
		return ang > 180 ? ang - 360 : ang;
	}
	public void outputValues(){
		SmartDashboard.putNumber("NavX Angle", getAngle());
	}
	public void reset() {
		if(type == GyroType.NAVX) {
			navX.reset();
		} else {
			gyro.reset();
		}
		assert(Math.abs(getAngle()) < 1);
	}
}
