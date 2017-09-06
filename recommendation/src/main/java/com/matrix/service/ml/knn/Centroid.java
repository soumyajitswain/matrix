package com.matrix.service.ml.knn;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Centroid {
	private static Logger LOG = LoggerFactory.getLogger(Centroid.class);
	
	public List<Point> points;
	public Point centroid;
	public int id;
	
	//Creates a new Cluster
	public Centroid(int id) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
	}

	public List<Point> getPoints() {
		return points;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Point getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public int getId() {
		return id;
	}
	
	public void clear() {
		points.clear();
	}
	
	public void plotCluster() {
		LOG.info("[Cluster: " + id+"]");
		LOG.info("[Centroid: " + centroid + "]");
		LOG.info("[Points: \n");
		for(Point p : points) {
			LOG.info(p.toString());
		}
		LOG.info("]");
	}
}
