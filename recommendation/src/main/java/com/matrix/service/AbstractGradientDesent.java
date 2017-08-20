package com.matrix.service;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.matrix.bean.User;
import com.matrix.bean.UserSkuMatrix;

public abstract class AbstractGradientDesent {
	
	private static final double GLOBAL_MINIMUM = Double.MIN_VALUE;
	
	private String method;
	
	private double learningAlpha;
	
	private double lamda;
	
	private double iteration;
	
	private Map<String, Double> prevParameterMap;
	
	@Autowired
	private static ApplicationContext context;
	
	public abstract boolean gradientDesent();
	
	public Parameter calculateWeight(List<UserSkuMatrix> ratings, User user) {
		Parameter p = new Parameter();
		double theta1=0.1,theta2=0.1,theta3=0.1;
		double tempTheta1=0,tempTheta2=0,tempTheta3=0;
		for(int i = 0;i<iteration;i++) {
			if(verifyConvergence(theta1, tempTheta1) 
					&& verifyConvergence(theta2, tempTheta2)
					&& verifyConvergence(theta3, tempTheta3)) {
				break;
			}

			tempTheta1 = theta1;
			tempTheta2 = theta2;
			tempTheta3 = theta3;

//			theta1 = theta1 - normalGradientDesent(ratings, theta1, theta2, theta3, 1);
//			theta2 = theta2 - normalGradientDesent(ratings, theta1, theta2, theta3, 2);
//			theta3 = theta3 - normalGradientDesent(ratings, theta1, theta2, theta3, 3);
		}
		
		return p;	
	}

	public static AbstractGradientDesent factory(AbstractGradientDesent g) {
		AbstractGradientDesent childClass = 
				(AbstractGradientDesent) context.getBean(g.getMethod());
       	return childClass;
	}
	
	private boolean verifyConvergence(double theta, double tempTheta) {
		return (theta - tempTheta) > GLOBAL_MINIMUM;
	}
	
	public double getIteration() {
		return iteration;
	}

	public AbstractGradientDesent iteration(double iteration) {
		this.iteration = iteration;
		return this;
	}

	public String getMethod() {
		return method;
	}

	public AbstractGradientDesent method(String method) {
		this.method = method;
		return this;
	}

	public double getLearningAlpha() {
		return learningAlpha;
	}

	public AbstractGradientDesent learningAlpha(double learningAlpha) {
		this.learningAlpha = learningAlpha;
		return this;
	}

	public double getLamda() {
		return lamda;
	}

	public AbstractGradientDesent lamda(double lamda) {
		this.lamda = lamda;
		return this;
	}

	public Map<String, Double> getPrevParameterMap() {
		return prevParameterMap;
	}

	public void setPrevParameterMap(Map<String, Double> prevParameterMap) {
		this.prevParameterMap = prevParameterMap;
	} 
	
    public class Parameter {
       public double theta0;
       public double theta1;
       public double theta2;
    }
	
}
