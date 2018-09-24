package org.orbital.relativity;

import java.util.ArrayList;
import java.util.List;

import org.orbital.math.kinematics.Force;
import org.orbital.math.kinematics.GravitationalForce;
import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;

//Page 62, chapter 2.2.
public class InertialFrame extends RelativeFrame {
	public InertialFrame(Particle o, Particle p, CartesianVector unitVectorX,
			CartesianVector unitVectorY, CartesianVector unitVectorZ,
			CartesianVector vAngular, CartesianVector aAngular) {
		super(o, p, unitVectorX, unitVectorY, unitVectorZ, vAngular, aAngular);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Mass defaultMass = new Mass("m", 10e26);
		
		CartesianVector r1 = new CartesianVector("R_1", 0, 0, 0);
		CartesianVector v1 = new CartesianVector("V_1", 10, 20, 30);
		CartesianVector a1initial = new CartesianVector("A_1", 0, 0, 0);
		
		Particle one = new Particle("P_1", defaultMass, r1, v1, a1initial);
		
		CartesianVector r2 = new CartesianVector("R_2", 0, 0, 0);
		CartesianVector v2 = new CartesianVector("V_2", 10, 20, 30);
		CartesianVector a2initial = new CartesianVector("A_2", 0, 0, 0);
		
		Particle two = new Particle("P_2", defaultMass, r2, v2, a2initial);
		
		Force gravityOneTwo = new GravitationalForce(one, two);
		one.addForce(gravityOneTwo);
		two.addForce(gravityOneTwo);
	}
}
