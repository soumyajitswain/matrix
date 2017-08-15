package com.matrix.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private double thetha;
	private String feature1Name;
	private double feature1Value;
	private String feature2Name;
	private double feature2Value;
	private String feature3Name;
	private double feature3Value;
	private double latitude;
	private double longitude;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getThetha() {
		return thetha;
	}
	public void setThetha(double thetha) {
		this.thetha = thetha;
	}
	public String getFeature1Name() {
		return feature1Name;
	}
	public void setFeature1Name(String feature1Name) {
		this.feature1Name = feature1Name;
	}
	public double getFeature1Value() {
		return feature1Value;
	}
	public void setFeature1Value(double feature1Value) {
		this.feature1Value = feature1Value;
	}
	public String getFeature2Name() {
		return feature2Name;
	}
	public void setFeature2Name(String feature2Name) {
		this.feature2Name = feature2Name;
	}
	public double getFeature2Value() {
		return feature2Value;
	}
	public void setFeature2Value(double feature2Value) {
		this.feature2Value = feature2Value;
	}
	public String getFeature3Name() {
		return feature3Name;
	}
	public void setFeature3Name(String feature3Name) {
		this.feature3Name = feature3Name;
	}
	public double getFeature3Value() {
		return feature3Value;
	}
	public void setFeature3Value(double feature3Value) {
		this.feature3Value = feature3Value;
	}

}
