package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JoinRoomDTO {
    private String id;
    private String roomKey;
    private String roomPw;
    private int roomNum;

}
