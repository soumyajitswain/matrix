package com.matrix.service.ml.nn;

import java.util.Random;

import com.matrix.service.ml.math.matrix.MatrixMathUtil;

public class NeuralNetwrok {
	
    private int iteration = 1;
    
    private double[][] input;
    
    private double[][] expected;
    
    private double[][] weight;
    
    public NeuralNetwrok() {
		
	}
	
    public NeuralNetwrok addSeed(double seed) {
    	return this;
    }
    
    public NeuralNetwrok addIteration(int iteration) {
    	this.iteration = iteration;
    	return this;
    }
    
    public NeuralNetwrok addLayer(LayerBuilder layer) {
    	return this;
    }
    
    public NeuralNetwrok expected(double[][] expected) {
    	this.expected = expected;
    	return this;
    }
    
    public NeuralNetwrok weight(long seed) {
    	Random r = new Random(seed);
    	
    	double[][] tempWeight = new double[input.length][input[0].length];
    	for(int i = 0;i < tempWeight.length ; i ++) {
    		for(int j = 0; j < tempWeight[i].length ; i++) {
    			tempWeight[i][j] = r.nextDouble() * 10;
    		}
    	}
    	
    	return this;
    }
    
	public void train() {
		
		LayerBuilder l1 = new Layer("layer1", "simple");
		LayerBuilder l2 = new Layer("layer2", "gaussian");
        
		//pass the initial weight
		for(int i=0; i<iteration; i++) {
			l1.activationType("simple").input(input);
			
			//find the dot between the products
			l2.activationType("signum").weight(weight).input(l1.output());
			
			//find the error
			double[][] e = error(l2.output());
			
			//find the delta
			double[][] delta = delta(e, MatrixMathUtil.nonlin(l2.output(), true) ); 
			
			//readjust the weight
		    weight = adjustWeight(l1.dot(delta)); 	
		
		} // loop through the same process again
	}
	
	public double[][] error(double[][] s) {
		return MatrixMathUtil.subtract(expected, s);
	}
	
	public double[][] delta(double[][] e, double[][] s) {
		
		return MatrixMathUtil.multiply(e, s);
	}
	
	public double[][] adjustWeight(double[][] w) {
		return MatrixMathUtil.add(weight, w);
	}
	
	public double predict(int[][] input) {
		
		return 0;
	}

}
