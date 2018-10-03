package org.orbital.math.vector;

public class CartesianTransform {
	public final double[][] matrix;
	
	private final static double RADIANS_TO_DEGREES = 180.0 / Math.PI;
	
	private final static double DEGREES_TO_RADIANS = Math.PI / 180.0;
	
	public CartesianTransform(double perigeeArgument, double rightAscension, double inclination) {
		this.matrix = new double[3][3];
		
		// Omega is the rightAscension.
		System.out.println("Omega (Right Ascension) = " + rightAscension);
		// i is the inclination
		System.out.println("i (Inclination) = " + inclination);
		// w is the perigreeArgument
		System.out.println("w (Perigee Argument) = " + perigeeArgument);

		perigeeArgument *= DEGREES_TO_RADIANS;
		
		rightAscension *= DEGREES_TO_RADIANS;
		
		inclination *= DEGREES_TO_RADIANS;
		
		// Unit I
		this.matrix[0][0] = -Math.sin(rightAscension) * Math.cos(inclination) * Math.sin(perigeeArgument) + Math.cos(rightAscension) * Math.cos(perigeeArgument);
		
		this.matrix[0][1] = Math.cos(rightAscension) * Math.cos(inclination) * Math.sin(perigeeArgument) + Math.sin(rightAscension) * Math.cos(perigeeArgument);
		
		this.matrix[0][2] = Math.sin(inclination) * Math.sin(perigeeArgument);
		
		
		// Unit J
		this.matrix[1][0] = -Math.sin(rightAscension) * Math.cos(inclination) * Math.cos(perigeeArgument) - Math.cos(rightAscension) * Math.sin(perigeeArgument);
		
		this.matrix[1][1] = Math.cos(rightAscension) * Math.cos(inclination) * Math.cos(perigeeArgument) - Math.sin(rightAscension) * Math.sin(perigeeArgument);
		
		this.matrix[1][2] = Math.sin(inclination) * Math.cos(perigeeArgument);
	
		// Unit K
		this.matrix[2][0] = Math.sin(rightAscension) * Math.sin(inclination);
		
		this.matrix[2][1] = -Math.cos(rightAscension) * Math.sin(inclination);
		
		this.matrix[2][2] = Math.cos(inclination);
	}
	
	private final double[][] transpose() {
	    int m = this.matrix.length;
	    int n = this.matrix[0].length;

	    double[][] trasposedMatrix = new double[n][m];

	    for(int x = 0; x < n; x++)
	    {
	        for(int y = 0; y < m; y++)
	        {
	            trasposedMatrix[x][y] = this.matrix[y][x];
	        }
	    }

	    return trasposedMatrix;
	}
	
	public CartesianVector scale(CartesianVector vector) {
		double[][] matrixTranspose = this.transpose();
		CartesianVector unitI = new CartesianVector("U_i", matrixTranspose[0][0], matrixTranspose[0][1], matrixTranspose[0][2]);
		CartesianVector unitJ = new CartesianVector("U_j", matrixTranspose[1][0], matrixTranspose[1][1], matrixTranspose[1][2]);
		CartesianVector unitK = new CartesianVector("U_k", matrixTranspose[2][0], matrixTranspose[2][1], matrixTranspose[2][2]);
		CartesianVector xP = unitI.scale("X", vector.x.getMagnitude());
		CartesianVector yP = unitJ.scale("Y", vector.y.getMagnitude());
		CartesianVector zP = unitK.scale("Z", vector.z.getMagnitude());
	
		return new CartesianVector(vector.getLabel() + "'", 
				xP.x.getMagnitude() + xP.y.getMagnitude() + xP.z.getMagnitude(), 
				yP.x.getMagnitude() + yP.y.getMagnitude() + yP.z.getMagnitude(), 
				zP.x.getMagnitude() + zP.y.getMagnitude() + zP.z.getMagnitude());
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 3; i++) {
			buffer.append("[");
			for (int j = 0; j < 3; j++) {
				buffer.append(this.matrix[i][j]);
				if (j < 2)
					buffer.append(",");
			}
			buffer.append("]");
			if (i < 2)
				buffer.append(",");
		}
		return buffer.toString();
	}
}
