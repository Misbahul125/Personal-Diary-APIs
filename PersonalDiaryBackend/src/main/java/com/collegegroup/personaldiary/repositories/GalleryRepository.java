package com.collegegroup.personaldiary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.collegegroup.personaldiary.entities.Gallery;
import com.collegegroup.personaldiary.entities.User;

public interface GalleryRepository extends JpaRepository<Gallery, Integer> {

	Page<Gallery> findByUser(User user, Pageable pageable);

	Page<Gallery> findByUserAndCaptionContaining(User user, String caption, Pageable pageable);

	Page<Gallery> findByUserAndDescriptionContaining(User user, String description, Pageable pageable);

}
