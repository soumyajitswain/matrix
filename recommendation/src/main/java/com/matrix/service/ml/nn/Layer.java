package com.matrix.service.ml.nn;

public class Layer implements LayerBuilder {
	
	private String layerName;
	
	private String  activationType;
	
	private double[][] input;
	
	private double[][] wight;
	
	public Layer(String layerName, String activationType) {
	     this.layerName = layerName;
	     this.activationType = activationType;
	}

	public Layer input(double[][] input) {
		this.input = input;
		return this;
	}

	public Layer weight(double[][] weight) {
		this.wight = weight;
		return this;
	}
	
	public double[][] output() {
		double[][] output = new double[1][1];
		return output;
	}
} 
