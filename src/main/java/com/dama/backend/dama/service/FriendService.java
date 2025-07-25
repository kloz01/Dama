package com.dama.backend.dama.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

import org.slf4j.Logger; // Import @Autowired
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; // Import Logger
import org.springframework.stereotype.Service; // Import LoggerFactory

import com.dama.backend.dama.Request.FriendReplyRequest;
import com.dama.backend.dama.dto.RequestsDTO;
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

@Service
public class FriendService {

    private static final Logger logger = LoggerFactory.getLogger(FriendService.class); // Initialize logger

    // Dichiarazione dei repository come final per l'iniezione nel costruttore
    private final FriendRequestStatusRepository statusRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // Costruttore annotato con @Autowired per l'iniezione delle dipendenze
    @Autowired
    public FriendService(
            FriendRequestStatusRepository statusRepository,
            FriendRequestRepository friendRequestRepository,
            FriendRepository friendRepository,
            UserRepository userRepository) {
        this.statusRepository = statusRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.friendRepository = friendRepository; // Correzione: assicurati che sia friendRepository = friendRepository
        this.userRepository = userRepository;
    }

    public void sendFriendRequest(UserDTO request, User user) {
        FriendRequestStatus defaultStatus = statusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("FriendRequestStatus with ID 1 (Pending Status) not found. Please ensure it exists in your database."));

        User user2 = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User with username " + request.getUsername() + " not found."));

        var friendRequest = FriendRequest.builder()
                .sender(user)
                .receiver(user2)
                .friendRequestStatus(defaultStatus)
                .build();
        friendRequestRepository.save(friendRequest);
    }

    public void manageFriendRequest(FriendReplyRequest request) {
        if (request.getStatus().equals(2)) {
            Optional<FriendRequest> optionalFriendRequest = friendRequestRepository.findById(request.getFriendRequestID());
            if (optionalFriendRequest.isPresent()) {
            FriendRequest  friendRequest = optionalFriendRequest.get();
            var friend = Friend.builder()
                    .user1(friendRequest.getSender())
                    .user2(friendRequest.getReceiver())
                    .build();
                friendRepository.save(friend);
            }
        } 
        friendRequestRepository.delete(
                friendRequestRepository.findById(request.getFriendRequestID())
                        .orElseThrow(() -> new EntityNotFoundException("FriendRequest not found for deletion."))
        );
    }

    public List<UserDTO> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        try {
            logger.debug("Searching users with query: {}", query);
            List<User> foundUsers = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
            logger.debug("Found {} users for query: {}", foundUsers.size(), query);

            List<UserDTO> userDTOs = foundUsers.stream()
                    .map(user -> UserDTO.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .name(user.getName())
                            .surname(user.getSurname())
                            .email(user.getEmail())
                            .build())
                    .collect(toList());
            logger.debug("Converted {} users to UserDTOs.", userDTOs.size());
            return userDTOs;
        } catch (Exception e) {
            logger.error("An error occurred while searching for users with query: {}", query, e);
            throw new RuntimeException("Error searching users: " + e.getMessage(), e);
        }
    }

    public List<RequestsDTO> userSentRequests(User user) {
        try {
            List<FriendRequest> foundUsers = friendRequestRepository.findBySender(user);

            List<RequestsDTO> requestDTOs = foundUsers.stream()
                    .map(sender -> RequestsDTO.builder()
                            .id(sender.getSender().getId())
                            .username(sender.getSender().getUsername())
                            .build())
                    .collect(toList());
            return requestDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Error searching users: " + e.getMessage(), e);
        }
    }
    
    public List<RequestsDTO> userRequests(User user) {
        try {
            List<FriendRequest> foundUsers = friendRequestRepository.findByReceiver(user);

            List<RequestsDTO> requestDTOs = foundUsers.stream()
                    .map(sender -> RequestsDTO.builder()
                            .id(sender.getReceiver().getId())
                            .username(sender.getReceiver().getUsername())
                            .build())
                    .collect(toList());
            return requestDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Error searching users: " + e.getMessage(), e);
        }
    }
}
