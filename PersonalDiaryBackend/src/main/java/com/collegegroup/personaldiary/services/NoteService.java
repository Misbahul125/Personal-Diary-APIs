package com.collegegroup.personaldiary.services;

import com.collegegroup.personaldiary.payloads.Note.ApiResponseNoteModels;
import com.collegegroup.personaldiary.payloads.Note.NoteModel;

public interface NoteService {
	
	public NoteModel createNote(NoteModel noteModel, Integer userId);
	
	public NoteModel getNoteById(Integer noteId);
	
	public ApiResponseNoteModels getNotesByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode);
	
	public ApiResponseNoteModels getAllNotes(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);
	
	public ApiResponseNoteModels searchNotesByTitle(String searchKey, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode);
	
	public ApiResponseNoteModels searchNotesByDescription(String searchKey, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode);
	
	public ApiResponseNoteModels searchNotesByTitleOrDescription(String searchKey, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode);
	
	public NoteModel updateNote(NoteModel noteModel);
	
	public void deleteNote(Integer noteId);

}
