package com.matrix.service.ml.nn;

public class NeuralNetwrok {
	
    private double seed = 1.0;	
	
    private int iteration = 1;
    
    private int[][] input;
    
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
		for(int i=0; i<iteration; i++) {
	       		
		}
	}
	
	public double predict(int[][] input) {
		return 0;
	}

}
