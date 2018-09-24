package org.orbital.util;

import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;
import org.orbital.orbit.Orbit;

public class Planets {
	/**
	 * AU to KM conversion factors.
	 */
	public static final double AU_SCALAR = 1.496e8;
	
	/**
	 * Basic earth definition in terms of a 2000 ephemeris state vectors.
	 */
	private static final CartesianVector R_2000_EARTH = new CartesianVector("R_Earth", -2.521092863852298E+07,  1.449279195712076E+08, -6.164888475164771E+02);
	
	private static final CartesianVector V_2000_EARTH = new CartesianVector("V_Earth", -2.983983333368269E+01, -5.207633918704476E+00,  6.169062303484907E-05);
	
	private static final Mass M_EARTH = new Mass("M_Earth", 5.97219e24);

	public static final Particle EARTH_2000 = new Particle("Earth", M_EARTH, R_2000_EARTH, V_2000_EARTH, CartesianVector.NULL_VECTOR);

	/**
	 * Basic null elements / state vectors for a solar-frame of reference.
	 */
	private static final Mass M_SUN = new Mass("M_Sun", 1.989e30);
	
	private static final Particle SUN_NULL = new Particle("Sun", M_SUN, CartesianVector.NULL_VECTOR, 
			CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR);
	
	private static final Orbit EARTH_SUN = new Orbit(SUN_NULL, EARTH_2000);
	
	public static void main(String[] args) {
		double yearInSeconds = EARTH_SUN.calculatePeriod();
		System.out.println(yearInSeconds / 24 / 60 / 60);
		System.out.println(EARTH_SUN);
	}
}
