package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReadMeetingRoomInDTO {
    private int roomNum;
    private int meetingRoomNum;
    private String meetingroomTitle;
    private String meetingRoomHost;
}
