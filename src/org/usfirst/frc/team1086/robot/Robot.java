package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.CameraCalculator.Example.CameraDriver;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;

public class Robot extends IterativeRobot {
	Drivetrain drive;
	CameraDriver camera;
	Joystick leftDrive, rightDrive;
	PIDController cameraTurn;
	@Override public void robotInit(){
		drive = new Drivetrain();
		leftDrive = new Joystick(0);
		rightDrive = new Joystick(1);
		camera = new CameraDriver();
		cameraTurn = new PIDController(0, 0, 0, new PIDInput(() -> {
			if(camera.c.seesTarget(camera.gear))
				return 2 * camera.c.getAngle(camera.gear) / camera.HFOV;
			else return 0;
		}), d -> {});
		cameraTurn.setAbsoluteTolerance(1);
		cameraTurn.setSetpoint(0);
		cameraTurn.setInputRange(-1, 1);
		cameraTurn.setOutputRange(-1, 1);
	}
	@Override public void autonomousInit(){}
	@Override public void autonomousPeriodic(){}
	@Override public void teleopPeriodic(){
		if(leftDrive.getRawButton(1)) {
			drive.mecanum(leftDrive.getY(), leftDrive.getX(), rightDrive.getX());
		} if(rightDrive.getRawButton(1)) {
			drive.mecanum(leftDrive.getY(), cameraTurn.get(), rightDrive.getX());
		}
	}
	@Override public void testPeriodic(){}
}

