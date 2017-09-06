package com.matrix.service.ml.math.distance;

import java.util.ArrayList;

public class CosineSimilarity extends AbstractDistance {

	@Override
	public double distance(ArrayList<Double> a, ArrayList<Double> b) {
		validateVector(a, b);

		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;
		
		for (int i = 0; i < a.size(); i++) {
			dotProduct += a.get(i) * b.get(i);
	        normA += Math.pow(a.get(i), 2);
	        normB += Math.pow(b.get(i), 2);
		}
		
		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}

}
