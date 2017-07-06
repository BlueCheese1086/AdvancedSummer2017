package org.usfirst.frc.team1086.CameraCalculator;

import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

public class Sighting {
    int x, y;
    double centerX, centerY;
    int height, width;
    double area;
    double solidity;
    double aspectRatio;
    double relativeAspectRatio;
    MatOfPoint m;
    
    /**
     * Creates a sighting object from a contour/MatOfPoint
     * @param m the contour to create it from
     */
    public Sighting(MatOfPoint m){
        this.m = m;
        height = Imgproc.boundingRect(m).height;
        width = Imgproc.boundingRect(m).width;
        x = Imgproc.boundingRect(m).x;
        y = Imgproc.boundingRect(m).y;
        area = Imgproc.contourArea(m);
        solidity = area / (width * height);
        aspectRatio = width / height;
        centerX = x + width / 2;
        centerY = y + height / 2;
    }
    /**
     * returns a string representation of the sighting
     * @return a string representation of the sighting
     */
    @Override public String toString(){
    	return "(" + x + ", " + y + ") to (" + (x + width) + ", " + (y + height) + ")";
    }

}