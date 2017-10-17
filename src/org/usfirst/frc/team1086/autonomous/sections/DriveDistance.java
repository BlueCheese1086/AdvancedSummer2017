package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Drivetrain;
import org.usfirst.frc.team1086.robot.EncoderManager;

import edu.wpi.first.wpilibj.PIDController;

public class DriveDistance extends AutonomousSection {
	Drivetrain drive;
	PIDController driver;
	public DriveDistance(double distance, Drivetrain drive){
		this.duration = -1;
		this.drive = drive;
		this.driver = drive.getDriveToDistanceController();
	}
	public DriveDistance(double distance, Drivetrain drive, long duration){
		this(distance, drive);
		this.duration = duration;
	}
	@Override public void start(){
		super.start();
		driver.enable();
	}
	@Override public void update(){
		drive.drive(driver.get(), 0, 0, true);
	}
	@Override public void finish(){
		driver.disable();
	}
	@Override public boolean isFinished(){
		return isTimedOut() || driver.getAvgError() < 3;
	}
}
