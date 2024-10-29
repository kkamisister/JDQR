package com.example.backend.etc.entity;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.owner.entity.Owner;
import jakarta.persistence.*;
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
public class Restaurant extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private String name;
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private Double latitude;
    private Double longitude;
    private String industry;

    @Column(name = "registration_number")
    private String registrationNumber;
}
