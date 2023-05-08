package com.collegegroup.personaldiary.payloads.Feedback;

import java.util.Date;

import com.collegegroup.personaldiary.payloads.user.UserModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackModel {

	private Integer feedbackId;
	
	private String description;
	
	private Date createdAt;

	private UserModel user;
	
}
