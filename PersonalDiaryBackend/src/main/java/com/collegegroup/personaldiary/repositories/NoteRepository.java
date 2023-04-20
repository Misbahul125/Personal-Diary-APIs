package com.collegegroup.personaldiary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.collegegroup.personaldiary.entities.Note;
import com.collegegroup.personaldiary.entities.User;

public interface NoteRepository extends JpaRepository<Note, Integer> {
	
	Page<Note> findByUser(User user, Pageable pageable);
	
	Page<Note> findByTitleContaining(String searchKey, Pageable pageable);
	
	Page<Note> findByDescriptionContaining(String searchKey, Pageable pageable);
	
	Page<Note> findByTitleOrDescription(String title, String description, Pageable pageable);

}
