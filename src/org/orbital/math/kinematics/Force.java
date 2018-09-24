package org.orbital.math.kinematics;

import org.orbital.math.vector.CartesianVector;

public class Force {
	private final CartesianVector a;
	
	private final double magnitude;
	
	public Force (CartesianVector a, double magnitude) {
		this.a = a;
		this.magnitude = magnitude;
	}

	/**
	 * @return the a
	 */
	public CartesianVector getA() {
		return a;
	}

	/**
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}
}
