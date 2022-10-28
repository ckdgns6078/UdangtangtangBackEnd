package com.udangtang.test2;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();


    // 클라이언트로부터 메시지를 받으면
    // List의 모든 클라이언트 세션을 반복하고
    // 보낸 사람의 세션ID를 비교하여 보낸 사람을 제외한
    // 모든 클라이언트에게 메시지를 보낸다.
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
        throws InterruptedException, IOException{
            for(WebSocketSession webSocketSession : sessions){
                if(webSocketSession.isOpen() &&
                        !session.getId().equals(webSocketSession.getId())){

                    webSocketSession.sendMessage(message);
                }
            }
    }

    // 모든 클라이언트를 추적할 수 있도록
    // 수신된 세션을 세션 List에 추가한다.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
}
