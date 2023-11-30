package ua.com.alevel.web.rest;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.exceptions.AccessException;
import ua.com.alevel.exceptions.EntityNotFoundException;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.utils.JwtUtil;
import ua.com.alevel.web.dto.requests.UserRequestDto;
import ua.com.alevel.web.dto.responses.JwtResponse;
import ua.com.alevel.web.dto.responses.UserResponseDto;

/**
 * REST controller for managing user-related operations.
 * Handles HTTP requests related to user information, including updates and retrieval.
 * Returns ResponseEntity with updated user information, JWT tokens, or error messages.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    private final UserFacade userFacade;

    /**
     * Constructor for UserRestController.
     *
     * @param userFacade UserFacade instance for handling user-related operations.
     */
    public UserRestController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * Endpoint for updating a user's information.
     * Requires a valid JWT token for authorization.
     *
     * @param fullJwtToken The full JWT token extracted from the Authorization header.
     * @param request      UserRequestDto containing updated user information.
     * @return ResponseEntity containing the updated user information or an error message.
     */
    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String fullJwtToken,
                                        @RequestBody UserRequestDto request) {
        try {
            String jwtToken = JwtUtil.extractToken(fullJwtToken);
            if (JwtUtil.authCheck(jwtToken)) {
                return ResponseEntity.ok(userFacade.updateUser(request, jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unknown error, please contact site support: " + e.getMessage());
        }
    }

    /**
     * Endpoint for retrieving user information by JWT token.
     * Requires a valid JWT token for authorization.
     *
     * @param fullJwtToken The full JWT token extracted from the Authorization header.
     * @return ResponseEntity containing a JWT token and user information or an error message.
     */
    @GetMapping("/getUserByToken")
    public ResponseEntity<?> findUserByToken(@RequestHeader("Authorization") String fullJwtToken) {
        try {
            String jwtToken = JwtUtil.extractToken(fullJwtToken);
            if (JwtUtil.authCheck(jwtToken)) {
                UserResponseDto user = userFacade.findUserByToken(jwtToken);
                return ResponseEntity.ok(new JwtResponse(
                        JwtUtil.generateJwtToken(user.getEmail()), user));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("    User not authorized");
            }
        } catch (NullPointerException e) {
            return null;
        } catch (EntityNotFoundException | UsernameNotFoundException | JwtException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unknown error, please contact site support: " + e.getMessage());
        }
    }
}
