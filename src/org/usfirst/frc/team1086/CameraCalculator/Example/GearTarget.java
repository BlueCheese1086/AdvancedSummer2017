package org.usfirst.frc.team1086.CameraCalculator.Example;

import org.usfirst.frc.team1086.CameraCalculator.Sighting;
import org.usfirst.frc.team1086.CameraCalculator.VisionTarget;
import java.util.ArrayList;

public class GearTarget extends VisionTarget {
    public GearTarget(){
        super("Gear Lift");
    }
    @Override public ArrayList<Sighting> validateSightings(ArrayList<Sighting> polys){
        return polys;
    }
    @Override public boolean estimationIsGood(double distance, double angle, ArrayList<Sighting> sightings){
        return Math.abs(120 - distance) < 120 && Math.abs(angle) < 35 && sightings.size() == 2;
    }
}