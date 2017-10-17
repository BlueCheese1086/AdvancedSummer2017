package org.usfirst.frc.team1086.robot;

import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team1086.CameraCalculator.Sighting;
import org.usfirst.frc.team1086.autonomous.AutonomousManager;
import org.usfirst.frc.team1086.autonomous.sections.Drive;
import org.usfirst.frc.team1086.autonomous.sections.LoganChaser;
import org.usfirst.frc.team1086.autonomous.sections.ReleaseGear;
import org.usfirst.frc.team1086.camera.Driver;
import org.usfirst.frc.team1086.subsystems.Climber;
import org.usfirst.frc.team1086.subsystems.GearHolder;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    Drivetrain drive;
    InputManager im;
    GearHolder evictor;
    Climber climber;
    Compressor compressor;
    Driver camera;
    PIDController cameraTurn, cameraStrafe, cameraDrive;
    SendableChooser<AutonomousManager> chooser;
    AutonomousManager middleGear;
    AutonomousManager selectedRoutine = null;
    boolean cameraOn = false; //Turn this to true/remove it when camera is on robot.
    @Override public void robotInit(){
    	im = new InputManager();
        drive = new Drivetrain();
        evictor = new GearHolder();
        climber = new Climber();
        compressor = new Compressor(RobotMap.COMPRESSOR);
        compressor.setClosedLoopControl(true);
        if(cameraOn){
	        camera = new Driver();
	        camera.initialize();
	        cameraTurn = camera.getTurnController(0.3, 0, 0);
	        cameraStrafe = camera.getStrafeController(0, 0, 0);
	        cameraDrive = camera.getDriveController(0.4, 0, 0);
	        cameraTurn.enable();
	        cameraStrafe.enable();
	        cameraDrive.enable();
        }
        defineAutonomousRoutines();
    }
    public void defineAutonomousRoutines(){
    	middleGear = new AutonomousManager();
    	middleGear.addSection(new Drive(200, drive, 0.7, 0, 0));
    	middleGear.addSection(new LoganChaser(camera, drive));
    	middleGear.addSection(new ReleaseGear(evictor, drive));
    	chooser.addDefault("Middle Gear", middleGear);
    }
    @Override public void autonomousInit(){
    	selectedRoutine = chooser.getSelected() == null ? middleGear : chooser.getSelected();
    	selectedRoutine.start();
    }
    @Override public void autonomousPeriodic(){
    	selectedRoutine.update();
    }
    @Override public void disabledInit(){
    	if(selectedRoutine != null){
    		selectedRoutine.finish();
    		selectedRoutine = null;
    	}
    }
    @Override public void teleopPeriodic(){
        drive.drive(im.getDrive(), im.getStrafe(), im.getTurn(), im.getShift());
        
        if(im.getClimb())
            climber.climb();
        else 
            climber.stop();
        
        if(im.getEvict())
            evictor.evict();
        else 
            evictor.hold();
        
        if(cameraOn){
        	if(im.getConfigCamera()) {
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
    }
    @Override public void testPeriodic(){}
}