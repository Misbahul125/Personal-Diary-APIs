package com.collegegroup.personaldiary.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gallery_images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GalleryImage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer galleryImageId;
	
	private String imageURL;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	@ManyToOne
	private Gallery gallery;
	
}
