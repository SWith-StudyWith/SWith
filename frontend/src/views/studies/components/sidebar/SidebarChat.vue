<template>
  <div>
    <div
      v-for="(item, idx) in recvList"
      :key="idx"
      class="chat-userinfo-box"
    >
      <img :src="item.imgUrl?item.imgUrl:require(`@/assets/img/navbar/profile.png`)" alt="" aria-expanded="false" style="width: 30px; background: #BDBDBD; border-radius: 70%; overflow: hidden;" >
      <div class="content">{{ item.createdAt }}</div>
      <div class="nickname">닉네임: {{ item.nickname }}</div>
      <div class="content">내용: {{ item.content }}</div>
    </div>
    내용: <input
      v-model="message"
      type="text"
      @keyup="sendMessage"
    >
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

</style>
