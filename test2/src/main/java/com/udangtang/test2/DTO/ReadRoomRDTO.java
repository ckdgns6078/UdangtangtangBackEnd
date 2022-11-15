package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReadRoomRDTO {
    private int roomNum;
    private String roomName;
    private String roomHost;
    private int roomMember;
    private String id;
}
