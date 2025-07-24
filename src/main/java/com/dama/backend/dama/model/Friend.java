package com.dama.backend.dama.model;

import com.dama.backend.dama.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Add this import
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Better for auto-incrementing IDs in PostgreSQL
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_1_id", nullable = false) // Use _id suffix and make it non-nullable if always present
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_2_id", nullable = false) // Use _id suffix and make it non-nullable if always present
    private User user2;
}