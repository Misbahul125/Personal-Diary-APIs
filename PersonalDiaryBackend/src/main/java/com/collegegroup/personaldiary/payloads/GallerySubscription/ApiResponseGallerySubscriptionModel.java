package com.collegegroup.personaldiary.payloads.GallerySubscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponseGallerySubscriptionModel {

	private boolean success;// Denotes whether api call is successful

	private int code;// shows Http codes

	private String message;// custom message

	private GallerySubscriptionModel gallerySubscriptionModel;
	
}
