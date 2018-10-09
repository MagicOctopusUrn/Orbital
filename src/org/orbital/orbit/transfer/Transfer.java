package org.orbital.orbit.transfer;

import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianVector;
import org.orbital.orbit.Orbit;

public abstract class Transfer {
	protected final Orbit start;
	
	protected final Orbit end;
	
	public Transfer(Orbit start, Orbit end) {
		this.start = start;
		this.end = end;
	}
	
	protected abstract double calculateDeltaV();
	
	public Orbit getStart() {
		return this.start;
	}
	
	public Orbit getEnd() {
		return this.end;
	}
	
	public Particle getBodyStart() {
		return this.start.getBody();
	}
	
	public Particle getBodyEnd() {
		return this.end.getBody();
	}
	
	public Particle getSatelliteStart() {
		return this.start.getSatellite();
	}
	
	public Particle getSatelliteEnd() {
		return this.end.getSatellite();
	}
	
	public Particle getBodyNull() {
		return this.start.getBody().getNullClone();
	}
	
	public Particle getSatelliteNull() {
		return this.start.getSatellite().getNullClone();
	}
}
