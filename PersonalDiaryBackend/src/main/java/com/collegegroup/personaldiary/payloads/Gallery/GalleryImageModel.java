package com.collegegroup.personaldiary.payloads.Gallery;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GalleryImageModel {

	private Integer galleryImageId;
	
	private String imageURL;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	//private GalleryModel galleryModel;
	
}
