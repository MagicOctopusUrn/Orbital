package org.orbital.math.kinematics;

import org.orbital.math.vector.CartesianVector;

public class GravitationalForce extends Force {
	public static void main(String[] args) {
		Mass defaultMass = new Mass("m", 10e26);
		Mass defaultMass2 = new Mass("m2", 10e26);
		
		CartesianVector r1 = new CartesianVector("R_1", 0, 0, 0);
		CartesianVector v1 = new CartesianVector("V_1", 10, 20, 30);
		CartesianVector a1initial = new CartesianVector("A_1", 0, 0, 0);
		
		Particle one = new Particle("P_1", defaultMass, r1, v1, a1initial);
		
		CartesianVector r2 = new CartesianVector("R_2", 3000, 0, 0);
		CartesianVector v2 = new CartesianVector("V_2", 10, 20, 30);
		CartesianVector a2initial = new CartesianVector("A_2", 0, 0, 0);
		
		Particle two = new Particle("P_2", defaultMass2, r2, v2, a2initial);
		
		Force gravityOneTwo = new GravitationalForce(one, two);
		
		System.out.println(gravityOneTwo.getA());
		
		Force gravityTwoOne = new GravitationalForce(two, one);
		
		System.out.println(gravityTwoOne.getA());
	}
	
	public static final double GRAVITATIONAL_CONSTANT = 6.6742e-11;
	
	private final Particle a;
	
	private final Particle b;
	
	public GravitationalForce(Particle a, Particle b) {
		super(getVector(a, b), getMagnitude(a, b));
		this.a = a;
		this.b = b;
	}

	private static CartesianVector getVector(Particle a, Particle b) {
		CartesianVector ra = a.r;
		CartesianVector rb = b.r;
		System.out.println(ra);
		System.out.println(rb);
		System.out.println(ra.subtract(rb));
		double distance = a.distance(b);
		System.out.println(distance);
		double bMass = b.m.getMagnitude();
		System.out.println(bMass);
		return ra.subtract(rb).scale("r^-3", 1.0/Math.pow(distance,3)).scale("m_2", bMass).scale("G", GravitationalForce.GRAVITATIONAL_CONSTANT);
		
		//return ra.scale("m_1", aMass).add(rb.scale("m_2", bMass)).scale("(m_1+m_2)^-1", 1.0 / (aMass + bMass));
	}

	public static double getMagnitude(Particle a, Particle b) {
		double mass = a.getMass().getMagnitude() * b.getMass().getMagnitude();
		double distance = a.distance(b);
		return (GravitationalForce.GRAVITATIONAL_CONSTANT * mass) / distance;
	}
}
