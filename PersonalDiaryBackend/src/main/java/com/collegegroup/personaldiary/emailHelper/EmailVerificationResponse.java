package com.collegegroup.personaldiary.emailHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailVerificationResponse {

	private String verificationCode;
	private String verificationMessage;
	
}
