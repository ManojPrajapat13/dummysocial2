package com.social.socialmediappli.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Setter
@Getter
@Entity
@Table(name = "influencer_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "influencerId")
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "influencerId")
    private Long influencerId;

    @Column(name = "InfluencerName", nullable = false)
    private String influencerName;

    @Column(name = "InfluencerEmail" , nullable = false , unique = true)
    private String influencerEmail;

    @Column(name = "InfluencerAge", nullable = false)
    private int influencerAge;

    @Column(name = "InfluencerMobileNo", nullable = false)
    private String influencerMobileNo;

    @Column(name = "facebookFollower" )
    private int facebookFollower;

    @Column(name = "instagramFollower" )
    private int instagramFollower;

    @Column(name = "yahooFollower" )
    private int yahooFollower;

    @Column(name = "twitterFollower" )
    private int twitterFollower;

    @Column(name = "category" ,nullable = false)
    private String category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "influencer_platform",
            joinColumns = @JoinColumn(name = "influencerId"),
            inverseJoinColumns = @JoinColumn(name = "platformId")
    )
    private Set<PlatForm> platFormNamess = new HashSet<>();

    @ManyToMany(mappedBy = "influencerListFb")
    private Set<User> userListFb = new HashSet<>();

    @ManyToMany(mappedBy = "influencerListIg")
    private Set<User> userListIg = new HashSet<>();

    @ManyToMany(mappedBy = "influencerListTw")
    private Set<User> userListTw = new HashSet<>();

    @ManyToMany(mappedBy = "influencerListYh")
    private Set<User> userListYh = new HashSet<>();

}
