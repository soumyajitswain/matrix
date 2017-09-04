package com.matrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.matrix.service.ml.recommendation.TrainModel;

@RestController
public class TrainingManager {

	@Autowired
	private TrainModel model;
	
	@RequestMapping(value="/train", method=RequestMethod.GET)
	public void train() {
		model.train();
	}
}
