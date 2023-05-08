package com.collegegroup.personaldiary.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collegegroup.personaldiary.payloads.GallerySubscription.ApiResponseGallerySubscriptionModel;
import com.collegegroup.personaldiary.payloads.GallerySubscription.ApiResponseGallerySubscriptionModels;
import com.collegegroup.personaldiary.payloads.GallerySubscription.GallerySubscriptionModel;
import com.collegegroup.personaldiary.services.GallerySubscriptionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/gallerySubscription")
public class GallerySubscriptionController {

	@Autowired
	private GallerySubscriptionService gallerySubscriptionService;

	@PostMapping("")
	public ResponseEntity<ApiResponseGallerySubscriptionModel> createGallerySubscription(
			@RequestBody GallerySubscriptionModel gallerySubscriptionModel) {

		GallerySubscriptionModel createdGallerySubscription = this.gallerySubscriptionService
				.createGallerySubscription(gallerySubscriptionModel);

		ApiResponseGallerySubscriptionModel apiResponseGallerySubscriptionModel = new ApiResponseGallerySubscriptionModel(
				true, HttpStatus.CREATED.value(), "Gallery Subscription Created Successfully",
				createdGallerySubscription);

		return new ResponseEntity<ApiResponseGallerySubscriptionModel>(apiResponseGallerySubscriptionModel,
				HttpStatus.CREATED);

	}

	@GetMapping("/{gallerySubscriptionId}")
	public ResponseEntity<ApiResponseGallerySubscriptionModel> getGallerySubscriptionById(
			@PathVariable Integer gallerySubscriptionId) {

		GallerySubscriptionModel gallerySubscriptionModel = this.gallerySubscriptionService
				.getGallerySubscriptionById(gallerySubscriptionId);

		ApiResponseGallerySubscriptionModel apiResponseGallerySubscriptionModel = new ApiResponseGallerySubscriptionModel(
				true, HttpStatus.OK.value(), "Gallery Subscription Fetched Successfully", gallerySubscriptionModel);

		return new ResponseEntity<ApiResponseGallerySubscriptionModel>(apiResponseGallerySubscriptionModel,
				HttpStatus.OK);

	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponseGallerySubscriptionModels> getAllGallerySubscription() {

		List<GallerySubscriptionModel> gallerySubscriptionModels = this.gallerySubscriptionService
				.getAllGallerySubscription();

		ApiResponseGallerySubscriptionModels apiResponseGallerySubscriptionModels = new ApiResponseGallerySubscriptionModels(
				true, HttpStatus.OK.value(), "Gallery Subscription(s) Fetched Successfully", gallerySubscriptionModels);

		return new ResponseEntity<ApiResponseGallerySubscriptionModels>(apiResponseGallerySubscriptionModels,
				HttpStatus.OK);

	}

	@PutMapping("")
	public ResponseEntity<ApiResponseGallerySubscriptionModel> updateGallerySubscription(@RequestBody GallerySubscriptionModel gallerySubscriptionModel) {
		
		GallerySubscriptionModel updatedGallerySubscriptionModel = this.gallerySubscriptionService.updateGallerySubscription(gallerySubscriptionModel);

		ApiResponseGallerySubscriptionModel apiResponseGallerySubscriptionModel = new ApiResponseGallerySubscriptionModel(true, HttpStatus.OK.value(),
				"Gallery Subscription Updated Successfully", updatedGallerySubscriptionModel);

		return new ResponseEntity<ApiResponseGallerySubscriptionModel>(apiResponseGallerySubscriptionModel, HttpStatus.OK);
	}
	
	@DeleteMapping("/{gallerySubscriptionId}")
	public ResponseEntity<ApiResponseGallerySubscriptionModel> deleteGallerySubscription(@PathVariable Integer gallerySubscriptionId) {
		
		this.gallerySubscriptionService.deleteGallerySubscription(gallerySubscriptionId);

		ApiResponseGallerySubscriptionModel apiResponseGallerySubscriptionModel = new ApiResponseGallerySubscriptionModel(true, HttpStatus.OK.value(),
				"Gallery Subscription Deleted Successfully", null);

		return new ResponseEntity<ApiResponseGallerySubscriptionModel>(apiResponseGallerySubscriptionModel, HttpStatus.OK);
	}

}
