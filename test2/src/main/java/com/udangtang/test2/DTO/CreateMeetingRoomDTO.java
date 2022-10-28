package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateMeetingRoomDTO {
    private int roomNum;
    private String meetingRoomTitle;
    private String meetingRoomHost;
}
