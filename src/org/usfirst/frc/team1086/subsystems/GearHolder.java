package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;

public class GearHolder {
	Solenoid evictor;
	public GearHolder(){
		evictor = new Solenoid(RobotMap.SPITTER);
	}
	public void evict(){
		evictor.set(false);
	}
	public void hold(){
		evictor.set(true);
	}
}
