package org.orbital.orbit;

import org.orbital.math.kinematics.GravitationalForce;
import org.orbital.math.kinematics.Mass;
import org.orbital.math.kinematics.Particle;
import org.orbital.math.vector.CartesianTransform;
import org.orbital.math.vector.CartesianVector;
import org.orbital.math.vector.LagrangianVector;

public class Orbit {
	public static final double KEPLER_TOLERANCE = Math.pow(10, -8);
	
	public static final int KEPLER_MAX_ITERATIONS = 1000;
	
	private final static double RADIANS_TO_DEGREES = 180.0 / Math.PI;
	
	private final static double DEGREES_TO_RADIANS = Math.PI / 180.0;
	
	public static void main(String[] args) {
		Mass defaultMass = new Mass("m", 5.972e24);
		Mass defaultMass2 = new Mass("m2", 1000);
		
		Particle body = new Particle("Earth", defaultMass, CartesianVector.NULL_VECTOR, 
				CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR, 6378, 1e9);
		
		Particle satelliteNull = new Particle("Satellite", defaultMass2, CartesianVector.NULL_VECTOR, 
				CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR);
		
		double angularMomentum = 80000.0;
		double eccentricity = 1.4;
		double rightAscension = 40.0;
		double inclination = 30.0;
		double perigeeArgument = 60.0;
		double trueAnomaly = 30.0;
		
		Orbit o = new Orbit(body, satelliteNull, angularMomentum, eccentricity, 
				rightAscension, inclination, perigeeArgument, trueAnomaly);
		
		System.out.println("TEST ORBIT WHAT THE FUCK:\t" + o);
		
		Orbit test = new Orbit(body, satelliteNull, 400, 4000, 150.0);
		System.out.println(test);
		
		/*
		CartesianVector r2 = new CartesianVector("R_2", 1600, 5310, 3800);
		CartesianVector v2 = new CartesianVector("V_2", -7.350, 0.4600, 2.470);
		CartesianVector a2 = new CartesianVector("A_2", 0, 0, 0);
		Particle satellite = new Particle("Satellite", defaultMass2, r2, v2, a2);
		
		satellite.addForce(new GravitationalForce(body, satellite));
	
		Orbit o = new Orbit(body, satellite);
		
		//System.out.println(satellite.getR());
		
		//System.out.println(satellite.getV());
		
		//System.out.println(satellite.getA());
		
		//System.out.println(o);
		
		//System.out.println(cua());
		
		System.out.println(o);
		
		System.out.println(o.calculateUniversalAnomaly(3600.0));
		
		System.out.println(o.calculateLagrangian(3600.0));
		
		long startTime = System.currentTimeMillis();
		
		for (int seconds = 0; seconds < o.calculatePeriod(); seconds++) {
			Particle satellitePrime = o.satellite.scaleLagrangian(o.calculateLagrangian(seconds));
			
			System.out.println(seconds + "s\t" + satellitePrime.r.toSmallString() + "\t" + 
			   satellitePrime.v.getMagnitude() + "km/s (" + satellitePrime.v.toSmallString() + ")");
		}
		
		System.out.println("Execution took " + (System.currentTimeMillis() - startTime) + "ms for " + o.calculatePeriod() + " seconds of simulation.");
		*/
	}
	
	protected final Particle body;
	
	protected final Particle satellite;
	
