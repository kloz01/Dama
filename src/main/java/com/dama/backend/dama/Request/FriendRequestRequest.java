package com.dama.backend.dama.Request;
import com.dama.backend.dama.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestRequest { 
    private UserDTO user1;
    private UserDTO user2;
}
