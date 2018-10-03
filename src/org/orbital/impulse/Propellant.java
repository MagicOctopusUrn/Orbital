package org.orbital.impulse;

import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;

// TODO
public class Propellant {
	private final double specificImpulse;
	
	private final Particle spacecraft;
	
	private final Particle propellant;
	
	private final Particle body;
	
	public Propellant(Particle body, Particle spacecraft, 
			Particle propellant, double specificImpulse) {
		this.body = body;
		this.spacecraft = spacecraft;
		this.propellant = propellant;
		this.specificImpulse = specificImpulse;
	}
	
	public double calcualteDeltaV (double time) {
		return -1.0;
	}
}
