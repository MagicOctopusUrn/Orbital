package org.orbital.orbit.transfer;

import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianTransform;
import org.orbital.math.vector.CartesianVector;
import org.orbital.math.vector.LagrangianVector;
import org.orbital.orbit.Orbit;
import org.orbital.util.Planets;

public class CoaxialTransfer extends Transfer {
	public static void main(String[] args) {
		Particle earth = Planets.EARTH_NULL;
		
		Particle satellite = new Particle("Spacecraft", new Mass("Spacecraft", 2000), 
				CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR, 0.0, 1.0e9);
		
		double startApogee = 20000.0 - earth.getRadiusParticle();
		double startPerigee = 10000.0 - earth.getRadiusParticle();
		double trueAnomalyStart = 150.0;		
		double endApogee = 20000.0 - earth.getRadiusParticle();
		double endPerigee = 0.0;
		double trueAnomalyEnd = 0.0;
		
		Orbit start = new Orbit(earth, satellite.getNullClone(), startPerigee, startApogee, trueAnomalyStart);

		System.out.println("Start\n" + start);
		
		Orbit end = new Orbit(earth, satellite.getNullClone(), endPerigee, endApogee, trueAnomalyEnd);

		System.out.println("End\n" + end);
		
		CoaxialTransfer ct = new CoaxialTransfer(start, end);
		
		System.out.println(ct);		
	}
	
	protected final Orbit transfer;
	
	public CoaxialTransfer(Orbit start, Orbit end) {
		super(start, end);
		this.transfer = calculateTransferOrbit();
	}
	
	private Orbit calculateTransferOrbit() {
		double u = this.getBodyNull().gravitationalParameter;
		double startAnomalyCos = Math.cos(this.start.calculateTrueAnomaly()
				* CartesianVector.DEGREE_CONVERSION);
		double endAnomalyCos = Math.cos(this.end.calculateTrueAnomaly()
				* CartesianVector.DEGREE_CONVERSION);
		double rStart = this.start.calculateDistance();
		double rEnd = this.end.calculateDistance();
		System.out.println(rStart + ":" + rEnd);
		double e = (rEnd - rStart) 
				/ (rEnd * endAnomalyCos - rStart * startAnomalyCos);
		double h = Math.sqrt(u * rStart * rEnd) * 
				Math.sqrt((endAnomalyCos - startAnomalyCos)
				/ (rEnd * endAnomalyCos - rStart * startAnomalyCos));
		System.out.println("? h=" + h + ",e=" + e);
		
		Particle satelliteNull = this.getSatelliteNull();
		
		return new Orbit(this.getBodyNull(), satelliteNull, h, e, 0.0, 
				0.0, 0.0, this.start.calculateTrueAnomaly() - 180.0);
	}

	@Override
	protected double calculateDeltaV() {
		double startV = this.start.getSatellite().v.getMagnitude();
		double transferV = this.transfer.getSatellite().v.getMagnitude();
		return Math.sqrt(Math.pow(startV, 2.0) + Math.pow(transferV, 2.0)
				- 2 * startV * transferV 
				* Math.cos((transfer.calculateFlightPathAngle() 
						- start.calculateFlightPathAngle())
						* CartesianVector.DEGREE_CONVERSION));
	}

	public double calculateOrientationDeltaV() {
		double angle = Math.atan((this.transfer.calculateRadialVelocity() - this.start.calculateRadialVelocity()) /
				(this.transfer.calculatePerpendicularVelocity() - this.start.calculatePerpendicularVelocity()));
		angle *= CartesianVector.RADIAN_CONVERSION;
		if (angle < 0.0) { // TODO Remove when implementing quadrants?
			angle += 180.0;
		}
		return angle ;
	}
	
	/**
	 * @return the transferOrbit
	 */
	public Orbit getTransferOrbit() {
		return transfer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CoaxialTransfer [calculateDeltaV()=" + calculateDeltaV() + 
				", calculateOrientationDeltaV()=" + calculateOrientationDeltaV() 
				+ "\ngetStart()=" + getStart() 
				+ "\n getEnd()=" + getEnd()
				+ "\n getTransferOrbit()=" + getTransferOrbit() + "]";
	}
}
