<template>
  <div class= "chatDiv">
    <p class="title">💬 채팅 </p>

    <div class="chat-body" id="chat-body">
      <div
        v-for="(item, idx) in recvList"
        :key="idx"
        class="chat"
      >
        <!-- 상대가 보낸 메세지  -->
        <div class="chat-other-message">
          <div class="chat-other-img">
            <img :src="item.imgUrl?item.imgUrl:require(`@/assets/img/navbar/profile.png`)" alt="" aria-expanded="false">
          </div>
          <div class="chat-other-content">
            <div class="chat-other-content1">
              <p class="chat-other-nickname">{{ item.nickname }}</p>
              <p class="chat-other-message-time">{{ item.createdAt }}</p>
            </div>
            <!-- <p class="chat-other-nickname">{{ item.memberId }}</p> -->
            <div class="chat-other-content2">
              <p class="other-content">{{ item.content }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <hr>
    <div class="chat-input" id="chat-input">
      <div class="inputText">
        <input
          v-model="message"
          type="text"
          @keyup="sendMessage"
        >
        <!-- <button :disabled='text === ""'>Send</button> -->
      </div>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { mapGetters } from 'vuex';

export default {
  name: 'App',
  data() {
    return {
      message: "",
      recvList: []
    }
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect()

  },
  computed: {
    ...mapGetters([
      'getUserInfo'
    ]),
  },
  methods: {
    sendMessage (e) {
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.send()
        this.message = ''
      }
    },
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          studyId: this.$route.params.studyId,
          memberId: this.getUserInfo.memberId,
          imgUrl: this.getUserInfo.profileImg,
          nickname: this.getUserInfo.nickname,
          content: this.message
        };
        // alert(msg.memberId);
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = 'http://localhost:8080/api/ws/'
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send/" + this.$route.params.studyId, res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body))
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );
    }
  }
}
</script>
<style scoped>
.chatDiv{
  /* float: right; */
  width: 90%;
  text-align: left;
  flex-direction: column;

  height: 100vh;
  display: flex;
  flex-direction: column;
}
.row{
  margin-bottom: 20px;
  border: 1px solid;
}
.title{
  font-size: 25px;
  font-weight:500;
  margin-top: 20px;
  margin-bottom: 30px;
}
img {
  width: 40px;
  height: 40px;
  border-radius: 70%;
}
.chat-input{
  display: flex;
}
.inputText{
  bottom: 0;
  margin: 10px;

  display: flex;
  width: 100%;
}
input{
  /* width: 100%; */
  height: 90px;
  border: none;
  border-radius: 10px;

  flex-grow: 1;

  /* input 클릭 시, 테두리 없애기 */
  outline: none;
  margin-bottom: 20px;
}

.chat-userinfo-box{
  margin: 10px;
}


/*  */
.chat-body{
  flex-grow: 1;
  /* overflow: auto; */
  padding: 1rem;

  overflow: scroll;
  scroll-behavior: smooth;
}
.chat {
  border-radius: 10px;
  /* padding: 1rem; */
  padding-bottom: 15px;
  /* width: fit-content; */
}
.chat-body::-webkit-scrollbar {
  display: none;
}
.chat-other-message{
  display: flex;
}
.chat-other-img{
  margin-right: 1rem;
}
.chat-other-nickname{
  font-size: 14px;
  font-weight: 700;
  margin-top: 0;
  margin-block-end: 0rem;
}
.chat-other-content{
  width: 100%;
}
/* nickname + date */
.chat-other-content1{
  display: flex;
  align-items: flex-end;
  line-break: anywhere;
  justify-content: flex-end;
  width: 100%;
}
/* message content */
.chat-other-content2{
  display: flex;
  align-items: flex-end;
  line-break: anywhere;
}
.other-content{
  margin: 0.4rem 1rem 0 0;
  border-radius: 0px 10px 10px 10px;
  background-color: #f3f3f3;
  max-width: 180px;
  color: #414141;
  padding: 0.8rem;
  font-size: 14px;
}
.chat-other-message-time {
  margin: 0;
  font-size: 10px;
  color: #9c9c9c;
  margin-left: auto;
}
</style>
