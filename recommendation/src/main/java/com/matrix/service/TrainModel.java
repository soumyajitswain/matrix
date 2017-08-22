package com.matrix.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.matrix.bean.User;
import com.matrix.bean.UserSkuMatrix;
import com.matrix.repository.UserRepository;
import com.matrix.repository.UserSkuMatrixRepository;

@Service
public class TrainModel {
	
    private static final Logger LOG = LoggerFactory.getLogger(TrainModel.class);
    
	private static final double GLOBAL_MINIMUM = Double.MIN_VALUE;
	
	@Autowired
	@Qualifier("simpleGrandientDesent")
	private AbstractGradientDesent gradientDesent;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserSkuMatrixRepository skuMatrixRepository;

	public void train() {
		Iterable<User> users = userRepository.findAll();
		users.forEach((user) -> {
			trainSingleUser(user);
		});
	}
	private void trainSingleUser(User user) {
		List<UserSkuMatrix> ratings = StreamSupport.stream(
				skuMatrixRepository.findByUserId(user.getId()).spliterator(), false)
				.collect(Collectors.toList());
		calculateWeight(ratings, user);
		
		gradientDesent.method("simpleGrandientDesent")
				.learningAlpha(0.1)
				.lamda(0.1)
				.iteration(10000);
		gradientDesent.calculateWeight(ratings, user);
	}
	private void calculateWeight(List<UserSkuMatrix> ratings, User user) {
		double theta1=0.1,theta2=0.1,theta3=0.1;
		double tempTheta1=0,tempTheta2=0,tempTheta3=0;
		for(int i = 0;i<10_000;i++) {
			if(verifyConvergence(theta1, tempTheta1) 
					&& verifyConvergence(theta2, tempTheta2)
					&& verifyConvergence(theta3, tempTheta3)) {
				break;
			}

			tempTheta1 = theta1;
			tempTheta2 = theta2;
			tempTheta3 = theta3;

			theta1 = theta1 - normalGradientDesent(ratings, theta1, theta2, theta3, 1);
			theta2 = theta2 - normalGradientDesent(ratings, theta1, theta2, theta3, 2);
			theta3 = theta3 - normalGradientDesent(ratings, theta1, theta2, theta3, 3);
		}

		updateThetaForUser(user, theta1, theta2, theta3);
		LOG.info("Parameter vector for user {} theta0 {}, theta1 {}, theta2 {}", 
					user.getId(),theta1, theta2, theta3);
	}
	private boolean verifyConvergence(double theta, double tempTheta) {
		return (theta - tempTheta) < GLOBAL_MINIMUM;

	}
	private double normalGradientDesent(List<UserSkuMatrix> ratings, 
			double theta1, double theta2, double theta3, int varCnt) {
		double m = ratings.size();
		double sum = sigma1(ratings, theta1, theta2, theta3, varCnt);
		return (1.0 / m) * sum;
	}

	private double sigma1(List<UserSkuMatrix> data, double theta0, double theta1,double theta2,double a) {
		double sum=0;
		for(UserSkuMatrix d:data) {
			double x = d.getSku().getFeature1Value(), y = d.getRank(), x1=d.getSku().getFeature2Value();

			double s = (hypothesis(theta0, theta1, theta2, x, x1)-y);
			if(a == 2) {
				s = s*x;
			} else if( a==3) {
				s = s*x1;
			}
			//LOG.info("X ="+x+" Y="+y+" S="+s+" feature="+a);
			sum = sum + s;
		}
		return sum;
	}
	private double hypothesis(double theta0, double theta1,double theta2, double x, double x1) {
		return theta0 + (theta1 * x)+ (theta2 * x1 * x1);
	}
	
	private void updateThetaForUser(User user, double theta0, double theta1, double theta2) {
	   user.setThetha0(theta0);
	   user.setThetha1(theta1);
	   user.setThetha2(theta2);
	   userRepository.save(user);
	}
}
