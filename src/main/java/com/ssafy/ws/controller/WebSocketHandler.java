package com.ssafy.ws.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ws.model.dto.ChatMessage;
import com.ssafy.ws.model.dto.ChatRoom;
import com.ssafy.ws.model.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler{
	private final ObjectMapper objectMapper;
	private final ChatService service;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
	}
	
	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = service.findRoomById(chatMessage.getRoomId());
        Set<WebSocketSession> sessions=room.getSessions();   //방에 있는 현재 사용자 한명이 WebsocketSession
        log.debug("sessions : {}", sessions);
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            //사용자가 방에 입장하면  Enter메세지를 보내도록 해놓음.  이건 새로운사용자가 socket 연결한 것이랑은 다름.
            //socket연결은 이 메세지 보내기전에 이미 되어있는 상태
            sessions.add(session);
            log.debug("sessions : {}", sessions);
            chatMessage.setMessageDate(service.returnNow());
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");  //TALK일 경우 msg가 있을 거고, ENTER일 경우 메세지 없으니까 message set
            List<ChatMessage> list = service.bringPrevMessage(chatMessage.getRoomId());
            list.add(chatMessage);
            sendToEachSocket(sessions,list);
        }else if (chatMessage.getType().equals(ChatMessage.MessageType.QUIT)) {
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다..");
            sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(chatMessage)) );
        }else if (chatMessage.getType().equals(ChatMessage.MessageType.TALK)){
        	log.debug("send Message : {}", chatMessage);
        	service.insertMessage(chatMessage);
            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)) ); //입장,퇴장 아닐 때는 클라이언트로부터 온 메세지 그대로 전달.
        }
    }
	private  void sendToEachSocket(Set<WebSocketSession> sessions, List<ChatMessage> list){
        sessions.parallelStream().forEach( roomSession -> {
            try {
            	if(roomSession.isOpen()) {
            		for(ChatMessage message : list) {
            			TextMessage tMessage = new TextMessage(objectMapper.writeValueAsString(message));
            			roomSession.sendMessage(tMessage);
            		}
            	}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
	private  void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message){
        sessions.parallelStream().forEach( roomSession -> {
            try {
            	if(roomSession.isOpen()) {
            		roomSession.sendMessage(message);            		
            	}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
