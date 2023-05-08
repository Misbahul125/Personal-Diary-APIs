package com.collegegroup.personaldiary.payloads.GallerySubscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GallerySubscriptionModel {

	private Integer gallerySubscriptionId;
	
	private String subscriptionName;
	
	private String subscriptionDescription;
	
	private String subscriptionDuration;
	
	private String subscriptionSpace;
	
	private Integer subscriptionAmount;
	
}
