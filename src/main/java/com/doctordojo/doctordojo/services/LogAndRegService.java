package com.doctordojo.doctordojo.services;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctordojo.doctordojo.models.User;
import com.doctordojo.doctordojo.repos.RoleRepository;
import com.doctordojo.doctordojo.repos.UserRepository;

@Service
public class LogAndRegService {
	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public LogAndRegService(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public void saveWithNurseRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepo.findByName("ROLE_NURSE"));
		userRepo.save(user);
	}
	
	public void saveWithPhysicianRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepo.findByName("ROLE_PHYSICIAN"));
		userRepo.save(user);
	}
	
	public void saveWithFrontOfficeRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepo.findByName("ROLE_FRONTOFFICE"));
		userRepo.save(user);
	}
	
	 public void saveUserWithAdminRole(User user) {
		 	System.out.println("In service save with admin");
	        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
	        userRepo.save(user);
	    }
	
	
	 public User findByUsername(String username) {
		 return userRepo.findByUsername(username);
	 }
	
	

}
