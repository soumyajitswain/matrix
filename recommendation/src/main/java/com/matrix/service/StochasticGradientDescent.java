package com.matrix.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.matrix.bean.User;
import com.matrix.bean.UserSkuMatrix;

@Service
public class StochasticGradientDescent extends AbstractGradientDescent {

	private static final Logger LOG = LoggerFactory.getLogger(StochasticGradientDescent.class);

	@Override
	public double gradientDesent(List<UserSkuMatrix> ratings, Parameter p, int factor) {
		double m = ratings.size();
		
		double total = partialDerivative(ratings,p,factor);
		
		double avg = (1.0 * total) / m;

		return avg*getLearningAlpha();
	}

	public Parameter calculateWeight(List<UserSkuMatrix> ratings, User user) {
		List<List<UserSkuMatrix>> batchList = splitData(ratings, 10);
		final Parameter p = new Parameter(0.1, 0.1, 0.1);
		batchList.forEach(list -> {

			Parameter p1 = calculateWeight(list, user, p);

			if(p.converged) {
				p.theta0 = p1.theta0;
				p.theta1 = p1.theta1;
				p.theta2 = p1.theta2;

				return;

			} else {

				p1=calculateWeight(list, user, p1);
				p.theta0 = p1.theta0;
				p.theta1 = p1.theta1;
				p.theta2 = p1.theta2;
			}

		});
		return p;
	}

	public Parameter calculateWeight(List<UserSkuMatrix> ratings, User user, Parameter p0) {

		double theta0=p0.theta0,theta1=p0.theta1,theta2=p0.theta2;
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

			theta0 = theta0 - gradientDesent(ratings, p,1);
			p = new Parameter(theta0, theta1, theta2);
			theta1 = theta1 - gradientDesent(ratings, p,2);
			p = new Parameter(theta0, theta1, theta2);
			theta2 = theta2 - gradientDesent(ratings, p,3);
			p = new Parameter(theta0, theta1, theta2);

		}
		
		Parameter p = new Parameter(theta0, theta1, theta2);
		p.converged = true;
		
		LOG.info("Parameter vector for user {} theta0 {}, theta1 {}, theta2 {}", 
				user.getId(),theta0, theta1, theta2);
		
		return p;	
	}

	private List<List<UserSkuMatrix>> splitData(List<UserSkuMatrix> ratings, int batchSize) {
		final int fBatchSize = batchSize == 0? 10:batchSize;
		
		int size = ratings.size();
		
		List<List<UserSkuMatrix>> batchList = IntStream.range(0, (size-1)/batchSize+1).mapToObj(n -> ratings.subList(n=n*fBatchSize, 
				size-batchSize>=n? n+batchSize: size)).collect(Collectors.toList());
		
		return suffle(batchList);
	}
	private List<List<UserSkuMatrix>> suffle(List<List<UserSkuMatrix>> suffledList) {
		
		Collections.shuffle(suffledList);
		
		return suffledList;
	}

}
