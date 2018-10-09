package org.orbital.impulse;

import org.orbital.orbit.Orbit;
import org.orbital.orbit.transfer.HohmannTransfer;

public class PhasingManeuver {
	private double time;
	
	private HohmannTransfer transfer;
	
	private final Orbit startOrbit;
	
	private final Orbit endOrbit;
	
	public PhasingManeuver(double time, Orbit orbit) {
		this.time = time;
		this.startOrbit = orbit;
		this.endOrbit = calculateEndOrbit();
		this.transfer = new HohmannTransfer(this.startOrbit, this.endOrbit);
	}

	private Orbit calculateEndOrbit() {
		return null;
	}

	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * @return the transfer
	 */
	public HohmannTransfer getTransfer() {
		return transfer;
	}

	/**
	 * @param transfer the transfer to set
	 */
	public void setTransfer(HohmannTransfer transfer) {
		this.transfer = transfer;
	}

	/**
	 * @return the startOrbit
	 */
	public Orbit getStartOrbit() {
		return startOrbit;
	}

	/**
	 * @return the endOrbit
	 */
	public Orbit getEndOrbit() {
		return endOrbit;
	}
}
