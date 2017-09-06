package com.matrix.service.ml.math.distance;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class EuclideanDistance extends AbstractDistance {

	@Override
	public double distance(ArrayList<Double> a, ArrayList<Double> b) {
		validateVector(a, b);
		
		double sum = 0.0;
		for(int i = 0; i<a.size(); i++) {
			double diff = a.get(i) - b.get(i);
			double rSqr = Math.pow(diff, 2);
			sum += rSqr;
		}
		double result = Math.sqrt(sum);
		return result;
	}
	
	

}
