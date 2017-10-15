package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.camera.Driver;
import org.usfirst.frc.team1086.robot.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;

public class LoganChaser extends AutonomousSection {
	PIDController drive, strafe, turn;
	Driver driver;
	Drivetrain drivetrain;
	public LoganChaser(Driver d, Drivetrain drivetrain){
		drive = d.getDriveController();
		strafe = d.getStrafeController();
		turn = d.getTurnController();
		this.driver = d;
		this.drivetrain = drivetrain;
	}
	@Override public void start(){
		drive.enable();
		strafe.enable();
		turn.enable();
	}
	@Override public void update(){
		if(driver.seesTarget())
			drivetrain.oct(drive.get(), strafe.get(), turn.get());
		else
			drivetrain.oct(0, 0, 0.6);
	}
	@Override public void finish(){
		drive.disable();
		strafe.disable();
		turn.disable();
	}
	@Override public boolean isFinished(){
		return isTimedOut() || (driver.seesTarget() && drive.getAvgError() < 5 && turn.getAvgError() < 5);
	}
}
