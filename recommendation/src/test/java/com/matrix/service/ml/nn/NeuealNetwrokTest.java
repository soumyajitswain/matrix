package com.matrix.service.ml.nn;

import org.junit.Test;

public class NeuealNetwrokTest {
	
	@Test
	public void trainNeuralNetwork() {
		NeuralNetwrok n = new NeuralNetwrok();
		n.addIteration(10)
		 .addSeed(1)
		 .addLayer(new Layer1("layer","input"));
	}

}
