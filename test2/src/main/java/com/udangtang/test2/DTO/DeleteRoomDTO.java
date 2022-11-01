package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DeleteRoomDTO {
    private String roomHost; //request 값
    private int roomNum;
    private String roomName;
    private String roomKey;
    private String roomPw;
    private String roomMember;
}
