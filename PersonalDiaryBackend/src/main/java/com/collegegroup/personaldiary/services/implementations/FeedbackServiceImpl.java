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

import com.collegegroup.personaldiary.entities.Feedback;
import com.collegegroup.personaldiary.entities.User;
import com.collegegroup.personaldiary.exceptions.ResourceNotFoundException;
import com.collegegroup.personaldiary.payloads.Feedback.ApiResponseFeedbackModels;
import com.collegegroup.personaldiary.payloads.Feedback.FeedbackModel;
import com.collegegroup.personaldiary.payloads.user.ApiResponseUserModels;
import com.collegegroup.personaldiary.payloads.user.UserModel;
import com.collegegroup.personaldiary.repositories.FeedbackRepository;
import com.collegegroup.personaldiary.repositories.UserRepository;
import com.collegegroup.personaldiary.services.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public FeedbackModel createFeedback(Integer userId, FeedbackModel feedbackModel) {

		User user = this.userRepository.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("User", "user ID", userId.toString())));

		Feedback feedback = this.modelMapper.map(feedbackModel, Feedback.class);

		feedback.setUser(user);
		feedback.setCreatedAt(new Date());

		Feedback createdFeedback = this.feedbackRepository.save(feedback);

		return this.modelMapper.map(createdFeedback, FeedbackModel.class);

	}

	@Override
	public FeedbackModel getFeedbackById(Integer feedbackId) {

		Feedback feedback = this.feedbackRepository.findById(feedbackId)
				.orElseThrow((() -> new ResourceNotFoundException("Feedback", "feedbackId", feedbackId.toString())));

		return this.modelMapper.map(feedback, FeedbackModel.class);

	}

	@Override
	public ApiResponseFeedbackModels getAllFeedbacks(Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode) {

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Feedback> pageFeedbacks = this.feedbackRepository.findAll(pageable);

		List<Feedback> allFeedbacks = pageFeedbacks.getContent();

		List<FeedbackModel> feedbackModels = allFeedbacks.stream().map(feedback -> this.modelMapper.map(feedback, FeedbackModel.class))
				.collect(Collectors.toList());

		ApiResponseFeedbackModels apiResponseFeedbackModels = new ApiResponseFeedbackModels(true, HttpStatus.OK.value(),
				"Feedbacks Fetched Successfully", pageFeedbacks.getNumber(), pageFeedbacks.getSize(), pageFeedbacks.getTotalElements(),
				pageFeedbacks.getTotalPages(), pageFeedbacks.isLast(), feedbackModels);

		if (pageFeedbacks.getNumber() >= pageFeedbacks.getTotalPages()) {

			apiResponseFeedbackModels.setMessage("No more feedback(s) found");
			apiResponseFeedbackModels.setFeedbackModels(null);

		}

		return apiResponseFeedbackModels;

	}

	@Override
	public void deleteFeedback(Integer feedbackId) {
		
		Feedback feedback = this.feedbackRepository.findById(feedbackId)
				.orElseThrow((() -> new ResourceNotFoundException("Feedback", "feedbackId", feedbackId.toString())));
		
		this.feedbackRepository.delete(feedback);
		
	}

}
