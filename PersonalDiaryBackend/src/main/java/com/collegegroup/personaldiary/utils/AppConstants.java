package com.collegegroup.personaldiary.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class AppConstants {

	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "10";
	
	public static final String SORT_BY_USER_ID = "id";
	public static final String SORT_BY_NOTE_ID = "noteId";
	public static final String SORT_BY_CREATED_AT = "createdAt";
	public static final String SORT_BY_UPDATED_AT = "updatedAt";
	
	public static final String SORT_MODE_ASCENDING = "0";
	public static final String SORT_MODE_DESCENDING = "1";
	
	//public static final String USER_PROFILE_IMAGE_PATH = "profileImages";
	//public static final String USER_GALLERY_IMAGE_PATH = environment.getProperty("project.image").concat("galleryImages");

	public static final Integer ADMIN_USER = 501;
	public static final Integer NORMAL_USER = 502;
	
	public static final Integer EMAIL_TYPE_AUTHORIZATION_OTP = 0;
	public static final Integer EMAIL_TYPE_RESET_PASSWORD_OTP = 1;
	
	//public static final String SEND_EMAIL = "SEND_EMAIL";
	//public static final String RESEND_EMAIL = "RESEND_EMAIL";
	
}
