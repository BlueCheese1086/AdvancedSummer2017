package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Drivetrain;
import org.usfirst.frc.team1086.subsystems.GearHolder;

public class ReleaseGear extends AutonomousSection {
	GearHolder s;
	Drivetrain drive;
	public ReleaseGear(GearHolder s, Drivetrain drive){
		this.duration = 1300;
		this.s = s;
		this.drive = drive;
	}
	@Override public void start(){
		super.start();
		s.evict();
	}
	@Override public void update(){
		if(System.currentTimeMillis() - startTime > 500){
			drive.drive(0.4, 0, 0, true);
			s.evict();
		}
		else {
			s.evict();
			drive.drive(0, 0, 0, true);
		}
	}
	@Override public void finish(){}

}
