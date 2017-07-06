package org.usfirst.frc.team1086.CameraCalculator.Example;

import org.usfirst.frc.team1086.CameraCalculator.Camera;
import org.usfirst.frc.team1086.CameraCalculator.CameraConfig;

import edu.wpi.first.wpilibj.CameraServer;

public class CameraDriver {
	public final int HFOV = 67;
	public final int VFOV = 67;
	public final int X_PIXELS = 320;
	public final int Y_PIXELS = 240;
	public final double HORIZONTAL_OFFSET = 0;
	public final double VERTICAL_OFFSET = 0;
	public final double DEPTH_OFFSET = 0;
	public double HORIZONTAL_ANGLE = 0;
	public double VERTICAL_ANGLE = 0; 
	public Camera c;
	public GearTarget gear;
    public CameraDriver(){
        gear = new GearTarget();
        RetroReflectivePipeline primary = new RetroReflectivePipeline();
        c = new Camera(HFOV, VFOV, X_PIXELS, Y_PIXELS, HORIZONTAL_OFFSET, VERTICAL_OFFSET, DEPTH_OFFSET, HORIZONTAL_ANGLE, VERTICAL_ANGLE);
        c.initializeCamera(CameraServer.getInstance().addAxisCamera("10.10.86.22"), "Gear_Camera");
        c.addVisionTarget(gear);
        c.addPipeline(primary);
    }
    public void configureAngles(double dis){
    	HORIZONTAL_ANGLE = CameraConfig.getXAngle(c.calculators.get(gear).rawX, dis, HORIZONTAL_OFFSET);
    	VERTICAL_ANGLE = CameraConfig.getYAngle(c.calculators.get(gear).rawV, gear.height - HORIZONTAL_OFFSET, dis);
    	c.hAngle = HORIZONTAL_ANGLE;
    	c.vAngle = VERTICAL_ANGLE;
    }
    
}