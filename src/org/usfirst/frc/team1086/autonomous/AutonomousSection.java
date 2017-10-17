package org.usfirst.frc.team1086.autonomous;

/**
 * A section of code to be run during autonomous.
 * @author Jack
 */
public abstract class AutonomousSection {
    protected long startTime;
    public long duration;
    boolean isStarted = false;
    
    /**
     * Creates the autonomous section.
     */
    public AutonomousSection(){}
    
    /**
     * Determines whether the section has exceeded its given runtime.
     * A duration of -1 indicates that there is no upper limit on the time to run.
     * Returns false if the section has not yet been started.
     * @return Whether or not the section is timed out.
     */
    public boolean isTimedOut(){
        return duration != -1 && isStarted && System.currentTimeMillis() - startTime > duration;
    }
    
    /**
     * Starts the section. This can be overriden, but in order to ensure that the section is properly
     * started, call super.start(). This is called when the section is started. This can be used to 
     * start PID controllers and set certain values for the section.
     */
    public void start(){
        isStarted = true;
        startTime = System.currentTimeMillis();
    }
    
    /**
     * This is called ~50 times per second to update the autonomous section.
     * This should be overriden. Put things in here that need constant update
     * such as getting values from a PID and using them. Also should be used for 
     * logging.
     */
    public abstract void update();
    
    /**
     * This is called when the autonomous section ends. This can be overriden.
     * Put things in here such as stopping a PID controller.
     */
    public abstract void finish();
    
    /**
     * Determines whether or not the section has finished. This can be overriden.
     * This can be used to end the section early if a PID has reached a value or
     * if the robot has driven a certain distance. 
     * @return whether or not the section is finished.
     */
    public boolean isFinished(){
        return isTimedOut();
    }
}