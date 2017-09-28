package org.usfirst.frc.team1086.camera;

import java.util.Optional;
import java.util.OptionalDouble;

import org.usfirst.frc.team1086.CameraCalculator.Camera;
import org.usfirst.frc.team1086.CameraCalculator.Pipeline;
import org.usfirst.frc.team1086.CameraCalculator.Sighting;
import org.usfirst.frc.team1086.CameraCalculator.VisionTarget;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Driver {
    public VisionTarget gear = new GearTarget();
    Pipeline primary = new RetroReflectivePipeline();
    PIDController turnController, driveController, strafeController;
    Camera gearCamera;
    public static final double GEAR_CAMERA_HFOV = 47 * Math.PI /  180.0;
    public static final double GEAR_CAMERA_VFOV = 47 * Math.PI /  180.0;
    public static final double GEAR_CAMERA_HEIGHT = 240;
    public static final double GEAR_CAMERA_WIDTH = 320;
    public static final double GEAR_CAMERA_HOFFSET = 0;
    public static final double GEAR_CAMERA_VOFFSET = 7.75;
    public static final double GEAR_CAMERA_DOFFSET = 0;
    public static double GEAR_CAMERA_XANGLE = 1.47887575993748 * Math.PI / 180;
    public static double GEAR_CAMERA_YANGLE = 7.15837384910151 * Math.PI / 180;
    public static final double turnP = 0;
    public static final double turnI = 0;
    public static final double turnD = 0;
    public static final double driveP = 0;
    public static final double driveI = 0;
    public static final double driveD = 0;
    public static final double strafeP = 0;
    public static final double strafeI = 0;
    public static final double strafeD = 0;
    public void initialize(){
        gearCamera = new Camera(GEAR_CAMERA_HFOV, GEAR_CAMERA_VFOV, GEAR_CAMERA_WIDTH, GEAR_CAMERA_HEIGHT, GEAR_CAMERA_HOFFSET,
                GEAR_CAMERA_VOFFSET, GEAR_CAMERA_DOFFSET, GEAR_CAMERA_XANGLE, GEAR_CAMERA_YANGLE);
        gearCamera.initializeCamera(null, "Gear_Camera");
        gearCamera.addVisionTarget(gear);
        gearCamera.addPipeline(primary);
    }
    
    /**
     * Returns a PID controller that aims the robot at the closest gear target
     * @param p kP for the PID controller
     * @param i kI for the PID controller
     * @param d kD for the PID controller
     * @return the PIDController
     */
    public PIDController getTurnController(double p, double i, double d){
        if(turnController == null){
            turnController = new PIDController(p, i, d, new PIDSource() {
                @Override public void setPIDSourceType(PIDSourceType pidst){}
                @Override public PIDSourceType getPIDSourceType(){
                    return PIDSourceType.kDisplacement;
                }
                @Override public double pidGet(){
                	Optional<Sighting> o = gearCamera.getSightings(gear).stream().filter(a -> a.distance.isPresent()).sorted((a, b) -> Double.compare(a.distance.getAsDouble(), b.distance.getAsDouble()))
                            .findFirst();
                	if(o.isPresent()) {
                		OptionalDouble od = o.get().angle;
	                	if(od.isPresent())
	                		return od.getAsDouble() / 30;
                	}
                	return 0;
                }
            }, a -> {});
            turnController.setAbsoluteTolerance(1);
            turnController.setSetpoint(0);
            turnController.setInputRange(-1, 1);
            turnController.setOutputRange(-1, 1);
        } else {
            turnController.setPID(p, i, d);
        }
        return turnController;
    }
    public PIDController getTurnController(){
    	return getTurnController(turnP, turnI, turnD);
    }
    
    /**
     * Returns a PID controller that drives the robot at the closest gear target.
     * TODO: modify the pidGet to drive backwards if strafing is needed.
     * @param p kP for the PID controller
     * @param i kI for the PID controller
     * @param d kD for the PID controller
     * @return the PIDController
     */
    public PIDController getDriveController(double p, double i, double d){
        if(driveController == null){
            driveController = new PIDController(p, i, d, new PIDSource() {
                @Override public void setPIDSourceType(PIDSourceType pidst){}
                @Override public PIDSourceType getPIDSourceType(){
                    return PIDSourceType.kDisplacement;
                }
                @Override public double pidGet(){
                	Optional<Sighting> o = gearCamera.getSightings(gear).stream().filter(a -> a.distance.isPresent()).sorted((a, b) -> Double.compare(a.distance.getAsDouble(), b.distance.getAsDouble()))
                            .findFirst();
                	if(o.isPresent()) {
                		OptionalDouble od = o.get().distance;
	                	if(od.isPresent())
	                		return od.getAsDouble();
                	}
                	return 0;
                }
            }, a -> {});
            driveController.setAbsoluteTolerance(1);
            driveController.setSetpoint(0);
            driveController.setInputRange(0, 200);
            driveController.setOutputRange(-1, 1);
        } else {
            driveController.setPID(p, i, d);
        }
        return turnController;
    }
    public PIDController getDriveController(){
    	return getDriveController(driveP, driveI, driveD);
    }
    
    /**
     * Returns a PID controller that drives the robot to in front of closest gear target.
     * @param p kP for the PID controller
     * @param i kI for the PID controller
     * @param d kD for the PID controller
     * @return the PIDController
     */
    public PIDController getStrafeController(double p, double i, double d){
        if(driveController == null){
            driveController = new PIDController(p, i, d, new PIDSource() {
                @Override public void setPIDSourceType(PIDSourceType pidst){}
                @Override public PIDSourceType getPIDSourceType(){
                    return PIDSourceType.kDisplacement;
                }
                @Override public double pidGet(){
                	Optional<Sighting> o = gearCamera.getSightings(gear).stream().filter(a -> a.distance.isPresent()).sorted((a, b) -> Double.compare(a.distance.getAsDouble(), b.distance.getAsDouble()))
                            .findFirst();
                	if(o.isPresent()) {
                		OptionalDouble od = o.get().rotation;
	                	if(od.isPresent())
	                		return od.getAsDouble() / 30;
                	}
                	return 0;
                }
            }, a -> {});
            driveController.setAbsoluteTolerance(1);
            driveController.setSetpoint(0);
            driveController.setInputRange(-1, 1);
            driveController.setOutputRange(-1, 1);
        } else {
            driveController.setPID(p, i, d);
        }
        return turnController;
    }
    public PIDController getStrafeController(){
    	return getStrafeController(strafeP, strafeI, strafeD);
    }
    public boolean seesTarget(){
    	return gearCamera.getSightings(gear).size() > 0;
    }
    public Camera getCamera(){
    	return gearCamera;
    }
}