package com.collegegroup.personaldiary.payloads.Note;

import java.util.Date;

import com.collegegroup.personaldiary.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoteModel {

	private Integer noteId;
	
	private String title;
	
	private String description;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	//@JsonIgnore
	private User user;
	
}
