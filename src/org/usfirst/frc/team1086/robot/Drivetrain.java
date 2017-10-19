package org.usfirst.frc.team1086.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Drivetrain {
	CANTalon leftFrontMecanum, rightFrontMecanum, leftRearMecanum, rightRearMecanum;
    CANTalon leftFrontColson, rightFrontColson, leftRearColson, rightRearColson;
    Solenoid trigger;
    EncoderManager em;
    Gyro g;
    PIDController turnToAngle, driveStraight, driveToDistance;
    public Drivetrain(){
        leftFrontMecanum = new CANTalon(RobotMap.LEFT_FRONT_MECANUM);
        leftRearMecanum = new CANTalon(RobotMap.LEFT_REAR_MECANUM);
        rightFrontMecanum = new CANTalon(RobotMap.RIGHT_FRONT_MECANUM);
        rightRearMecanum = new CANTalon(RobotMap.RIGHT_REAR_MECANUM);      
        leftFrontColson = new CANTalon(RobotMap.LEFT_FRONT_COLSON);
        leftRearColson = new CANTalon(RobotMap.LEFT_REAR_COLSON);
        rightFrontColson = new CANTalon(RobotMap.RIGHT_FRONT_COLSON);
        rightRearColson = new CANTalon(RobotMap.RIGHT_REAR_COLSON);
        rightFrontMecanum.setInverted(true);
        rightFrontColson.setInverted(true);
        rightRearMecanum.setInverted(true);
        rightRearColson.setInverted(true);
        trigger = new Solenoid(RobotMap.TRIGGER);
        this.getTurnToAngleController();
        this.getDriveStraightController();
        this.getDriveToDistanceController();
        g = Gyro.getGyro();
    }
    public void drive(double leftY, double leftX, double rightX, boolean trigger){
        this.trigger.set(!trigger);
        if(!trigger){
            mecanum(leftY * Math.abs(leftY), leftX * Math.abs(leftX), rightX * Math.abs(rightX));
        } else {
            colson(leftY * Math.abs(leftY), rightX * Math.abs(rightX));
        }
    }    
    public void mecanum(double leftY, double leftX, double rightX){
        leftFrontMecanum.set((leftY - rightX - leftX));
        leftFrontColson.set((leftY - rightX - leftX));
        rightFrontMecanum.set((leftY + rightX + leftX));
        rightFrontColson.set((leftY + rightX + leftX));
        leftRearMecanum.set((leftY - rightX + leftX));
        leftRearColson.set((leftY - rightX + leftX));
        rightRearMecanum.set((leftY + rightX - leftX));
        rightRearColson.set((leftY + rightX - leftX));
        
    }
    public void colson(double leftY, double rightX){
        leftFrontMecanum.set(leftY - rightX);
        leftFrontColson.set(leftY - rightX);
        rightFrontMecanum.set(leftY + rightX);
        rightFrontColson.set(leftY + rightX);
        leftRearMecanum.set(leftY - rightX);
        leftRearColson.set(leftY - rightX);
        rightRearMecanum.set(leftY + rightX);
        rightRearColson.set(leftY + rightX);
    }
    public PIDController getTurnToAngleController(){
    	if(this.turnToAngle != null)
    		return this.turnToAngle;
    	PIDController controller = new PIDController(0.04, 0, 0.05, new PIDInput(() -> {
    		return g.getAngle();
    	}), o -> {});
    	controller.setInputRange(-180, 180);
    	controller.setOutputRange(-1, 1);
    	controller.setContinuous(true);
    	this.turnToAngle = controller;
    	LiveWindow.addActuator("Turn Controller", "PID", this.turnToAngle);
    	return controller;
    }
    public PIDController getDriveStraightController(){
    	if(this.driveStraight != null)
    		return this.driveStraight;
    	PIDController controller = new PIDController(0.06, 0.0003, 0.07, new PIDInput(() -> {
    		return g.getAngle();
    	}), o -> {});
    	controller.setInputRange(-180, 180);
    	controller.setOutputRange(-1, 1);
    	controller.setContinuous(true);
    	this.driveStraight = controller;
    	LiveWindow.addActuator("Drive Straight Controller", "PID", this.driveStraight);
    	return controller;
    }
    public PIDController getDriveToDistanceController(){
    	if(this.driveToDistance != null)
    		return this.driveToDistance;
    	PIDController controller = new PIDController(0, 0, 0, new PIDInput(() -> {
    		return em.getDistance();
    	}), o -> {});
    	controller.setInputRange(-180, 180);
    	controller.setOutputRange(-1, 1);
    	controller.setContinuous(true);
    	this.driveToDistance = controller;
    	return controller;
    }
    public Gyro getGyro(){
    	return g;
    }
}