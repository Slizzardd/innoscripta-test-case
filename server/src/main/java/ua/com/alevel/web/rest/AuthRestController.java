package ua.com.alevel.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.exceptions.EntityExistException;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.utils.JwtUtil;
import ua.com.alevel.web.dto.requests.UserRequestDto;
import ua.com.alevel.web.dto.responses.JwtResponse;
import ua.com.alevel.web.dto.responses.UserResponseDto;

/**
 * RestController handling authentication-related operations including user registration and login.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final UserFacade userFacade;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor for AuthRestController.
     *
     * @param userFacade           UserFacade instance for handling user-related operations.
     * @param authenticationManager AuthenticationManager for handling user authentication.
     */
    public AuthRestController(UserFacade userFacade, AuthenticationManager authenticationManager) {
        this.userFacade = userFacade;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Endpoint for user registration.
     *
     * @param userRequestDto UserRequestDto containing user registration information (email and password).
     * @return ResponseEntity containing a JWT token and user information upon successful registration,
     * or an error message if the user already exists or an unknown error occurs.
     */
    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestDto) {
        try {
            UserResponseDto user = userFacade.createUser(userRequestDto);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequestDto.getEmail(),
                            userRequestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(new JwtResponse(
                    JwtUtil.generateJwtToken(user.getEmail()), userFacade.findUserByEmail(user.getEmail())));
        } catch (EntityExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unknown error, please contact site support: " + e.getMessage());
        }
    }

    /**
     * Endpoint for user login.
     *
     * @param userRequestDto UserRequestDto containing user login information (email and password).
     * @return ResponseEntity containing a JWT token and user information upon successful login,
     * or an error message if the user is not found, the password is incorrect, or an unknown error occurs.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequestDto userRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequestDto.getEmail(),
                            userRequestDto.getPassword()));

            String username = authentication.getName();
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(new JwtResponse(JwtUtil.generateJwtToken(username), userFacade.findUserByEmail(username)));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unknown error, please contact site support: " + e.getMessage());
        }
    }
}
