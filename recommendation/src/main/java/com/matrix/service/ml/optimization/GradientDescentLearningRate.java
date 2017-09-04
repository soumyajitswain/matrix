package com.matrix.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matrix.bean.UserSkuMatrix;

@Service
public class GradientDescentLearningRate extends AbstractGradientDescent {

	@Override
	public double gradientDesent(List<UserSkuMatrix> ratings, Parameter p, int factor) {
		double m = ratings.size();
		double total = partialDerivative(ratings,p,factor);
		double avg = (1.0 * total) / m;
		return avg*getLearningAlpha();
	}

}
