package org.orbital.orbit;

import org.orbital.math.kinematics.Force;
import org.orbital.math.kinematics.GravitationalForce;
import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;

@Deprecated
public class ParabolicOrbit extends Orbit {
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
		
		for (int i = 0; i < 360; i++) {
			ParabolicOrbit orbit = new ParabolicOrbit(one, two, 7000 - 6378, i);
		
			System.out.println(orbit);
		}
	}
	
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
