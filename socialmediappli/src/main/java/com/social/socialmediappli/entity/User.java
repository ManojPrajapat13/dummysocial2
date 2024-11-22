package com.social.socialmediappli.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "user_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "userName",nullable = false)
    private String userName;

    @Column(name = "userEmail",nullable = false, unique = true)
    private String userEmail;

    @Column(name = "userAge",nullable = false)
    private int userAge;

    @Column(name = "userMobileNo",nullable = false)
    private String userMobileNo;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "user_platform",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "platformId")
    )
    private Set<PlatForm> platFormNames = new HashSet<>();


    @ManyToMany( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "User_Influ_Follower_Fb",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "influencerId", referencedColumnName = "influencerId")
    )
    private Set<Influencer> influencerListFb = new HashSet<>();

    @ManyToMany( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "User_Influ_Follower_Ig",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "influencerId", referencedColumnName = "influencerId")
    )
    private Set<Influencer> influencerListIg = new HashSet<>();

    @ManyToMany( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "User_Influ_Follower_Tw",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "influencerId", referencedColumnName = "influencerId")
    )
    private Set<Influencer> influencerListTw = new HashSet<>();

    @ManyToMany( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "User_Influ_Follower_Yh",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "influencerId", referencedColumnName = "influencerId")
    )
    private Set<Influencer> influencerListYh = new HashSet<>();

}