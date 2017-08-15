package com.matrix.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.matrix.bean.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>{

}
