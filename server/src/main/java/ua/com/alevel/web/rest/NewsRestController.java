package ua.com.alevel.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.exceptions.EntityNotFoundException;
import ua.com.alevel.facade.NewsFacade;
import ua.com.alevel.utils.JwtUtil;
import ua.com.alevel.web.dto.requests.DataTableRequest;

/**
 * REST controller for managing news.
 * Handles HTTP requests related to CRUD operations on news.
 * Returns ResponseEntity with data or error messages.
 */
@RestController
@RequestMapping("/api/v1/news")
public class NewsRestController {

    private final NewsFacade newsFacade;

    /**
     * Constructor for NewsRestController.
     *
     * @param newsFacade NewsFacade instance for handling news-related operations.
     */
    public NewsRestController(NewsFacade newsFacade) {
        this.newsFacade = newsFacade;
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAllNews(@ModelAttribute DataTableRequest request,
                                         @RequestHeader(name = "Authorization", required = false) String fullJwtToken) {
        try {
            if (fullJwtToken != null) {
                String jwtToken = JwtUtil.extractToken(fullJwtToken);
                if (JwtUtil.authCheck(jwtToken)) {
                    return ResponseEntity.ok(newsFacade.findAllNews(request, jwtToken));
                } else {
                    return ResponseEntity.ok(newsFacade.findAllNews(request, null));
                }
            } else {
                return ResponseEntity.ok(newsFacade.findAllNews(request, null));
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unknown error, please contact site support: " + e.getMessage());
        }
    }

    /**
     * Endpoint for retrieving all unique publishers of news.
     *
     * @return ResponseEntity containing a list of unique publishers or an error message if an unknown error occurs.
     */
    @GetMapping("/getAllPublisher")
    public ResponseEntity<?> getAllPublisher() {
        try {
            return ResponseEntity.ok(newsFacade.findAllUniqPublisher());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unknown error, please contact site support: " + e.getMessage());
        }
    }
}
