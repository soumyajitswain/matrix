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

    public RealMatrix multiply(final RealMatrix m)
        throws DimensionMismatchException {

        final int nRows = m.getRowDimension();
        final int nCols = m.getColumnDimension();
        final RealMatrix out = MatrixUtils.createRealMatrix(nRows, nCols);
        
        for (int row = 0; row < nRows; ++row) {
            for (int col = 0; col < nCols; ++col) {
                double exp = Math.exp(m.getEntry(row, col));
                out.setEntry(row, col, exp);
            }
        }

        return out;
    }
	
	

}
