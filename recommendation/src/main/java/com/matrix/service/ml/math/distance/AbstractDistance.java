package com.matrix.service.ml.math.distance;

import java.util.ArrayList;

public abstract class AbstractDistance {
   public abstract double distance(ArrayList<Double> a, ArrayList<Double> b);
   
   protected void validateVector(ArrayList<Double> a, ArrayList<Double> b) {
	   if(a.size() == 0 && b.size() == 0) {
		   throw new IllegalArgumentException("The arraylist size is 0");
	   }
	   
	   if(a.size() != b.size()) {
		   throw new IllegalArgumentException("The vector size doesn't match");
	   }
   }
}
