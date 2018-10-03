package org.orbital.orbit;

import org.orbital.math.kinematics.GravitationalForce;
import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;
import org.orbital.util.Planets;

// TODO Possibly extend to algorithm to determine optimal hohmann transfer between orbits.
// Dependant on what algorithms already exist (there may be some).
public class HohmannTransfer {
	public static void main(String[] args) {
		Particle earth = Planets.EARTH_NULL;
		Particle spacecraft = new Particle("Spacecraft", new Mass("Spacecraft", 2000), 
				CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR);
		
		double startPerigee = 480;
		double startApogee = 800;
		double endApogee = 16000;
		double endPerigee = endApogee;
		
		Orbit startOrbit = new Orbit(earth, spacecraft, startPerigee, startApogee, 0.0, 0.0);
		
		Orbit endOrbit = new Orbit(earth, spacecraft, endPerigee, endApogee, 0.0, 0.0);
		
		HohmannTransfer ht = new HohmannTransfer(startOrbit, endOrbit);
		
		double apseLineStart; //?
		
		double apseLineEnd;   //?
	}

	private final Orbit start;
	
	private final Orbit end;
	
	private final Orbit apogeeDelta;
	
	private final Orbit perigeeDelta;
	
	public HohmannTransfer(Orbit start, Orbit end) {
		this.start = start;
		this.end = end;
	}
}
