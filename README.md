## WebSocket Real Time Chatting
간단한 인증 기능만 구현한 이후, Spring의 WebSocket을 활용하여 실시간 채팅을 구현한 내용입니다.

회원가입 진행 없이 미리 DB에 저장된 User 정보를 토대로 로그인을 하고,

채팅방을 생성하거나 기존에 만들어진 방에 들어가 실시간 채팅을 진행하는 것을 구현하였습니다.

테스트는 2개의 브라우저를 통해 양방향 소통이 되는 것인지를 확인 및 채팅방 입장 전, 

이전 채팅의 내용 출력이 잘 되는지에 중점을 두었습니다.

![실시간채팅1](https://github.com/LDG12/WebSocket/assets/99185099/ec1b2146-1624-44ec-9799-5b947e71e0d6)



## 사용 도구
1. Spring Boot
2. MySql
3. MyBatis
4. STS

## 구현 기능
1. WebSocket을 통한 실시간 채팅 기능
2. 채팅방에 User 입장시, Default로 30분 이전까지의 채팅 내용을 Loading 후 출력
3. STOMP 방식으로 재구현 예정
