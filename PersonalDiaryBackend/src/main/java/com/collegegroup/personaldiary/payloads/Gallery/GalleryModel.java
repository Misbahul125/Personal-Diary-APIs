package com.collegegroup.personaldiary.payloads.Gallery;

import java.util.Date;
import java.util.List;

import com.collegegroup.personaldiary.payloads.user.UserModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GalleryModel {

	private Integer galleryId;
	
	private String caption;
	
	private String description;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private List<GalleryImageModel> galleryImages;
	
	private UserModel user;
	
}
