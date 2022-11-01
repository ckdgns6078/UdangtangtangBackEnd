package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UpdateRoomDTO {
    private String roomHost;
    private int roomNum;
    private String roomKey;
    private String roomPw;
    private String roomName;
    private int roomMember;
}
