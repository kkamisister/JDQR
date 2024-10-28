package com.example.backend.user.entity;

import com.example.backend.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "members")
public class User{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "code")
	private String code;
	@Column(name = "email")
	private String email;
	@Column(name = "name")
	private String name;
	@Column(name = "industry")
	private String industry;
	@Column(name = "address")
	private String address;
	@Column(name = "image")
	private String image;
	@Column(name = "registration_number")
	private String registrationNumber;
	@Column(name = "lat")
	private Double lat;
	@Column(name = "lng")
	private Double lng;

}
