package com.matrix.service.ml.nn;

public interface LayerBuilder {
	
	public default LayerBuilder input(double[][] input) {
		return this;
	}

	
}
