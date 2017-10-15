package org.usfirst.frc.team1086.robot;

import com.ctre.CANTalon;

public class Drivetrain {
    CANTalon frontLeft, frontRight, rearLeft, rearRight;
    public Drivetrain(){
        frontLeft = new CANTalon(RobotMap.FRONT_LEFT);
        rearLeft = new CANTalon(RobotMap.REAR_LEFT);
        rearRight = new CANTalon(RobotMap.REAR_RIGHT);
        frontRight = new CANTalon(RobotMap.FRONT_RIGHT);
        frontLeft.setInverted(true);
        rearLeft.setInverted(true);
    }
    public void oct(double leftY, double leftX, double rightX){
        //if ()
    }
    public void mecanum(double leftY, double leftX, double rightX){
        frontLeft.set(-leftY - rightX - leftX);
        frontRight.set(-leftY + rightX + leftX);
        rearLeft.set(leftY - rightX + leftX);
        rearRight.set(leftY + rightX - leftX);
    }
    public void colson(double leftY, double rightX){
        frontLeft.set(-leftY - rightX);
        frontRight.set(-leftY + rightX);
        rearLeft.set(leftY - rightX);
        rearRight.set(leftY + rightX);
    }
}
