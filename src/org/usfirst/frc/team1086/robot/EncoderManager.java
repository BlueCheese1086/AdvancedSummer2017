package org.usfirst.frc.team1086.robot;

import edu.wpi.first.wpilibj.PIDController;

public class EncoderManager {
	public PIDController getDrivePID(double distance) {
		return new PIDController(0, 0, 0, null, null);
	}
}
