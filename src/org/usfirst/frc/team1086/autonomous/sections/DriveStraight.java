package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;

public class DriveStraight extends AutonomousSection {
	double forward, turn, strafe;
	Drivetrain drive;
	PIDController straightDriver;
	public DriveStraight(long duration, Drivetrain drive, double forward){
		this.duration = duration;
		this.drive = drive;
		this.forward = forward;
	}
	@Override public void start(){
		super.start();
		straightDriver = drive.getTurnToAngleController();
		straightDriver.enable();
		straightDriver.setSetpoint(0);
		drive.getGyro().reset();
	}
	@Override public void update(){
		drive.drive(forward, 0, straightDriver.get(), true);
	}
	@Override public void finish(){
		straightDriver.disable();
	}
}
