package com.udangtang.test2.DTO;

import lombok.Data;

@Data
public class SettingReadRoomDTO {
    private String id;
    private int roomNum;
    private String roomName;
    private String roomKey;
    private String roomPw;
    private String roomHost;
    private int roomMember;
}

