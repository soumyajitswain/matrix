package com.matrix.service.ml.nn;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Layer implements LayerBuilder {
	
	private String layerName;
	
	private String  activationType;
	
	private double[][] input;
	
	private double[][] wight;
	
	public Layer(String layerName, String activationType) {
	     this.layerName = layerName;
	     this.activationType = activationType;
	}

	@Override
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
	
	public double[][] dot(double[][] wt) {
		RealMatrix  i =  MatrixUtils.createRealMatrix(this.input);
		RealMatrix  w =  MatrixUtils.createRealMatrix(wt);
		return i.transpose().multiply(w).getData();
	}
} 
