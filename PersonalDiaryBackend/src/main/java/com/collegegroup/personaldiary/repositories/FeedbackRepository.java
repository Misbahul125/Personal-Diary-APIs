package com.collegegroup.personaldiary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collegegroup.personaldiary.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}
