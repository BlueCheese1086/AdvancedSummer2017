package org.usfirst.frc.team1086.CameraCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class Sighting {
    int x, y;
    public double centerX, centerY;
    public int height, width;
    public double area;
    public double solidity;
    public double aspectRatio;
    public double relativeAspectRatio;
    public int pieces = 1;
    public OptionalDouble angle, distance, rotation, rawV, rawH = rawV = rotation = distance = angle = OptionalDouble.empty();
    List<Point> points;
    
    /**
     * Creates a sighting object from a contour/MatOfPoint
     * @param m the contour to create it from
     */
    public Sighting(MatOfPoint m){
    	points = new ArrayList<>(m.toList());
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
     * Combines this sighting with another sighting
     * @param sighting the sighting to combine with this one
     */
    public void addSighting(Sighting sighting){
        pieces += sighting.pieces;
        points.addAll(new ArrayList<>(sighting.points));
        x = Math.min(x, sighting.x);
        y = Math.min(y, sighting.y);
        width = Math.max(x + width, sighting.x + sighting.width) - x;
        height = Math.max(y + height, sighting.y + sighting.height) - y;
        centerX = x + width / 2;
        centerY = y + height / 2;
        area += sighting.area;
        solidity = area / width * height;
        aspectRatio = width / height;
        rawH = rawV = rotation = distance = angle = OptionalDouble.empty();
    }
    
    /**
     * Calculates the distance to another sighting
     * @param sighting the sighting to return the distance to
     * @return the distance to the given sighting
     */
    public double distanceTo(Sighting sighting){
        double minDistance = Double.MAX_VALUE;
        for(int i = 0; i < points.size(); i++){
            Point p = points.get(i);
            Point p1 = points.get((i + 1) % points.size());
            for(int j = 0; j < sighting.points.size(); j++){
                Point p2 = sighting.points.get(j);
                Point p3 = sighting.points.get((j + 1) % sighting.points.size());
                Line l1 = new Line(p.x, p.y, p1.x, p1.y);
                Line l2 = new Line(p2.x, p2.y, p3.x, p3.y);
                minDistance = Math.min(minDistance, l1.ptSegDistSq(p2.x, p2.y));
                minDistance = Math.min(minDistance, l2.ptSegDistSq(p.x, p.y));
            }
        }
        return Math.sqrt(minDistance);
    }
    
    /**
     * returns a string representation of the sighting
     * @return a string representation of the sighting
     */
    @Override public String toString(){
    	return "(" + x + ", " + y + ") to (" + (x + width) + ", " + (y + height) + ")";
    }
}
class Line {
	double x1, x2, y1, y2;
	public Line(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	public double ptSegDistSq(double x, double y) {
		  double l2 = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
		  if (l2 == 0) return (x - x1) * (x - x1) + (y - y1) * (y - y1);
		  double t = ((x - x1) * (x2 - x1) + (y - y1) * (y2 - y1)) / l2;
		  t = Math.max(0, Math.min(1, t));
		  double nX = x1 + t * (x2 - x1);
		  double nY = y1 + t * (y2 - y1);
		  return (x - nX) * (x - nX) + (y - nY) * (y - nY);
	}
}