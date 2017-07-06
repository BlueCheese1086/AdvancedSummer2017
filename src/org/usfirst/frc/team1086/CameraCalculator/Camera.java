package org.usfirst.frc.team1086.CameraCalculator;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import org.opencv.core.Mat;

public class Camera {
    public double vFOV;
    public double hFOV;
    public double xPixels;
    public double yPixels;
    public double horizontalOffset, verticalOffset, depthOffset;
    public double hAngle, vAngle;
    public ArrayList<Pipeline> pipes = new ArrayList();
    public HashMap<VisionTarget, CameraCalculator> calculators = new HashMap();
    
    /**
     * Instantiates the camera object
     * @param vFOV the vertical FOV on the camera
     * @param hFOV the horizontal FOV on the camera
     * @param xPixels the number of pixels in the x direction
     * @param yPixels the number of pixels in the y direction
     * @param horizontalOffset the number of inches from the center of the robot the front bottom center of the lens of the camera is (in the horizontal direction)
     * @param verticalOffset the number of inches from the ground the center of the camera lens is.
     * @param depthOffset the number of inches how deep in to the robot the camera is. Currently unused.
     * @param hAngle the horizontal angle of the camera
     * @param vAngle the vertical angle of the camera
     */
    public Camera(double vFOV, double hFOV, double xPixels, double yPixels, double horizontalOffset, double verticalOffset, double depthOffset, double hAngle, double vAngle){
        this.vFOV = vFOV;
        this.hFOV = hFOV;
        this.xPixels = xPixels;
        this.yPixels = yPixels;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
        this.depthOffset = depthOffset;
        this.hAngle = hAngle;
        this.vAngle = vAngle;
    }
    
    /**
     * Tells whether the camera can see a specified vision target
     * @param vt the vision target to check
     * @return whether or not the camera can see it
     */
    public boolean seesTarget(VisionTarget vt){
        if(!calculators.containsKey(vt))
            return false;
        return calculators.get(vt).seesTarget();
    }
    
    /**
     * Tells the approximate distance to the specified vision target
     * @param vt the specified vision target
     * @return the distance
     */
    public double getDistance(VisionTarget vt){
        if(!seesTarget(vt))
            return -1;
        else return calculators.get(vt).getLastDistance();
    }
    
    /**
     * Tells the approximate horizontal angle to the specified vision target
     * @param vt the specified vision target
     * @return the angle
     */
    public double getAngle(VisionTarget vt){
        if(!seesTarget(vt))
            return -1;
        else return calculators.get(vt).getLastAngle();
    }
    
    /**
     * Adds a pipeline that processes the images the camera takes
     * @param p the pipeline to add.
     */
    public void addPipeline(Pipeline p){
        pipes.add(p);
    }
    
    /**
     * Adds a vision target for the camera to send the data to
     * @param target the vision target to add
     */
    public void addVisionTarget(VisionTarget target){
        calculators.put(target, target.getCalculator(this));
    }
    
    /**
     * Configures the horizontal and vertical angles of the camera
     * @param target the target used to configure the robot. The center of the robot must be perfectly aligned with the given target
     * @param distance the distance between the robot and the target
     */
    public void configure(VisionTarget target, double distance){
        hAngle = CameraConfig.getXAngle(calculators.get(target).rawX, distance, horizontalOffset);
    	vAngle = CameraConfig.getYAngle(calculators.get(target).rawV, target.height - verticalOffset, distance);
    }
    
    /**
     * Initializes the camera with a VisionSource
     * @param source the source of the camera
     * @param name the name of the camera
     */
    public void initializeCamera(VideoSource source, String name){
        CvSink sink = new CvSink(name);
        sink.setSource(source);
        sink.setEnabled(true);
        new Thread(() -> {
            try {
                CameraServer.getInstance().startAutomaticCapture(source);
                Mat sourceMat = new Mat();
                while (!interrupted()) {
                    sink.grabFrame(sourceMat);
                    process(sourceMat);
                    sleep(50);
                }
            } catch (Exception e) {}
        }).start();
    }
    
    /**
     * Processes the frames that the camera processes
     * @param source the captured frame
     */
    private void process(Mat source){
        for(Pipeline pipe : pipes){
            ArrayList<Sighting> sightings = pipe.process(source);
            pipe.getSupportedTargets().stream().filter(target -> calculators.containsKey(target)).forEach(target -> {
                calculators.get(target).updateObjects((ArrayList<Sighting>) sightings.clone());
            });
        }
    }
}