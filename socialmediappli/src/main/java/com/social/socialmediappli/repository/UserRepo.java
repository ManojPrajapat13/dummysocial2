package com.social.socialmediappli.repository;

import com.social.socialmediappli.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUserId(Long userId);

}
