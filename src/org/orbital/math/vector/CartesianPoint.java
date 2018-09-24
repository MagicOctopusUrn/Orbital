package org.orbital.math.vector;

public class CartesianPoint extends Point {
	public final double x, y, z;
	
	public CartesianPoint(String label, double x, double y, double z) {
		super(label, 0.0);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CartesianPoint(" + label + ") [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}
