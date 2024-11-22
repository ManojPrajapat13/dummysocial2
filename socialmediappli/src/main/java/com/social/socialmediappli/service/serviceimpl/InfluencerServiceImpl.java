package com.social.socialmediappli.service.serviceimpl;

import com.social.socialmediappli.dto.InfluencerDto;
import com.social.socialmediappli.entity.Influencer;
import com.social.socialmediappli.entity.PlatForm;
import com.social.socialmediappli.entity.User;
import com.social.socialmediappli.exceptions.InfluencerNotFoundException;
import com.social.socialmediappli.exceptions.PlatformNotFoundException;
import com.social.socialmediappli.repository.InfluencerRepo;
import com.social.socialmediappli.repository.PlatformRepo;
import com.social.socialmediappli.service.InfluencerService;
import com.social.socialmediappli.utilitymethod.UtilityMethods;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InfluencerServiceImpl implements InfluencerService {

    private final InfluencerRepo influencerRepo;
    private final PlatformRepo platformRepo;
    private final ModelMapper modelMapper;
    private final UtilityMethods utilityMethods;

    // for influencer registration
    @Override
    public String registerInfluencer(InfluencerDto influencerDto){
        Influencer influencer = modelMapper.map(influencerDto, Influencer.class);
        // check platform name
        Set<PlatForm> platforms = new HashSet<>();
        for(PlatForm p : influencer.getPlatFormNamess()){
            PlatForm existPlatform = platformRepo.findByPlatformName(p.getPlatformName());
            if(existPlatform != null){
                platforms.add(existPlatform);
            }else {
                platforms.add(p);
            }
        }

        influencer.setPlatFormNamess(platforms);

        influencerRepo.save(influencer);
        return "influencer registration successfully..";
    }

    // get one influencer
    @Override
    public InfluencerDto getOneInfluencer(Long influencerId){
        return influencerRepo.findById(influencerId)
                .map(influencer -> modelMapper.map(influencer, InfluencerDto.class))
                .orElseThrow(() -> new InfluencerNotFoundException("Influencer with ID " + influencerId + " not found"));
    }

    // get all influencer
    @Override
    public List<InfluencerDto> getAllInfluencer(){
        List<Influencer> influencerList= (List<Influencer>) influencerRepo.findAll();
        List<InfluencerDto> influencerDtoList = new ArrayList<>();

        for(Influencer influencer : influencerList){
            InfluencerDto influencerDto = modelMapper.map(influencer , InfluencerDto.class);
            influencerDtoList.add(influencerDto);
        }
        return influencerDtoList;
    }

    // delete one influencer
    @Override
    public String deleteOneInfluencer(Long influencerId, String platformName){
        Influencer influencer = influencerRepo.findByInfluencerId(influencerId);
        PlatForm platForm = platformRepo.findByPlatformName(platformName);
        if (utilityMethods.checkInfluencerPresent(influencerId, platformName)){
            influencer.getPlatFormNamess().remove(platForm);
            platForm.getInfluencers().remove(influencer);
            influencerRepo.save(influencer);
            return "Influencer successfully removed from the platform " + platformName;
        }else{
            throw new InfluencerNotFoundException("influencer is not found on this platform.......");
        }
    }

    // get all platform names...
    @Override
    public Set<PlatForm> getAllPlatformName(){
        // Retrieve all PlatForm entries from the database
        List<PlatForm> allPlatforms = (List<PlatForm>) platformRepo.findAll();
        //  store in set for unique
        return new HashSet<>(allPlatforms);
    }

    @Override
    public Set<User> getAllFollowerOfInfluencerOnSpecificPlatform(Long influencerId, String platformName){
        Set<User> followers = new HashSet<>();
        if(utilityMethods.checkInfluencerPresent(influencerId, platformName)){
            Influencer influencer = influencerRepo.findByInfluencerId(influencerId);
            switch(platformName){
                case ("Facebook"):
                    followers.addAll(influencer.getUserListFb());
                    break;
                case ("Instagram"):
                    followers.addAll(influencer.getUserListIg());
                    break;
                case ("Twitter"):
                    followers.addAll(influencer.getUserListTw());
                    break;
                case ("Yahoo"):
                    followers.addAll(influencer.getUserListYh());
                    break;
                default:
                    throw new PlatformNotFoundException("platform is not found...");
            }
            return followers;
        }else{
            throw new InfluencerNotFoundException("influencer is not present on this "+ platformName);
        }
    }
}
