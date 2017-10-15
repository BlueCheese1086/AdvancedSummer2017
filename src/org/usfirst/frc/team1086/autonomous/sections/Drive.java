package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Drivetrain;

public class Drive extends AutonomousSection {
	double forward, turn, strafe;
	Drivetrain drive;
	public Drive(long duration, Drivetrain drive, double forward, double turn, double strafe){
		this.duration = duration;
		this.drive = drive;
		this.forward = forward;
		this.turn = turn;
		this.strafe = strafe;
	}
	@Override public void update(){
		drive.drive(forward, strafe, turn, false);
	}
	@Override public void finish(){}
}
