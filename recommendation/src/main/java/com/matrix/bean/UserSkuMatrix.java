package com.matrix.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USERSKUMATRIX")
public class UserSkuMatrix {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private double rank;
	
	@ManyToOne
	@JoinColumn(name = "skuId")
	private Sku sku;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}

}
