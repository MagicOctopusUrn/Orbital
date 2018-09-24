package org.orbital.math.vector;

public class Vector {
	protected final String label;
	
	protected double magnitude;
	
	public Vector(String label, double magnitude) {
		this.label = label;
		this.magnitude = magnitude;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}
	
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + label + "=" + magnitude + " units]";
	}
}
