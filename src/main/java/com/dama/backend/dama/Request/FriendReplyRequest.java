package com.dama.backend.dama.Request;


import com.dama.backend.dama.model.FriendRequest;
import com.dama.backend.dama.model.FriendRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendReplyRequest {
    private FriendRequestStatus status;
    private FriendRequest friendRequest;
}
