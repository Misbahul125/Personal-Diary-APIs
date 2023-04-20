package com.collegegroup.personaldiary.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer noteId;
	
	private String title;
	
	private String description;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	@ManyToOne
	private User user;
	
}
