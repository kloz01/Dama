package com.dama.backend.dama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dama.backend.dama.model.FriendRequest;
import com.dama.backend.dama.user.User;

public interface  FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    List<FriendRequest> findByReceiver(User receiver);

    List<FriendRequest> findBySender(User sender);
}
