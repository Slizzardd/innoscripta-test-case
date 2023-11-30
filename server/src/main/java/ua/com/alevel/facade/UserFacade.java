package ua.com.alevel.facade;

import ua.com.alevel.web.dto.requests.UserRequestDto;
import ua.com.alevel.web.dto.responses.UserResponseDto;

public interface UserFacade extends BaseFacade<UserRequestDto, UserResponseDto> {

    UserResponseDto createUser(UserRequestDto req);

    UserResponseDto updateUser(UserRequestDto req, String authToken);


    UserResponseDto findUserByEmail(String email);

    UserResponseDto findUserByToken(String authToken);

}
