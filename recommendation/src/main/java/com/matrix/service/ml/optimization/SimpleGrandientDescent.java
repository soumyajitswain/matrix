package com.matrix.service.ml.optimization;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.matrix.bean.UserSkuMatrix;
import com.matrix.service.ml.optimization.AbstractGradientDescent.Parameter;

@Service
public class SimpleGrandientDescent extends AbstractGradientDescent {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(SimpleGrandientDescent.class);
	@Override
	public double gradientDesent(List<UserSkuMatrix> ratings, Parameter p, int factor) {
		double m = ratings.size();
		double total = partialDerivative(ratings,p,factor);
		return (1.0 * total) / m;
	}

}
