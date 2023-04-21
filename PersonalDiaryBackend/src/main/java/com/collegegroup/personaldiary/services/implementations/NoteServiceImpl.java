package com.collegegroup.personaldiary.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.collegegroup.personaldiary.entities.Note;
import com.collegegroup.personaldiary.entities.User;
import com.collegegroup.personaldiary.exceptions.ResourceNotFoundException;
import com.collegegroup.personaldiary.payloads.Note.ApiResponseNoteModels;
import com.collegegroup.personaldiary.payloads.Note.NoteModel;
import com.collegegroup.personaldiary.repositories.NoteRepository;
import com.collegegroup.personaldiary.repositories.UserRepository;
import com.collegegroup.personaldiary.services.NoteService;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public NoteModel createNote(NoteModel noteModel, Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		Note note = this.modelMapper.map(noteModel, Note.class);
		note.setCreatedAt(new Date());
		note.setUpdatedAt(null);
		note.setUser(user);

		Note newNote = this.noteRepository.save(note);

		return this.modelMapper.map(newNote, NoteModel.class);

	}

	@Override
	public NoteModel getNoteById(Integer noteId) {

		Note note = this.noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "NoteId", noteId.toString()));

		return this.modelMapper.map(note, NoteModel.class);

	}

	@Override
	public ApiResponseNoteModels getNotesByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Note> pageNotes = this.noteRepository.findByUser(user, pageable);

		List<Note> allNotes = pageNotes.getContent();

		List<NoteModel> noteModels = allNotes.stream().map((note) -> this.modelMapper.map(note, NoteModel.class))
				.collect(Collectors.toList());

		ApiResponseNoteModels apiResponseNoteModels = new ApiResponseNoteModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageNotes.getNumber(), pageNotes.getSize(), pageNotes.getTotalElements(),
				pageNotes.getTotalPages(), pageNotes.isLast(), noteModels);

		if (pageNotes.getNumber() >= pageNotes.getTotalPages()) {

			apiResponseNoteModels.setMessage("No more note(s) found for this user");
			apiResponseNoteModels.setNoteModels(null);

		}

		return apiResponseNoteModels;

	}

	@Override
	public ApiResponseNoteModels getAllNotes(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Note> pageNotes = this.noteRepository.findAll(pageable);

		List<Note> allNotes = pageNotes.getContent();

		List<NoteModel> noteModels = allNotes.stream().map((note) -> this.modelMapper.map(note, NoteModel.class))
				.collect(Collectors.toList());

		ApiResponseNoteModels apiResponseNoteModels = new ApiResponseNoteModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageNotes.getNumber(), pageNotes.getSize(), pageNotes.getTotalElements(),
				pageNotes.getTotalPages(), pageNotes.isLast(), noteModels);

		if (pageNotes.getNumber() >= pageNotes.getTotalPages()) {

			apiResponseNoteModels.setMessage("No more note(s) found");
			apiResponseNoteModels.setNoteModels(null);

		}

		return apiResponseNoteModels;

	}

	@Override
	public ApiResponseNoteModels searchNotesByUserAndTitle(Integer userId, String searchKey, Integer pageNumber, Integer pageSize,
			String sortBy, Integer sortMode) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Note> pageNotes = this.noteRepository.findByUserAndTitleContaining(user, searchKey, pageable);

		List<Note> allNotes = pageNotes.getContent();

		List<NoteModel> noteModels = allNotes.stream().map((note) -> this.modelMapper.map(note, NoteModel.class))
				.collect(Collectors.toList());

		ApiResponseNoteModels apiResponseNoteModels = new ApiResponseNoteModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageNotes.getNumber(), pageNotes.getSize(), pageNotes.getTotalElements(),
				pageNotes.getTotalPages(), pageNotes.isLast(), noteModels);

		if (pageNotes.getNumber() >= pageNotes.getTotalPages()) {

			apiResponseNoteModels.setMessage("No more note(s) found for this user");
			apiResponseNoteModels.setNoteModels(null);

		}

		return apiResponseNoteModels;

	}

	@Override
	public ApiResponseNoteModels searchNotesByUserAndDescription(Integer userId, String searchKey, Integer pageNumber, Integer pageSize,
			String sortBy, Integer sortMode) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Note> pageNotes = this.noteRepository.findByUserAndDescriptionContaining(user, searchKey, pageable);

		List<Note> allNotes = pageNotes.getContent();

		List<NoteModel> noteModels = allNotes.stream().map((note) -> this.modelMapper.map(note, NoteModel.class))
				.collect(Collectors.toList());

		ApiResponseNoteModels apiResponseNoteModels = new ApiResponseNoteModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageNotes.getNumber(), pageNotes.getSize(), pageNotes.getTotalElements(),
				pageNotes.getTotalPages(), pageNotes.isLast(), noteModels);

		if (pageNotes.getNumber() >= pageNotes.getTotalPages()) {

			apiResponseNoteModels.setMessage("No more note(s) found for this user");
			apiResponseNoteModels.setNoteModels(null);

		}

		return apiResponseNoteModels;

	}
	
	@Override
	public ApiResponseNoteModels searchNotesByUserAndTitleOrDescription(Integer userId, String searchKey, Integer pageNumber, Integer pageSize,
			String sortBy, Integer sortMode) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Note> pageNotes = this.noteRepository.findByUserAndTitleOrDescription(user, searchKey, searchKey, pageable);

		List<Note> allNotes = pageNotes.getContent();

		List<NoteModel> noteModels = allNotes.stream().map((note) -> this.modelMapper.map(note, NoteModel.class))
				.collect(Collectors.toList());

		ApiResponseNoteModels apiResponseNoteModels = new ApiResponseNoteModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageNotes.getNumber(), pageNotes.getSize(), pageNotes.getTotalElements(),
				pageNotes.getTotalPages(), pageNotes.isLast(), noteModels);

		if (pageNotes.getNumber() >= pageNotes.getTotalPages()) {

			apiResponseNoteModels.setMessage("No more note(s) found for this user");
			apiResponseNoteModels.setNoteModels(null);

		}

		return apiResponseNoteModels;

	}

	@Override
	public NoteModel updateNote(NoteModel noteModel) {

		Note note = this.noteRepository.findById(noteModel.getNoteId())
				.orElseThrow(() -> new ResourceNotFoundException("Note", "note ID", noteModel.getNoteId().toString()));

		if (noteModel.getTitle() != null && !noteModel.getTitle().isEmpty())
			note.setTitle(noteModel.getTitle());

		if (noteModel.getDescription() != null && !noteModel.getDescription().isEmpty())
			note.setDescription(noteModel.getDescription());

		if (noteModel.getUser() != null && noteModel.getUser().getId() != null
				&& noteModel.getUser().getId() != note.getUser().getId()) {
			User user = this.userRepository.findById(noteModel.getUser().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Note", "UserId", noteModel.getUser().getId().toString()));
			note.setUser(user);
		}
		
		note.setUpdatedAt(new Date());

		Note updatedNote = this.noteRepository.save(note);

		return this.modelMapper.map(updatedNote, NoteModel.class);

	}

	@Override
	public void deleteNote(Integer noteId) {
		
		Note note = this.noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "note ID", noteId.toString()));
		
		this.noteRepository.delete(note);
		
	}

}
