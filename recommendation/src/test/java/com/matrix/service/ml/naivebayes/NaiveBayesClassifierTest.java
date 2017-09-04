package com.matrix.service.ml.naivebayes;

import java.util.HashMap;
import java.util.Map;

public class NaiveBayesClassifierTest {

	public static void main(String args[]) {
		Map<String, String> trainingMap = new HashMap<String, String>();
		trainingMap.put("one", "The word contain one");
		trainingMap.put("two", "Two is the second word");
		NaiveBayesClassifier bayesClassifier = new NaiveBayesClassifier();
		bayesClassifier.train(trainingMap);
		
		System.out.println(bayesClassifier.classify("I am searching for one"));
	}
}
