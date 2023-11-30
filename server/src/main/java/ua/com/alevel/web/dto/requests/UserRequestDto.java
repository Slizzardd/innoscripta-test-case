package ua.com.alevel.web.dto.requests;

import java.util.List;

public class UserRequestDto extends RequestDto {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<String> preferredPublishers;

    public List<String> getPreferredPublishers() {
        return preferredPublishers;
    }

    public void setPreferredPublishers(List<String> preferredPublishers) {
        this.preferredPublishers = preferredPublishers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
