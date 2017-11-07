package com.matrix.service.ml.nn;

public interface LayerBuilder {
	
	public default LayerBuilder input(double[][] input) {
		return this;
	}

	public default LayerBuilder activationType(String activatinType) {
		return this;
	}
	
	public double[][] output();
	
	public double[][] dot(double[][] wt);
	
}
