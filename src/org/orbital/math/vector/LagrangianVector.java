package org.orbital.math.vector;

import org.orbital.orbit.Orbit;

public class LagrangianVector {
	private final static String G_LABEL = "G", G_PRIME_LABEL = "G'", 
			F_LABEL = "F", F_PRIME_LABEL = "F'";
	
	public final Vector g, f, gPrime, fPrime;
	
	public LagrangianVector (double fMag, double gMag, double fPrimeMag, double gPrimeMag) {
		this.f = new Vector(F_LABEL, fMag);
		this.g = new Vector(G_LABEL, gMag);
		this.fPrime = new Vector(F_PRIME_LABEL, fPrimeMag);
		this.gPrime = new Vector(G_PRIME_LABEL, gPrimeMag);
	}
	
	public LagrangianVector(Orbit o, double deltaTime) {
		double u = o.getBody().gravitationalParameter;
		double a = 1.0 / o.calculateSemimajorAxis();
		double ua = o.calculateUniversalAnomaly(deltaTime);
		double r0 = o.getSatellite().r.getMagnitude();
		double z = a * Math.pow(ua, 2.0);
		double cz = Orbit.calculateStumpffC(z), sz = Orbit.calculateStumpffS(z);
		double fMag = 1.0 - (Math.pow(ua, 2.0) / r0) * cz;
		double gMag = deltaTime - (1.0 / Math.sqrt(u)) * Math.pow(ua, 3.0) * sz;
		
		CartesianVector rPrime = o.getSatellite().r.scale(F_LABEL, fMag)
				.add(o.getSatellite().v.scale(G_LABEL, gMag));
		
		double r = rPrime.getMagnitude();
		
		double fPrimeMag = (Math.sqrt(u) / (r * r0)) * (a * Math.pow(ua, 3.0) * sz - ua);
		
		double gPrimeMag = 1.0 - (Math.pow(ua, 2.0) / r) * cz;
		
		this.f = new Vector(F_LABEL, fMag);
		this.g = new Vector(G_LABEL, gMag);
		this.fPrime = new Vector(F_PRIME_LABEL, fPrimeMag);
		this.gPrime = new Vector(G_PRIME_LABEL, gPrimeMag);
	}

	/**
	 * @return the g
	 */
	public Vector getG() {
		return g;
	}

	/**
	 * @return the f
	 */
	public Vector getF() {
		return f;
	}

	/**
	 * @return the gPrime
	 */
	public Vector getGPrime() {
		return gPrime;
	}

	/**
	 * @return the fPrime
	 */
	public Vector getFPrime() {
		return fPrime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LagrangianVector [g=" + g + ", f=" + f + ", gPrime=" + gPrime
				+ ", fPrime=" + fPrime + "]";
	}
}
