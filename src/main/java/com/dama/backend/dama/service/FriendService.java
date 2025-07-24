package com.dama.backend.dama.service;

import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.toList;

import com.dama.backend.dama.Request.FriendReplyRequest;
import com.dama.backend.dama.Request.FriendRequestRequest;
import com.dama.backend.dama.dto.UserDTO;
import com.dama.backend.dama.model.Friend;
import com.dama.backend.dama.model.FriendRequest;
import com.dama.backend.dama.model.FriendRequestStatus;
import com.dama.backend.dama.repository.FriendRepository;
import com.dama.backend.dama.repository.FriendRequestRepository;
import com.dama.backend.dama.repository.FriendRequestStatusRepository;
import com.dama.backend.dama.repository.UserRepository;
import com.dama.backend.dama.user.User;

import jakarta.persistence.EntityNotFoundException;

public class FriendService {
    
    static private FriendRequestStatusRepository statusRepository;
    static private FriendRequestRepository friendRequestRepository;
    static private FriendRepository friendRepository;
    static private UserRepository userRepository;

public void sendFriendRequest(FriendRequestRequest request, User user) {
        FriendRequestStatus defaultStatus = statusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Role with ID 1 (Pending Status) not found")); // Supponendo che 1 sia lo stato di pending

        User user2 = userRepository.findByUsername(request.getUser2().getUsername())
                      .orElseThrow(() -> new RuntimeException("User with ID " + request.getUser2().getId() + " not found"));
        var friendRequest = FriendRequest.builder()
                .user1(user)
                .user2(user2) 
                .friendRequestStatus(defaultStatus)
                .build();
        friendRequestRepository.save(friendRequest);
    }

    public void manageFriendRequest(FriendReplyRequest request)
    {
        if (request.getStatus().getId().equals(2))
        {
            var friend = Friend.builder()
            .user1(request.getFriendRequest().getUser1())
            .user2(request.getFriendRequest().getUser2())
            .build();
            friendRepository.save(friend);
        }
        else 
        {
           friendRequestRepository.delete(
                friendRequestRepository.findById(request.getFriendRequest().getId())
                    .orElseThrow(() -> new EntityNotFoundException("FriendRequest not found"))
            );
        }

    }
     public List<UserDTO> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<User> foundUsers = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
        return foundUsers.stream()
                         .map(user -> UserDTO.builder()
                                 .id(user.getId())
                                 .username(user.getUsername())
                                 .name(user.getName())
                                 .surname(user.getSurname())
                                 .email(user.getEmail())
                                 .build())
                         .collect(toList());
    }
}
