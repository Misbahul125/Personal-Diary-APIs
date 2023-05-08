package com.collegegroup.personaldiary.services;

import com.collegegroup.personaldiary.payloads.Feedback.ApiResponseFeedbackModels;
import com.collegegroup.personaldiary.payloads.Feedback.FeedbackModel;

public interface FeedbackService {

	public FeedbackModel createFeedback(Integer userId, FeedbackModel feedbackModel);
	
	public FeedbackModel getFeedbackById(Integer feedbackId);
	
	public ApiResponseFeedbackModels getAllFeedbacks(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);
	
	public void deleteFeedback(Integer feedbackId);
	
}
