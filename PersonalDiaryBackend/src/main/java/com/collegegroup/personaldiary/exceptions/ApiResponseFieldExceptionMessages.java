package com.collegegroup.personaldiary.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponseFieldExceptionMessages {

	private boolean success;// Denotes whether api call is successful

	private int code;// shows Http codes

	private List<FieldExceptionMessage> messages;

}