	public Orbit(Particle bodyNull, Particle satelliteNull, 
			double perigee, double apogee, double trueAnomaly) {
		//this(bodyNull, satelliteNull, calculateAngularMomentum(bodyNull, perigee, apogee), 
				//calculateEccentricity(bodyNull, perigee, apogee), 0.0, 0.0, 0.0, 0.0);
		this.body = bodyNull;
		
		double hp = calculateAngularMomentumAtPerigee(bodyNull, perigee, apogee);
		double eccentricity = calculateEccentricity(bodyNull, perigee, apogee);
		double perigeePrime = bodyNull.getRadiusParticle() + perigee;
		
		double scalarR = (Math.pow(hp, 2.0) / (body.gravitationalParameter)) /
				 (1.0 + (eccentricity * Math.cos(trueAnomaly * DEGREES_TO_RADIANS)));
		
		double rx = scalarR * Math.cos(trueAnomaly * DEGREES_TO_RADIANS);
		double ry = scalarR * Math.sin(trueAnomaly * DEGREES_TO_RADIANS);
		double rz = scalarR * 0.0;

		CartesianVector satelliteVectorRNull = new CartesianVector("R_null", rx, ry, rz);
		
		Particle satellite = new Particle("Satellite", satelliteNull.getMass(), 
				satelliteVectorRNull, CartesianVector.NULL_VECTOR, CartesianVector.NULL_VECTOR);
		
		GravitationalForce force = new GravitationalForce(this.body, satellite);

		double scalarV = ((body.gravitationalParameter) / hp);
		
		double vx = scalarV * -Math.sin(trueAnomaly * DEGREES_TO_RADIANS);
		double vy = scalarV * (eccentricity + Math.cos(trueAnomaly * DEGREES_TO_RADIANS));
		double vz = scalarV * 0.0;
		
		CartesianVector satelliteVectorVNull = new CartesianVector("V_null", vx, vy, vz);
		
		satellite = new Particle("Satellite", satelliteNull.getMass(), 
				satelliteVectorRNull, satelliteVectorVNull, CartesianVector.NULL_VECTOR);
		
		this.satellite = satellite;
		
		this.satellite.addForce(force);
	}
	
	private final static double calculateEccentricity(Particle body, double perigee, double apogee) {
		double apogeePrime = body.radiusParticle + apogee;
		double perigeePrime = body.radiusParticle + perigee;
		
		return (apogeePrime - perigeePrime) / (apogeePrime + perigeePrime);
	}
	
	private final static double calculateAngularMomentumAtPerigee(Particle body, double perigee, double apogee) {
		double e = calculateEccentricity(body, perigee, apogee);
		double g = body.gravitationalParameter;
		double perigeePrime = body.radiusParticle + perigee;
		if (0 < e && e < 1){
			return Math.sqrt(perigeePrime * (1 + e) * g);
		} else {
			return -1;
		}
	}
	
	public Orbit(Particle bodyNull, Particle satelliteNull, 
			double angularMomentum, double eccentricity,
			double rightAscension, double inclination, 
			double perigeeArgument, double trueAnomaly) {		
		this.body = bodyNull;
		
		CartesianTransform transform = new CartesianTransform(perigeeArgument, rightAscension, inclination);
		
		double scalarR = (Math.pow(angularMomentum, 2.0) / (body.gravitationalParameter)) /
				 (1.0 + (eccentricity * Math.cos(trueAnomaly * DEGREES_TO_RADIANS)));
		
		System.out.println(scalarR);

		double rx = scalarR * Math.cos(trueAnomaly * DEGREES_TO_RADIANS);
		double ry = scalarR * Math.sin(trueAnomaly * DEGREES_TO_RADIANS);
		double rz = scalarR * 0.0;
		
		CartesianVector satelliteVectorRNull = new CartesianVector("R_null", rx, ry, rz);
		
		double scalarV = ((body.gravitationalParameter) / angularMomentum);
		
		System.out.println(satelliteVectorRNull.toString());
		
		double vx = scalarV * -Math.sin(trueAnomaly * DEGREES_TO_RADIANS);
		double vy = scalarV * (eccentricity + Math.cos(trueAnomaly * DEGREES_TO_RADIANS));
		double vz = scalarV * 0.0;
		
		CartesianVector satelliteVectorVNull = new CartesianVector("V_null", vx, vy, vz);
		
		System.out.println(satelliteVectorVNull.toString());
		
		CartesianVector satelliteVectorRFinal = transform.scale(satelliteVectorRNull);
		
		CartesianVector satelliteVectorVFinal = transform.scale(satelliteVectorVNull);

		this.satellite = new Particle("Satellite", satelliteNull.getMass(), 
				satelliteVectorRFinal, satelliteVectorVFinal, CartesianVector.NULL_VECTOR);
		
		this.satellite.addForce(new GravitationalForce(this.body, this.satellite));
	}
	
	public void transform(CartesianTransform transform) {
		this.satellite.transform(transform);
	}
	
