package ua.com.alevel.web.dto.responses;

import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.persistence.types.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserResponseDto extends ResponseDto {

    private String email;

    private String firstName;

    private String lastName;

    private Role role;

    private List<String> preferredPublishers;

    public UserResponseDto(User user) {
        setId(user.getId());
        setCreated(user.getCreated());
        setUpdated(user.getUpdated());
        this.email = user.getEmail();
        this.role = user.getRole();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.preferredPublishers = splitStringToList(user.getPreferredPublisher());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<String> getPreferredPublishers() {
        return preferredPublishers;
    }

    public void setPreferredPublishers(List<String> preferredPublishers) {
        this.preferredPublishers = preferredPublishers;
    }

    private List<String> splitStringToList(String line) {
        if (line == null || line.isEmpty()) {
            return new ArrayList<>();
        } else {
            String[] stringArray = line.split(",");
            return Arrays.asList(stringArray);
        }

    }
}
