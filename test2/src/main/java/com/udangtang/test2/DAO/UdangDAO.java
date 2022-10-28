package com.udangtang.test2.DAO;

import com.udangtang.test2.DTO.*;
import org.apache.ibatis.annotations.*;
import org.hibernate.mapping.Join;

import java.util.ArrayList;

@Mapper
public interface UdangDAO {

    // 로그인(회원가입) 정보 저장
    // insert 구문 : 데이터가 없으면 isert 하고 있으면 아무것도 안하는 query문
    @Insert("insert into user(id,name) values (#{id} , #{name}) on duplicate key update id = #{id} , name = #{name}")
    void login(UserDTO dto);


    //방 생성
    @Insert("INSERT INTO testDB.room (roomName , roomKey , roomPw , roomHost , roomMember ) VALUES (#{roomName},#{roomKey},#{roomPw},#{roomHost},#{roomMember})")
    void insertRoom(CreateRoomDTO dto);
    @Insert("insert into list values ((select roomNum from room where roomKey = #{roomKey} and roomPw = #{roomPw} and roomHost = #{roomHost} ), #{roomHost} )")
    void insertList(CreateRoomDTO dto);


    //방 참여
//    @Select("select roomNum from room where roomKey = #{roomKey} and roomPw = #{roomPw}")
//    boolean checkRoom(JoinRoomDTO dto);
    @Insert("insert into list values ((select roomNum from room where roomKey = #{roomKey} and roomPw = #{roomPw}) , #{id} )")
    boolean joinRoom(JoinRoomDTO dto);

    @Update("update room set roomMember = roomMember+1 where roomKey = #{roomKey} and roomPw = #{roomPw}")
    boolean updateMember(JoinRoomDTO dto);



    //방 가져오기 ReadRoom
    @Select("select roomNum , roomName , roomHost , roomMember from room where roomNum in ( select roomNum from list where id = #{id})")
    ArrayList<ReadRoomRDTO> readRoom(ReadRoomDTO dto);


    //회의 내용 가져오기 Contents
    @Select("select contentsNum ,contentsWriter , contentsText ,contentsTime from contents where roomNum = #{roomNum} and meetNum = #{meetNum}")
    ArrayList<ReadContentsDTO> readContents(ReadContentsDTO dto);


    // meeting 생성
    @Insert("INSERT INTO testDB.meeting (roomNum, meetTitle, meetDate) VALUES (#{roomNum}, #{meetTitle}, #{meetDate})")
    void createMeeting(CreateMeetingDTO createMeetingDTO);

    // meeting 가져오기
    // 들어온 roomNum와 같은 방의 정보들 SELECT
    @Select("SELECT r.roomName , m.meetNum , m.meettitle , m.meetDate FROM room as r left join meeting as m on r.roomNum = m.roomNum where r.roomNum = #{roomNum}")
    ArrayList<ReadMeetingDTO> readMeeting(ReadMeetingDTO readMeetingDTO);

    // meeting 삭제(해당 meeting의 meetNum을 갖고 있는 댓글들 먼저 삭제하게 만들 것)
    @Delete("DELETE FROM testDB.meeting WHERE roomNum = #{roomNum} AND meetNum = #{meetNum}")
    boolean deleteMeeting(DeleteMeetingDTO deleteMeetingDTO);
    @Delete("DELETE FROM testDB.reply WHERE roomNum = #{roomNum} AND meetNum = #{meetNum}")
    void deleteFromMeetingReply(DeleteMeetingDTO deleteMeetingDTO);


    // 내용 생성 Contents
    @Insert("INSERT INTO testDB.contents (roomNum, meetNum, contentsText, contentsTime, contentsWriter) VALUES (${roomNum}, ${meetNum}, '${contentsText}', '${contentsTime}', '${contentsWriter}')")
    void createTest(RecordDTO recordDTO);

    //내용 바꾸기 Contents
    @Update("update contents set contentsText = #{contentsText} where roomNum = #{roomNum} and meetNum = #{meetNum} and contentsNum = #{contentsNum}")
    boolean updateContents(UpdateContentsDTO dto);

    //s내용 삭제하기 Contents
    @Delete("delete table contents where roomNum = #{roomNum} and meetNum =#{meetNum} and contentsNum = #{contentsNum}")
    boolean deleteContents(DeleteContentsDTO dto);


    // 로그인 정보 삭제
    @Delete("DELETE FROM testDB.user WHERE id = #{userId}")
    void DeleteUser(UserDTO dto);

    // reply 추가
    @Insert("INSERT INTO testDB.reply (roomNum, meetNum, replyText, replyWriter) VALUES (${roomNum}, ${meetNum}, '${replyText}', '${replyWriter}')")
    void createReply(CreateReplyDTO createReplyDTO);

    // reply 리스트 가져오기
    @Select("select replyNum ,replyText , replyWriter , replyDate from reply where roomNum = #{roomNum} and meetNum=#{meetNum};")
    ArrayList<ReadReplyDTO> readReply(ReadReplyDTO readReplyDTO);

    // reply 수정
    @Update("UPDATE testDB.reply SET replyText = '${replyText}' WHERE roomNum = ${roomNum} AND meetNum = ${meetNum} AND replyWriter = '${replyWriter}'")
    void updateReply(UpdateReplyDTO updateReplyDTO);

    // reply 삭제
    @Delete("DELETE FROM testDB.reply WHERE roomNum = #{roomNum} AND meetNum = #{meetNum} AND replyWriter = '${replyWriter}')")
    void deleteReply(DeleteReplyDTO deleteReplyDTO);

    //MeetingRoom create 생성 , meetingroom 키값 response
    @Insert("insert into meetingRoom(roomNum , meetingRoomTitle , meetingRoomHost) values ( #{roomNum} , #{meetingRoomTitle} , #{meetingRoomHost})")
    void createMeetingRoom(CreateMeetingRoomDTO dto);
    @Select("select meetingRoomNum from meetingRoom where roomNum = #{roomNum} and meetingRoomTitle = #{meetingRoomTitle} and meetingRoomHost = #{meetingRoomHost}")
    ArrayList<CreateMeetingRoomRDTO> responseMeetingRoom(CreateMeetingRoomDTO dto);

    @Select("select meetingRoomNum , meetingRoomTitle , meetingRoomHost from meetingRoom where roomNum = #{roomNum} and meetingCheck = 1")
    ArrayList<ReadMeetingRoomDTO> readMeetingRoom(ReadMeetingRoomDTO dto);

    @Select("select meetingRoomTitle , meetingRoomHost from meetingRoom where roomNum = #{roomNum} and meetingRoomNum = #{meetingRoomNum}")
    ArrayList<ReadMeetingRoomInDTO> readMeetingRoomInDTO(ReadMeetingRoomInDTO dto);
}
