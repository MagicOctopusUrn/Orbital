package org.orbital.math.kinematics;

import java.util.ArrayList;
import java.util.List;

import org.orbital.math.vector.CartesianPoint;
import org.orbital.math.vector.CartesianVector;
import org.orbital.math.vector.LagrangianVector;

public class Particle {
	public static void main(String[] args) {
		Mass m = new Mass("m", 1.0);
		CartesianVector r = new CartesianVector("r", -5368, -1784, 3691);
		CartesianVector v = new CartesianVector("v", 90, 125, 170);
		CartesianVector a = new CartesianVector("a", 0, 0, 0);
		Particle p = new Particle("P1", m, r, v, a);
		System.out.println("R=\t" + p.r);
		System.out.println("V=\t" + p.v);
		System.out.println("A=\t" + p.a);
		System.out.println("U_t=\t" + p.unitTangent);
		System.out.println("U_b=\t" + p.unitBinormal);
		System.out.println("U_n=\t" + p.unitNormal);
		System.out.println("A_n=\t" + p.aNormal);
		System.out.println("rho=\t" + p.radiusCenter);
		System.out.println("C=\t" + p.coordinatesCenter);
		
		System.out.println(p);
	}
	
	public final String label;
	
	public final Mass m;
	
	public final CartesianVector r;
	
	public final CartesianVector v;
	
	public final CartesianVector a;
	
	public final CartesianVector unitTangent;
	
	public final CartesianVector unitBinormal;
	
	public final CartesianVector unitNormal;
	
	public final CartesianPoint coordinatesCenter;
	
	public final List<Force> forces;
	
	public final double aNormal;
	
	public final double radiusCenter;
	
	public final double gravitationalParameter;
	
	public final double radiusParticle;
	
	public final double ascension;
	
	public final double declination;
	
	// Constructor for point masses.
	public Particle (String label, Mass m, CartesianVector r, 
			CartesianVector v, CartesianVector a) {
		this.label = label;
		
		this.r = r;
		this.v = v;
		this.a = a;
		
		this.unitTangent = new CartesianVector("U_t", v.x.getMagnitude() / v.getMagnitude(),
				v.y.getMagnitude() / v.getMagnitude(),
				v.z.getMagnitude() / v.getMagnitude());
		
		CartesianVector va = this.v.crossProduct(a);
		this.unitBinormal = new CartesianVector("U_b", va.x.getMagnitude() / va.getMagnitude(),
				va.y.getMagnitude() / va.getMagnitude(),
				va.z.getMagnitude() / va.getMagnitude());
		
		this.unitNormal = this.unitBinormal.crossProduct(this.unitTangent);
	
		this.aNormal = this.a.dotProduct(this.unitNormal);
		
		this.radiusCenter = (this.v.getMagnitude() * this.v.getMagnitude()) / this.aNormal;
		
		CartesianVector centralVector = this.r.add(this.unitNormal.scale("rho", this.radiusCenter));
		
		this.coordinatesCenter = new CartesianPoint("C", centralVector.x.getMagnitude(),
				centralVector.y.getMagnitude(), centralVector.z.getMagnitude());
	
		this.m = m;
		
		this.forces = new ArrayList<Force>(0);
		
		this.gravitationalParameter = (GravitationalForce.GRAVITATIONAL_CONSTANT * this.m.getMagnitude()) / 1.0e9;
	
		this.radiusParticle = 0.0;
		
		double rl = r.x.getMagnitude() / r.getMagnitude();
		double rm = r.y.getMagnitude() / r.getMagnitude();
		double rn = r.z.getMagnitude() / r.getMagnitude();
		
		this.declination = Math.asin(rn) * (180.0 / Math.PI);
		
		if (rm > 0) {
			this.ascension = Math.acos(rl / Math.cos(Math.asin(rn))) * (180.0 / Math.PI);
		} else {
			this.ascension = 360.0 - Math.acos(rl / Math.cos(Math.asin(rn))) * (180.0 / Math.PI);
		}
	}
	
	public Particle (String label, Mass m, CartesianVector r, 
			CartesianVector v, CartesianVector a, double radiusParticle) {
		this.label = label;
		
		this.r = r;
		this.v = v;
		this.a = a;
		
		this.unitTangent = new CartesianVector("U_t", v.x.getMagnitude() / v.getMagnitude(),
				v.y.getMagnitude() / v.getMagnitude(),
				v.z.getMagnitude() / v.getMagnitude());
		
		CartesianVector va = this.v.crossProduct(a);
		this.unitBinormal = new CartesianVector("U_b", va.x.getMagnitude() / va.getMagnitude(),
				va.y.getMagnitude() / va.getMagnitude(),
				va.z.getMagnitude() / va.getMagnitude());
		
		this.unitNormal = this.unitBinormal.crossProduct(this.unitTangent);
	
		this.aNormal = this.a.dotProduct(this.unitNormal);
		
		this.radiusCenter = (this.v.getMagnitude() * this.v.getMagnitude()) / this.aNormal;
		
		CartesianVector centralVector = this.r.add(this.unitNormal.scale("rho", this.radiusCenter));
		
		this.coordinatesCenter = new CartesianPoint("C", centralVector.x.getMagnitude(),
				centralVector.y.getMagnitude(), centralVector.z.getMagnitude());
	
		this.m = m;
		
		this.forces = new ArrayList<Force>(0);
		
		this.gravitationalParameter = (GravitationalForce.GRAVITATIONAL_CONSTANT * this.m.getMagnitude()) / 1.0e9;
	
		this.radiusParticle = radiusParticle;
		
		double rl = r.x.getMagnitude() / r.getMagnitude();
		double rm = r.y.getMagnitude() / r.getMagnitude();
		double rn = r.z.getMagnitude() / r.getMagnitude();
		
		this.declination = Math.asin(rn);
		
		if (rm > 0) {
			this.ascension = Math.acos(rl / Math.cos(this.declination));
		} else {
			this.ascension = 360.0 - Math.acos(rl / Math.cos(this.declination));
		}
	}

