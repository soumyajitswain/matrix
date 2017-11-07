package com.matrix.service.ml.nn;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import com.matrix.service.ml.math.matrix.MatrixMathUtil;

public class Layer implements LayerBuilder {

	private String layerName;

	private String  activationType;

	private double[][] input;

	private double[][] weight;

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
		this.weight = weight;
		return this;
	}

	public Layer activationType(String activatinType) {
		this.activationType = activatinType;
		return this;
	}

	public double[][] output() {
		double[][] o = null;
		switch(this.activationType) {
		case "simple" :
			o = input;
			break;
		case "signum" :
			o = MatrixMathUtil.multiply(input, false);
			break;
		}
		
		return input;

	}

	public double[][] dot(double[][] wt) {
		RealMatrix  i =  MatrixUtils.createRealMatrix(this.input);
		RealMatrix  w =  MatrixUtils.createRealMatrix(wt);
		return i.transpose().multiply(w).getData();
	}
} 
