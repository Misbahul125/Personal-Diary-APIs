package com.collegegroup.personaldiary.payloads.ToDo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponseToDoModel {
	
	private boolean success;//Denotes whether api call is successful
	
	private int code;//shows Http codes 
	
	private String message;//custom message
	
	private ToDoModel toDoModel;

}
