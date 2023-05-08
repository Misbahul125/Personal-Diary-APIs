package com.collegegroup.personaldiary.entities;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gallery_subscription")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GallerySubscription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer gallerySubscriptionId;
	
	private String subscriptionName;
	
	private String subscriptionDescription;
	
	private String subscriptionDuration;
	
	private String subscriptionSpace;
	
	private Integer subscriptionAmount;
	
	@ManyToMany(mappedBy = "gallerySubscriptions")
	private Set<PaymentDetails> paymentDetails;
	
}
