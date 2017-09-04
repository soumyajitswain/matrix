package com.matrix.service.ml.naivebayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class NaiveBayesClassifier {
	final List<CategoryMatrix> categoryList = new ArrayList<CategoryMatrix>();

	public void train(Map<String, String> trainingMap) {

		trainingMap.forEach((key, value) -> {
			CategoryMatrix categoryMatrix = 
					createCategoryMatrix(tokenize(value));
			categoryMatrix.name = key;
			categoryList.add(categoryMatrix);

		}); 	

		calculatePrior(categoryList);
	}

	private void calculatePrior(List<CategoryMatrix> categoryList) {

		float totalWordCount = categoryList.stream().map(e -> e.totalWords).reduce((x, y) -> x + y).get().floatValue();
		categoryList.forEach(category ->  category.prior = (float)category.totalWords/totalWordCount);

	}

	private CategoryMatrix createCategoryMatrix(List<String> words) {
		CategoryMatrix categoryMatrix = new CategoryMatrix();
		categoryMatrix.totalWords = words.size();
		Map<Object, Long> collect = 
				words.stream().collect(Collectors.groupingBy(Function.identity(),  Collectors.<String> counting()));
		calculateLikelyhood(collect, categoryMatrix);
		return categoryMatrix;
	}

	private Map<String, Float> calculateLikelyhood(
			Map<Object, Long> collect, CategoryMatrix categoryMatrix) {
		final Map<String, Float> likelyhood = new HashMap<String, Float>();
		collect.forEach((key, value) -> {
			float likeHoodProb = (float)value/(float)categoryMatrix.totalWords;
			likelyhood.put(key.toString(), likeHoodProb);
		});
		categoryMatrix.likelyhood = likelyhood;
		return likelyhood;

	}
	private List<String> tokenize(String value) {
		final String[] words = value.split("\\s");
		return Arrays.asList(words);
	}

	public String classify(String sentence) {
		List<String> words = tokenize(sentence);

		float maxProb = 0;
		String chosenCategory = null;
		for(CategoryMatrix categoryMatrix:categoryList) {
			float p = calculateTotalLikelyHood(categoryMatrix.likelyhood, words) * categoryMatrix.prior;
			if(maxProb == 0) {
				maxProb = p;
				chosenCategory = categoryMatrix.name;
			} else {
				if(maxProb < p) {
					maxProb = p;
					chosenCategory = categoryMatrix.name;
				}
			}
		}
		return chosenCategory;
	}
	private Float calculateTotalLikelyHood(Map<String, Float> likeMap, List<String> words) {
		float totalLikelyHood = 0f;
		for(String word:words) {
			Float likeHood = likeMap.get(word);
			if(likeHood != null)
				totalLikelyHood = totalLikelyHood == 0?likeHood:totalLikelyHood*likeHood;
		}
		return totalLikelyHood;
	}
	public class CategoryMatrix {
		private String name;
		private float prior;
		private long totalWords;
		private Map<String, Float> likelyhood = new HashMap<String, Float>();
	}
}
