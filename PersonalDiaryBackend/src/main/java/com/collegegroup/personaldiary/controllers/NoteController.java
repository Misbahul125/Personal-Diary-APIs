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

import com.collegegroup.personaldiary.payloads.Note.ApiResponseNoteModel;
import com.collegegroup.personaldiary.payloads.Note.ApiResponseNoteModels;
import com.collegegroup.personaldiary.payloads.Note.NoteModel;
import com.collegegroup.personaldiary.services.NoteService;
import com.collegegroup.personaldiary.utils.AppConstants;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class NoteController {

	@Autowired
	private NoteService noteService;

	// create
	@PostMapping("/user/{userId}/createNote")
	public ResponseEntity<ApiResponseNoteModel> createNote(@RequestBody NoteModel noteModel,
			@PathVariable Integer userId) {
		NoteModel createdNote = this.noteService.createNote(noteModel, userId);

		ApiResponseNoteModel apiResponseNoteModel = new ApiResponseNoteModel(true, HttpStatus.CREATED.value(),
				"Note Created Successfully", createdNote);

		return new ResponseEntity<ApiResponseNoteModel>(apiResponseNoteModel, HttpStatus.CREATED);
	}

	@GetMapping("/note/{noteId}")
	public ResponseEntity<ApiResponseNoteModel> getNoteById(@PathVariable Integer noteId) {

		NoteModel noteModel = this.noteService.getNoteById(noteId);

		ApiResponseNoteModel apiResponseNoteModel = new ApiResponseNoteModel(true, HttpStatus.OK.value(),
				"Note Fetched Successfully", noteModel);

		return new ResponseEntity<ApiResponseNoteModel>(apiResponseNoteModel, HttpStatus.OK);

	}

	// get by user
	@GetMapping("/user/{userId}/notes")
	public ResponseEntity<ApiResponseNoteModels> getNotesByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseNoteModels apiResponseNoteModels = this.noteService.getNotesByUser(userId, pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseNoteModels>(apiResponseNoteModels, HttpStatus.OK);
	}

	@GetMapping("/notes")
	public ResponseEntity<ApiResponseNoteModels> getAllNotes(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseNoteModels apiResponseNoteModels = this.noteService.getAllNotes(pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseNoteModels>(apiResponseNoteModels, HttpStatus.OK);

	}

	// search notes
	@GetMapping("/user/{userId}/notes/searchByTitle")
	public ResponseEntity<ApiResponseNoteModels> searchNotesByTitleAndUser(
			@PathVariable Integer userId,
			@RequestParam(value = "searchKey", required = true) String searchKey,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseNoteModels apiResponseNoteModels = this.noteService.searchNotesByUserAndTitle(userId, searchKey, pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseNoteModels>(apiResponseNoteModels, HttpStatus.OK);

	}
	
	@GetMapping("/user/{userId}/notes/searchByDescription")
	public ResponseEntity<ApiResponseNoteModels> searchNotesByDescriptionAndUser(
			@PathVariable Integer userId,
			@RequestParam(value = "searchKey", required = true) String searchKey,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseNoteModels apiResponseNoteModels = this.noteService.searchNotesByUserAndDescription(userId, searchKey, pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseNoteModels>(apiResponseNoteModels, HttpStatus.OK);

	}
	
	@GetMapping("/user/{userId}/notes/searchAll")
	public ResponseEntity<ApiResponseNoteModels> searchNotesByUserAndTitleOrDescription(
			@PathVariable Integer userId,
			@RequestParam(value = "searchKey", required = true) String searchKey,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseNoteModels apiResponseNoteModels = this.noteService.searchNotesByUserAndTitleOrDescription(userId, searchKey, pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseNoteModels>(apiResponseNoteModels, HttpStatus.OK);

	}

	@PutMapping("/note")
	public ResponseEntity<ApiResponseNoteModel> updateNote(@RequestBody NoteModel noteModel) {

		NoteModel updatedNote = this.noteService.updateNote(noteModel);

		ApiResponseNoteModel apiResponseNoteModel = new ApiResponseNoteModel(true, HttpStatus.OK.value(),
				"Note Updated Successfully", updatedNote);

		return new ResponseEntity<ApiResponseNoteModel>(apiResponseNoteModel, HttpStatus.OK);

	}

	@DeleteMapping("/note/{noteId}")
	public ResponseEntity<ApiResponseNoteModel> delete(@PathVariable Integer noteId) {

		this.noteService.deleteNote(noteId);

		ApiResponseNoteModel apiResponseNoteModel = new ApiResponseNoteModel(true, HttpStatus.OK.value(),
				"Note Deleted Successfully", null);

		return new ResponseEntity<ApiResponseNoteModel>(apiResponseNoteModel, HttpStatus.OK);

	}

}
