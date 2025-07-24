package com.dama.backend.dama.model;

import com.dama.backend.dama.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Import this
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne; // Import this!
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
@Table(name = "friends_request") // Matches your requested table name
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // User who sent the friend request
    @ManyToOne // A FriendRequest has ONE sender (User)
    @JoinColumn(name = "sender_user_id", nullable = false) // Use an explicit and clear column name
    private User sender;

    // User who receives the friend request
    @ManyToOne // A FriendRequest has ONE receiver (User)
    @JoinColumn(name = "receiver_user_id", nullable = false) // Use an explicit and clear column name
    private User receiver;

    // The status of the friend request (PENDING, ACCEPTED, DECLINED)
    @ManyToOne // A FriendRequest has ONE FriendRequestStatus
    @JoinColumn(name = "status_id", nullable = false) // Foreign key to the status table
    private FriendRequestStatus friendRequestStatus;
}