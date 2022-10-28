package com.udangtang.test2.controller;

import com.udangtang.test2.DAO.UdangDAO;
import com.udangtang.test2.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

@RestController
public class UdangController {
    private static final String DIRECTORY = "C:/PDF";
    private static final String DEFAULT_FILE_NAME = "java-tutorial.pdf";
    private ServletContext servletContext;

    @Autowired
    private UdangDAO udangDAO;



    //session 생성 method


    //session 검사 method


    //회원가입 및 로그인 더 해야됨
    @PostMapping("login")
    public boolean login(@RequestBody UserDTO userDTO){
        //session 검사 method
        try{
            udangDAO.login(userDTO);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }
    //방 생성   c
    @PostMapping("createRoom")
    public boolean createRoom(@RequestBody CreateRoomDTO createRoomDTO){
        //session 검사 method
        try{
            udangDAO.insertRoom(createRoomDTO);
            udangDAO.insertList(createRoomDTO);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //방 입장
    @PostMapping("joinRoom")
    public boolean joinRoom(@RequestBody JoinRoomDTO joinRoomDTO){
        try{

            udangDAO.joinRoom(joinRoomDTO);
            System.out.println("_____________________");
            System.out.print(joinRoomDTO);
            System.out.println("_____________________");
            udangDAO.updateMember(joinRoomDTO);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    //방 목록 가져오기  r
    @PostMapping("readRoom")
    public List readRoom(@RequestBody ReadRoomDTO readRoomDTO) {
        ArrayList<ReadRoomRDTO> list = new ArrayList<>();

        try{
            list = udangDAO.readRoom(readRoomDTO);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
//    // 방 목록 삭제   d
//    @PostMapping("/DeleteRoom")
//    public boolean DeleteRoom(){
//
//    }
// meeting 생성  c
@PostMapping("createMeeting")
public Boolean createMeeting(@RequestBody CreateMeetingDTO createMeetingDTO) {
    try{
        udangDAO.createMeeting(createMeetingDTO);
        return true;
    }catch (Exception e){
        e.printStackTrace();
        return false;
    }
}


    // meeting 가져오기 r
    @PostMapping ("readMeeting")
    public ArrayList<ReadMeetingDTO> readMeeting(@RequestBody ReadMeetingDTO readMeetingDTO) {
        ArrayList<ReadMeetingDTO> readMeetingList = new ArrayList<>();
        try{
            readMeetingList = udangDAO.readMeeting(readMeetingDTO);
            return readMeetingList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // meeting 삭제  d
    @PostMapping("deleteMeeting")
    public boolean deleteMeeting(@RequestBody DeleteMeetingDTO deleteMeetingDTO) {
        boolean check = false;

        try{
            // meeting 삭제 전, 해당 meeting의 reply 전체 삭제
            udangDAO.deleteFromMeetingReply(deleteMeetingDTO);
            //  삭제 후, 해당 meeting 삭제
            udangDAO.deleteMeeting(deleteMeetingDTO);
            check = true;
        }catch (Exception e){
            e.printStackTrace();
            return check;
        }

        return check;
    }


    // Contents 작성 c 음성인식이 완료되고 추후 설계



    // Contents 내용 가져오기 r
    @PostMapping("readContents")
    public ArrayList<ReadContentsDTO> readContents(@RequestBody ReadContentsDTO readContentsDTO){
        ArrayList<ReadContentsDTO> list;
        try{
            list = udangDAO.readContents(readContentsDTO);

            return list;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // Contents 수정 u
    @PostMapping("updateContents")
    public boolean updateContents(@RequestBody UpdateContentsDTO updateContentsDTO){
        try{
            udangDAO.updateContents(updateContentsDTO);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


    }
    // Contents 삭제 d
    @PostMapping("deleteContents")
    public boolean deleteContents(@RequestBody DeleteContentsDTO deleteContentsDTO){
        try{
            udangDAO.deleteContents(deleteContentsDTO);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // reply 추가 c
    @PostMapping("createReply")
    public boolean createReply(@RequestBody CreateReplyDTO createReplyDTO) {
        try {
            udangDAO.createReply(createReplyDTO);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // reply 리스트 가져오기 r
    @PostMapping("readReply")
    public ArrayList<ReadReplyDTO> readReply(@RequestBody ReadReplyDTO readReplyDTO) {
        ArrayList<ReadReplyDTO> list;
        try {
            list = udangDAO.readReply(readReplyDTO);
            return list;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





    // reply 수정 u
    @PostMapping("updateReply")
    public boolean updateReply(@RequestBody UpdateReplyDTO updateReplyDTO) {
        try {
            udangDAO.updateReply(updateReplyDTO);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // reply 삭제 d
    @PostMapping("deleteReply")
    public boolean deleteReply(@RequestBody DeleteReplyDTO deleteReplyDTO) {
        try {
            udangDAO.deleteReply(deleteReplyDTO);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @PostMapping("createMeetingRoom")
    public ArrayList<CreateMeetingRoomRDTO> createMeetingRoom(@RequestBody CreateMeetingRoomDTO createMeetingRoomDTO){
        ArrayList<CreateMeetingRoomRDTO> list;
        try{
            udangDAO.createMeetingRoom(createMeetingRoomDTO);
            list = udangDAO.responseMeetingRoom(createMeetingRoomDTO);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @PostMapping("readMeetingRoom")
    public ArrayList<ReadMeetingRoomDTO> readMeetingRoom(@RequestBody ReadMeetingRoomDTO readMeetingRoomDTO){
        ArrayList<ReadMeetingRoomDTO> list;
        try{
            list = udangDAO.readMeetingRoom(readMeetingRoomDTO);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("readMeetingRoomIn")
    public ArrayList<ReadMeetingRoomInDTO> readMeetingRoomIn(@RequestBody ReadMeetingRoomInDTO readMeetingRoomInDTO){
        ArrayList<ReadMeetingRoomInDTO> list;
        try{
        list = udangDAO.readMeetingRoomInDTO(readMeetingRoomInDTO);
        return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //file 타입 전송
//    @PostMapping("/yTest")
//    public void downloadFile3(@RequestBody File file){
//            System.out.println(file);
//
//    }
//    public String post(@RequestBody BlobType data) {
//        System.out.println(data);
//
//        return "나 값이 들어온 것 같음";
//    }



}