	/*
	public Orbit(Particle bodyNull, Particle satelliteNull, 
			double apogee, double perigee, double trueAnomaly,
			double rightAscension, double inclination, 
			double perigeeArgument) {		
		this.body = bodyNull;
		
		double eccentricity = (apogee - perigee) / (apogee + perigee);
		
		double meanAnomaly = Math.sqrt(this.body.gravitationalParameter / Math.pow(apogee, 3.0));

		double pi = Math.PI;
		
		meanAnomaly /= 360.0;
		
		meanAnomaly = 2.0*pi*(meanAnomaly-Math.floor(meanAnomaly));
		
		double eccentricAnomaly, F, K = Orbit.RADIANS_TO_DEGREES;
		
		if (eccentricity < 0.8) {
			eccentricAnomaly = meanAnomaly;
		} else {
			eccentricAnomaly = pi;
		}
		
		F = eccentricAnomaly - eccentricity * Math.sin(meanAnomaly) - meanAnomaly;
		
		int j = 0;
		while ((Math.abs(F) > Orbit.KEPLER_TOLERANCE) && j < Orbit.KEPLER_MAX_ITERATIONS) {
			eccentricAnomaly = eccentricAnomaly - F / (1.0 - eccentricity * Math.cos(eccentricAnomaly));
			F = eccentricAnomaly - eccentricity * Math.sin(eccentricAnomaly) - meanAnomaly;
			j++;
		}
		
		eccentricAnomaly = eccentricAnomaly / K;
		
		double E = eccentricAnomaly * K;
		
		F = Math.sqrt(1.0 - (eccentricity * eccentricity));
		
		double P = Math.atan2(F * Math.sin(E), Math.cos(E) - eccentricity) / K;
		
		double radius = apogee * (1.0 - eccentricity * Math.cos(eccentricAnomaly));
		
		double specificAngularMomentum = Math.sqrt((this.body.gravitationalParameter * apogee * F));

		double rx = Math.cos(perigeeArgument);
		double ry = Math.sin(perigeeArgument);
		double rz = 0.0;
		
		CartesianVector satelliteVectorRNull = new CartesianVector("R_null", rx, ry, rz);

		double scale = (Math.pow(specificAngularMomentum, 2.0) / body.gravitationalParameter) * 
				1 / (1 + eccentricity * Math.cos(perigeeArgument));
		
		satelliteVectorRNull = satelliteVectorRNull.scale("h**2/u * (1/1+ecos(theta))", scale); 
		
		System.out.println(satelliteVectorRNull.toString());
		
		CartesianVector satelliteVectorVNull = new CartesianVector("V_null", rx, ry, rz);
		
		
		
		CartesianVector satelliteVectorRFinal = new CartesianVector("R", 0.0, 0.0, 0.0);
		
		CartesianVector satelliteVectorVFinal = new CartesianVector("R", 0.0, 0.0, 0.0);
		
		this.satellite = new Particle("Satellite", satelliteNull.getMass(), 
				satelliteVectorRFinal, satelliteVectorVFinal, CartesianVector.NULL_VECTOR);
		
		this.satellite.addForce(new GravitationalForce(this.body, this.satellite));
	}
	*/
	
	public Orbit(Particle bodyNull, Particle satellite) {
		super();
		this.body = bodyNull;
		this.satellite = satellite;
	}
	
	public CartesianVector calculateAngularVector() {
		return this.satellite.r.crossProduct(this.satellite.v);
	}
	
	public CartesianVector calculateNodeLine() {
		CartesianVector k = new CartesianVector("K", 0.0, 0.0, 1.0);
		return k.crossProduct(this.calculateAngularVector());
	}
	
	public double calculateRightAscension() {
		CartesianVector n = calculateNodeLine();
		if (n.y.getMagnitude() >= 0)
			return Math.acos(n.x.getMagnitude() / n.getMagnitude()) * RADIANS_TO_DEGREES;
		else
			return 360 - Math.acos(n.x.getMagnitude() / n.getMagnitude()) * RADIANS_TO_DEGREES;
	}
	
	public double calculatePerigeeArgument() {
		CartesianVector n = this.calculateNodeLine();
		CartesianVector e = this.calculateEccentricityVector();
		if (e.z.getMagnitude() >= 0)
			return Math.acos(n.scale("1/n", 1.0 / n.getMagnitude()).dotProduct(
				e.scale("1/e", 1.0 / e.getMagnitude()))) * RADIANS_TO_DEGREES;
		else 
			return 360.0 - Math.acos(n.scale("1/n", 1.0 / n.getMagnitude()).dotProduct(
					e.scale("1/e", 1.0 / e.getMagnitude()))) * RADIANS_TO_DEGREES;
	}
	
