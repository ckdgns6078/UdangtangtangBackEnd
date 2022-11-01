package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UpdateMeetingDTO {
    private int roomNum;    //request 받는 값
    private int meetNum;    //update할때 request받는 값
    private String meetTitle;
    private String meetDate;

}
