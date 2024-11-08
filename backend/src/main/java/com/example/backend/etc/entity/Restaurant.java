package com.example.backend.etc.entity;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.etc.dto.RestaurantProfileDto;
import com.example.backend.owner.entity.Owner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "industry")
    private String industry;
    @Column(name = "image")
    private String image;
    @Column(name = "registration_number")
    private String registrationNumber;
    @Column(name = "open")
    private Boolean open;

    public static Restaurant of(RestaurantProfileDto profile,Owner owner){
        return Restaurant.builder()
            .owner(owner)
            .name(profile.getRestaurantName())
            .address(profile.getAddress())
            .phoneNumber(profile.getPhoneNumber())
            .latitude(profile.getLat())
            .longitude(profile.getLng())
            .industry(profile.getIndustry())
            .image(profile.getImage())
            .registrationNumber(profile.getRegistrationNumber())
            .open(Boolean.FALSE)
            .build();
    }

    public void setOwner(Owner owner){
        this.owner = owner;
    }
}
