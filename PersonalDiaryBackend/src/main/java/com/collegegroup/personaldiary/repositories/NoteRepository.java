package com.collegegroup.personaldiary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.collegegroup.personaldiary.entities.Note;
import com.collegegroup.personaldiary.entities.User;

public interface NoteRepository extends JpaRepository<Note, Integer> {
	
	Page<Note> findByUser(User user, Pageable pageable);
	
	//@Query(value="select * from notes n where n.title like %:searchKey% and n.user_id = :userId", nativeQuery = true) 
	Page<Note> findByUserAndTitleContaining(User user, String title, Pageable pageable);
	
	Page<Note> findByUserAndDescriptionContaining(User user, String description, Pageable pageable);
	
	Page<Note> findByUserAndTitleOrDescription(User user, String title, String description, Pageable pageable);

}
