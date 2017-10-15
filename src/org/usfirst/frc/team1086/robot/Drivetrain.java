package org.usfirst.frc.team1086.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;

public class Drivetrain {
	CANTalon leftFrontMecanum, rightFrontMecanum, leftRearMecanum, rightRearMecanum;
    CANTalon leftFrontColson, rightFrontColson, leftRearColson, rightRearColson;
    Solenoid trigger;
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
        leftFrontMecanum.set(0.9 * (leftY - rightX - leftX));
        leftFrontColson.set(0.9 * (leftY - rightX - leftX));
        rightFrontMecanum.set(0.9 * (leftY + rightX + leftX));
        rightFrontColson.set(0.9 * (leftY + rightX + leftX));
        leftRearMecanum.set(0.9 * (leftY - rightX + leftX));
        leftRearColson.set(0.9 * (leftY - rightX + leftX));
        rightRearMecanum.set(0.9 * (leftY + rightX - leftX));
        rightRearColson.set(0.9 * (leftY + rightX - leftX));
        
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
}