package com.matrix.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.matrix.bean.User;
import com.matrix.bean.UserSkuMatrix;
import com.matrix.service.AbstractGradientDesent.Parameter;

@Service
public class GradientDesentLamda extends AbstractGradientDesent {
    
	private static final Logger LOG = LoggerFactory.getLogger(GradientDesentLamda.class);
    
	@Override
	public double gradientDesent(List<UserSkuMatrix> ratings, Parameter p, int factor) {
		double m = ratings.size();
		double total = partialDerivative(ratings,p,factor);
		double avg = (1.0 * total) / m;
		return avg*getLearningAlpha();
	}
	public Parameter calculateWeight(List<UserSkuMatrix> ratings, User user) {
		int s = ratings.size();
		double theta0=0.1,theta1=0.1,theta2=0.1;
		double tempTheta0=0,tempTheta1=0,tempTheta2=0;
		for(int i = 0;i<getIteration();i++) {
			if(verifyConvergence(theta0, tempTheta0) 
					&& verifyConvergence(theta1, tempTheta1)
					&& verifyConvergence(theta2, tempTheta2)) {
				break;
			}

			tempTheta0 = theta0;
			tempTheta1 = theta1;
			tempTheta2 = theta2;

			Parameter p = new Parameter(theta0, theta1, theta2);
			
			theta0 = (theta0*lamdaFactor(s)) - gradientDesent(ratings, p,1);
			p = new Parameter(theta0, theta1, theta2);
			theta1 = (theta1*lamdaFactor(s)) - gradientDesent(ratings, p,2);
			p = new Parameter(theta0, theta1, theta2);
			theta2 = (theta2*lamdaFactor(s)) - gradientDesent(ratings, p,3);
			p = new Parameter(theta0, theta1, theta2);
			
		}
		Parameter p = new Parameter(theta0, theta1, theta2);
		LOG.info("Parameter vector for user {} theta0 {}, theta1 {}, theta2 {}", 
				user.getId(),theta0, theta1, theta2);
		return p;	
	}
	
	private double lamdaFactor(int size) {
		double lamdaFactor = getLearningAlpha()*getLamda()/size;
		return 1-lamdaFactor;
	}

}
