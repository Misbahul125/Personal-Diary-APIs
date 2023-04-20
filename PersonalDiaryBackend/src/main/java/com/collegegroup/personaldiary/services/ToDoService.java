package com.collegegroup.personaldiary.services;

import com.collegegroup.personaldiary.payloads.ToDo.ApiResponseToDoModels;
import com.collegegroup.personaldiary.payloads.ToDo.ToDoModel;

public interface ToDoService {

	public ToDoModel createToDo(ToDoModel toDoModel, Integer userId);
	
	public ToDoModel getToDoById(Integer toDoId);
	
	public ApiResponseToDoModels getToDosByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode);
	
	public ApiResponseToDoModels getAllToDos(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);
	
	public ApiResponseToDoModels searchToDosByText(String searchKey, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode);
	
	public ToDoModel updateToDo(ToDoModel toDoModel);
	
	public void deleteToDo(Integer toDoId);
	
}
