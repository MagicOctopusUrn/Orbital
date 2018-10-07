package org.orbital.impulse;

import org.orbital.math.vector.CartesianVector;

public class Maneuver {
	private final CartesianVector r;
	
	private final CartesianVector v;
	
	public Maneuver(CartesianVector r, CartesianVector v) {
		this.r = r;
		this.v = v;
	}
}
