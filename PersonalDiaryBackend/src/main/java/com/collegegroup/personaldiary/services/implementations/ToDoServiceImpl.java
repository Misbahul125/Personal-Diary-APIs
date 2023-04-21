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

import com.collegegroup.personaldiary.entities.ToDo;
import com.collegegroup.personaldiary.entities.User;
import com.collegegroup.personaldiary.exceptions.ResourceNotFoundException;
import com.collegegroup.personaldiary.payloads.ToDo.ApiResponseToDoModels;
import com.collegegroup.personaldiary.payloads.ToDo.ToDoModel;
import com.collegegroup.personaldiary.repositories.ToDoRepository;
import com.collegegroup.personaldiary.repositories.UserRepository;
import com.collegegroup.personaldiary.services.ToDoService;

@Service
public class ToDoServiceImpl implements ToDoService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ToDoRepository toDoRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ToDoModel createToDo(ToDoModel toDoModel, Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		ToDo toDo = this.modelMapper.map(toDoModel, ToDo.class);
		toDo.setCreatedAt(new Date());
		toDo.setUpdatedAt(null);
		toDo.setUser(user);

		ToDo newToDo = this.toDoRepository.save(toDo);

		return this.modelMapper.map(newToDo, ToDoModel.class);

	}

	@Override
	public ToDoModel getToDoById(Integer toDoId) {

		ToDo toDo = this.toDoRepository.findById(toDoId)
				.orElseThrow(() -> new ResourceNotFoundException("ToDo", "ToDoId", toDoId.toString()));

		return this.modelMapper.map(toDo, ToDoModel.class);

	}

	@Override
	public ApiResponseToDoModels getToDosByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<ToDo> pageToDos = this.toDoRepository.findByUser(user, pageable);

		List<ToDo> allToDos = pageToDos.getContent();

		List<ToDoModel> toDoModels = allToDos.stream().map((toDo) -> this.modelMapper.map(toDo, ToDoModel.class))
				.collect(Collectors.toList());

		ApiResponseToDoModels apiResponseToDoModels = new ApiResponseToDoModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageToDos.getNumber(), pageToDos.getSize(), pageToDos.getTotalElements(),
				pageToDos.getTotalPages(), pageToDos.isLast(), toDoModels);

		if (pageToDos.getNumber() >= pageToDos.getTotalPages()) {

			apiResponseToDoModels.setMessage("No more todo(s) found for this user");
			apiResponseToDoModels.setToDoModels(null);

		}

		return apiResponseToDoModels;

	}

	@Override
	public ApiResponseToDoModels getAllToDos(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<ToDo> pageToDos = this.toDoRepository.findAll(pageable);

		List<ToDo> allToDos = pageToDos.getContent();

		List<ToDoModel> toDoModels = allToDos.stream().map((toDo) -> this.modelMapper.map(toDo, ToDoModel.class))
				.collect(Collectors.toList());

		ApiResponseToDoModels apiResponseToDoModels = new ApiResponseToDoModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageToDos.getNumber(), pageToDos.getSize(), pageToDos.getTotalElements(),
				pageToDos.getTotalPages(), pageToDos.isLast(), toDoModels);

		if (pageToDos.getNumber() >= pageToDos.getTotalPages()) {

			apiResponseToDoModels.setMessage("No more todo(s) found");
			apiResponseToDoModels.setToDoModels(null);

		}

		return apiResponseToDoModels;

	}

	@Override
	public ApiResponseToDoModels searchToDosByUserAndText(Integer userId, String searchKey, Integer pageNumber, Integer pageSize,
			String sortBy, Integer sortMode) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<ToDo> pageToDos = this.toDoRepository.findByUserAndTextContaining(user, searchKey, pageable);

		List<ToDo> allToDos = pageToDos.getContent();

		List<ToDoModel> toDoModels = allToDos.stream().map((toDo) -> this.modelMapper.map(toDo, ToDoModel.class))
				.collect(Collectors.toList());

		ApiResponseToDoModels apiResponseToDoModels = new ApiResponseToDoModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageToDos.getNumber(), pageToDos.getSize(), pageToDos.getTotalElements(),
				pageToDos.getTotalPages(), pageToDos.isLast(), toDoModels);

		if (pageToDos.getNumber() >= pageToDos.getTotalPages()) {

			apiResponseToDoModels.setMessage("No more todo(s) found");
			apiResponseToDoModels.setToDoModels(null);

		}

		return apiResponseToDoModels;

	}

	@Override
	public ToDoModel updateToDo(ToDoModel toDoModel) {

		ToDo toDo = this.toDoRepository.findById(toDoModel.getToDoId())
				.orElseThrow(() -> new ResourceNotFoundException("ToDo", "ToDoId", toDoModel.getToDoId().toString()));
		
		if (toDoModel.getText() != null && !toDoModel.getText().isEmpty())
			toDo.setText(toDoModel.getText());

		if (toDoModel.getUser() != null && toDoModel.getUser().getId() != null
				&& toDoModel.getUser().getId() != toDo.getUser().getId()) {
			User user = this.userRepository.findById(toDoModel.getUser().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Note", "UserId", toDoModel.getUser().getId().toString()));
			toDo.setUser(user);
		}
		
		toDo.setUpdatedAt(new Date());

		ToDo updatedToDo = this.toDoRepository.save(toDo);

		return this.modelMapper.map(updatedToDo, ToDoModel.class);
		
	}

	@Override
	public void deleteToDo(Integer toDoId) {

		ToDo toDo = this.toDoRepository.findById(toDoId)
				.orElseThrow(() -> new ResourceNotFoundException("ToDo", "ToDoId", toDoId.toString()));
		
		this.toDoRepository.delete(toDo);
		
	}

}
