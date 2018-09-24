package org.orbital.math.kinematics;

public class Mass {
	protected final String label;
	
	protected final double magnitude;

	public Mass(String label, double magnitude) {
		super();
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
}
