package com.dama.backend.dama.Request;


import com.dama.backend.dama.model.FriendRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendReplyRequest {
    private Integer status;
    private FriendRequest friendRequest;
}
