package com.udangtang.test2.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DeleteContentsDTO {
    private int roomNum;
    private int meetNum;
    private int contentsNum;
}
