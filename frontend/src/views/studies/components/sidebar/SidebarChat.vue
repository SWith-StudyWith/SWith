<template>
  <div class= "chatDiv">
    <p class="title">💬 채팅 </p>

    <SidebarChatList :msgs="recvList"/>
    <hr>
    <div class="chat-input" id="chat-input">
      <div class="inputText">
        <input
          v-model="message"
          type="text"
          @keyup="sendMessage"
        >
      </div>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { mapGetters } from 'vuex';
import SidebarChatList from '@/views/studies/components/sidebar/SidebarChatList.vue';

export default {
  name: 'App',
  data() {
    return {
      message: "",
      recvList: [],
      memId: '',
    }
  },
  components:{
    SidebarChatList,
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
        this.memId = msg.memberId;
        // console.log(this.memId);
        this.stompClient.send("/receive", JSON.stringify(msg), {});

        setTimeout(() => {
          const element = document.getElementById('chat-body');
          element.scrollTop = element.scrollHeight;
        }, 0);
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
            setTimeout(() => {
              const element = document.getElementById('chat-body');
              element.scrollTop = element.scrollHeight;
            }, 0);
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
  width: 100%;
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
  margin-top: 40px;
  margin-bottom: 30px;
}
.chat-input{
  display: flex;
}
.inputText{
  bottom: 0;
  margin: 5px;

  display: flex;
  width: 100%;
}
input{
  /* width: 100%; */
  height: 100px;
  border: none;
  border-radius: 10px;
  padding: 15px;
  margin: 10px 0px;
  background-color: #F8F8F8;
  flex-grow: 1;

  /* input 클릭 시, 테두리 없애기 */
  outline: none;
}
</style>
