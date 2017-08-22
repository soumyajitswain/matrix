package com.matrix.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.matrix.bean.UserSkuMatrix;

@Service
public class SimpleGrandientDesent extends AbstractGradientDesent {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(SimpleGrandientDesent.class);
	@Override
	public double gradientDesent(List<UserSkuMatrix> ratings, Parameter p, int factor) {
		double m = ratings.size();
		double total = partialDerivative(ratings,p,factor);
		return (1.0 * total) / m;
	}

}
