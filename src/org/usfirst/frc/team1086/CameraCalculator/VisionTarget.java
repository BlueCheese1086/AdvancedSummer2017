package org.usfirst.frc.team1086.CameraCalculator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The vision target class.
 * 
 * Each type of vision target should inherit from VisionTarget and be its own class. 
 * Rather than create multiple instances with the same name, *always* access each vision target by checking the static HashMap
 * 
 * @author Jack
 */
public abstract class VisionTarget {
    public double height;
    public double aspectRatio;
    public static HashMap<String, VisionTarget> targets = new HashMap();
    
    /**
     * Instantiates the vision target with a name
     * @param s the name of the vision target.
     */
    public VisionTarget(String s){
        VisionTarget.targets.put(s, this);
    }
    
    /**
     * This method should validate the sightings. 
     * This abstract method should take the list of possible sightings and determine whether or not they are a valid sighting of the vision target
     * @param polys the sightings to evaluate.
     * @return the valid sightings
     */
    public abstract ArrayList<Sighting> validateSightings(ArrayList<Sighting> polys);
    
    /**
     * This method should determine whether or not the estimation provided by the camera calculator is good.
     * @param distance the estimated distance
     * @param angle the estimated angle
     * @param sightings the valid sightings
     * @return whether or not the estimation given is a good/usable estimation
     */
    public abstract boolean estimationIsGood(double distance, double angle, ArrayList<Sighting> sightings);
    
    /**
     * Creates a camera calculator to be used by a given camera for this vision target
     * @param c the camera
     * @return the camera calculator
     */
    public CameraCalculator getCalculator(Camera c){
        return new CameraCalculator(c, this);
    }
}