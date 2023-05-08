package com.collegegroup.personaldiary.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.collegegroup.personaldiary.entities.Gallery;
import com.collegegroup.personaldiary.entities.GalleryImage;
import com.collegegroup.personaldiary.entities.User;
import com.collegegroup.personaldiary.exceptions.ResourceNotFoundException;
import com.collegegroup.personaldiary.payloads.Gallery.ApiResponseGalleryModels;
import com.collegegroup.personaldiary.payloads.Gallery.GalleryModel;
import com.collegegroup.personaldiary.repositories.GalleryImageRepository;
import com.collegegroup.personaldiary.repositories.GalleryRepository;
import com.collegegroup.personaldiary.repositories.UserRepository;
import com.collegegroup.personaldiary.services.GalleryService;
import com.collegegroup.personaldiary.services.S3FileService;

@Service
public class GalleryServiceImpl implements GalleryService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GalleryImageRepository galleryImageRepository;

	@Autowired
	private GalleryRepository galleryRepository;

	@Autowired
	private S3FileService s3FileService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public GalleryModel createGallery(Integer userId, String caption, String description,
			List<MultipartFile> multipartFiles) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// save image

		List<String> savedFileURLs = s3FileService.saveFile(multipartFiles);

		// create gallery first
		Gallery gallery = new Gallery();
		gallery.setCaption(caption);
		gallery.setDescription(description);
		// gallery.setGalleryImages(galleryImages);
		gallery.setUser(user);
		gallery.setCreatedAt(new Date());

		Gallery savedGallery = this.galleryRepository.save(gallery);

		// then create gallery image, and store corresponding gallery object

		List<GalleryImage> galleryImages = new ArrayList<>();

		for (String fileURL : savedFileURLs) {
			GalleryImage galleryImage = new GalleryImage();
			galleryImage.setImageURL(fileURL);
			galleryImage.setGallery(savedGallery);
			galleryImage.setCreatedAt(new Date());

			GalleryImage savedGalleryImage = this.galleryImageRepository.save(galleryImage);

			galleryImages.add(savedGalleryImage);
		}

		savedGallery.setGalleryImages(galleryImages);
		Gallery finalUpdatedGallery = this.galleryRepository.save(savedGallery);

		return this.modelMapper.map(finalUpdatedGallery, GalleryModel.class);

	}

	@Override
	public GalleryModel getGalleryById(Integer galleryId) {

		Gallery gallery = this.galleryRepository.findById(galleryId)
				.orElseThrow(() -> new ResourceNotFoundException("Gallery", "GalleryId", galleryId.toString()));

		return this.modelMapper.map(gallery, GalleryModel.class);

	}

	@Override
	public ApiResponseGalleryModels getGalleriesByUserId(Integer userId, Integer pageNumber, Integer pageSize,
			String sortBy, Integer sortMode) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Gallery> pageGalleries = this.galleryRepository.findByUser(user, pageable);

		List<Gallery> allGalleries = pageGalleries.getContent();

		List<GalleryModel> galleryModels = allGalleries.stream()
				.map((gallery) -> this.modelMapper.map(gallery, GalleryModel.class)).collect(Collectors.toList());

		ApiResponseGalleryModels apiResponseGalleryModels = new ApiResponseGalleryModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageGalleries.getNumber(), pageGalleries.getSize(),
				pageGalleries.getTotalElements(), pageGalleries.getTotalPages(), pageGalleries.isLast(), galleryModels);

		if (pageGalleries.getNumber() >= pageGalleries.getTotalPages()) {

			apiResponseGalleryModels.setMessage("No more note(s) found for this user");
			apiResponseGalleryModels.setGalleryModels(null);

		}

		return apiResponseGalleryModels;

	}

	@Override
	public ApiResponseGalleryModels getAllGalleries(Integer pageNumber, Integer pageSize, String sortBy,
			Integer sortMode) {

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Gallery> pageGalleries = this.galleryRepository.findAll(pageable);

		List<Gallery> allGalleries = pageGalleries.getContent();

		List<GalleryModel> galleryModels = allGalleries.stream()
				.map((gallery) -> this.modelMapper.map(gallery, GalleryModel.class)).collect(Collectors.toList());

		ApiResponseGalleryModels apiResponseGalleryModels = new ApiResponseGalleryModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageGalleries.getNumber(), pageGalleries.getSize(),
				pageGalleries.getTotalElements(), pageGalleries.getTotalPages(), pageGalleries.isLast(), galleryModels);

		if (pageGalleries.getNumber() >= pageGalleries.getTotalPages()) {

			apiResponseGalleryModels.setMessage("No more note(s) found for this user");
			apiResponseGalleryModels.setGalleryModels(null);

		}

		return apiResponseGalleryModels;

	}

	@Override
	public ApiResponseGalleryModels searchGalleriesByUserAndCaption(Integer userId, String searchKey,
			Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Gallery> pageGalleries = this.galleryRepository.findByUserAndCaptionContaining(user, searchKey, pageable);

		List<Gallery> allGalleries = pageGalleries.getContent();

		List<GalleryModel> galleryModels = allGalleries.stream()
				.map((gallery) -> this.modelMapper.map(gallery, GalleryModel.class)).collect(Collectors.toList());

		ApiResponseGalleryModels apiResponseGalleryModels = new ApiResponseGalleryModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageGalleries.getNumber(), pageGalleries.getSize(),
				pageGalleries.getTotalElements(), pageGalleries.getTotalPages(), pageGalleries.isLast(), galleryModels);

		if (pageGalleries.getNumber() >= pageGalleries.getTotalPages()) {

			apiResponseGalleryModels.setMessage("No more note(s) found for this user");
			apiResponseGalleryModels.setGalleryModels(null);

		}

		return apiResponseGalleryModels;

	}

	@Override
	public ApiResponseGalleryModels searchGalleriesByUserAndDescription(Integer userId, String searchKey,
			Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

		// sorting format
		Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// paging format
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// retrieving paged data items
		Page<Gallery> pageGalleries = this.galleryRepository.findByUserAndDescriptionContaining(user, searchKey,
				pageable);

		List<Gallery> allGalleries = pageGalleries.getContent();

		List<GalleryModel> galleryModels = allGalleries.stream()
				.map((gallery) -> this.modelMapper.map(gallery, GalleryModel.class)).collect(Collectors.toList());

		ApiResponseGalleryModels apiResponseGalleryModels = new ApiResponseGalleryModels(true, HttpStatus.OK.value(),
				"Notes Fetched Successfully", pageGalleries.getNumber(), pageGalleries.getSize(),
				pageGalleries.getTotalElements(), pageGalleries.getTotalPages(), pageGalleries.isLast(), galleryModels);

		if (pageGalleries.getNumber() >= pageGalleries.getTotalPages()) {

			apiResponseGalleryModels.setMessage("No more note(s) found for this user");
			apiResponseGalleryModels.setGalleryModels(null);

		}

		return apiResponseGalleryModels;

	}

	@Override
	public GalleryModel updateGallery(Integer galleryId, String caption, String description, Integer galleryImageId,
			MultipartFile multipartFile) {

		Gallery gallery = this.galleryRepository.findById(galleryId)
				.orElseThrow(() -> new ResourceNotFoundException("Gallery", "galleryId", galleryId.toString()));

		// check if update action contains any file or not
		if (galleryImageId != null) {

			GalleryImage galleryImage = this.galleryImageRepository.findById(galleryImageId).orElseThrow(
					() -> new ResourceNotFoundException("Gallery Image", "galleryImageId", galleryImageId.toString()));

			if (galleryImage.getImageURL() != null && !galleryImage.getImageURL().isEmpty() && multipartFile != null) {

				String updateFileURL = this.s3FileService.updateFile(galleryImage.getImageURL(), multipartFile);

				galleryImage.setImageURL(updateFileURL);
				galleryImage.setUpdatedAt(new Date());

				this.galleryImageRepository.save(galleryImage);

			}
			else {
				throw new ResourceNotFoundException("Gallery Image", "galleryImageId", galleryImageId.toString());
			}

		}

		if (caption != null && !caption.isEmpty())
			gallery.setCaption(caption);

		if (description != null && !description.isEmpty())
			gallery.setDescription(description);

		gallery.setUpdatedAt(new Date());

		Gallery updatedGallery = this.galleryRepository.save(gallery);

		return this.modelMapper.map(updatedGallery, GalleryModel.class);

	}

	@Override
	public void deleteGalleryById(Integer galleryId) {

		Gallery gallery = this.galleryRepository.findById(galleryId)
				.orElseThrow(() -> new ResourceNotFoundException("Gallery", "galleryId", galleryId.toString()));

		List<String> imageURLsToBeDeleted = new ArrayList<>();

		for (GalleryImage galleryImage : gallery.getGalleryImages())
			imageURLsToBeDeleted.add(galleryImage.getImageURL());

		boolean areDeleted = this.s3FileService.deleteFiles(imageURLsToBeDeleted);

		if (areDeleted) {

			for (GalleryImage galleryImage : gallery.getGalleryImages())
				this.galleryImageRepository.delete(galleryImage);

			this.galleryRepository.delete(gallery);
		}

	}

	@Override
	public GalleryModel deleteGalleryImageById(Integer galleryId, Integer galleryImageId) {
		
		Gallery gallery = galleryRepository.findById(galleryId)
				.orElseThrow(() -> new ResourceNotFoundException("Gallery", "galleryId", galleryId.toString()));

		GalleryImage galleryImage = this.galleryImageRepository.findById(galleryImageId).orElseThrow(
				() -> new ResourceNotFoundException("Gallery Image", "galleryImageId", galleryImageId.toString()));

		List<String> imageURLsToBeDeleted = new ArrayList<>();

		imageURLsToBeDeleted.add(galleryImage.getImageURL());

		boolean isDeleted = this.s3FileService.deleteFiles(imageURLsToBeDeleted);

		if (isDeleted)
			this.galleryImageRepository.delete(galleryImage);
		
		Gallery updatedGallery = this.galleryRepository.findById(galleryId)
		.orElseThrow(() -> new ResourceNotFoundException("Gallery", "galleryId", galleryId.toString()));
		
		return this.modelMapper.map(updatedGallery, GalleryModel.class);

	}

}
