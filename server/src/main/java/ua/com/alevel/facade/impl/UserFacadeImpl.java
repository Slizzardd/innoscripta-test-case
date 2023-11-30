package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.web.dto.requests.UserRequestDto;
import ua.com.alevel.web.dto.responses.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto req) {
        User user = new User();
        setMainUserInformation(req, user);

        user.setPassword(req.getPassword());

        return new UserResponseDto(userService.createUser(user));
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto req, String authToken) {
        User user = userService.findUserByJwtToken(authToken);
        setMainUserInformation(req, user);

        user.setPreferredPublisher(convertListToString(req.getPreferredPublishers()));
        return new UserResponseDto(userService.updateUser(user, authToken));
    }

    @Override
    public UserResponseDto findUserByEmail(String email) {
        return new UserResponseDto(userService.findUserByEmail(email));
    }

    @Override
    public UserResponseDto findUserByToken(String authToken) {
        return new UserResponseDto(userService.findUserByJwtToken(authToken));
    }

    private void setMainUserInformation(UserRequestDto req, User user) {
        user.setEmail(req.getEmail());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
    }

    private String convertListToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        return list.stream()
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(","));
    }
}
