package com.collegegroup.personaldiary.entities;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer paymentId;
	
	private Integer paymentPurpose;
	
	private String paymentDescription;
	
	private String orderId;
	
	private String razorpayPaymentId;
	
	private Integer paymentAmount;
	
	private Date paymentDateTime;
	
	private boolean paymentStatus;
	
	@ManyToMany
	private Set<GallerySubscription> gallerySubscriptions;
	
	@ManyToOne
	private User user;
	
}
