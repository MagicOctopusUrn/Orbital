package org.orbital.math.vector;

import java.text.DecimalFormat;

public class CartesianVector extends Vector {
	private final static double RADIAN_CONVERSION = 180.0 / Math.PI;
	
	public static final CartesianVector NULL_VECTOR = new CartesianVector("Unit_Null", 0.0, 0.0, 0.0);
	
	public static void main(String[] args) {
		CartesianVector a = new CartesianVector("a", 1, 6, 18);
		CartesianVector b = new CartesianVector("a", 42, -69, 98);
		System.out.println(a.dotProduct(b));
		System.out.println(a.magnitude);
		System.out.println(b.magnitude);
		System.out.println(a.deltaTheta(b).getAngle());
		System.out.println(a.projAtoB(b));
		System.out.println(a.projBtoA(b));
	}
	
	private final static String X_LABEL = "X", Y_LABEL = "Y", Z_LABEL = "Z";
	
	public final Vector x, y, z;
	
	public final Theta thetaX, thetaY, thetaZ;
	
	public CartesianVector(String label, double xMag, double yMag, double zMag) {
		super(label, Math.sqrt(xMag * xMag + yMag * yMag + zMag * zMag));
		this.x = new Vector(CartesianVector.X_LABEL, xMag);
		this.y = new Vector(CartesianVector.Y_LABEL, yMag);
		this.z = new Vector(CartesianVector.Z_LABEL, zMag);
		this.thetaX = new Theta(CartesianVector.X_LABEL, Math.acos(xMag/this.magnitude) * RADIAN_CONVERSION);
		this.thetaY = new Theta(CartesianVector.Y_LABEL, Math.acos(yMag/this.magnitude) * RADIAN_CONVERSION);
		this.thetaZ = new Theta(CartesianVector.Z_LABEL, Math.acos(zMag/this.magnitude) * RADIAN_CONVERSION);
	}
	
	public double dotProduct(CartesianVector vector) {
		return this.x.getMagnitude() * vector.x.getMagnitude() +
			this.y.getMagnitude() * vector.y.getMagnitude() +
			this.z.getMagnitude() * vector.z.getMagnitude();
	}
	
	public Theta deltaTheta(CartesianVector vector) {
		return new Theta(Math.acos(this.dotProduct(vector) 
				/ (this.magnitude * vector.magnitude)) 
				* RADIAN_CONVERSION);
	}
	
	public double projAtoB(CartesianVector vector) {
		return this.dotProduct(vector) / this.magnitude;
	}
	
	public double projBtoA(CartesianVector vector) {
		return this.dotProduct(vector) / vector.magnitude;
	}
	
	public CartesianVector crossProduct(CartesianVector vector) {
		double xMag = this.y.getMagnitude() * vector.z.getMagnitude() - this.z.getMagnitude() * vector.y.getMagnitude();
		double yMag = -1 * (this.x.getMagnitude() * vector.z.getMagnitude() - this.z.getMagnitude() * vector.x.getMagnitude());
		double zMag = this.x.getMagnitude() * vector.y.getMagnitude() - this.y.getMagnitude() * vector.x.getMagnitude();
		return new CartesianVector(this.label + " x (" + vector.label + ")", xMag, yMag, zMag);
	}
	
	public CartesianVector scale(String scalarLabel, double scalar) {
		return new CartesianVector(scalarLabel + " * (" + this.label + ")", scalar * this.x.magnitude,
				scalar * this.y.magnitude, scalar * this.z.magnitude);
	}
	
	public CartesianVector add(CartesianVector vector) {
		return new CartesianVector(this.label + " + (" + vector.label + ")",
				this.x.magnitude + vector.x.magnitude,
				this.y.magnitude + vector.y.magnitude,
				this.z.magnitude + vector.z.magnitude);
	}
	
	public CartesianVector subtract(CartesianVector vector) {
		return new CartesianVector(this.label + " - (" + vector.label + ")",
				this.x.magnitude - vector.x.magnitude,
				this.y.magnitude - vector.y.magnitude,
				this.z.magnitude - vector.z.magnitude);
	}
	
	public void setMagnitudeX(double magnitude) {
		this.x.setMagnitude(magnitude);
	}
	
	public void setMagnitudeY(double magnitude) {
		this.y.setMagnitude(magnitude);
	}
	
	public void setMagnitudeZ(double magnitude) {
		this.z.setMagnitude(magnitude);
	}
	
	public void setMagnitude(double x, double y, double z) {
		this.setMagnitudeX(x);
		this.setMagnitudeY(y);
		this.setMagnitudeZ(z);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CartesianVector(" + label + ") [x=" + x + ", y=" + y + ", z=" + z
				+ ", thetaX=" + thetaX + ", thetaY=" + thetaY + ", thetaZ="
				+ thetaZ + ", magnitude=" + magnitude + "]";
	}
	
	public String toSmallString() {
		DecimalFormat df = new DecimalFormat("#.#####");
		return "(" + df.format(x.getMagnitude()) + "," 
				+ df.format(y.getMagnitude()) + "," 
				+ df.format(z.getMagnitude()) + ")";
	}
}
