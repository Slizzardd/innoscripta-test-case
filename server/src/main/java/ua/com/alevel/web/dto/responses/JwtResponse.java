package ua.com.alevel.web.dto.responses;

public class JwtResponse {
    private String token;
    private UserResponseDto user;

    public JwtResponse(String token, UserResponseDto userResponseDto) {
        this.token = token;
        this.user = userResponseDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }
}
