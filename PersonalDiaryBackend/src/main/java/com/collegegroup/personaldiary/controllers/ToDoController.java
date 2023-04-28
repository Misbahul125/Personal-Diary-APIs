package com.collegegroup.personaldiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collegegroup.personaldiary.payloads.ToDo.ApiResponseToDoModel;
import com.collegegroup.personaldiary.payloads.ToDo.ApiResponseToDoModels;
import com.collegegroup.personaldiary.payloads.ToDo.ToDoModel;
import com.collegegroup.personaldiary.services.ToDoService;
import com.collegegroup.personaldiary.utils.AppConstants;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ToDoController {

	@Autowired
	private ToDoService toDoService;

	// create
	@PostMapping("/user/{userId}/createTodo")
	public ResponseEntity<ApiResponseToDoModel> createToDo(@RequestBody ToDoModel toDoModel,
			@PathVariable Integer userId) {
		ToDoModel createdToDo = this.toDoService.createToDo(toDoModel, userId);

		ApiResponseToDoModel apiResponseToDoModel = new ApiResponseToDoModel(true, HttpStatus.CREATED.value(),
				"ToDo Created Successfully", createdToDo);

		return new ResponseEntity<ApiResponseToDoModel>(apiResponseToDoModel, HttpStatus.CREATED);
	}

	@GetMapping("/todo/{todoId}")
	public ResponseEntity<ApiResponseToDoModel> getToDoById(@PathVariable("todoId") Integer toDoId) {

		ToDoModel toDoModel = this.toDoService.getToDoById(toDoId);

		ApiResponseToDoModel apiResponseToDoModel = new ApiResponseToDoModel(true, HttpStatus.OK.value(),
				"ToDo Fetched Successfully", toDoModel);

		return new ResponseEntity<ApiResponseToDoModel>(apiResponseToDoModel, HttpStatus.OK);

	}

	// get by user
	@GetMapping("/user/{userId}/todos")
	public ResponseEntity<ApiResponseToDoModels> getToDOsByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseToDoModels apiResponseToDoModels = this.toDoService.getToDosByUser(userId, pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseToDoModels>(apiResponseToDoModels, HttpStatus.OK);
	}

	@GetMapping("/todos")
	public ResponseEntity<ApiResponseToDoModels> getAllToDos(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseToDoModels apiResponseToDoModels = this.toDoService.getAllToDos(pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseToDoModels>(apiResponseToDoModels, HttpStatus.OK);

	}

	// search notes
	@GetMapping("/user/{userId}/todos/searchByText")
	public ResponseEntity<ApiResponseToDoModels> searchToDosByUserAndText(
			@PathVariable Integer userId,
			@RequestParam(value = "searchKey", required = true) String searchKey,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseToDoModels apiResponseToDoModels = this.toDoService.searchToDosByUserAndText(userId, searchKey, pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseToDoModels>(apiResponseToDoModels, HttpStatus.OK);

	}

	@PutMapping("/todo")
	public ResponseEntity<ApiResponseToDoModel> updateToDo(@RequestBody ToDoModel toDoModel) {

		ToDoModel updatedToDo = this.toDoService.updateToDo(toDoModel);

		ApiResponseToDoModel apiResponseToDoModel = new ApiResponseToDoModel(true, HttpStatus.OK.value(),
				"Todo Updated Successfully", updatedToDo);

		return new ResponseEntity<ApiResponseToDoModel>(apiResponseToDoModel, HttpStatus.OK);

	}
	
	@PutMapping("/todo/{todoId}/updateStatus")
	public ResponseEntity<ApiResponseToDoModel> updateCompletionStatus(@PathVariable("todoId") Integer toDoId) {
		
		String status = "";

		ToDoModel updatedToDo = this.toDoService.updateCompletionStatus(toDoId);
		
		if (updatedToDo.getIsCompleted())
			status = "complete";
		else
			status = "incomplete";

		ApiResponseToDoModel apiResponseToDoModel = new ApiResponseToDoModel(true, HttpStatus.OK.value(),
				"Todo is marked as "+status, updatedToDo);

		return new ResponseEntity<ApiResponseToDoModel>(apiResponseToDoModel, HttpStatus.OK);

	}

	@DeleteMapping("/todo/{todoId}")
	public ResponseEntity<ApiResponseToDoModel> deleteToDo(@PathVariable("todoId") Integer toDoId) {

		this.toDoService.deleteToDo(toDoId);

		ApiResponseToDoModel apiResponseToDoModel = new ApiResponseToDoModel(true, HttpStatus.OK.value(),
				"Todo Deleted Successfully", null);

		return new ResponseEntity<ApiResponseToDoModel>(apiResponseToDoModel, HttpStatus.OK);

	}

}
