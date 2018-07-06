package com.doctordojo.doctordojo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctordojo.doctordojo.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);

}
