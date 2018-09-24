package org.orbital.orbit;

import org.orbital.math.kinematics.Force;
import org.orbital.math.kinematics.GravitationalForce;
import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;

@Deprecated
public class HyperbolicOrbit {
	public HyperbolicOrbit(Particle one, Particle two, double perigee) {
		// TODO Auto-generated constructor stub
	}

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
		
		HyperbolicOrbit orbit = new HyperbolicOrbit(one, two, 400);
		
		System.out.println(orbit);
	}
}
