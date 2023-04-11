package com.collegegroup.personaldiary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collegegroup.personaldiary.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
	
}