package com.social.socialmediappli.service;

import com.social.socialmediappli.dto.InfluencerDto;
import com.social.socialmediappli.entity.PlatForm;
import com.social.socialmediappli.entity.User;

import java.util.*;

public interface InfluencerService {

    String registerInfluencer(InfluencerDto influencerDto);

    InfluencerDto getOneInfluencer(Long influencerId);

    List<InfluencerDto> getAllInfluencer();

    String deleteOneInfluencer(Long influencerId , String platformName);

    Set<PlatForm> getAllPlatformName();

    Set<User> getAllFollowerOfInfluencerOnSpecificPlatform(Long influencerId, String platformName);

}
