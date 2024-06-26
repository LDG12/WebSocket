<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var = "root" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<input type="text" placeholder="보낼 메세지를 입력하세요." class="content">
	<button type="button" value="전송" class="sendBtn" onclick="sendMsg()">전송</button>
	<button type="button" value="방나가기" class="quit" onclick="quit()">방 나가기 </button>
	<div>
    	<span>메세지</span>
    	<div class="msgArea"></div>
	</div>
</body>
<script>
	let socket = new WebSocket("ws://localhost:8080/ws/chat");
	socket.onopen = function(e){
		console.log('open Server');
		enterRoom(socket);
	};
	socket.onclose = function(e){
		console.log('disconnect and close server');
	};
	socket.onerror = function(e){
		console.log(e);
	}
	
	socket.onmessage = function(e){
		console.log(e.data);
        let msgArea = document.querySelector('.msgArea');
        let newMsg = document.createElement('div');
        newMsg.innerText=e.data;
        msgArea.append(newMsg);
	}
	function enterRoom(socket){
		var enterMsg={"type" : "ENTER", "roomId" : "${chatRoom.roomId}", "sender":"${loginUser.id}", "message":"" };
		socket.send(JSON.stringify(enterMsg));
	}
	
	
	function sendMsg(){
		let content = document.querySelector(".content").value;
		var talkMsg = {"type" : "TALK", "roomId" : "${chatRoom.roomId}", "sender" : "${loginUser.id}", "message":content};
		console.log(talkMsg);
		socket.send(JSON.stringify(talkMsg));
	}
	
	function quit(){
		var quitMsg = {"type" : "QUIT", "roomId" : "${chatRoom.roomId}", "sender" : "${loginUser.id}", "message":""};
		socket.send(JSON.stringify(quitMsg));
		socket.close();
		location.href="${root}/";
	}
</script>
</html>