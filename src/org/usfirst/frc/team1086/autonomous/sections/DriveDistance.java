package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Drivetrain;
import org.usfirst.frc.team1086.robot.EncoderManager;

import edu.wpi.first.wpilibj.PIDController;

public class DriveDistance extends AutonomousSection {
	Drivetrain drive;
	PIDController driver;
	public DriveDistance(double distance, Drivetrain drive, EncoderManager man){
		this.duration = -1;
		this.drive = drive;
		this.driver = man.getDrivePID(distance);
	}
	public DriveDistance(double distance, Drivetrain drive, EncoderManager man, long duration){
		this(distance, drive, man);
		this.duration = duration;
	}
	@Override public void start(){
		super.start();
		driver.enable();
	}
	@Override public void update(){
		drive.oct(driver.get(), 0, 0);
	}
	@Override public void finish(){
		driver.disable();
	}
	@Override public boolean isFinished(){
		return isTimedOut() || driver.getAvgError() < 3;
	}
}
