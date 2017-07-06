package org.usfirst.frc.team1086.robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDInput implements PIDSource {
	DoubleGetter d;
	public PIDInput(DoubleGetter d){
		this.d = d;
	}
	@Override public void setPIDSourceType(PIDSourceType pidSource){}
	@Override public PIDSourceType getPIDSourceType(){
		return PIDSourceType.kDisplacement;
	}
	@Override public double pidGet(){
		return d.get();
	}	
}
