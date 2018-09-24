package org.orbital.relativity;

import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;

public class RelativeFrame {
	
	public static void main(String[] args) {
		Mass defaultMass = new Mass("m", 1.0);
		
		CartesianVector ro = new CartesianVector("R_o", 100, 200, 300);
		CartesianVector vo = new CartesianVector("V_o", -50, 30, -10);
		CartesianVector ao = new CartesianVector("A_o", -15, 40, 25);
		Particle po = new Particle("P_o", defaultMass, ro, vo, ao);
	
		CartesianVector rp = new CartesianVector("R_p", 300, -100, 150);
		CartesianVector vp = new CartesianVector("V_p", 70, 25, -20);
		CartesianVector ap = new CartesianVector("A_p", 7.5, -8.5, 6);
		Particle p = new Particle("P", defaultMass, rp, vp, ap);
	
		System.out.println("P_R=\t" + p.r);
		System.out.println("P_V=\t" + p.v);
		System.out.println("P_A=\t" + p.a);
		System.out.println("P_U_t=\t" + p.unitTangent);
		System.out.println("P_U_b=\t" + p.unitBinormal);
		System.out.println("P_U_n=\t" + p.unitNormal);
		System.out.println("P_A_n=\t" + p.aNormal);
		System.out.println("P_rho=\t" + p.radiusCenter);
		System.out.println("P_C=\t" + p.coordinatesCenter);

		System.out.println("P_O_R=\t" + p.r);
		System.out.println("P_O_V=\t" + p.v);
		System.out.println("P_O_A=\t" + p.a);
		System.out.println("P_O_U_t=\t" + p.unitTangent);
		System.out.println("P_O_U_b=\t" + p.unitBinormal);
		System.out.println("P_O_U_n=\t" + p.unitNormal);
		System.out.println("P_O_A_n=\t" + p.aNormal);
		System.out.println("P_O_rho=\t" + p.radiusCenter);
		System.out.println("P_O_C=\t" + p.coordinatesCenter);
		
		CartesianVector unitVectorX = new CartesianVector("U_x", 0.5571, -0.06331, -0.8280);
		
		CartesianVector unitVectorY = new CartesianVector("U_y", 0.7428, 0.4839, 0.4627);
		
		CartesianVector unitVectorZ = new CartesianVector("U_z", 0.3714, -0.8728, 0.3166);
		
		CartesianVector vAngular = new CartesianVector("omega_v", 1.0, -0.4, 0.6);

		CartesianVector aAngular = new CartesianVector("omega_a", -1.0, 0.3, -0.4);
		
		RelativeFrame frame = new RelativeFrame(po, p, unitVectorX, unitVectorY, unitVectorZ, vAngular, aAngular);
		
		System.out.println(frame.relativeR);

		System.out.println(frame.relativeV);

		System.out.println(frame.relativeUnitV);

		System.out.println(frame.relativeA);

		System.out.println(frame.relativeUnitA);
	}
	
	private final Particle o;
	
	private final Particle p;
	
	private final CartesianVector unitVectorX;
	
	private final CartesianVector unitVectorY;
	
	private final CartesianVector unitVectorZ;
	
	private final CartesianVector vAngular;
	
	private final CartesianVector aAngular;
	
	private final CartesianVector relativeR;
	
	private final CartesianVector relativeV, relativeUnitV;
	
	private final CartesianVector relativeA, relativeUnitA;
	
	public RelativeFrame(Particle o, Particle p, CartesianVector unitVectorX,
			CartesianVector unitVectorY, CartesianVector unitVectorZ,
			CartesianVector vAngular, CartesianVector aAngular) {
		this.o = o;
		this.p = p;
		this.vAngular = vAngular;
		this.aAngular = aAngular;
		this.unitVectorX = unitVectorX;
		this.unitVectorY = unitVectorY;
		this.unitVectorZ = unitVectorZ;
		this.relativeR = this.p.r.subtract(this.o.r);
		
		this.relativeV = this.p.v.subtract(this.o.v).subtract(vAngular.crossProduct(this.relativeR));
		CartesianVector vrx = unitVectorX.scale("U_x", this.relativeV.x.getMagnitude());
		//System.out.println(this.relativeV.x.getMagnitude() + "*" + unitVectorX + "=" + vrx);
		CartesianVector vry = unitVectorY.scale("U_y", this.relativeV.y.getMagnitude());
		//System.out.println(this.relativeV.y.getMagnitude() + "*" + unitVectorY + "=" + vry);
		CartesianVector vrz = unitVectorZ.scale("U_z", this.relativeV.z.getMagnitude());
		//System.out.println(this.relativeV.z.getMagnitude() + "*" + unitVectorZ + "=" + vrz);
		
		this.relativeUnitV = vrx.add(vry).add(vrz);
		
		CartesianVector ar = this.p.a.subtract(this.o.a);
		//System.out.println("A\t" + this.p.a);
		//System.out.println("A_o\t" + this.o.a);
		//System.out.println("R_rel\t" + this.relativeR);
		CartesianVector ar3 = this.aAngular.crossProduct(this.relativeR);
		//System.out.println("A_3\t" + ar3);
		CartesianVector ar2 = this.vAngular.crossProduct(this.vAngular.crossProduct(this.relativeR));
		//System.out.println("A_2\t" + ar2);
		CartesianVector ar1 = this.vAngular.crossProduct(this.relativeV).scale("2",2.0);
		//System.out.println("v_r\t" + this.relativeV);
		//System.out.println("omega_a\t" + this.vAngular);
		//System.out.println("A_1\t" + ar1);
		
		this.relativeA = ar.subtract(ar3).subtract(ar2).subtract(ar1);
		
		CartesianVector arx = unitVectorX.scale("A_r_x", this.relativeA.x.getMagnitude());
		CartesianVector ary = unitVectorY.scale("A_r_y", this.relativeA.y.getMagnitude());
		CartesianVector arz = unitVectorZ.scale("A_r_z", this.relativeA.z.getMagnitude());
		
		this.relativeUnitA = arx.add(ary).add(arz);
	}

	/**
	 * @return the o
	 */
	public Particle getO() {
		return o;
	}

	/**
	 * @return the p
	 */
	public Particle getP() {
		return p;
	}

	/**
	 * @return the unitVectorX
	 */
	public CartesianVector getUnitVectorX() {
		return unitVectorX;
	}

	/**
	 * @return the unitVectorY
	 */
	public CartesianVector getUnitVectorY() {
		return unitVectorY;
	}

	/**
	 * @return the unitVectorZ
	 */
	public CartesianVector getUnitVectorZ() {
		return unitVectorZ;
	}

	/**
	 * @return the vAngular
	 */
	public CartesianVector getvAngular() {
		return vAngular;
	}

	/**
	 * @return the aAngular
	 */
	public CartesianVector getaAngular() {
		return aAngular;
	}

	/**
	 * @return the relativeR
	 */
	public CartesianVector getRelativeR() {
		return relativeR;
	}

	/**
	 * @return the relativeV
	 */
	public CartesianVector getRelativeV() {
		return relativeV;
	}
}
