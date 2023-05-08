package com.collegegroup.personaldiary.services;

import java.util.List;

import com.collegegroup.personaldiary.payloads.GallerySubscription.GallerySubscriptionModel;

public interface GallerySubscriptionService {

	public GallerySubscriptionModel createGallerySubscription(GallerySubscriptionModel gallerySubscriptionModel);
	
	public GallerySubscriptionModel getGallerySubscriptionById(Integer gallerySubscriptionId);
	
	public List<GallerySubscriptionModel> getAllGallerySubscription();
	
	public GallerySubscriptionModel updateGallerySubscription(GallerySubscriptionModel gallerySubscriptionModel);
	
	public void deleteGallerySubscription(Integer gallerySubscriptionId);
	
}
