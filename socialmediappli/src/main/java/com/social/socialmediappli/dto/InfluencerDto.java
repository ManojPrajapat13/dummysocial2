package com.social.socialmediappli.dto;

import com.social.socialmediappli.entity.PlatForm;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class InfluencerDto {

    private Long influencerId;

    private String influencerName;

    private String influencerEmail;

    private Long influencerAge;

    private String influencerMobileNo;

    private String category;

    private int instagramFollower;

    private int facebookFollower;

    private int yahooFollower;

    private int twitterFollower;

    private Set<PlatForm> platFormNamess = new HashSet<>();
}
