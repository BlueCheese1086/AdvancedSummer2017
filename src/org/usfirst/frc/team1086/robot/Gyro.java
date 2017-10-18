package org.usfirst.frc.team1086.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SerialPort;

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
	}
	public double getAngle() {
		if(type == GyroType.NAVX) {
			return navX.getYaw();
		} else {
			return gyro.getAngle();
		}
	}
	public void reset() {
		if(type == GyroType.NAVX) {
			navX.reset();
		} else {
			gyro.reset();
		}
	}
}
