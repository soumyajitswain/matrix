package com.matrix.service.ml.math.matrix;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Mostly uses the apache math library.Math is important ingredient for any data mining project.
 * 
 * @author sswain
 *
 */
public class MatrixMathUtil {

    public static double[][] nonlin(final double[][] m, boolean y)
        throws DimensionMismatchException {
        
    	RealMatrix sm = MatrixUtils.createRealMatrix(m); 
        
        final int nRows = sm.getRowDimension();
        final int nCols = sm.getColumnDimension();
        final RealMatrix out = MatrixUtils.createRealMatrix(nRows, nCols);
        
        for (int row = 0; row < nRows; ++row) {
            for (int col = 0; col < nCols; ++col) {
            	
            	double exp = 0;
            	
            	if(y) {
            		exp = sm.getEntry(row, col) * (1 - sm.getEntry(row, col)); 
            	} else {
                    exp = Math.exp(sm.getEntry(row, col));
            	}
                
            	out.setEntry(row, col, exp);
            }
        }

        return out.getData();
    }
	
	public static double[][] add(final double[][] s,final double[][] t) {
		RealMatrix sm = MatrixUtils.createRealMatrix(s);
		RealMatrix tm = MatrixUtils.createRealMatrix(t);
		RealMatrix o = sm.add(tm);
		return o.getData();
	}
	
	public static double[][] subtract(final double[][] s, final double[][] t) {
		RealMatrix sm = MatrixUtils.createRealMatrix(s);
		RealMatrix tm = MatrixUtils.createRealMatrix(t);
        return sm.subtract(tm).getData(); 		
	}

	public static double[][] multiply(double[][] s, double[][] t) {
		RealMatrix sm = MatrixUtils.createRealMatrix(s);
		RealMatrix tm = MatrixUtils.createRealMatrix(t);
        return sm.multiply(tm).getData(); 		
	}
}
