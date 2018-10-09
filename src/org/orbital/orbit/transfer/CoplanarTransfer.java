package org.orbital.orbit.transfer;

import org.orbital.orbit.Orbit;

public class CoplanarTransfer extends Transfer {
	public CoplanarTransfer(Orbit start, Orbit end) {
		super(start, end);
		
	}

	@Override
	protected double calculateDeltaV() {
		// TODO Auto-generated method stub
		return 0;
	}
}
