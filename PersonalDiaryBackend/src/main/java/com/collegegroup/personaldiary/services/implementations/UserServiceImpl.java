package com.collegegroup.personaldiary.services.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.collegegroup.personaldiary.emailHelper.EmailHelper;
import com.collegegroup.personaldiary.emailHelper.EmailRequest;
import com.collegegroup.personaldiary.emailHelper.EmailVerificationResponse;
import com.collegegroup.personaldiary.entities.User;
import com.collegegroup.personaldiary.exceptions.CredentialException;
import com.collegegroup.personaldiary.exceptions.ResourceNotFoundException;
import com.collegegroup.personaldiary.payloads.user.ApiResponseUserModels;
import com.collegegroup.personaldiary.payloads.user.UserModel;
import com.collegegroup.personaldiary.repositories.UserRepository;
import com.collegegroup.personaldiary.services.S3FileService;
import com.collegegroup.personaldiary.services.UserService;
import com.collegegroup.personaldiary.utils.AESHelper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private S3FileService s3FileService;

	@Value("${project.image}")
	private String path;

	@Autowired
	private ModelMapper modelMapper;

//	@Override
//	public UserModel registerNewUser(UserModel userModel) {
//		
//		User user = this.modelMapper.map(userModel, User.class);
//		
//		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
//		
//		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
//		
//		user.getRoles().add(role);
//		
//		User newUser = this.userRepository.save(user);
//		
//		return this.modelMapper.map(newUser, UserModel.class);
//		
//	}

	@Override
	public EmailVerificationResponse sendOTP(EmailRequest emailRequest) {

		EmailHelper emailHelper = new EmailHelper();

		String code = emailHelper.sendEmail(emailRequest);

		EmailVerificationResponse emailVerificationResponse = new EmailVerificationResponse(code,
				"Veification Code is sent successfully !!");

		return emailVerificationResponse;

	}

	@Override
	public UserModel createUser(UserModel userModel) {

		User user = getUserByEmail(userModel.getEmail());

		if (user != null) {
			user = null;
			throw new CredentialException("This email ID already exist. Please try to login.");
		}

		try {
			userModel.setPassword(AESHelper.encrypt(userModel.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		User createdUser = this.modelMapper.map(userModel, User.class);

		createdUser = this.userRepository.save(createdUser);

		return this.modelMapper.map(createdUser, UserModel.class);
	}

	@Override
	public UserModel getUserById(Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("User", "user ID", userId.toString())));

		return this.modelMapper.map(user, UserModel.class);
	}

	@Override
	public UserModel getUserByEmailAndPassword(String email, String password) {

		User user = this.userRepository.findByEmail(email);
		// .orElseThrow((() -> new ResourceNotFoundException("User", "user email",
		// email)));

		if (user != null) {

			// encrypting password
			String encryptedPassword = "";
			try {
				encryptedPassword = AESHelper.encrypt(password);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (user.getPassword().equals(encryptedPassword)) {
				return this.modelMapper.map(user, UserModel.class);
			} else {
				throw new CredentialException("Incorrect Password");
			}

		} else {
			throw new ResourceNotFoundException("User", "user email", email);
		}

	}

	@Override
	public boolean resetPassword(String email, String password) {

		User user = getUserByEmail(email);

		if (user != null) {

			try {
				user.setPassword(AESHelper.encrypt(password));
				this.userRepository.save(user);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			throw new ResourceNotFoundException("User", "user email", email);
		}

		return true;

	}

	@Override
	public User getUserByEmail(String email) {

		User user = this.userRepository.findByEmail(email);

		return user;

	}

	@Override
	public ApiResponseUserModels getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<User> pageUsers = this.userRepository.findAll(pageable);

		List<User> allUsers = pageUsers.getContent();

		List<UserModel> userModels = allUsers.stream().map(user -> this.modelMapper.map(user, UserModel.class))
				.collect(Collectors.toList());

		ApiResponseUserModels apiResponseUserModels = new ApiResponseUserModels(true, HttpStatus.OK.value(),
				"Users Fetched Successfully", pageUsers.getNumber(), pageUsers.getSize(), pageUsers.getTotalElements(),
				pageUsers.getTotalPages(), pageUsers.isLast(), userModels);

		if (pageUsers.getNumber() >= pageUsers.getTotalPages()) {

			apiResponseUserModels.setMessage("No more user(s) found");
			apiResponseUserModels.setUserModels(null);

		}

		return apiResponseUserModels;
	}

	@Override
	public UserModel updateUser(UserModel userModel) {

		// getting the user from DB, also helps us to check if user is present in DB or
		// not
		User userFromDB = this.userRepository.findById(userModel.getId())
				.orElseThrow((() -> new ResourceNotFoundException("User", "user ID", userModel.getId().toString())));

		if (userModel.getFullName() != null && !userModel.getFullName().isEmpty())
			userFromDB.setFullName(userModel.getFullName());

		if (userModel.getEmail() != null && !userModel.getEmail().isEmpty())
			userFromDB.setEmail(userModel.getEmail());

		if (userModel.getAbout() != null && !userModel.getAbout().isEmpty())
			userFromDB.setAbout(userModel.getAbout());

		if (userModel.getRole() != null && !userModel.getRole().isEmpty())
			userFromDB.setRole(userModel.getRole());

		if (userModel.isActive() != userFromDB.isActive())
			userFromDB.setActive(userModel.isActive());

		User updatedUser = this.userRepository.save(userFromDB);

		return this.modelMapper.map(updatedUser, UserModel.class);
	}

	@Override
	public void deleteUserById(Integer userId) {

		User userFromDB = this.userRepository.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("User", "user ID", userId.toString())));

		this.userRepository.delete(userFromDB);
	}

	
	// images

	@Override
	public UserModel uploadUserProfileImage(Integer userId, MultipartFile multipartFile) throws IOException {

		User user = this.userRepository.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("User", "user ID", userId.toString())));

		//check if image already exists or not
		//if so then delete the existing one
		if (user.getImageURL() != null && !user.getImageURL().isEmpty()) {
			
			List<String> fileURLsToBeDeleted = new ArrayList<>();
			fileURLsToBeDeleted.add(user.getImageURL());
			
			this.s3FileService.deleteFiles(fileURLsToBeDeleted);
		}

		//upload new image
		List<MultipartFile> multipartFiles = new ArrayList<>();
		multipartFiles.add(multipartFile);
		
		List<String> savedFileURLs = this.s3FileService.saveFile(multipartFiles);

		user.setImageURL(savedFileURLs.get(0));
		User updatedUser = this.userRepository.save(user);

		return this.modelMapper.map(updatedUser, UserModel.class);

	}

//	@Override
//	public InputStream getUserProfileImage(Integer userId) throws Exception {
//
//		User user = this.userRepository.findById(userId)
//				.orElseThrow((() -> new ResourceNotFoundException("User", "user ID", userId.toString())));
//
//		if (user.getImageURL() != null && !user.getImageURL().isEmpty()) {
//
//			InputStream inputStream = this.fileService.getResource(path, user.getImageURL());
//
//			return inputStream;
//
//		}
//
//		return null;
//
//	}
	
	public boolean deleteUserProfileImage(Integer userId) throws Exception {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("User", "user ID", userId.toString())));
		
		if (user.getImageURL() != null && !user.getImageURL().isEmpty()) {

			List<String> fileURLsToBeDeleted = new ArrayList<>();
			fileURLsToBeDeleted.add(user.getImageURL());

			this.s3FileService.deleteFiles(fileURLsToBeDeleted);
			
			user.setImageURL(null);
			this.userRepository.save(user);
			
			return true;
			
		}
		
		return false;
		
	}

}
