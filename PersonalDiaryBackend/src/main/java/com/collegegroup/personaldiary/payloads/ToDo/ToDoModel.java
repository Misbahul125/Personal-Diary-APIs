package com.collegegroup.personaldiary.payloads.ToDo;

import java.util.Date;

import com.collegegroup.personaldiary.payloads.user.UserModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ToDoModel {

	private Integer toDoId;
	
	private String text;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private UserModel user;
	
}
