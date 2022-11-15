package com.udangtang.test2.controller;

import com.udangtang.test2.DAO.UdangDAO;
import com.udangtang.test2.DTO.*;
import com.udangtang.test2.service.SttService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UdangController {
    @Autowired
    private UdangDAO udangDAO;



    //session 생성 method


    //session 검사 method


    // [search] 방 찾기
    @PostMapping("searchRoom")
    public ArrayList<ReadRoomRDTO> searchRoom(@RequestBody ReadRoomRDTO readRoomDTO){
        ArrayList<ReadRoomRDTO> list = new ArrayList<>();
        try {
            list = udangDAO.searchRoom(readRoomDTO);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    // [search] 회의목록 찾기
    @PostMapping("searchMeeting")
    public ArrayList<ReadMeetingDTO> searchMeeting(@RequestBody ReadMeetingDTO readMeetingDTO){
        ArrayList<ReadMeetingDTO> list = new ArrayList<>();
        try {
            list = udangDAO.searchMeeting(readMeetingDTO);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



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

    // 방 생성   c
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

    // 방 입장
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


    // 방 탈퇴(호스트x) list 불러오기
    @PostMapping("outReadRoom")
    public ArrayList<SettingReadRoomDTO> outRoomList(@RequestBody SettingReadRoomDTO settingReadRoomDTO){
        System.out.println(settingReadRoomDTO.getId());
        ArrayList<SettingReadRoomDTO> outRoomList = new ArrayList<>();
        try {
            outRoomList = udangDAO.outReadRoom(settingReadRoomDTO);
            return outRoomList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    // 방 탈퇴하기
    @PostMapping("outRoom")
    public boolean outRoom(@RequestBody ReadRoomDTO readRoomDTO){
        int checkNum = 0;
        // 매개변수로 id와 roomNum값을 받는다.
        try{
            checkNum = udangDAO.readForOutRoom(readRoomDTO);   // list에서 삭제됨.
            if(checkNum > 0){
                udangDAO.updateOutRoom(readRoomDTO);
                return true;
            }else{
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    //방 목록 가져오기  r
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


    // 방 목록 삭제   d
//    @PostMapping("deleteRoom")
//    public boolean DeleteRoom(@RequestBody OutRoomDTO outRoomDTO){
//        try {
//            udangDAO.outRoomReply(outRoomDTO);
//            udangDAO.outRoomMeeting(outRoomDTO);
//            udangDAO.outRoomContents(outRoomDTO);
//            udangDAO.outRoomMeetingRoom(outRoomDTO);
//            udangDAO.outRoom(outRoomDTO);
//            udangDAO.outRoomList(outRoomDTO);
//            return true;
//        }catch(Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    //방 수정 host o ( 방 이름만 수정 )
    @PostMapping("updateReadRoom")
    public ArrayList<UpdateRoomDTO> updateReadRoom(@RequestBody UpdateRoomDTO updateRoomDTO){
        ArrayList<UpdateRoomDTO> list;
        try{
            list = udangDAO.updateReadRoom(updateRoomDTO);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("updateRoom") // roomNum , roomName
    public boolean updateRoom(@RequestBody UpdateRoomDTO updateRoomDTO){
        // 입력한 key값
        String key = updateRoomDTO.getRoomKey();
        // 입력한 pw값
        String pw = updateRoomDTO.getRoomPw();
        // 입력한 roomNum값
        int requestNum = updateRoomDTO.getRoomNum();
        // 입력한 roomName값
        String roomName = updateRoomDTO.getRoomName();

        System.out.println("========================================1");
        System.out.println("key: " + key);
        System.out.println("pw: " + pw);
        System.out.println("roomName: " + roomName);
        System.out.println("requestNum: " + requestNum);
        System.out.println("========================================1");
        ArrayList<UpdateRoomDTO> list;

        // SELECT(결과값 arraylist인데 getRoomKey 가져왔을 때 어떻게 처리하려나)
        udangDAO.updateCheckRoom(updateRoomDTO);
        System.out.println("========================================2");
        System.out.println("key: " + updateRoomDTO.getRoomKey());
        System.out.println("pw: " + updateRoomDTO.getRoomPw());
        System.out.println("roomName: " + updateRoomDTO.getRoomName());
        System.out.println("========================================2");
        if(key.equals(updateRoomDTO.getRoomKey()) && pw.equals(updateRoomDTO.getRoomPw())){
            try{
                // 입력한 roomName값을 넣어준다.
                updateRoomDTO.setRoomName(roomName);
                System.out.println("========================================3");
                System.out.println("key: " + updateRoomDTO.getRoomKey());
                System.out.println("pw: " + updateRoomDTO.getRoomPw());
                System.out.println("roomName: " + updateRoomDTO.getRoomName());
                System.out.println("========================================3");
                udangDAO.updateRoom(updateRoomDTO);
                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }

    // roomNum meetingRoom delete 하기 위해 목록주기
    @PostMapping("deleteReadRoom")
    public ArrayList<DeleteRoomDTO> deleteReadRoom(@RequestBody DeleteRoomDTO deleteRoomDTO){
        ArrayList<DeleteRoomDTO> list;
        try{
            list = udangDAO.deleteReadRoom(deleteRoomDTO);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("deleteRoom")
    public boolean deleteRoom(@RequestBody DeleteRoomDTO deleteRoomDTO){
        try{
            udangDAO.deleteRoom(deleteRoomDTO);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    // meeting 리스트 불러오기
    @PostMapping("updateReadMeeting") // roomNum
    public ArrayList<UpdateMeetingDTO> updateReadMeeting(@RequestBody UpdateMeetingDTO updateMeetingDTO){
        ArrayList<UpdateMeetingDTO> list;
        try{
            list = udangDAO.updateReadMeeting(updateMeetingDTO);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // meeting에 있는 회의들 수정
    @PostMapping("updateMeeting")   // roomNum, meetRoom
    public boolean updateMeeting(@RequestBody UpdateMeetingDTO updateMeetingDTO){
        try{
            udangDAO.updateMeeting(updateMeetingDTO);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }









    // meeting 생성  c
    @PostMapping("createMeeting")
    public Boolean createMeeting(@RequestBody CreateMeetingDTO createMeetingDTO) {
    try{
        udangDAO.createMeeting(createMeetingDTO);
        udangDAO.EndMeeting(createMeetingDTO);
        return true;
    }catch (Exception e){
        e.printStackTrace();
        return false;
    }
}


    // meeting 가져오기 r
    @PostMapping ("readMeeting")
    public ArrayList<ReadMeetingDTO> readMeeting(@RequestBody ReadMeetingDTO readMeetingDTO) {
        System.out.println("readMeetingDTO : " + readMeetingDTO);
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
    @PostMapping("yTest")
    public String running(@RequestPart(value="file") MultipartFile file, @RequestPart(value="key") RecordDTO recordDTO) throws IOException{
        SttService sttService = new SttService();
        ArrayList<String> result = new ArrayList<>();
        String token = "";
        String resultId = "";
        String contentsText = "";
        System.out.println("-------------------------- getroomNum값 : " + recordDTO.getRoomNum());
        System.out.println("-------------------------- getmeetNum값 : " + recordDTO.getMeetNum());




//        RecordDTO recordDTO = new RecordDTO();


        System.out.println(file);

        System.out.println("------------------");
//        // 파일 path, 확장자명 설정
        String tempPath ="C:\\fileTest" + "\\" + file.getOriginalFilename();

//        // 해당 경로에 파일 생성
        File pcmFile = new File(tempPath + ".pcm");

//        // fileStream에 MultipartFile file의 데이터를 입력
        InputStream fileinputStream = file.getInputStream();

//        // fileStream에 담긴 데이터를 convfile변수에 복사
        FileUtils.copyInputStreamToFile(fileinputStream, pcmFile);

        System.out.println("--------< 프론트에서 넘어온 파일 변환 결과(pcmFile)...>----------");
        System.out.println(pcmFile);

        try {
            System.out.println("--------<< STT 진행 중...>>----------");
            token = sttService.test();                         // 토큰 만들기

            resultId = sttService.run(token, pcmFile);         // 녹음 파일 ID 생성하기
            System.out.println("--------< STT 후, ID...>----------");
            System.out.println("resultID : " + resultId);

            result = sttService.get(token, resultId);         // 녹음 파일 ID로 AI로 텍스트 json 형태로 저장하기
            System.out.println("--------< STT 후, 결과값...>----------");
            System.out.println(result);


            for(String sResult : result){
                contentsText += sResult + "/";
            }


            // STT 결과값 dto의 contentsText에 세팅
            recordDTO.setContentsText(contentsText);
//            recordDTO.setRoomNum(1);
//            recordDTO.setMeetNum(2);
//            recordDTO.setContentsTime("23:30:00");
//            recordDTO.setContentsWriter("갸갸갹");

            // DAO로 DB연동(함수명 변경할것)
            udangDAO.createContents(recordDTO);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("음성 파일 변환에 문제가 생겼습니다.");
        }

        return contentsText;
    }



    // Contents 내용 가져오기 r
    @PostMapping("readContents")
    public ArrayList<ReadContentsDTO> readContents(@RequestBody ReadContentsDTO readContentsDTO){
        System.out.println(readContentsDTO);
        ArrayList<ReadContentsDTO> list;
        try{
            list = udangDAO.readContents(readContentsDTO);
//            String text = readContentsDTO.getContentsText();
//            String changeText = text.replace("/", "\n");
//            readContentsDTO.setContentsText(changeText);

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
        int checkNum = 0;
        try {
            checkNum = udangDAO.updateReply(updateReplyDTO);
            if(checkNum > 0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // reply 삭제 d
    @PostMapping("deleteReply")
    public boolean deleteReply(@RequestBody DeleteReplyDTO deleteReplyDTO) {
        try {
            System.out.println(deleteReplyDTO.getRoomNum());
            System.out.println(deleteReplyDTO.getMeetNum());
            System.out.println(deleteReplyDTO.getReplyWriter());
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

//    @PostMapping("updateMeetingState")
//    public boolean updateMeetingState(@RequestBody UpdateMeetingStateDTO updateMeetingStateDTO){
//        try {
//            udangDAO.UpdateMeetingState(updateMeetingStateDTO);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("meetingState 오류");
//            return false;
//        }
//    }

}

