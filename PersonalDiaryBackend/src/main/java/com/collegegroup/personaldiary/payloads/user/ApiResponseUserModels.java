//Used for multiple users
package com.collegegroup.personaldiary.payloads.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponseUserModels {

	private boolean success;

	private int code;

	private String message;

	private Integer pageNumber;

	private Integer pageSize;

	private Long totalItems;

	private Integer totalPages;

	private Boolean isLastPage;

	private List<UserModel> userModels;

}
