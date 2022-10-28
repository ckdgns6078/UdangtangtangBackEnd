package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateMeetingRoomRDTO {
    private int meetingRoomNum;
    private String roomName;
    private String meetTitle;
    private String meetDate;
    private int meetNum;

}
