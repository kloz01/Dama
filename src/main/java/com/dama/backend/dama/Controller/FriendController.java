package com.dama.backend.dama.Controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dama.backend.dama.Request.FriendReplyRequest;
import com.dama.backend.dama.Request.FriendRequestRequest;
import com.dama.backend.dama.dto.UserDTO;
import com.dama.backend.dama.service.FriendService;
import com.dama.backend.dama.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dama2077/friends")
@RequiredArgsConstructor
public class FriendController {
      private final FriendService service;
@PostMapping("/sendRequest")
    public ResponseEntity<String> sendFriendRequest(
            @Valid @RequestBody FriendRequestRequest request,
            @AuthenticationPrincipal User currentUser 
    ) {
        if (currentUser == null) {
            return new ResponseEntity<>("User not authenticated.", HttpStatus.UNAUTHORIZED);
        }

        try {
            // Passa l'intero oggetto User al servizio
            service.sendFriendRequest(request, currentUser);
            return new ResponseEntity<>("Friend request sent successfully!", HttpStatus.OK);
        } catch (RuntimeException e) {
            // Potresti voler distinguere tra diversi tipi di eccezioni qui
            return new ResponseEntity<>("Failed to send friend request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        @PostMapping("/manageRequest")
    public ResponseEntity<String> manageFriendRequest(@Valid @RequestBody FriendReplyRequest request) {
        try {
            service.manageFriendRequest(request);
            return new ResponseEntity<>("Friend request sent successfully!", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to send friend request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search") 
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String query) {
        try {
            List<UserDTO> foundUsers = service.searchUsers(query);

            if (foundUsers.isEmpty()) {   
                return new ResponseEntity<>(foundUsers, HttpStatus.OK);
            }
            return new ResponseEntity<>(foundUsers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
