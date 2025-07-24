package com.dama.backend.dama.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dama.backend.dama.model.FriendRequestStatus;

public interface  FriendRequestStatusRepository extends JpaRepository<FriendRequestStatus, Integer> {
    
}
