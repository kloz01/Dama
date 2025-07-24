package com.dama.backend.dama.model;

import com.dama.backend.dama.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friends_request")
public class FriendRequest {
    @Id
    @GeneratedValue
    private Integer id;
    
        @JoinColumn(name = "user_1")
        @JsonBackReference
    private User user1;

        @JoinColumn(name = "user_2")
        @JsonBackReference
    private User user2;
        @JoinColumn(name = "friend_request_status_id")
    private FriendRequestStatus friendRequestStatus;
}