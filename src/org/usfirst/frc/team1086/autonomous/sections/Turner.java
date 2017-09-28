package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.camera.Driver;
import org.usfirst.frc.team1086.robot.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;

public class Turner extends AutonomousSection {
	PIDController turnPID;
	Drivetrain drive;
	public Turner(PIDController turnPID, Drivetrain drive){
		this.turnPID = turnPID;
		this.drive = drive;
		duration = -1;
	}
	public Turner(PIDController turnPID, Drivetrain drive, long duration){
		this(turnPID, drive);
		this.duration = duration;
	}
	@Override public void start(){
		super.start();
		turnPID.enable();
	}
	@Override public void update(){
		drive.mecanum(0, 0, turnPID.get());
	}
	@Override public void finish(){
		turnPID.disable();
	}
	@Override public boolean isFinished(){
		return isTimedOut() || turnPID.getAvgError() < 2;
	}
	
}
