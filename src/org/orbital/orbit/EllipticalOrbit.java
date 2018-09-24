package org.orbital.orbit;

import org.orbital.math.kinematics.Force;
import org.orbital.math.kinematics.GravitationalForce;
import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;

@Deprecated
public class EllipticalOrbit {
	public static void main(String[] args) {
		Mass defaultMass = new Mass("m", 5.972e24);
		Mass defaultMass2 = new Mass("m2", 1000);
		
		CartesianVector r1 = new CartesianVector("R_1", 0, 0, 0);
		CartesianVector v1 = new CartesianVector("V_1", 10, 20, 30);
		CartesianVector a1initial = new CartesianVector("A_1", 0, 0, 0);
		
		Particle one = new Particle("Earth", defaultMass, r1, v1, a1initial, 6378);
		
		CartesianVector r2 = new CartesianVector("R_2", 3000, 0, 0);
		CartesianVector v2 = new CartesianVector("V_2", 10, 20, 30);
		CartesianVector a2initial = new CartesianVector("A_2", 0, 0, 0);
		
		Particle two = new Particle("Satellite", defaultMass2, r2, v2, a2initial, 0.1);
		
		Force gravityOneTwo = new GravitationalForce(one, two);
		
		System.out.println(gravityOneTwo.getA());
		
		Force gravityTwoOne = new GravitationalForce(two, one);
		
		System.out.println(gravityTwoOne.getA());
		
		EllipticalOrbit orbit = new EllipticalOrbit(one, two, 400, 4000);
		
		System.out.println(orbit);
	}
	
	private final double eccentricity;
	
	private final double angularMomentum;
	
	private final double perigee;
	
	private final double apogee;
	
	private final double perigeeV;
	
	private final double apogeeV;
	
	private final double semiMajorAxis;
	
	private final double trueAnomalyAveragedRadius;
	
	private final double trueAnomaly;
	
	private final double trueAnomalyV;
	
	private final int period;
	
	private final Particle body;
	
	private final Particle satellite;
	
	public EllipticalOrbit(Particle body, Particle satellite, double perigee, double apogee) {
		this.body = body;
		this.satellite = satellite;
		this.perigee = perigee + body.radiusParticle;
		this.apogee = apogee + body.radiusParticle;
		this.eccentricity = (this.apogee - this.perigee) / (this.apogee + this.perigee);
		this.angularMomentum = Math.sqrt(body.gravitationalParameter * this.perigee * (1.0 + this.eccentricity));
		this.perigeeV = this.angularMomentum / this.perigee;
		this.apogeeV = this.angularMomentum / this.apogee;
		this.semiMajorAxis = (this.perigee + this.apogee) / 2.0;
		this.trueAnomalyAveragedRadius = Math.sqrt(this.perigee * this.apogee);
		double tAnomaly = this.trueAnomalyAveragedRadius;
		tAnomaly /= Math.pow(this.angularMomentum, 2.0);
		tAnomaly *= body.gravitationalParameter;
		tAnomaly = Math.pow(tAnomaly, -1.0);
		tAnomaly = Math.acos((tAnomaly - 1) / this.eccentricity);
		this.trueAnomaly = (180.0 / Math.PI) * tAnomaly;
		this.trueAnomalyV = Math.sqrt(2.0 * (body.gravitationalParameter / this.trueAnomalyAveragedRadius - body.gravitationalParameter / (2.0 * this.semiMajorAxis)));
		this.period = (int)(((Math.PI * 2.0) / Math.sqrt(body.gravitationalParameter)) * Math.pow(this.semiMajorAxis, 1.5));
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
	 * @return the apogee
	 */
	public double getApogee() {
		return apogee;
	}

	/**
	 * @return the perigeeV
	 */
	public double getPerigeeV() {
		return perigeeV;
	}

	/**
	 * @return the apogeeV
	 */
	public double getApogeeV() {
		return apogeeV;
	}

	/**
	 * @return the semiMajorAxis
	 */
	public double getSemiMajorAxis() {
		return semiMajorAxis;
	}

	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
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
	 * @return the trueAnomalyAveragedRadius
	 */
	public double getTrueAnomalyAveragedRadius() {
		return trueAnomalyAveragedRadius;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EllipticalOrbit [eccentricity=" + eccentricity
				+ ", angularMomentum=" + angularMomentum + ", perigee="
				+ perigee + ", apogee=" + apogee + ", perigeeV=" + perigeeV
				+ ", apogeeV=" + apogeeV + ", semiMajorAxis=" + semiMajorAxis
				+ ", trueAnomalyAveragedRadius=" + trueAnomalyAveragedRadius
				+ ", trueAnomaly=" + trueAnomaly + ", trueAnomalyV="
				+ trueAnomalyV + ", period=" + period + ", body=" + body
				+ ", satellite=" + satellite + "]";
	}
}
