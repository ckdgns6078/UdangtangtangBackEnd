package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ReadContentsDTO {
    private int roomNum;
    private int meetNum;
    private int contentsNum;
    private String contentsWriter;
    private String contentsText;
    private String contentsTime;
    private int meetId;
}
