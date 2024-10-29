package com.example.backend.restaurants.entity;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "restaurants")
public class Restaurants extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;
	@Column(name = "address")
	private String address;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "industry")
	private String industry;
	@Column(name = "image")
	private String image;
	@Column(name = "registration_number")
	private String registrationNumber;
	@Column(name = "latitude")
	private Double lat;
	@Column(name = "longitude")
	private Double lng;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private User user;

}
