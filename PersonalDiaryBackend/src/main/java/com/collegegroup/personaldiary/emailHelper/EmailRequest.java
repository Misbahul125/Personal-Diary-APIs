package com.collegegroup.personaldiary.emailHelper;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailRequest {

	@Email(message = "Email is invalid !!")
	private String recipientEmail;
	
	private Integer emailType;
	
}
