package com.matrix.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.matrix.bean.UserSkuMatrix;

@Transactional
public interface UserSkuMatrixRepository 
				extends CrudRepository<UserSkuMatrix, Long>{
	
	public List<UserSkuMatrix> findByUserId(Long userId);

}
