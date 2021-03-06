package com.matrix.service.ml.optimization;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.matrix.bean.User;
import com.matrix.bean.UserSkuMatrix;

public abstract class AbstractGradientDescent {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractGradientDescent.class);
	
	private static final double GLOBAL_MINIMUM = Double.MIN_VALUE;
	
	private String method;
	
	private double learningAlpha = 0.1;
	
	private double lamda;
	
	private double iteration;
	
	private Map<String, Double> prevParameterMap;
	
	@Autowired
	private static ApplicationContext context;
	
	public abstract double gradientDesent(List<UserSkuMatrix> ratings,Parameter p, int fator);
	
	public Parameter calculateWeight(List<UserSkuMatrix> ratings, User user) {
		
		double theta0=0.1,theta1=0.1,theta2=0.1;
		double tempTheta0=0,tempTheta1=0,tempTheta2=0;
		
		for(int i = 0;i<iteration;i++) {
		
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
		
		LOG.info("Parameter vector for user {} theta0 {}, theta1 {}, theta2 {}", 
				user.getId(),theta0, theta1, theta2);
		
		return p;	
	}

	public static AbstractGradientDescent factory(AbstractGradientDescent g) {
		
		AbstractGradientDescent childClass = 
				(AbstractGradientDescent) context.getBean(g.getMethod());
       	
		return childClass;
	}
	
	protected boolean verifyConvergence(double theta, double tempTheta) {
		
		return (theta - tempTheta) < GLOBAL_MINIMUM;
	}
	
	protected double partialDerivative(List<UserSkuMatrix> ratings, 
			Parameter p, int factor){
		
		double sum=0.0;
		
		for(UserSkuMatrix d:ratings) {
			
			double x = d.getSku().getFeature1Value(), 
					y = d.getRank(), x1=d.getSku().getFeature2Value();

			Hypothesis h = new Hypothesis(p, x, x1);
		
			double s = (h.hypothesis2()-y);
			
			if(factor == 2) {
				s = s*x;
			} else if( factor==3) {
				s = s*x1;
			}
		
			sum = sum + s;
		}
		
		return sum;
	}

	public double getIteration() {
	
		return iteration;
	
	}

	public AbstractGradientDescent iteration(double iteration) {
		
		this.iteration = iteration;
		
		return this;
		
	}

	public String getMethod() {
		
		return method;
	}

	public AbstractGradientDescent method(String method) {
		
		this.method = method;
		
		return this;
	}

	public double getLearningAlpha() {
		
		return learningAlpha;
	}

	public AbstractGradientDescent learningAlpha(double learningAlpha) {
		
		this.learningAlpha = learningAlpha;
		
		return this;
	}

	public double getLamda() {
		
		return lamda;
	}

	public AbstractGradientDescent lamda(double lamda) {
		
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
       public boolean converged;
       public boolean used = false;
       
       public Parameter() {
    	   
       }
       
       public Parameter(double theta0, double theta1, double theta2) {
    	
    	   this.theta0 = theta0;
    	   this.theta1 = theta1;
    	   this.theta2 = theta2;
       
       }
    }
	
    public class Hypothesis {
	
    	private double theta0;
		private double theta1;
		private double theta2;
		private double x;
		private double x1;
		
		public Hypothesis(Parameter p, double x, double x1) {
			this.theta0 = p.theta0;
			this.theta1 = p.theta1;
			this.theta2 = p.theta2;
			this.x = x;
			this.x1 = x1;
		}
		
		public double hypothesis1() {
			return theta0 + (theta1 * x)+ (theta2 * x1);
		}
		public double hypothesis2() {
			return theta0 + (theta1 * x)+ (theta2 * x1 * x1);
		}

	}
}
