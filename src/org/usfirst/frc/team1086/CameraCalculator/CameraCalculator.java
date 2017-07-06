package org.usfirst.frc.team1086.CameraCalculator;

import java.util.ArrayList;

public class CameraCalculator {
    ArrayList<Sighting> visionObjects = new ArrayList();
    public double distance, angle;
    public double rawV, rawX;
    public double targetRotation;
    Camera camera;
    VisionTarget visionTarget;
    /**
     * Creates the camera calculator
     * @param c the camera the calculator uses to get its image data
     * @param v the vision target it finds
     */
    public CameraCalculator(Camera c, VisionTarget v){
        this.camera = c;
        this.visionTarget = v;
    }
    
    /**
     * Updates the data from the camera
     * @param polys the data from the camera
     */                                                                                 
    void updateObjects(ArrayList<Sighting> polys){
        visionObjects.clear();
        visionObjects.addAll(polys);
        visionObjects = visionTarget.validateSightings(visionObjects);
        for(Sighting s : visionObjects){
            s.relativeAspectRatio = s.aspectRatio / visionTarget.aspectRatio;
            targetRotation = 0;
            if(Math.abs(s.relativeAspectRatio) <= 1)
            	targetRotation = Math.acos(s.relativeAspectRatio);
        }
        calculateDistance();
        calculateAngle();
    }
    
    /**
     * Calculates the distance to the specified vision target
     */
    public void calculateDistance(){
        if(visionObjects.isEmpty()){
            distance = -1;
        } else {
            double midY = visionObjects.stream().mapToDouble(p -> p.centerY / visionObjects.size()).sum();
            double angleFromCameraToTarget = rawV = getYAngle(midY);
            double verticalAngle = angleFromCameraToTarget + camera.vAngle;
            double changeInY = visionTarget.height - camera.verticalOffset;
            double distanceToTarget = changeInY / Math.sin(verticalAngle);
            double horizontalDistance = distanceToTarget * Math.cos(verticalAngle);
            distance = horizontalDistance;
        }
    }
    
    /**
     * Calculates the angle to the specified vision target
     */
    public void calculateAngle(){
        if(visionObjects.isEmpty()){
            angle = Math.PI;
        } else {
            double midX = visionObjects.stream().mapToDouble(p -> p.centerX / visionObjects.size()).sum();
            rawX = getXAngle(midX);
            double horizontalAngle = Math.PI / 2 - getXAngle(midX);
            double f = Math.sqrt(
                            distance * distance + Math.pow(camera.horizontalOffset, 2)
                                            - 2 * distance * camera.horizontalOffset * Math.cos(horizontalAngle));
            double c = Math.asin(camera.horizontalOffset * Math.sin(horizontalAngle) / f);
            double b = Math.PI - horizontalAngle - c;
            angle = (Math.PI / 2 - b) - camera.hAngle;
        }
    }
    
    /**
     * Finds the horizontal angle to a specific pixel on the camera
     * @param x the pixel
     * @return the angle
     */
    public double getXAngle(double x){
        double HF = (camera.xPixels / 2) / Math.tan(camera.hFOV / 2);
        double cx = (camera.xPixels / 2) - 0.5;
        return Math.atan((cx - x) / HF);
    }
    
    /**
     * Finds the vertical angle to a specific pixel on the camera
     * @param y the pixel
     * @return the angle
     */
    public double getYAngle(double y){
        double VF = (camera.yPixels / 2) / Math.tan(camera.vFOV / 2);
        double cy = (camera.yPixels / 2) - 0.5;
        return Math.atan((cy - y) / VF);
    }
    
    /**
     * Returns the most recent distance calculated to the target
     * @return the most recent distance calculated to the specified target or -1 if the target is not found.
     */
    public double getLastDistance(){
        return visionTarget.estimationIsGood(distance, angle, visionObjects) ? distance : -1;
    }
    
    /**
     * Returns the most recent horizontal angle calculated to the target
     * @return the most recent horizontal angle calculated to the specified target or 180 if the target is not found.
     */
    public double getLastAngle(){
        return visionTarget.estimationIsGood(distance, angle, visionObjects) ? angle : 180;
    }
    
    /**
     * Tells whether or not the specified vision target can be seen
     * @return whether or not the specified vision target can be seen
     */
    public boolean seesTarget(){
        return visionTarget.estimationIsGood(distance, angle, visionObjects) && distance != -1;
    }
}