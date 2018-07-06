package com.doctordojo.doctordojo.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctordojo.doctordojo.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	
	List<Role> findAll();
	
	List<Role> findByName(String name);

}
