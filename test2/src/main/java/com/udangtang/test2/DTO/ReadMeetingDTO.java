package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class ReadMeetingDTO {
    private int roomNum;       //request 받는 값
    private String roomName;
    private int meetNum;
    private String meetTitle;
    private String meetDate;

}
