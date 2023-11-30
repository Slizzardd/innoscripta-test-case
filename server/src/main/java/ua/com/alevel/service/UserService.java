package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.User;

import java.util.List;

public interface UserService extends BaseService<User> {

    User createUser(User user);

    User updateUser(User targetUser, String actualJwtToken);


    User findUserByJwtToken(String jwtToken);

    User findUserByEmail(String email);

}
