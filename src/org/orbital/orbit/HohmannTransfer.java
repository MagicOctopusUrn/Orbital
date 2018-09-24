package org.orbital.orbit;

// TODO Possibly extend to algorithm to determine optimal hohmann transfer between orbits.
// Dependant on what algorithms already exist (there may be some).
public class HohmannTransfer {
	private final Orbit start;
	
	private final Orbit end;
	
	public HohmannTransfer(Orbit start, Orbit end) {
		this.start = start;
		this.end = end;
	}
}
