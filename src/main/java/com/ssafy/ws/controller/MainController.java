package com.ssafy.ws.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.ws.model.dto.ChatRoom;
import com.ssafy.ws.model.dto.User;
import com.ssafy.ws.model.service.ChatService;
import com.ssafy.ws.model.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	private final ChatService service;
	private final UserService uservice;
	
	@GetMapping("/chat/moveChat")
	public String moveChat(Model m) {
		System.out.println("call move chat");
		List<ChatRoom> list = service.selectAllRoom();
		m.addAttribute("list", list);
		return "chatList";
	}
	
	@PostMapping("/chat/createRoom")
	public String createRoom(@RequestParam("name") String roomName) {
		System.out.println("call create Room");
		ChatRoom room = service.createRoom(roomName);
		log.debug("room : {}", room);
		int result = service.insert(room);
		log.debug("Result : {}", result);
		return "redirect:/chat/moveChat";
	}
	
	@GetMapping("/chat/chatRoom/{roomId}")
	public String chatRoom(Model m, @PathVariable("roomId") String roomId) {
		ChatRoom room = service.findRoomById(roomId);
		log.debug("room = {}, roomId = {}", room, roomId);
		m.addAttribute("chatRoom", room);
		return "chatRoom";
	}
	
	@PostMapping("/user/login")
	public String login(@ModelAttribute User dto, HttpSession session) {
		User user = uservice.login(dto);
		session.setAttribute("loginUser", user);
		return "redirect:/";
	}
}
