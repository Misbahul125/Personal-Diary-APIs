package com.collegegroup.personaldiary.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegegroup.personaldiary.entities.GallerySubscription;
import com.collegegroup.personaldiary.exceptions.ResourceNotFoundException;
import com.collegegroup.personaldiary.payloads.GallerySubscription.GallerySubscriptionModel;
import com.collegegroup.personaldiary.repositories.GallerySubscriptionRepository;
import com.collegegroup.personaldiary.services.GallerySubscriptionService;

@Service
public class GallerySubscriptionServiceImpl implements GallerySubscriptionService {
	
	@Autowired
	private GallerySubscriptionRepository gallerySubscriptionRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public GallerySubscriptionModel createGallerySubscription(GallerySubscriptionModel gallerySubscriptionModel) {
		
		GallerySubscription gallerySubscription = this.modelMapper.map(gallerySubscriptionModel, GallerySubscription.class);
		
		GallerySubscription createdGallerySubscription = this.gallerySubscriptionRepository.save(gallerySubscription);
		
		return this.modelMapper.map(createdGallerySubscription, GallerySubscriptionModel.class);
		
	}

	@Override
	public GallerySubscriptionModel getGallerySubscriptionById(Integer gallerySubscriptionId) {
		
		GallerySubscription gallerySubscription = this.gallerySubscriptionRepository.findById(gallerySubscriptionId)
				.orElseThrow((() -> new ResourceNotFoundException("Gallery Subscription", "gallerySubscription Id", gallerySubscriptionId.toString())));
		
		return this.modelMapper.map(gallerySubscription, GallerySubscriptionModel.class);
		
	}

	@Override
	public List<GallerySubscriptionModel> getAllGallerySubscription() {
		
		List<GallerySubscription> gallerySubscriptions = this.gallerySubscriptionRepository.findAll();
		
		List<GallerySubscriptionModel> gallerySubscriptionModels = gallerySubscriptions.stream().map(gallerySubscription -> this.modelMapper.map(gallerySubscription, GallerySubscriptionModel.class))
				.collect(Collectors.toList());
		
		return gallerySubscriptionModels;
		
	}

	@Override
	public GallerySubscriptionModel updateGallerySubscription(GallerySubscriptionModel gallerySubscriptionModel) {
		
		GallerySubscription gallerySubscription = this.gallerySubscriptionRepository.findById(gallerySubscriptionModel.getGallerySubscriptionId())
				.orElseThrow((() -> new ResourceNotFoundException("Gallery Subscription", "gallerySubscription Id", gallerySubscriptionModel.getGallerySubscriptionId().toString())));
		
		if(gallerySubscriptionModel.getSubscriptionName() != null && !gallerySubscriptionModel.getSubscriptionName().isEmpty())
			gallerySubscription.setSubscriptionName(gallerySubscriptionModel.getSubscriptionName());
		
		if(gallerySubscriptionModel.getSubscriptionDescription() != null && !gallerySubscriptionModel.getSubscriptionDescription().isEmpty())
			gallerySubscription.setSubscriptionDescription(gallerySubscriptionModel.getSubscriptionDescription());
		
		if(gallerySubscriptionModel.getSubscriptionDuration() != null && !gallerySubscriptionModel.getSubscriptionDuration().isEmpty())
			gallerySubscription.setSubscriptionDuration(gallerySubscriptionModel.getSubscriptionDuration());
		
		if(gallerySubscriptionModel.getSubscriptionSpace() != null && !gallerySubscriptionModel.getSubscriptionSpace().isEmpty())
			gallerySubscription.setSubscriptionSpace(gallerySubscriptionModel.getSubscriptionSpace());
		
		if(gallerySubscriptionModel.getSubscriptionAmount() != null)
			gallerySubscription.setSubscriptionAmount(gallerySubscriptionModel.getSubscriptionAmount());
		
		GallerySubscription updatedGalleryService = this.gallerySubscriptionRepository.save(gallerySubscription);
		
		return this.modelMapper.map(updatedGalleryService, GallerySubscriptionModel.class);
		
	}

	@Override
	public void deleteGallerySubscription(Integer gallerySubscriptionId) {
		
		GallerySubscription gallerySubscription = this.gallerySubscriptionRepository.findById(gallerySubscriptionId)
				.orElseThrow((() -> new ResourceNotFoundException("Gallery Subscription", "gallerySubscription Id", gallerySubscriptionId.toString())));
		
		this.gallerySubscriptionRepository.delete(gallerySubscription);
		
	}

}