	public double calculateTrueAnomaly() {
		CartesianVector e = this.calculateEccentricityVector();
		CartesianVector r = this.satellite.r;
		if (this.calculateRadialVelocity() >= 0)
			return Math.acos(e.scale("1/e", 1.0 / e.getMagnitude()).dotProduct(
				r.scale("1/r", 1.0 / r.getMagnitude()))) * RADIANS_TO_DEGREES;
		else 
			return 360.0 - Math.acos(e.scale("1/e", 1.0 / e.getMagnitude()).dotProduct(
					r.scale("1/r", 1.0 / r.getMagnitude()))) * RADIANS_TO_DEGREES;
	}
	
	public CartesianVector calculateEccentricityVector() {
		double r = this.satellite.r.getMagnitude();
		double u = this.body.gravitationalParameter;
		CartesianVector rPrime = this.satellite.r.scale("u/r", u / r);
		CartesianVector vPrime = this.satellite.v.crossProduct(this.calculateAngularVector());
		return vPrime.subtract(rPrime).scale("1/u", 1.0 / u);
	}
	
	public double calculateEccentricity() {
		return this.calculateEccentricityVector().getMagnitude();
	}
	
	public double calculateRadialVelocity() {
		double r = this.satellite.r.getMagnitude();
		return this.satellite.v.dotProduct(this.satellite.r) / r;
	}
	
	public double calculatePerpendicularVelocity() {
		return this.calculateAngularMomentum() / this.calculateDistance();
	}
	
	public double calculateInclination() {
		CartesianVector h = calculateAngularVector();
		return Math.acos(h.z.getMagnitude() / h.getMagnitude()) * RADIANS_TO_DEGREES;
	}
	
	public double calculatePerigee() {
		double h = this.calculateAngularVector().getMagnitude();
		double u = this.body.gravitationalParameter;
		double e = this.calculateEccentricity();
		return Math.pow(h, 2.0) / u * (1 / (1 + e * Math.cos(0)));
	}
	
	public double calculateApogee() {
		double h = this.calculateAngularVector().getMagnitude();
		double u = this.body.gravitationalParameter;
		double e = this.calculateEccentricity();
		return Math.pow(h, 2.0) / u * (1 / (1 + e * Math.cos(Math.PI)));
	}
	
	public double calculatePerigeeFromSurface() {
		return this.calculatePerigee() - this.body.radiusParticle;
	}
	
	public double calculateApogeeFromSurface() {
		return this.calculateApogee() - this.body.radiusParticle;
	}
	
	public double calculateSemimajorAxis() {
		return 0.5 * (this.calculatePerigee() + this.calculateApogee());
	}
	
	public double calculatePeriod() {
		return (2.0 * Math.PI * Math.pow(this.calculateSemimajorAxis(), 1.5)) / Math.sqrt(this.body.gravitationalParameter);
	}
	
	public double calculateFlightPathAngle() {
		return Math.atan(this.calculateRadialVelocity() 
				/ this.calculatePerpendicularVelocity()) * Orbit.RADIANS_TO_DEGREES;
	}
	
	public static double calculateStumpffC(double z) {
		if (z > 0) {
			return (1.0 - Math.cos(Math.sqrt(z))) / z;
		} else if (z < 0) {
			return (Math.cosh(Math.sqrt(-z)) - 1.0) / -z;
		} else {
			return 1.0 / 2.0;
		}
	}
	
	public static double calculateStumpffS(double z) {
		if (z > 0) {
			return (Math.sqrt(z) - Math.sin(Math.sqrt(z))) / Math.pow(Math.sqrt(z), 3.0);
		} else if (z < 0) {
			return (Math.sinh(Math.sqrt(-z)) - Math.sqrt(-z)) / Math.pow(Math.sqrt(-z), 3.0);
		} else {
			return 1.0 / 6.0;
		}
	}
	
	public LagrangianVector calculateLagrangian(double deltaTime) {
		return new LagrangianVector(this, deltaTime);
	}
	
	public double calculateAngularMomentum() {
		double rp = this.calculatePerigee();
		double ra = this.calculateApogee();
		double u = this.body.gravitationalParameter;
		return Math.sqrt(2.0 * u) * Math.sqrt((ra * rp) / (ra + rp));
	}
	
