package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.camera.Driver;
import org.usfirst.frc.team1086.robot.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;

public class Turner extends AutonomousSection {
	PIDController turnPID;
	Drivetrain drive;
	public Turner(Drivetrain drive){
		this.turnPID = drive.getTurnToAngleController();
		this.drive = drive;
		duration = -1;
	}
	public Turner(Drivetrain drive, long duration){
		this(drive);
		this.duration = duration;
	}
	@Override public void start(){
		super.start();
		turnPID.enable();
	}
	@Override public void update(){
		drive.drive(0, 0, turnPID.get(), true);
	}
	@Override public void finish(){
		turnPID.disable();
	}
	@Override public boolean isFinished(){
		return isTimedOut() || turnPID.getAvgError() < 2;
	}
	
}
