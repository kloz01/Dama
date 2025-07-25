package com.dama.backend.dama.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Import Logger
import org.springframework.web.bind.annotation.RestController; // Import LoggerFactory

import com.dama.backend.dama.Request.FriendReplyRequest;
import com.dama.backend.dama.dto.RequestsDTO;
import com.dama.backend.dama.dto.UserDTO;
import com.dama.backend.dama.service.FriendService;
import com.dama.backend.dama.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendController {

    private static final Logger logger = LoggerFactory.getLogger(FriendController.class); // Initialize logger

    private final FriendService service;

    @PostMapping("/sendRequest")
    public ResponseEntity<Map<String, String>> sendFriendRequest( 
                @Valid @RequestBody UserDTO request,
                @AuthenticationPrincipal User currentUser
        ) {
            if (currentUser == null) {
            return new ResponseEntity<>(Map.of("error", "User not authenticated."), HttpStatus.UNAUTHORIZED);
            }

            try {
                service.sendFriendRequest(request, currentUser);
                return new ResponseEntity<>(Map.of("message", "Friend request sent successfully!"), HttpStatus.OK);
            } catch (RuntimeException e) {
                logger.error("Failed to send friend request: {}", e.getMessage(), e); // Log the exception
                return new ResponseEntity<>(Map.of("error", "Failed to send friend request: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @PostMapping("/manageRequest")
    public ResponseEntity<String> manageFriendRequest(@Valid @RequestBody FriendReplyRequest request) {
        try {
            service.manageFriendRequest(request);
            return new ResponseEntity<>("Friend request managed successfully!", HttpStatus.OK); // Changed message
        } catch (RuntimeException e) {
            logger.error("Failed to manage friend request: {}", e.getMessage(), e); // Log the exception
            return new ResponseEntity<>("Failed to manage friend request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String query) {
        try {
            logger.debug("Received search request for query: {}", query);
            List<UserDTO> foundUsers = service.searchUsers(query);
            logger.debug("Found {} users for query: {}", foundUsers.size(), query);

            if (foundUsers.isEmpty()) {
                // Return 200 OK with an empty list if no users are found
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
            return new ResponseEntity<>(foundUsers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument for user search: {}", e.getMessage()); // Log the specific warning
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            // Log the exception here to see the full stack trace
            logger.error("An unexpected error occurred during user search with query: {}", query, e);
            // Return a more informative message if possible, or just the 500 status
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
    @GetMapping("/pendingRequests")
    public ResponseEntity<List<RequestsDTO>> userRequests( @AuthenticationPrincipal User currentUser) {
        try {
            List<RequestsDTO> foundUsers = service.userRequests(currentUser);
            if (foundUsers.isEmpty()) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
            return new ResponseEntity<>(foundUsers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            // Return a more informative message if possible, or just the 500 status
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
    @GetMapping("/sentRequests")
    public ResponseEntity<List<RequestsDTO>> userSentRequests( @AuthenticationPrincipal User currentUser) {
        try {
            List<RequestsDTO> foundUsers = service.userSentRequests(currentUser);
            if (foundUsers.isEmpty()) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
            return new ResponseEntity<>(foundUsers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            // Return a more informative message if possible, or just the 500 status
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
