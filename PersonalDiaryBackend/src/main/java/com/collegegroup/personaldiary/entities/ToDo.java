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
@Table(name = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ToDo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer toDoId;
	
	private String text;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private Boolean isCompleted;
	
	@ManyToOne
	private User user;
	
}