	/**
	 * @return the r
	 */
	public CartesianVector getR() {
		return r;
	}

	/**
	 * @return the v
	 */
	public CartesianVector getV() {
		return v;
	}

	/**
	 * @return the a
	 */
	public CartesianVector getA() {
		return a;
	}

	/**
	 * @return the unitTangent
	 */
	public CartesianVector getUnitTangent() {
		return unitTangent;
	}

	/**
	 * @return the unitBinormal
	 */
	public CartesianVector getUnitBinormal() {
		return unitBinormal;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the m
	 */
	public Mass getMass() {
		return m;
	}

	/**
	 * @return the unitNormal
	 */
	public CartesianVector getUnitNormal() {
		return unitNormal;
	}

	/**
	 * @return the coordinates
	 */
	public CartesianPoint getCoordinatesCenter() {
		return coordinatesCenter;
	}

	/**
	 * @return the aNormal
	 */
	public double getANormal() {
		return aNormal;
	}

	/**
	 * @return the radius
	 */
	public double getRadiusCenter() {
		return radiusCenter;
	}

	/**
	 * @return the forces
	 */
	public List<Force> getForces() {
		return forces;
	}
	
	public boolean addForce(Force f) {
		CartesianVector a = f.getA();
		if (this.a.x.getMagnitude() == this.a.y.getMagnitude() 
				&& this.a.y.getMagnitude() == this.a.z.getMagnitude() 
				&& this.a.z.getMagnitude() == 0) {
			this.a.setMagnitude(a.x.getMagnitude(), 
					a.y.getMagnitude(), a.z.getMagnitude());
		} else {
			CartesianVector aPrime = this.a.crossProduct(a);
			this.a.setMagnitude(aPrime.x.getMagnitude(), 
					aPrime.y.getMagnitude(), aPrime.z.getMagnitude());
		}
		return this.forces.add(f);
	}

	/**
	 * @return the aNormal
	 */
	public double getaNormal() {
		return aNormal;
	}

	/**
	 * @return the gravitationalParameter
	 */
	public double getGravitationalParameter() {
		return gravitationalParameter;
	}

	/**
	 * @return the radiusParticle
	 */
	public double getRadiusParticle() {
		return radiusParticle;
	}

	/**
	 * @return the ascension
	 */
	public double getAscension() {
		return ascension;
	}

	/**
	 * @return the declination
	 */
	public double getDeclination() {
		return declination;
	}
	
	public Particle scaleLagrangian(LagrangianVector v) {
		CartesianVector rPrime = this.r.scale(v.getF().getLabel(), v.getF().getMagnitude())
				.add(this.v.scale(v.getG().getLabel(), v.getG().getMagnitude()));
		CartesianVector vPrime = this.r.scale(v.getFPrime().getLabel(), v.getFPrime().getMagnitude())
				.add(this.v.scale(v.getGPrime().getLabel(), v.getGPrime().getMagnitude()));
		Particle p = new Particle(this.getLabel(), this.getMass(), rPrime, vPrime, this.a);
		return p;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Particle [label=" + label + ", m=" + m + ", r=" + r + ", v="
				+ v + ", a=" + a + ", unitTangent=" + unitTangent
				+ ", unitBinormal=" + unitBinormal + ", unitNormal="
				+ unitNormal + ", coordinatesCenter=" + coordinatesCenter
				+ ", forces=" + forces + ", aNormal=" + aNormal
				+ ", radiusCenter=" + radiusCenter
				+ ", gravitationalParameter=" + gravitationalParameter
				+ ", radiusParticle=" + radiusParticle + ", ascension="
				+ ascension + ", declination=" + declination + "]";
	}

	public double distance(Particle particle) {
		return Math.sqrt(Math.pow(particle.getR().x.getMagnitude() - this.getR().x.getMagnitude(), 2.0) + 
				Math.pow(particle.getR().y.getMagnitude() - this.getR().y.getMagnitude(), 2.0) + 
				Math.pow(particle.getR().z.getMagnitude() - this.getR().z.getMagnitude(), 2.0));
	}
	
	
}
