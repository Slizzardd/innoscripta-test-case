package ua.com.alevel.service.impl;

import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.alevel.exceptions.AccessException;
import ua.com.alevel.exceptions.AuthorizationException;
import ua.com.alevel.exceptions.EntityExistException;
import ua.com.alevel.exceptions.EntityNotFoundException;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.persistence.repository.UserRepository;
import ua.com.alevel.persistence.types.Role;
import ua.com.alevel.service.UserService;
import ua.com.alevel.utils.JwtUtil;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        checkExistUserData(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        logger.info("Creating user with email: {}", user.getEmail());
        User savedUser = userRepository.save(user);
        logger.info("User created successfully with id: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public User updateUser(User targetUser, String actualJwtToken) {
        checkOwnerOrAdministration(findUserByJwtToken(actualJwtToken), targetUser.getId());

        if (userRepository.existsByEmail(targetUser.getEmail())) {
            logger.info("Updating targetUser with id: {}", targetUser.getId());
            User updatedUser = userRepository.save(targetUser);
            logger.info("User updated successfully with id: {}", updatedUser.getId());
            return updatedUser;
        } else {
            throw new EntityNotFoundException("The targetUser with this ID does not exist");
        }
    }

    @Override
    public User findUserByJwtToken(String jwtToken) {
        try {
            String email = JwtUtil.extractUsername(jwtToken);
            return userRepository.findByEmail(email).orElse(null);
        } catch (MalformedJwtException e) {
            throw new AuthorizationException(e.toString());
        }
    }

    @Override
    public User findUserByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email).orElse(null);
            logger.info("User found by email: {}", email);
            return user;
        } else {
            logger.warn("Attempted to find non-existing user by email: {}", email);
            throw new EntityNotFoundException("The user with this email does not exist");
        }
    }

    private void checkExistUserData(User user) throws EntityExistException {
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("Attempted to create/update user with existing email: {}", user.getEmail());
            throw new EntityExistException("A user with this EMAIL already exists");
        }
    }

    private void checkOwnerOrAdministration(User actualUser, Long targetUserId) throws AccessException {
            if (actualUser.getRole() != Role.ADMIN && !Objects.equals(actualUser.getId(), targetUserId)) {
            logger.error("Attempted to get wrong data user with existing email: {}", actualUser.getEmail());
            throw new AccessException("You do not have permission for this data");
        }
    }


}
