package com.matrix.service.ml.knn;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.service.ml.math.distance.EuclideanDistance;


/**
 * 
 * @author sswain
 *
 */
@Service
public class Knn {
	
	private static Logger LOG = LoggerFactory.getLogger(Knn.class);
	
	/**
	 * @see #euclideanDistance Measure distance between two points.
	 */
	@Autowired
	private EuclideanDistance euclideanDistance;
	
	/**
	 * @param parameter Hold all input variables in memory
	 */
    private Map<String, Object> parameters = 
    					new HashMap<String, Object>();
    
    /**
     * 
     * @param c No of centroids
     * @return
     */
    public Knn setC(Object c) {
    	parameters.put("C", c);
    	return this;
    }
    
    /**
     * 
     * @param metrics No of metrics
     * @return
     */
    public Knn setDistanceMetrics(Object metrics) {
    	parameters.put("distanceMetrics", metrics);
    	return this;
    }

    /**
     * 
     * @param interation No of loops to identify the right cluster
     * @return
     */
    public Knn setIteration(Object iteration) {
    	parameters.put("iteration", iteration);
    	return this;
    }
    
    /**
     * The method trains a model and create the list of centroids based on @see {@link Knn}{@link #setC(Object)}.
     * @param pairs
     */
    public void fit(List<Pair<Integer, Integer>> pairs) {
    	List<Point> points = convertToPoints(pairs);
    	
    	List<Centroid> centroids = initializeCluster(points);
    	
    	startTraining(centroids, points);	
    }
    
    /**
     * Run the iteration using {@link #parameters} iteration value. The loop will be broken when the old and new centroid 
     * will converge into the GLOBAL MINIMUM. 
     * @param centroids
     * @param points
     */
    private void startTraining(List<Centroid> centroids, List<Point> points) {
		for(int i = 0;i <= (int)parameters.get("iteration");i++) {
			
			List<Centroid> oldCentroid = centroids;
			
			assignPoints(centroids, points);
			
			centroids = calculateCentroid(centroids);
			
			double distance = getDistanceBetweenCentroids(oldCentroid, centroids);
			
			if(distance == 0) { 
				break;
			}
		}
		
	}
    
    /**
     * Calculate the distance between old and new centroid. Both centroid should coneverge to GLOBAL MINIMUM for 
     * the iteration to finish.
     * @param oldCentroid
     * @param centroids
     * @return
     */
    private double getDistanceBetweenCentroids(List<Centroid> oldCentroid, List<Centroid> centroids) {
    	double distance = 0;
		for(int i = 0; i < oldCentroid.size(); i++) {
			Point o = oldCentroid.get(i).getCentroid();
			Point n = centroids.get(i).getCentroid();
			distance += measureDistance(o, n); 
		}
		return distance;
	}

	/**
     * Recalculate the centroid based on the average distance between all the points assign in the for a cluster. 
     * @param centroids
     * @return
     */
    private List<Centroid> calculateCentroid(List<Centroid> centroids) {
       for( Centroid c : centroids) {
    	   List<Point> points = c.getPoints();
    	   double sumX = 0;
    	   double sumY = 0;
    	   int clusterSize = points.size();
    	   
    	   for(Point p : points) {
    		   sumX = sumX + p.getX();
    		   sumY = sumY + p.getY();
    	   }
    	   
    	   c.getCentroid().setX(sumX/clusterSize);
    	   c.getCentroid().setY(sumY/clusterSize);
       }
    	return centroids;
	}

	/**
     * 
     * @param centroids
     * @param points
     */
    private void assignPoints(List<Centroid> centroids, List<Point> points) {
    	double max = Double.MAX_VALUE;
    	double min = max;
    	int cluster = 0;  
    	double distance = 0;
    	
		for(Point p : points) {
			
			min = max;
			
			for (int i = 0; i < centroids.size(); i++) {
				Centroid c = centroids.get(i);
				distance = measureDistance(p, c.getCentroid());
				
				if(distance < min) {
					min = distance;
					cluster = i;
				}
				
			}
			p.setCluster(cluster);
			centroids.get(cluster).points.add(p);
		}
    	
    }
    
    /**
     * 
     * @param p
     * @param c
     * @return
     */
	private double measureDistance(Point p, Point c) {
		ArrayList<Double> left = new ArrayList<Double>();
        left.add(p.getX());
        left.add(p.getY());
        
        ArrayList<Double> right = new ArrayList<Double>();
        right.add(c.getX());
        right.add(c.getY());
        
		return euclideanDistance.distance(left, right);
	}

	/**
	 * 
	 * @param points
	 * @return
	 */
	private List<Centroid> initializeCluster(List<Point> points) {
    	List<Centroid> clusters = new ArrayList<Centroid>();
		for(int i = 1; i <= (int)parameters.get("C");	i++) {
			Centroid c = new Centroid(i);
			Collections.shuffle(points);
			c.setCentroid(points.get(0));
		}
		
		return clusters;
	}

	/**
     * Transform the pairs to points
     * @param pairs
     */
	private List<Point> convertToPoints(List<Pair<Integer, Integer>> pairs) {
	    List<Point> points = new ArrayList<Point>();
	    pairs.forEach(pair -> {
	    	Point p = new Point(pair.getLeft(), pair.getRight());
	    	points.add(p);
	    });
	    
	    return points;
	}
}
