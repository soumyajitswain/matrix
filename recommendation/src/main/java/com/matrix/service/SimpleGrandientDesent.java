package com.matrix.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matrix.bean.UserSkuMatrix;

@Service
public class SimpleGrandientDesent extends AbstractGradientDesent {

	@Override
	public double gradientDesent(List<UserSkuMatrix> ratings, Parameter p, int factor) {
		double m = ratings.size();
		double total = partialDerivative(ratings,p,factor);
		return (1.0 * total) / m;
	}
	
	private double partialDerivative(List<UserSkuMatrix> ratings, Parameter p, int factor){
		double sum=0.0;
		for(UserSkuMatrix d:ratings) {
			double x = d.getSku().getFeature1Value(), 
					y = d.getRank(), x1=d.getSku().getFeature2Value();

			Hypothesis h = new Hypothesis(p, x, x1);
			double s = (h.hypothesis1()-y);
			if(factor == 2) {
				s = s*x;
			} else if( factor==3) {
				s = s*x1;
			}
			sum = sum + s;
		}

		return sum;
	}

}
