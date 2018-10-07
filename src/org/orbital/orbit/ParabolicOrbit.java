package org.orbital.orbit;

import org.orbital.math.kinematics.Force;
import org.orbital.math.kinematics.GravitationalForce;
import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;

@Deprecated
public class ParabolicOrbit extends Orbit {	
	private final double eccentricity;
	
	private final double angularMomentum;
	
	private final double perigee;
	
	private final double perigeeV;
	
	private final double trueAnomaly;
	
	private final double trueAnomalyV;
	
	private final double escapeV;
	
	public ParabolicOrbit(Particle body, Particle satellite, double perigee, double trueAnomaly) {
		super (body, satellite);
		this.eccentricity = 1.0;
		this.trueAnomaly = trueAnomaly * Math.PI / 180.0 - Math.PI;
		this.perigee = perigee + body.getRadiusParticle();
		this.angularMomentum = Math.sqrt(this.perigee * 2.0 * body.gravitationalParameter); 
		double r = Math.pow(this.angularMomentum, 2.0) / (body.gravitationalParameter * (1.0 + Math.cos(this.trueAnomaly)));
		double r0 = Math.pow(this.angularMomentum, 2.0) / (body.gravitationalParameter * (1.0 + Math.cos(0.0)));
		this.escapeV = Math.sqrt((2.0 * body.gravitationalParameter) / r0);
		this.perigeeV = Math.sqrt((2.0 * body.gravitationalParameter) / r0);
		this.trueAnomalyV = Math.sqrt((2.0 * body.gravitationalParameter) / r);
	}

	/**
	 * @return the eccentricity
	 */
	public double getEccentricity() {
		return eccentricity;
	}

	/**
	 * @return the angularMomentum
	 */
	public double getAngularMomentum() {
		return angularMomentum;
	}

	/**
	 * @return the perigee
	 */
	public double getPerigee() {
		return perigee;
	}

	/**
	 * @return the perigeeV
	 */
	public double getPerigeeV() {
		return perigeeV;
	}

	/**
	 * @return the trueAnomaly
	 */
	public double getTrueAnomaly() {
		return trueAnomaly;
	}

	/**
	 * @return the trueAnomalyV
	 */
	public double getTrueAnomalyV() {
		return trueAnomalyV;
	}

	/**
	 * @return the escapeV
	 */
	public double getEscapeV() {
		return escapeV;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParabolicOrbit [eccentricity=" + eccentricity
				+ ", angularMomentum=" + angularMomentum + ", perigee="
				+ perigee + ", perigeeV=" + perigeeV + ", trueAnomaly="
				+ trueAnomaly + ", trueAnomalyV=" + trueAnomalyV + ", escapeV="
				+ escapeV + ", body=" + super.body + ", satellite=" + super.satellite + "]";
	}
}
