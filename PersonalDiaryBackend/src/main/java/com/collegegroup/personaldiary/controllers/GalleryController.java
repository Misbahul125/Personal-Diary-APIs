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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.collegegroup.personaldiary.payloads.Gallery.ApiResponseGalleryModel;
import com.collegegroup.personaldiary.payloads.Gallery.ApiResponseGalleryModels;
import com.collegegroup.personaldiary.payloads.Gallery.GalleryModel;
import com.collegegroup.personaldiary.services.GalleryService;
import com.collegegroup.personaldiary.utils.AppConstants;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class GalleryController {

	@Autowired
	private GalleryService galleryService;

	// create
	@PostMapping("/user/{userId}/createGallery")
	public ResponseEntity<ApiResponseGalleryModel> createGallery(
			@PathVariable Integer userId,
			@RequestParam(value = "caption", required = true) String caption,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "images", required = true) List<MultipartFile> multipartFiles
			) {
		GalleryModel createdGallery = this.galleryService.createGallery(userId, caption, description, multipartFiles);

		ApiResponseGalleryModel apiResponseGalleryModel = new ApiResponseGalleryModel(true, HttpStatus.CREATED.value(),
				"Gallery Created Successfully", createdGallery);

		return new ResponseEntity<ApiResponseGalleryModel>(apiResponseGalleryModel, HttpStatus.CREATED);
	}
	
	@GetMapping("/gallery/{galleryId}")
	public ResponseEntity<ApiResponseGalleryModel> getGalleryById(@PathVariable Integer galleryId) {

		GalleryModel galleryModel = this.galleryService.getGalleryById(galleryId);

		ApiResponseGalleryModel apiResponseGalleryModel = new ApiResponseGalleryModel(true, HttpStatus.OK.value(),
				"Gallery Fetched Successfully", galleryModel);

		return new ResponseEntity<ApiResponseGalleryModel>(apiResponseGalleryModel, HttpStatus.OK);

	}
	
	@GetMapping("/user/{userId}/galleries")
	public ResponseEntity<ApiResponseGalleryModels> getGalleriesByUser(
			@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseGalleryModels apiResponseGalleryModels = this.galleryService.getGalleriesByUserId(userId, pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseGalleryModels>(apiResponseGalleryModels, HttpStatus.OK);
	}
	
	@GetMapping("/galleries")
	public ResponseEntity<ApiResponseGalleryModels> getAllGalleries(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseGalleryModels apiResponseGalleryModels = this.galleryService.getAllGalleries(pageNumber, pageSize,
				sortBy, sortMode);

		return new ResponseEntity<ApiResponseGalleryModels>(apiResponseGalleryModels, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/galleries/searchByCaption")
	public ResponseEntity<ApiResponseGalleryModels> searchNotesByCaption(
			@PathVariable Integer userId,
			@RequestParam(value = "searchKey", required = true) String searchKey,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseGalleryModels apiResponseGalleryModels = this.galleryService.searchGalleriesByUserAndCaption(userId, searchKey, pageNumber, 
				pageSize, sortBy, sortMode);

		return new ResponseEntity<ApiResponseGalleryModels>(apiResponseGalleryModels, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/galleries/searchByDescription")
	public ResponseEntity<ApiResponseGalleryModels> searchByDescription(
			@PathVariable Integer userId,
			@RequestParam(value = "searchKey", required = true) String searchKey,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CREATED_AT, required = false) String sortBy,
			@RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE_DESCENDING, required = false) Integer sortMode) {

		ApiResponseGalleryModels apiResponseGalleryModels = this.galleryService.searchGalleriesByUserAndDescription(userId, searchKey, pageNumber, 
				pageSize, sortBy, sortMode);

		return new ResponseEntity<ApiResponseGalleryModels>(apiResponseGalleryModels, HttpStatus.OK);
	}
	
	@PutMapping("/gallery")
	public ResponseEntity<ApiResponseGalleryModel> updateGallery(
			@RequestParam(value = "galleryId", required = true) Integer galleryId,
			@RequestParam(value = "caption", required = false) String caption,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "galleryImageId", required = false) Integer galleryImageId,
			@RequestParam(value = "image", required = false) MultipartFile multipartFile
			) {

		GalleryModel updatedGallery = this.galleryService.updateGallery(galleryId, caption, description, galleryImageId, multipartFile);

		ApiResponseGalleryModel apiResponseGalleryModel = new ApiResponseGalleryModel(true, HttpStatus.OK.value(),
				"Gallery Updated Successfully", updatedGallery);

		return new ResponseEntity<ApiResponseGalleryModel>(apiResponseGalleryModel, HttpStatus.OK);

	}
	
	@DeleteMapping("/gallery/{galleryId}")
	public ResponseEntity<ApiResponseGalleryModel> deleteGalleryById(@PathVariable Integer galleryId) {

		this.galleryService.deleteGalleryById(galleryId);

		ApiResponseGalleryModel apiResponseGalleryModel = new ApiResponseGalleryModel(true, HttpStatus.OK.value(),
				"Gallery Deleted Successfully", null);

		return new ResponseEntity<ApiResponseGalleryModel>(apiResponseGalleryModel, HttpStatus.OK);

	}
	
	@DeleteMapping("/gallery/{galleryId}/galleryImage/{galleryImageId}")
	public ResponseEntity<ApiResponseGalleryModel> deleteGalleryImageById(
			@PathVariable Integer galleryId,
			@PathVariable Integer galleryImageId
			) {

		GalleryModel galleryModel = this.galleryService.deleteGalleryImageById(galleryId, galleryImageId);

		ApiResponseGalleryModel apiResponseGalleryModel = new ApiResponseGalleryModel(true, HttpStatus.OK.value(),
				"Gallery Image Deleted Successfully", galleryModel);

		return new ResponseEntity<ApiResponseGalleryModel>(apiResponseGalleryModel, HttpStatus.OK);

	}

}
