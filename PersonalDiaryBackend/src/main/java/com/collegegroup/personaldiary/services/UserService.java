package com.collegegroup.personaldiary.services;

import com.collegegroup.personaldiary.payloads.user.ApiResponseUserModels;
import com.collegegroup.personaldiary.payloads.user.UserModel;

public interface UserService {
	
	//public UserModel registerNewUser(UserModel userModel);
	
	public UserModel createUser(UserModel userModel);
	
	public UserModel getUserById(Integer userId);

	public ApiResponseUserModels getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);
	
	public UserModel updateUser(UserModel userModel);
	
	public void deleteUserById(Integer userId);
}