	public Orbit scaleOrbit(LagrangianVector vector) {
		return new Orbit(this.body, this.satellite.scaleLagrangian(vector));
	}
	
	public Orbit scaleOrbitInverse() {
		return new Orbit(this.body, 
			this.satellite.scaleLagrangian(
				this.calculateLagrangian(
					this.calculatePeriod() / 2.0)));
	}
	
	public double calculateUniversalAnomaly(double deltaTime) {
		double u = this.body.gravitationalParameter;
		double a = 1.0 / this.calculateSemimajorAxis();
		double ua = Math.sqrt(u) * Math.abs(a) * deltaTime;
		double vr0 = this.calculateRadialVelocity();
		double r0 = this.satellite.r.getMagnitude();
		double z = a * Math.pow(ua, 2.0);
		double sz, cz, f = 1.0, fPrime = 1.0, i = 0;
		while (Math.abs(f / fPrime) > Orbit.KEPLER_TOLERANCE && ++i < Orbit.KEPLER_MAX_ITERATIONS) {
			cz = calculateStumpffC(z);
			sz = calculateStumpffS(z);
			f = ((r0 * vr0) / Math.sqrt(u)) * Math.pow(ua, 2.0) * cz 
					+ (1.0 - a * r0) * Math.pow(ua, 3.0) * sz 
					+ r0 * ua - Math.sqrt(u) * deltaTime;
			fPrime = ((r0 * vr0) / Math.sqrt(u)) * ua 
					* (1.0 - a * Math.pow(ua, 2.0) * sz)
					+ (1.0 - a * r0) * Math.pow(ua, 2.0) * cz
					+ r0;
			
			//System.out.println("ua=" + ua + "\tz=" + z + "\tC(z)=" + cz + "\tS(z)=" + sz);
			
			ua = ua - f / fPrime;
			
			z = a * Math.pow(ua, 2.0);

			//System.out.println(f + "=f\t" + fPrime + "=f'\t" + f / fPrime + "=ratio");
			//System.out.println((Math.abs(f / fPrime) > Orbit.KEPLER_TOLERANCE) + "\t" + (i < Orbit.KEPLER_MAX_ITERATIONS));
		}
		return ua;
	}
	
	public double calculateDistance() {
		return body.distance(satellite);
	}

	public double calculateEscapeV() {
		double angularMomentumPerigee = Math.sqrt(this.calculatePerigee() * 2.0 * body.gravitationalParameter); 
		double r0 = Math.pow(angularMomentumPerigee, 2.0) 
				/ (body.gravitationalParameter * (1.0 + Math.cos(0.0)));
		return Math.sqrt((2.0 * body.gravitationalParameter) / r0);
	}
	
	/**
	 * @return the body
	 */
	public Particle getBody() {
		return body;
	}

	/**
	 * @return the satellite
	 */
	public Particle getSatellite() {
		return satellite;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Orbit [calculateAngularVector()=" + calculateAngularVector().toSmallString()
				+ "\ncalculateNodeLine()=" + calculateNodeLine().toSmallString()
				+ "\ncalculateRightAscension()=" + calculateRightAscension()
				+ "\ncalculatePerigeeArgument()=" + calculatePerigeeArgument()
				+ "\ncalculateTrueAnomaly()=" + calculateTrueAnomaly()
				+ "\ncalculateEccentricityVector()="
				+ calculateEccentricityVector().toSmallString() + "\ncalculateEccentricity()="
				+ calculateEccentricity() + "\ncalculatePerpendicularVelocity()="
				+ calculatePerpendicularVelocity() + "\ncalculateRadialVelocity()="
				+ calculateRadialVelocity() + "\ncalculateInclination()="
				+ calculateInclination() + "\ncalculateDistance()="
				+ calculateDistance() + "\ncalculatePerigee()="
				+ calculatePerigee() + "\ncalculateApogee()="
				+ calculateApogee() + "\ncalculateSemimajorAxis()="
				+ calculateSemimajorAxis() + "\ncalculatePeriod()="
				+ calculatePeriod()	+ "\ncalculateEscapeV()="
				+ calculateEscapeV() +"\ncalculateFlightPathAngle()="
				+ calculateFlightPathAngle() + "\nSat_R()="
				+ this.satellite.r.toSmallString() + "\nSat_V()="
				+ this.satellite.v.toSmallString()  + "]";
	}
}
