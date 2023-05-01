package com.collegegroup.personaldiary.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.collegegroup.personaldiary.payloads.Gallery.ApiResponseGalleryModels;
import com.collegegroup.personaldiary.payloads.Gallery.GalleryModel;

public interface GalleryService {

	public GalleryModel createGallery(Integer userId, String caption, String description, List<MultipartFile> multipartFiles);
	
	public GalleryModel getGalleryById(Integer galleryId);
	
	public ApiResponseGalleryModels getGalleriesByUserId(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode);
	
	public ApiResponseGalleryModels getAllGalleries(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);
	
	public ApiResponseGalleryModels searchGalleriesByUserAndCaption(Integer userId, String searchKey, 
			Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);
	
	public ApiResponseGalleryModels searchGalleriesByUserAndDescription(Integer userId, String searchKey, 
			Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);
	
	public GalleryModel updateGallery(Integer galleryId, String caption, String description, Integer galleryImageId, MultipartFile multipartFile);
	
	public void deleteGalleryById(Integer galleryId);
	
	public GalleryModel deleteGalleryImageById(Integer galleryId, Integer galleryImageId);
	
}
