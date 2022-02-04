<template>
  <div class= "chatDiv">
    <p class="title">💬 채팅 </p>

    <div class="chat-body" id="chat-body">
      <div
        v-for="(item, idx) in recvList"
        :key="idx"
        class="chat"
      >
        <span>
          <img :src="item.imgUrl?item.imgUrl:require(`@/assets/img/navbar/profile.png`)" alt="" aria-expanded="false">
          <p class="nickname">{{ item.nickname }}</p>
        </span>
        <div class="content">내용: {{ item.content }}</div>
        <div class="content">{{ item.createdAt }}</div>
      </div>
    </div>
    <div>
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
  width: 30px;
  height: 30px;
  border-radius: 70%;
}
.nickname{
  font-size: 17px;
  margin-bottom: 2px;
}
.email{
  font-size: 13px;
  color: rgb(199, 199, 199);
}

.inputText{
  position: fixed;
  bottom: 0;
  margin: 10px;
  /* display: block; */

  display: flex;
}
input{
  /* width: 100%; */
  height: 100px;
  border: none;
  border-radius: 3%;

  flex-grow: 1;
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
  background: #BDBDBD;
  border-radius: 10px;
  padding: 1rem;
  width: fit-content;
}
.chat-body::-webkit-scrollbar {
  display: none;
}
</style>
