package com.collegegroup.personaldiary.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldExceptionMessage {
	
	private String fieldName;
	
	private String fieldError;

}
