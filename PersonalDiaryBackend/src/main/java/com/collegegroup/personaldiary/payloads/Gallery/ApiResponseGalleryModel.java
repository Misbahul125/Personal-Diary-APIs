package com.collegegroup.personaldiary.payloads.Gallery;

import com.collegegroup.personaldiary.payloads.ToDo.ToDoModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponseGalleryModel {

	private boolean success;//Denotes whether api call is successful
	
	private int code;//shows Http codes 
	
	private String message;//custom message
	
	private GalleryModel galleryModel;
	
}
