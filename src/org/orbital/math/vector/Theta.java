package org.orbital.math.vector;

public class Theta {
	private final String label;
	
	private double angle;

	public Theta(String label, double angle) {
		this.label = label;
		this.angle = angle;
	}

	public Theta(double angle) {
		this.label = "";
		this.angle = angle;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	public void getAngle(double angle) {
		this.angle = angle;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[Theta_" + label + "=" + angle + " degrees]";
	}
}
