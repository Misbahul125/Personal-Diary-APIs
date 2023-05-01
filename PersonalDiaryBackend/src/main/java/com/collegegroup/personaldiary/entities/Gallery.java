package com.collegegroup.personaldiary.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gallery")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Gallery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer galleryId;
	
	private String caption;
	
	private String description;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	@OneToMany(mappedBy = "gallery", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<GalleryImage> galleryImages;
	
	@ManyToOne
	private User user;
	
}
