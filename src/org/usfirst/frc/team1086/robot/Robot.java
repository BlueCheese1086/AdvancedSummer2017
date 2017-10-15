package org.usfirst.frc.team1086.robot;

import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team1086.CameraCalculator.Sighting;
import org.usfirst.frc.team1086.autonomous.AutonomousManager;
import org.usfirst.frc.team1086.camera.Driver;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    Drivetrain drive;
    Driver camera;
    Joystick leftDrive, rightDrive;
    PIDController cameraTurn, cameraStrafe, cameraDrive;
    SendableChooser<AutonomousManager> chooser;
    AutonomousManager middleGear;
    @Override public void robotInit(){
        drive = new Drivetrain();
        leftDrive = new Joystick(0);
        rightDrive = new Joystick(1);
        camera = new Driver();
        camera.initialize();
        cameraTurn = camera.getTurnController(0.3, 0, 0);
        cameraStrafe = camera.getStrafeController(0, 0, 0);
        cameraDrive = camera.getDriveController(0.4, 0, 0);
        cameraTurn.enable();
        cameraStrafe.enable();
        cameraDrive.enable();
        middleGear = new AutonomousManager();
        
    }
    @Override public void autonomousInit(){
    }
    @Override public void autonomousPeriodic(){}
    @Override public void teleopPeriodic(){
        if(leftDrive.getRawButton(1)){
            drive.oct(leftDrive.getY(), leftDrive.getX(), rightDrive.getX());
        } else drive.oct(0, 0, 0);
        if(rightDrive.getRawButton(1)){
            drive.oct(cameraDrive.get(), cameraStrafe.get(), cameraTurn.get());
        } else if(leftDrive.getRawButton(2)) {
        	try {
        	camera.getCamera().configure(camera.gear, 52);
        	System.out.println("X: " + (camera.getCamera().hAngle * 180.0 / Math.PI));
        	System.out.println("Y: " + (camera.getCamera().vAngle * 180.0 / Math.PI));
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }
        ArrayList<Sighting> sightings = camera.getCamera().getSightings(camera.gear);
        SmartDashboard.putNumber("Number of sightings", sightings.size());
        double[] angles = new double[sightings.size()];
        double[] distances = new double[sightings.size()];
        int ind = 0;
        for(Sighting s : sightings) {
        	angles[ind] = s.angle.getAsDouble() * 180.0 / Math.PI;
        	distances[ind++] = s.distance.getAsDouble();
        }
        SmartDashboard.putString("Angles: ", Arrays.toString(angles));
        SmartDashboard.putString("Distances: ", Arrays.toString(distances));
    }
    @Override public void testPeriodic(){}
}