package com.social.socialmediappli.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "platform_data")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "platformId")
public class PlatForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "platformId")
    private Long platformId;

    @Column(name = "platformName")
    private String platformName;

    @ManyToMany(mappedBy = "platFormNames")  // user
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "platFormNamess")  // influencer
    private Set<Influencer> influencers = new HashSet<>();

}