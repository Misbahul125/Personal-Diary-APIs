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

import com.collegegroup.personaldiary.emailHelper.ApiResponseEmailVerification;
import com.collegegroup.personaldiary.emailHelper.EmailRequest;
import com.collegegroup.personaldiary.emailHelper.EmailVerificationResponse;
import com.collegegroup.personaldiary.payloads.user.ApiResponseUserModel;
import com.collegegroup.personaldiary.payloads.user.ApiResponseUserModels;
import com.collegegroup.personaldiary.payloads.user.UserModel;
import com.collegegroup.personaldiary.services.UserService;
import com.collegegroup.personaldiary.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/sendOtp")
	public ResponseEntity<ApiResponseEmailVerification> sendOTP(@Valid @RequestBody EmailRequest emailRequest) {
		
		EmailVerificationResponse emailVerificationResponse  = this.userService.sendOTP(emailRequest);

		ApiResponseEmailVerification apiResponseEmailVerification = new ApiResponseEmailVerification(true, HttpStatus.CREATED.value(),
				"Email Sent Successfully", emailVerificationResponse);
		
		return new ResponseEntity<ApiResponseEmailVerification>(apiResponseEmailVerification, HttpStatus.CREATED);
		
	}

	// POST-create user
	@PostMapping("/")
	public ResponseEntity<ApiResponseUserModel> createUser(
			@Valid @RequestBody UserModel userModel,
			@RequestParam(value = "originalCode", required = true) Integer originalCode,
			@RequestParam(value = "userCode", required = true) Integer userCode) {
		
		ApiResponseUserModel apiResponseUserModel = null;
		
		if (originalCode.equals(userCode)) {
			
			UserModel createdUser = this.userService.createUser(userModel);
			
			apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.CREATED.value(),
					"User Created Successfully", createdUser);
			
			return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.CREATED);
		}
		else {
			apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.BAD_REQUEST.value(),
					"Invalid OTP!!", null);
			
			return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.BAD_REQUEST);
		}
		
	}

	// GET-get user
	// Single user
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponseUserModel> getSingleUser(@PathVariable Integer userId) {
		UserModel userModel = this.userService.getUserById(userId);

		ApiResponseUserModel apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.OK.value(),
				"User Fetched Successfully", userModel);

		return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponseUserModel> getUserByEmailAndPassword(@RequestBody UserModel userModel) {
		UserModel user = this.userService.getUserByEmailAndPassword(userModel.getEmail(), userModel.getPassword());

		ApiResponseUserModel apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.OK.value(),
				"User Fetched Successfully", user);

		return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.OK);
	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<ApiResponseUserModel> resetPassword(
			@RequestBody UserModel userModel,
			@RequestParam(value = "originalCode", required = true) Integer originalCode,
			@RequestParam(value = "userCode", required = true) Integer userCode) {
		
		ApiResponseUserModel apiResponseUserModel = null;
		
		System.out.println(originalCode);
		System.out.println(userCode);
		
		if (originalCode.equals(userCode)) {
			
			System.out.println("Equal");
			
			boolean isSuccess = this.userService.resetPassword(userModel.getEmail(), userModel.getPassword());
			
			if(isSuccess) {
				apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.OK.value(),
						"Password Updated Successfully", null);
				
				return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.OK);
			}
			else {
				apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.EXPECTATION_FAILED.value(),
						"Something went wrong. Please try again later.", null);
				return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.EXPECTATION_FAILED);
			}
			
		} else {
			
			System.out.println("Not Equal");

			apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.BAD_REQUEST.value(),
					"Invalid OTP!!", null);
			
			return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.BAD_REQUEST);
			
		}
		
	}

	// Multiple user
	@GetMapping("/all")
	public ResponseEntity<ApiResponseUserModels> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_USER_ID, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE, required = false) Integer sortMode) {

		ApiResponseUserModels apiResponseUserModels = this.userService.getAllUsers(pageNumber, pageSize, sortBy,
				sortMode);

		return new ResponseEntity<ApiResponseUserModels>(apiResponseUserModels, HttpStatus.OK);
	}

	// PUT-update user
	@PutMapping("/")
	public ResponseEntity<ApiResponseUserModel> updateUser(@Valid @RequestBody UserModel userModel) {
		UserModel updatedUser = this.userService.updateUser(userModel);

		ApiResponseUserModel apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.OK.value(),
				"User Updated Successfully", updatedUser);

		return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.OK);
	}

	// DELETE-delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseUserModel> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUserById(userId);

		ApiResponseUserModel apiResponseUserModel = new ApiResponseUserModel(true, HttpStatus.OK.value(),
				"User Deleted Successfully", null);

		return new ResponseEntity<ApiResponseUserModel>(apiResponseUserModel, HttpStatus.OK);
	}

}
