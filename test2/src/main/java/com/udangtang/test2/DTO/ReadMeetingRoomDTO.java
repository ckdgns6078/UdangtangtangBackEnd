package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReadMeetingRoomDTO {
    private int roomNum;
    private int meetingRoomNum;
    private String meetingRoomTitle;
    private String meetingRoomHost;

}
