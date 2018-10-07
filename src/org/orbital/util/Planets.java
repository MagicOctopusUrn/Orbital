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

	public static final Particle EARTH_2000 = new Particle("Earth", M_EARTH, R_2000_EARTH, V_2000_EARTH, CartesianVector.NULL_VECTOR, 6378, 1e9);

	public static final Particle EARTH_NULL = new Particle("Earth", M_EARTH, CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR, 6378, 1e9);
	
	/**
	 * Basic jupiter definition in terms of a 2000 ephemeris state vectors.
	 */
	private static final CartesianVector R_2000_JUPITER = new CartesianVector("R_Jupiter",  5.989091595026654E+08,  4.391225931434094E+08, -1.523254615467653E+07);
	
	private static final CartesianVector V_2000_JUPITER = new CartesianVector("V_Jupiter", -7.901937631606453E+00, 1.116317697592017E+01,  1.306729060953327E-01);
	
	private static final Mass M_JUPITER = new Mass("M_Jupiter", 1898.13e24);

	public static final Particle JUPITER_2000 = new Particle("Jupiter", M_JUPITER, R_2000_JUPITER, V_2000_JUPITER, CartesianVector.NULL_VECTOR);


	/**
	 * Basic null elements / state vectors for a solar-frame of reference.
	 */
	private static final Mass M_SUN = new Mass("M_Sun", 1.989e30);
	
	private static final Particle SUN_NULL = new Particle("Sun", M_SUN, CartesianVector.NULL_VECTOR, 
			CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR);
	
	public static void main(String[] args) {
		System.out.println(EARTH_NULL);
		
		Orbit SUN_EARTH = new Orbit(SUN_NULL, EARTH_2000);
		Orbit JUPITER_EARTH = new Orbit(JUPITER_2000, EARTH_2000);
		Orbit SUN_JUPITER =  new Orbit(SUN_NULL, JUPITER_2000);
		
		double yearInSecondsEarth = SUN_EARTH.calculatePeriod();
		System.out.println(yearInSecondsEarth / 24 / 60 / 60);
		System.out.println(SUN_EARTH);
		for (int d = 0; d < (yearInSecondsEarth / 24 / 60 / 60); d++) {
			Particle earthPrime = SUN_EARTH.getSatellite().scaleLagrangian(SUN_EARTH.calculateLagrangian(d * 24 * 60 * 60));
			
			System.out.println("S_E\t" + d + " d\t" + earthPrime.r.toSmallString() + "\t" + 
					earthPrime.v.getMagnitude() + "km/s (" + earthPrime.v.toSmallString() + ")");
		}
		
		System.out.println("============= - JUPITER - =============");
		
		double yearInSecondsJupiter = SUN_JUPITER.calculatePeriod();
		System.out.println(yearInSecondsJupiter / 24 / 60 / 60);
		System.out.println(SUN_JUPITER);
		for (int d = 0; d < (yearInSecondsJupiter / 24 / 60 / 60); d++) {
			Particle jupiterPrime = SUN_JUPITER.getSatellite().scaleLagrangian(SUN_JUPITER.calculateLagrangian(d * 24 * 60 * 60));
			
			System.out.println("S_E\t" + d + " d\t" + jupiterPrime.r.toSmallString() + "\t" + 
					jupiterPrime.v.getMagnitude() + "km/s (" + jupiterPrime.v.toSmallString() + ")");
		}
	}
}
