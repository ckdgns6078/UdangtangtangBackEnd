package com.udangtang.test2.DAO;

import com.udangtang.test2.DTO.*;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface UdangDAO {

    // 로그인(회원가입) 정보 저장
    // insert 구문 : 데이터가 없으면 isert 하고 있으면 아무것도 안하는 query문
    @Insert("insert into testDB.user (id,name) values (#{id} , #{name}) on duplicate key update id = #{id} , name = #{name}")
    void login(UserDTO dto);


    //방 생성
    @Insert("INSERT INTO testDB.room (roomName , roomKey , roomPw , roomHost , roomMember ) VALUES (#{roomName},#{roomKey},#{roomPw},#{roomHost},#{roomMember})")
    void insertRoom(CreateRoomDTO dto);
    @Insert("insert into testDB.list values ((select roomNum from testDB.room where roomKey = #{roomKey} and roomPw = #{roomPw} and roomHost = #{roomHost} ), #{roomHost} )")
    void insertList(CreateRoomDTO dto);


    //방 참여
//    @Select("select roomNum from room where roomKey = #{roomKey} and roomPw = #{roomPw}")
//    boolean checkRoom(JoinRoomDTO dto);
    @Insert("insert into testDB.list values ((select roomNum from testDB.room where roomKey = #{roomKey} and roomPw = #{roomPw}) , #{id} )")
    boolean joinRoom(JoinRoomDTO dto);

    @Update("update testDB.room set roomMember = roomMember+1 where roomKey = #{roomKey} and roomPw = #{roomPw}")
    boolean updateMember(JoinRoomDTO dto);


    // 방 가져오기 ReadRoom
    @Select("select roomNum , roomName , roomHost , roomMember from testDB.room where roomNum in ( select roomNum from testDB.list where id = #{id})")
    ArrayList<ReadRoomRDTO> readRoom(ReadRoomDTO dto);


    // 방 탈퇴(호스트 x) list 불러오기
    // 내가 만든 방은 안 보여야 돼. 로그인한 사람의 id == room테이블에 있는 roomHost, roomNum = testDB.list.roomNum
    @Select("SELECT * FROM testDB.room WHERE roomHost != #{id} AND roomNum in (select roomNum from testDB.list where id = #{id})")
    ArrayList<SettingReadRoomDTO> outReadRoom(SettingReadRoomDTO settingReadRoomDTO);


    // 방 탈퇴하기(delete)
    @Delete("DELETE FROM testDB.list WHERE roomNum = #{roomNum} AND id = #{id}")
    boolean readForOutRoom(ReadRoomDTO readRoomDTO);

    // 방 탈퇴 후, room테이블의 roomMember Update
    @Update("Update testDB.room set roomMember = roomMember-1 WHERE roomNum = #{roomNum}")
    void updateOutRoom(ReadRoomDTO readRoomDTO);


    // 방 삭제 DeleteRoom  (contents, reply 삭제 -> meeting 삭제 -> room 최종 삭제)
    // 1. (request) room 테이블에서 / 로그인한 id와 roomHost가 같은 room만 select 출력 [기존에 있는 readRoom 이용]
    // 2. (방 삭제버튼 request) 삭제하고자 하는 방의 roomNum값을 매개변수로 받는다.
    // 3. 해당 roomNum값과 같은 reply테이블의 row들 삭제
    // 4. 해당 roomNum값과 같은 contents테이블의 row들 삭제
    // 5. 해당 roomNum값과 같은 meeting테이블의 row들 삭제
    // 6. 해당 roomNum값과 같은 meetingRoom테이블 row들 삭제
    // 7. 해당 roomNum값과 같은 room테이블의 row들 삭제
    // 8. 해당 roomNum값과 같은 list테이블의 row들 삭제
    @Delete("DELETE FROM testDB.reply WHERE roomNum = 2")
    boolean outRoomReply(OutRoomDTO outRoomDTO);
    @Delete("DELETE FROM testDB.meeting WHERE roomNum = 2")
    boolean outRoomMeeting(OutRoomDTO outRoomDTO);
    @Delete("DELETE FROM testDB.contents WHERE roomNum = 2")
    boolean outRoomContents(OutRoomDTO outRoomDTO);
    @Delete("DELETE FROM testDB.meetingRoom WHERE roomNum = 2")
    boolean outRoomMeetingRoom(OutRoomDTO outRoomDTO);
    @Delete("DELETE FROM testDB.room WHERE roomNum = 2")
    boolean outRoom(OutRoomDTO outRoomDTO);
    @Delete("DELETE FROM testDB.list WHERE roomNum = 2")
    boolean outRoomList(OutRoomDTO outRoomDTO);




    // createRoom 했던 방들 리스트 불러오기
    @Select("select roomNum , roomName , roomKey , roomMember,roomPw from testDB.room where roomHost = #{roomHost}")
    ArrayList<UpdateRoomDTO> updateReadRoom(UpdateRoomDTO dto);


    // createRoom 했던 방들의 제목을 수정하는 내용
    @Select("select roomNum , roomName , roomKey , roomMember,roomPw from testDB.room where roomHost = #{roomHost} AND roomNum = #{roomNum}")
    ArrayList<UpdateRoomDTO> updateCheckRoom(UpdateRoomDTO dto);
    @Update("update testDB.room set roomName = #{roomName} where roomNum = #{roomNum}")
    void updateRoom(UpdateRoomDTO dto);
    //=-----------------------------------------------

    // meetingRoom 관련 update
    @Select("select meetNum , meetTitle , meetDate from testDB.meeting where roomNum = #{roomNum}")
    ArrayList<UpdateMeetingDTO> updateReadMeeting(UpdateMeetingDTO dto);
    //------------------------------------------------

    @Update("update testDB.meeting set meetTitle = #{meetTitle} where roomNum = #{roomNum} AND meetNum = #{meetNum}")
    void updateMeeting(UpdateMeetingDTO dto);

    //meetingRoom Update 관련 method
    @Select("select * from testDB.room where roomHost = #{roomHost}")
    ArrayList<DeleteRoomDTO> deleteReadRoom(DeleteRoomDTO dto);

    @Delete("DELETE m,c,l,r\n" +
                "FROM testDB.room AS m\n" +
                "LEFT JOIN testDB.list AS l on l.roomNum = m.roomNum\n" +
                "LEFT JOIN testDB.contents AS c on l.roomNum = c.roomNum\n" +
                "LEFT JOIN testDB.reply AS r on r.roomNum = c.roomNum\n" +
            "WHERE m.roomNum = #{roomNum}")
    void deleteRoom(DeleteRoomDTO dto);

    //---------------------------------------






    //회의 내용 가져오기 Contents
    @Select("select contentsNum ,contentsWriter , contentsText ,contentsTime from testDB.contents where roomNum = #{roomNum} and meetNum = #{meetNum}")
    ArrayList<ReadContentsDTO> readContents(ReadContentsDTO dto);


    // meeting 생성
    @Insert("INSERT INTO testDB.meeting (roomNum, meetTitle, meetDate) VALUES (#{roomNum}, #{meetTitle}, #{meetDate})")
    void createMeeting(CreateMeetingDTO createMeetingDTO);


    // EndMeeting
    @Update("update testDB.meetingRoom set meetingCheck = 0 where roomNum = #{roomNum} and meetingRoomNum = #{meetingRoomNum}")
    void EndMeeting(CreateMeetingDTO createMeetingDTO);


    // meeting 가져오기
    // 들어온 roomNum와 같은 방의 정보들 SELECT
    @Select("SELECT r.roomName , m.meetNum , m.meetTitle , m.meetDate FROM testDB.room as r left join meeting as m on r.roomNum = m.roomNum where r.roomNum = #{roomNum}")
    ArrayList<ReadMeetingDTO> readMeeting(ReadMeetingDTO readMeetingDTO);

    // meeting 삭제(해당 meeting의 meetNum을 갖고 있는 댓글들 먼저 삭제하게 만들 것)
    @Delete("DELETE FROM testDB.meeting WHERE roomNum = #{roomNum} AND meetNum = #{meetNum}")
    boolean deleteMeeting(DeleteMeetingDTO deleteMeetingDTO);
    @Delete("DELETE FROM testDB.reply WHERE roomNum = #{roomNum} AND meetNum = #{meetNum}")
    void deleteFromMeetingReply(DeleteMeetingDTO deleteMeetingDTO);


    // 내용 생성 Contents
    @Insert("INSERT INTO testDB.contents (roomNum, meetNum, contentsText, contentsTime, contentsWriter) VALUES (${roomNum}, ${meetNum}, '${contentsText}', '${contentsTime}', '${contentsWriter}')")
    void createContents(RecordDTO recordDTO);

    //내용 바꾸기 Contents
    @Update("update testDB.contents set contentsText = #{contentsText} where roomNum = #{roomNum} and meetNum = #{meetNum} and contentsNum = #{contentsNum}")
    boolean updateContents(UpdateContentsDTO dto);

    //s내용 삭제하기 Contents
    @Delete("delete from testDB.contents where roomNum = #{roomNum} and meetNum = #{meetNum} and contentsNum = #{contentsNum}")
    boolean deleteContents(DeleteContentsDTO dto);


    // 로그인 정보 삭제
    @Delete("DELETE FROM testDB.user WHERE id = #{userId}")
    void DeleteUser(UserDTO dto);

    // reply 추가
    @Insert("INSERT INTO testDB.reply (roomNum, meetNum, replyText, replyWriter, replyDate) VALUES (${roomNum}, ${meetNum}, '${replyText}', '${replyWriter}', '${replyDate}')")
    void createReply(CreateReplyDTO createReplyDTO);

    // reply 리스트 가져오기
    @Select("select replyNum ,replyText , replyWriter , replyDate from testDB.reply where roomNum = #{roomNum} and meetNum=#{meetNum};")
    ArrayList<ReadReplyDTO> readReply(ReadReplyDTO readReplyDTO);

    // reply 수정
    @Update("UPDATE testDB.reply SET replyText = #{replyText} WHERE roomNum = #{roomNum} AND meetNum = #{meetNum} AND replyNum = #{replyNum}")
    void updateReply(UpdateReplyDTO updateReplyDTO);

    // reply 삭제
    @Delete("DELETE FROM testDB.reply WHERE roomNum = #{roomNum} AND meetNum = #{meetNum} AND replyNum = #{replyNum}")
    void deleteReply(DeleteReplyDTO deleteReplyDTO);

    //MeetingRoom create 생성 , meetingroom 키값 response
    @Insert("insert into testDB.meetingRoom (roomNum , meetingRoomTitle , meetingRoomHost) values ( #{roomNum} , #{meetingRoomTitle} , #{meetingRoomHost})")
    void createMeetingRoom(CreateMeetingRoomDTO dto);
    @Select("select meetingRoomNum from testDB.meetingRoom where roomNum = #{roomNum} and meetingRoomTitle = #{meetingRoomTitle} and meetingRoomHost = #{meetingRoomHost}")
    ArrayList<CreateMeetingRoomRDTO> responseMeetingRoom(CreateMeetingRoomDTO dto);

    @Select("select meetingRoomNum , meetingRoomTitle , meetingRoomHost from testDB.meetingRoom where roomNum = #{roomNum} and meetingCheck = 1")
    ArrayList<ReadMeetingRoomDTO> readMeetingRoom(ReadMeetingRoomDTO dto);

    @Select("select meetingRoomTitle , meetingRoomHost from testDB.meetingRoom where roomNum = #{roomNum} and meetingRoomNum = #{meetingRoomNum}")
    ArrayList<ReadMeetingRoomInDTO> readMeetingRoomInDTO(ReadMeetingRoomInDTO dto);
}
