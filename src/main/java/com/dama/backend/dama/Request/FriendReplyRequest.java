package com.dama.backend.dama.Request;


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
    private Integer friendRequestID;
}
