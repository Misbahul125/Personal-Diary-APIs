package com.collegegroup.personaldiary.services;

import com.collegegroup.personaldiary.emailHelper.EmailRequest;
import com.collegegroup.personaldiary.emailHelper.EmailVerificationResponse;
import com.collegegroup.personaldiary.entities.User;
import com.collegegroup.personaldiary.payloads.user.ApiResponseUserModels;
import com.collegegroup.personaldiary.payloads.user.UserModel;

public interface UserService {
	
	//public UserModel registerNewUser(UserModel userModel);
	
	public EmailVerificationResponse sendOTP(EmailRequest emailRequest);
	
	public UserModel createUser(UserModel userModel);
	
	public UserModel getUserById(Integer userId);
	
	public UserModel getUserByEmailAndPassword(String email, String password);
	
	public boolean resetPassword(String email, String password);
	
	public User getUserByEmail(String email);

	public ApiResponseUserModels getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);
	
	public UserModel updateUser(UserModel userModel);
	
	public void deleteUserById(Integer userId);

}
