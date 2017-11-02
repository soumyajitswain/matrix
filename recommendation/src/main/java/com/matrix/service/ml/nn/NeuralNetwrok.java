package com.matrix.service.ml.nn;

import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.web.servlet.handler.MatchableHandlerMapping;

import com.matrix.service.ml.math.matrix.MatrixMathUtil;

public class NeuralNetwrok {
	
    private double seed = 1.0;	
	
    private int iteration = 1;
    
    private double[][] input;
    
    public NeuralNetwrok() {
		
	}
	
    public NeuralNetwrok addSeed(double seed) {
    	this.seed = seed;
    	return this;
    }
    
    public NeuralNetwrok addIteration(int iteration) {
    	this.iteration = iteration;
    	return this;
    }
    
    public NeuralNetwrok addLayer(LayerBuilder layer) {
    	return this;
    }
    
	public void train() {
		
		LayerBuilder l1 = new Layer("layer1", "simple");
		LayerBuilder l2 = new Layer("layer2", "gaussian");
        
		//pass the intial weight
		
		for(int i=0; i<iteration; i++) {
			l1.input(input);
			//find the dot between the products
			
			//find the error
			
			//find the delta
			
			//readjust the weight
		} // loop through the same process again
	}
	
	public double predict(int[][] input) {
		return 0;
	}

}
