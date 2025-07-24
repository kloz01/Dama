package com.dama.backend.dama.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dama.backend.dama.model.Friend;


public interface FriendRepository extends JpaRepository<Friend, Integer> {
    
}
