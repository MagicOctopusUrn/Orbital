package org.orbital.orbit;

import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;
import org.orbital.util.Planets;

// TODO Possibly extend to algorithm to determine optimal hohmann transfer between orbits.
// Dependant on what algorithms already exist (there may be some).
public class HohmannTransfer {
	public static void main(String[] args) {
		double startPerigee = 480.0;
		double startApogee = 800.0;
		double endPerigee = 16000.0;
		double endApogee = 16001.0;
		
		Particle earth = Planets.EARTH_NULL;
		Particle spacecraft = new Particle("Spacecraft", new Mass("Spacecraft", 2000), 
				CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR, 0.0, 1.0e9);
		
		Orbit startOrbit = new Orbit(earth, spacecraft, startPerigee, startApogee);
		
		System.out.println(startOrbit);
		
		Orbit endOrbit = new Orbit(earth, spacecraft, endPerigee, endApogee);
		
		System.out.println(endOrbit);
		
		HohmannTransfer ht = new HohmannTransfer(startOrbit, endOrbit);
		
		System.out.println(ht);
	}
	
	private final Particle body;
	
	private final Particle satellite;

	private final Orbit start;
	
	private final Orbit end;
	
	private final Orbit perigeeDelta;
	
	private final Orbit apogeeDelta;
	
	public HohmannTransfer(Orbit start, Orbit end) {
		this.start = start;
		this.body = start.body;
		this.satellite = start.satellite;
		this.end = end;
		this.perigeeDelta = calculatePerigeeDelta();
		this.apogeeDelta = calculateApogeeDelta();
	}

	private Orbit calculatePerigeeDelta() {
		if (this.start.calculateApogeeFromSurface() == this.end.calculateApogeeFromSurface()) {
			return this.start;
		} else {
			return new Orbit(this.body, this.satellite, 
					this.start.calculatePerigeeFromSurface(), 
					this.end.calculateApogeeFromSurface());
		}
	}

	private Orbit calculateApogeeDelta() {
		if (this.start.calculatePerigeeFromSurface() == this.end.calculatePerigeeFromSurface()) {
			return this.end;
		} else {
			return new Orbit(this.body, this.satellite, 
					this.start.calculateApogeeFromSurface(), 
					this.end.calculatePerigeeFromSurface());
		}
	}
	
	private double calculateDeltaVPerigeeStartPerigee() {
		double a = this.start.calculatePerigee();
		double b = this.perigeeDelta.calculateApogee();
		double h1 = this.start.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		double h2 = this.perigeeDelta.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		double h3 = this.end.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		System.out.println(a + "\t" + b + "\t" + h1 + "\t" + h2 + "\t" + h3);
		return Math.abs((h1 / a) - (h2 / a));
	}
	
	private double calculateDeltaVPerigeeStartApogee() {
		double a = this.start.calculatePerigee();
		double b = this.perigeeDelta.calculateApogee();
		double h1 = this.start.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		double h2 = this.perigeeDelta.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		double h3 = this.end.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		System.out.println(a + "\t" + b + "\t" + h1 + "\t" + h2 + "\t" + h3);
		return Math.abs((h3 / b) - (h2 / b));
	}
	
	private double calculateDeltaVPerigeeStartTotal() {
		return calculateDeltaVPerigeeStartPerigee() + calculateDeltaVPerigeeStartApogee();
	}
	
	private double calculateDeltaVApogeeStartTotal() {
		return calculateDeltaVApogeeStartPerigee() + calculateDeltaVApogeeStartApogee();
	}

	private double calculateDeltaVApogeeStartPerigee() {
		double a = this.start.calculateApogee();
		double b = this.apogeeDelta.calculateApogee();
		double h1 = this.start.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		double h2 = this.apogeeDelta.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		double h3 = this.end.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		System.out.println(a + "\t" + b + "\t" + h1 + "\t" + h2 + "\t" + h3);
		return Math.abs((h2 / a) - (h1 / a));
	}

	private double calculateDeltaVApogeeStartApogee() {
		double a = this.start.calculateApogee();
		double b = this.apogeeDelta.calculateApogee();
		double h1 = this.start.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		double h2 = this.apogeeDelta.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		double h3 = this.end.calculateAngularVector().getMagnitude() / this.satellite.getScale();
		System.out.println(a + "\t" + b + "\t" + h1 + "\t" + h2 + "\t" + h3);
		return Math.abs((h3 / b) - (h2 / b));
	}

	/**
	 * @return the body
	 */
	public Particle getBody() {
		return body;
	}

	/**
	 * @return the satellite
	 */
	public Particle getSatellite() {
		return satellite;
	}

	/**
	 * @return the start
	 */
	public Orbit getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Orbit getEnd() {
		return end;
	}

	/**
	 * @return the perigeeDelta
	 */
	public Orbit getPerigeeDelta() {
		return perigeeDelta;
	}

	/**
	 * @return the apogeeDelta
	 */
	public Orbit getApogeeDelta() {
		return apogeeDelta;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HohmannTransfer [body=" + body + "\nsatellite=" + satellite
				+ ", start=" + start + "\nend=" + end + "\nperigeeDelta="
				+ perigeeDelta + "\napogeeDelta=" + apogeeDelta
				+ "\ncalculateDeltaVPerigeeStartPerigee()=" + calculateDeltaVPerigeeStartPerigee()
				+ ",calculateDeltaVPerigeeStartApogee()=" + calculateDeltaVPerigeeStartApogee()
				+ ",calculateDeltaVPerigeeStartTotal()=" + calculateDeltaVPerigeeStartTotal()
				+ "\ncalculateDeltaVApogeeStartPerigee()=" + calculateDeltaVApogeeStartPerigee()
				+ ",calculateDeltaVApogeeStartApogee()=" + calculateDeltaVApogeeStartApogee()
				+ ",calculateDeltaVApogeeStartTotal()=" + calculateDeltaVApogeeStartTotal() + "]";
	}
}
