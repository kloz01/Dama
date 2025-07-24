package com.dama.backend.dama.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dama.backend.dama.model.FriendRequest;

public interface  FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    
}
