package org.usfirst.frc.team1086.autonomous;
import java.util.ArrayList;

public class AutonomousManager {
    ArrayList<AutonomousSection> sections = new ArrayList();
    int currectionSection = 0;
    boolean started = false;
    
    /**
     * Goes to specified section number and starts that section. Also finishes the current section.
     * @param sectionNumber the section to go to.
     */
    public void goToSection(int sectionNumber){
        if(sectionNumber >= sections.size() || sectionNumber < 0)
            throw new IndexOutOfBoundsException("sectionNumber must be non-negative and less than the number of autonomous sections.");
        sections.get(currectionSection).finish();
        currectionSection = sectionNumber;
        sections.get(currectionSection).start();
    }
    
    /**
     * Goes to the next section.
     * The same as goToSection(getSection() + 1)
     */
    public void next(){
        goToSection(currectionSection + 1);
    }
    
    /**
     * Repeats the current section.
     * The same as goToSection(getSection())
     */
    public void repeat(){
        goToSection(currectionSection);
    }
    
    /**
     * Goes to the previous section.
     * The same as goToSection(getSection() - 1)
     */
    public void previous(){
        goToSection(currectionSection - 1);
    }
    
    /**
     * Updates the current section and goes to the next section if the current is finished.
     */
    public void update(){
        if(started){
            sections.get(currectionSection).update();
            if(sections.get(currectionSection).isFinished()){
                if(currectionSection + 1 < sections.size())
                    next();
                else 
                    finish();
            }
        }
    }
    
    /**
     * Gets the current section.
     * @return the current section
     */
    public int getSection(){
        return currectionSection;
    }
    
    /**
     * Adds a new section.
     * @param section the section to add.
     */
    public void addSection(AutonomousSection section){
        sections.add(section);
    }
    
    /**
     * Starts the autonomous routine.
     */
    public void start(){
        started = true;
        sections.get(currectionSection = 0).start();
    }
    
    /**
     * Returns whether or not the autonomous routine is running.
     * @return whether or not the autonomous routine is running
     */
    public boolean isRunning(){
        return started;
    }
    
    /**
     * Ends the routine and finishes the current section.
     */
    public void finish(){
        sections.get(currectionSection).finish();
        started = false;
    }
}