package org.usfirst.frc.team1086.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputManager {
	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	Joystick auxStick = new Joystick(5);
	public double getDrive(){
		return leftStick.getY() * (leftStick.getRawButton(ButtonMap.SAFETY_DRIVE) ? 1 : 0);
	}
	public double getStrafe(){
		return leftStick.getX() * (leftStick.getRawButton(ButtonMap.SAFETY_DRIVE) ? 1 : 0);
	}
	public double getTurn(){
		return rightStick.getX() * (leftStick.getRawButton(ButtonMap.SAFETY_DRIVE) ? 1 : 0);
	}
	public boolean getShift(){
		return rightStick.getRawButton(ButtonMap.OCTO_SHIFTER);
	}
	public boolean getClimb(){
		return auxStick.getRawButton(ButtonMap.CLIMB);
	}
	public boolean getEvict(){
		return auxStick.getRawButton(ButtonMap.EVICT);
	}
	public boolean getTurnRight(){
		return leftStick.getRawButton(ButtonMap.TURN_RIGHT);
	}
	public boolean getTurnLeft(){
		return leftStick.getRawButton(ButtonMap.TURN_LEFT);
	}
	public boolean getDriveStraight(){
		return rightStick.getRawButton(ButtonMap.STRAIGHT_DRIVE);
	}
	public boolean getGearDrive(){
		return rightStick.getRawButton(ButtonMap.GEAR_DRIVE);
	}
	public boolean getConfigCamera(){
		return leftStick.getRawButton(ButtonMap.CONFIG_CAMERA);
	}
}