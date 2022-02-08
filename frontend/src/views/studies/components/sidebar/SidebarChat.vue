<template>
  <div class= "chatDiv">
    <p class="title">💬 채팅 </p>
    <!-- <div class="chat-body spinner-border text-light" role="status" v-if="state.loading">
      <span class="visually-hidden">Loading...</span>
    </div> -->
    <div class="chat-body" id="chat-body"
      @scroll="scrollMove">

      <SidebarChatMessage
        v-for="(chat, idx) in state.chatList"
        :key="idx"
        :chat = "chat"
        :prev="[idx == 0 ? null : state.chatList[idx-1]]"
      >
      </SidebarChatMessage>
    </div>

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
import SidebarChatMessage from '@/views/studies/components/sidebar/SidebarChatMessage.vue';
import dayjs from 'dayjs'
import { computed, onUpdated, reactive } from '@vue/runtime-core';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import { getChatList} from '@/api/study'

export default {
  name: 'App',
  data() {
    return {
      message: "",
      recvList: [],
    }
  },
  props:{
    chatLog : Object
  }
  ,
  components:{
    SidebarChatMessage
  },
  setup(props){
    const store = useStore();
    const route = useRoute();

    const state = reactive({
      // chatList
      chatList: computed(() => {
        return props.chatLog
      }),

      // messageList에서 불러온 list
      // loadList: [],
      loadList: computed(() => {
        return props.chatLog
      }),

      // 채팅창 열었을 때, 스크롤 맨 밑에 있도록
      init: true,
      // message 전송했을 때,
      sended: false,

      loaded: false,
      loading: true,
      isLoading: computed(() => {
        return state.loding
      }),

      // 스크롤 상단에 도착했는지
      isTop: false,
      // 더이상 API 호출X  => 추가로 불러온 list들이 <15일 때,
      isNoScroll: false,
      // 스크롤 위치 저장하기 위함
      prevScrollHeight: 0,
      element: computed(() => {
        return document.getElementById('chat-body')
      }),
    })

    // 이전 채팅방 리스트 가져오기
    function messageList() {
      console.log('더 가져오자~');

      getChatList(
        route.params.studyId,
        props.chatLog.length,
        (res) => {
          console.log(res.data)
          // console.log("props.chatLog.length : " +props.chatLog.length);
          if (res.data.code === 200) {
            store.dispatch('GET_CHAT_LIST', {studyId: route.params.studyId, index: props.chatLog.length});

            var size = res.data.data.length
            // console.log("size : " + size)

            for(var i = 0; i < size; i++){
              // console.log(res.data.data[i])
              state.loadList.unshift(res.data.data[i])
            }

            console.log(state.loadList)
            // size < 15 면, 더이상 API 호출되지 않도록
            if(size < 15) state.isNoScroll = true

            state.loading = false
            state.loaded = true
            // state.chatLog.push(res.data)
            // this.$props.chatLog.push(state.loadList)
            // state.chatLog = [...state.recvList].reverse()
            // state.chatList = [...state.loadList].reverse()

            state.loadList = null;
          }
        },
        (err) => {
          console.log(err);
        },
      )
    }

    // scrollTop == 0 (꼭대기), 다음 list 가져오기
    function scrollMove(){
      // console.log("scrolltop : " + state.element.scrollTop)
      if(state.element.scrollTop == 0 && !state.isNoScroll){
        messageList()
      }
    }

    onUpdated(() => {
      // 채팅창 열었을 때 혹은 메세지 전송했을 때, 스크롤 맨 밑에 가도록
      if(state.init || state.sended){
        state.init = false
        state.sended = false
        state.element.scrollTop = 99999
        state.element.scrollTop = state.element.scrollHeight
      }

      // 스크롤 올렸을 때, 리스트 추가로 호출했을 때
      if(state.loaded){
        state.loaded = false
        if(state.element.scrollTop == 0){
          state.element.scrollTop = state.element.scrollHeight - state.prevScrollHeight
        }

        state.prevScrollHeight = state.element.scrollHeight
      }
    })

    return {
      state,
      messageList,
      scrollMove,
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
    send(state) {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          studyId: this.$route.params.studyId,
          memberId: this.getUserInfo.memberId,
          imgUrl: this.getUserInfo.profileImg,
          nickname: this.getUserInfo.nickname,
          content: this.message,
          createdAt: dayjs().format('YY/MM/DD hh:mm A'),
        };

        this.stompClient.send("/receive", JSON.stringify(msg), {});
        this.recvList.push(msg);

        // this.$state.value.sended = true;
        state.sended = true;
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

          // 스크롤 하단 고정
          // const element = document.getElementById('chat-body');
          // element.scrollTop = 99999;

          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send/" + this.$route.params.studyId, res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            // this.recvList.push(JSON.parse(res.body))
            this.$props.chatLog.push(JSON.parse(res.body))
            // setTimeout(() => {
            //   const element = document.getElementById('chat-body');
            //   element.scrollTop = element.scrollHeight;
            // }, 0);
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
/*  */
.chat-body{
  flex-grow: 1;
  /* overflow: auto; */
  padding: 1rem;

  overflow-y: scroll;
  scroll-behavior: smooth;
}
.chat-body::-webkit-scrollbar {
  /* display: none; */
}
::-webkit-scrollbar{
    width: 12px;
}
::-webkit-scrollbar-thumb{
    background-color: #999;
    border-radius: 10px;
}
::-webkit-scrollbar-track{
    background-color: #1E304F;
}
</style>
