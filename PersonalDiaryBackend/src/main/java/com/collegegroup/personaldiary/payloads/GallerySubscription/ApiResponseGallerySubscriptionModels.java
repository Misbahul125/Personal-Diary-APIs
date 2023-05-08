package com.collegegroup.personaldiary.payloads.GallerySubscription;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponseGallerySubscriptionModels {

	private boolean success;// Denotes whether api call is successful

	private int code;// shows Http codes

	private String message;// custom message

	private List<GallerySubscriptionModel> gallerySubscriptionModels;
	
}
