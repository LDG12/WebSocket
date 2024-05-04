package com.ssafy.ws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ws.model.dto.ChatRoom;
import com.ssafy.ws.model.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class DemoApplicationTests {
	
	@Autowired
	SqlSessionTemplate session;
	
	@Autowired
	ChatService service;
	
	@Test
	void contextLoads() {
		assertNotNull(session);
	}
	
	@Test
	void insertTest() {
		ChatRoom room = service.createRoom("test2");
		log.debug("room : {}", room);
		int result = service.insert(room);
		log.debug("result : {}", result);
		assertEquals(1, result);
	}
}
