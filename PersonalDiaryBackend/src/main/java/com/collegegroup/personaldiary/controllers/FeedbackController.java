package com.collegegroup.personaldiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collegegroup.personaldiary.payloads.Feedback.ApiResponseFeedbackModel;
import com.collegegroup.personaldiary.payloads.Feedback.ApiResponseFeedbackModels;
import com.collegegroup.personaldiary.payloads.Feedback.FeedbackModel;
import com.collegegroup.personaldiary.services.FeedbackService;
import com.collegegroup.personaldiary.utils.AppConstants;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	
	@PostMapping("/user/{userId}/createFeedback")
	public ResponseEntity<ApiResponseFeedbackModel> createFeedback(@RequestBody FeedbackModel feedbackModel,
			@PathVariable Integer userId) {
		
		FeedbackModel createdFeedback = this.feedbackService.createFeedback(userId, feedbackModel);

		ApiResponseFeedbackModel apiResponseFeedbackModel = new ApiResponseFeedbackModel(true, HttpStatus.CREATED.value(),
				"Feedback Created Successfully", createdFeedback);

		return new ResponseEntity<ApiResponseFeedbackModel>(apiResponseFeedbackModel, HttpStatus.CREATED);
	}
	
	@GetMapping("/feedback/{feedbackId}")
	public ResponseEntity<ApiResponseFeedbackModel> getFeedbackById(@PathVariable("feedbackId") Integer feedbackId) {

		FeedbackModel feedbackModel = this.feedbackService.getFeedbackById(feedbackId);

		ApiResponseFeedbackModel apiResponseFeedbackModel = new ApiResponseFeedbackModel(true, HttpStatus.OK.value(),
				"Feedback Fetched Successfully", feedbackModel);

		return new ResponseEntity<ApiResponseFeedbackModel>(apiResponseFeedbackModel, HttpStatus.OK);

	}
	
	@GetMapping("/feedbacks/all")
	public ResponseEntity<ApiResponseFeedbackModels> getAllFeedbacks(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseFeedbackModels apiResponseFeedbackModels = this.feedbackService.getAllFeedbacks(pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseFeedbackModels>(apiResponseFeedbackModels, HttpStatus.OK);

	}
	
	@DeleteMapping("/feedback/{feedbackId}")
	public ResponseEntity<ApiResponseFeedbackModel> deleteFeedback(@PathVariable("feedbackId") Integer feedbackId) {

		this.feedbackService.deleteFeedback(feedbackId);

		ApiResponseFeedbackModel apiResponseFeedbackModel = new ApiResponseFeedbackModel(true, HttpStatus.OK.value(),
				"Feedback Deleted Successfully", null);

		return new ResponseEntity<ApiResponseFeedbackModel>(apiResponseFeedbackModel, HttpStatus.OK);

	}
	
}
