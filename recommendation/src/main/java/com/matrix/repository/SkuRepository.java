package com.matrix.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.matrix.bean.Sku;

@Transactional
public interface SkuRepository extends CrudRepository<Sku, Long>{

}
