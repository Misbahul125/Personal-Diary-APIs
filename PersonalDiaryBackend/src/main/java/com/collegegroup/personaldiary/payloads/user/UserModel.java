/**
 * 
 * It was Originally UserDTO but was changed to userModel
 * This will contain user details. 
 */
 
package com.collegegroup.personaldiary.payloads.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {
	
	private Integer id;
	
	@NotEmpty(message = "Name cannot be empty !!")
	@Size(min = 5, message = "Name should have at least 5 characters !!")
	private String fullName;
	
	@Email(message = "Email is invalid !!")
	private String email;
	
	//@NotEmpty(message = "Password cannot be empty !!")
	@Size(min = 8, message = "Password should have at least 8 characters !!")
	private String password;
	
	private String role;
	
	private boolean active;
	
	private String imageURL;
	
	@NotEmpty(message = "About cannot be empty !!")
	private String about;

}
