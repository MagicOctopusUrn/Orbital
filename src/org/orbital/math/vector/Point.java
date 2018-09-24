package org.orbital.math.vector;

public class Point {
	protected final String label;
	
	protected final double scalar;

	public Point(String label, double d) {
		super();
		this.label = label;
		this.scalar = d;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the scalar
	 */
	public double getScalar() {
		return scalar;
	}
}
