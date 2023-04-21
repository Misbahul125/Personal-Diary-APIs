package com.collegegroup.personaldiary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.collegegroup.personaldiary.entities.ToDo;
import com.collegegroup.personaldiary.entities.User;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

	Page<ToDo> findByUser(User user, Pageable pageable);
	
	Page<ToDo> findByUserAndTextContaining(User user, String searchKey, Pageable pageable);
	
	
}