<template>
  <div class= "chatDiv">
      <p class="title">💬 채팅 </p>
    <loading v-model:active="state.loading"
          :can-cancel="false"
          :is-full-page="false"
          :height="height"
          :width="width"
          :color="color"
          :loader="loader"
          :background-color="bgColor"
          class="vld-overlay"
          :style="state.loading ? '-webkit-backdrop-filter: blur(3px); backdrop-filter: blur(3px);' : ''"
      ></loading>
    <div>
    <div class="chat-body" id="chat-body"
      @scroll="scrollMove">

      <SidebarChatMessage
        v-for="(chat, idx) in state.chatList"
        :key="idx"
        :chat = "chat"
        :prev="[idx == 0 ? null : state.chatList[idx-1]]"
      >
      </SidebarChatMessage>

      <!-- 채팅 기록 없을 때 -->
      <div v-if="state.isNull" style="font-size:12px; background-color: #9EABCB; border-radius: 10px;
        padding: 10px 5px; text-align: center; opacity: 0.9; margin-top: 20px">
        <p style="margin-bottom:5px; ">스터디의 채팅 기록이 없습니다 !</p>
        <p style="margin-bottom:5px;">스터디원들과 자유롭게 대화해보세요 💙</p>
      </div>

      <div class="init-btn" v-if="state.isScrollInit">
          <button class="btn-primary button" @click="scrollInit">↓</button>
      </div>

    </div>
    <hr>
    <div class="chat-input" id="chat-input">
      <div class="inputText">
        <textarea
          v-model="state.message"
          id="message"
          type="text"
          @keydown.enter.exact.prevent="sendMessage"
        ></textarea>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import SidebarChatMessage from '@/views/studies/components/sidebar/SidebarChatMessage.vue';
import dayjs from 'dayjs'
import { computed, onUpdated, reactive } from '@vue/runtime-core';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import { getChatList } from '@/api/study'
import Loading from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/vue-loading.css';

export default {
  name: 'App',
  data() {
    return {
      loader: 'dots',
      color: '#F5CEC7',
      bgColor: '#1E304F',
      height: 80,
      width: 80,
    }
  },
  components:{
    SidebarChatMessage,
    Loading
  },
  setup(){
    const store = useStore();
    const route = useRoute();
    let socket, stompClient;

    store.dispatch('GET_CHAT_LIST', {studyId: route.params.studyId, index: 0});

    const state = reactive({
      message: '',

      userInfo : store.getters.getUserInfo,
      // 채팅창에 보여줄 메세지 리스트 (아래에서 위로)
      chatList: [],
      // 서버에서 받은 데이터 모아줄 리스트 (위에서 아래)
      recvList: [],

      // 채팅창 열었을 때, 스크롤 맨 밑에 있도록
      init: true,
      // 채팅 기록이 없을 때
      isNull: false,

      // 새로운 메세지를 받았을 때
      recv: false,
      loaded: false,
      loading: false,

      // 스크롤 상단에 도착했는지
      isTop: false,
      // 더이상 API 호출X  => 추가로 불러온 list들이 <15일 때,
      isNoScroll: false,
      isScrollInit: false,
      // 스크롤 위치 저장하기 위함
      prevScrollHeight: 0,
      // 맨 처음 스크롤 위치 가져오자
      storeScrollHeight: 0,
      element: computed(() => {
        return document.getElementById('chat-body')
      }),
    })

    // 이전 채팅방 리스트 가져오기
    async function messageList() {
        getChatList(
          route.params.studyId,
          state.chatList.length,
            (res) => {
            store
              .dispatch("GET_CHAT_LIST", {
                studyId: route.params.studyId,
                index: state.chatList.length
              })
              .then(function(){
                console.log(res.data.data.length)
                // 채팅 기록이 없을 때,
                if(res.data.data.length == 0){
                  state.isNull = true
                }else state.isNull = false

                  var size = res.data.data.length
                  for(var i = 0; i < size; i++){
                    state.recvList.push(res.data.data[i])
                  }

                  // size < 15 면, 더이상 API 호출되지 않도록
                  if(size < 15) {
                    state.isNoScroll = true
                  }

                  state.chatList = [...state.recvList].reverse()
                  state.loaded = true
                  // state.isNull = false

              })
            },
          (err) => {
            console.log(err);
          },
        )
    }

    function loadingCall(){
      // 처음 scrollHeight 받아오고, 이상이 될 때마다 scrollInit 호출되도록 ?
      state.loading = true
      setTimeout(() => {
        state.loading = false
        state.storeScrollHeight = state.element.scrollHeight
      }, 1500)
    }

    async function scrollMove(){
      state.prevScrollHeight = state.element.scrollHeight - state.element.scrollTop

      // scrollTop == 0 (꼭대기), 다음 list 가져오기
      if(state.element.scrollTop == 0 && !state.isNoScroll){
        await messageList()
      }

      // 저장된 스크롤 높이 도달 시, 스크롤 내리는 버튼 활성화 되도록
      if(state.storeScrollHeight < state.prevScrollHeight && state.storeScrollHeight != 0){
        state.isScrollInit = true
      }
    }

    // 버튼 클릭 시, 맨 아래로 내려가기
    function scrollInit(){
      state.element.scrollTop = state.element.scrollHeight
      state.isScrollInit = false
      state.prevScrollHeight = 0
    }

    onUpdated(() => {
      // 채팅창 열었을 때, 스크롤 맨 밑에 가도록
      if(state.init || state.recv){
        state.init = false
        state.recv = false
        state.element.scrollTop = state.element.scrollHeight
      }

      // 이전 리스트 추가로 호출했을 때
      if(state.loaded){
        state.loaded = false
        if(state.element.scrollTop == 0){
          // 스크롤 있던 위치 받아오기 => 시작 위치
          state.element.scrollTop = state.element.scrollHeight - state.prevScrollHeight
        }
        state.prevScrollHeight = state.element.scrollHeight
      }

      //
      if(state.storeScrollHeight < state.prevScrollHeight && state.storeScrollHeight != 0){
        state.isScrollInit = true
      }
    })

    function sendMessage(e) {
      if(e.keyCode === 13 && this.userName !== '' && state.message !== ''){
        send()
        state.message = ''
      }
    }

    function send() {
      // console.log("Send message:" + state.message);
      if (stompClient && stompClient.connected) {
        const msg = {
          studyId: route.params.studyId,
          memberId: state.userInfo.memberId,
          imgUrl: state.userInfo.profileImg,
          nickname: state.userInfo.nickname,
          content: state.message,
          created: dayjs().format('YY/MM/DD hh:mm A')
        };

        stompClient.send("/receive", JSON.stringify(msg), {});
        // console.log(msg)
        state.isNull = false
        setTimeout(() => {
          const element = document.getElementById('chat-body');
          element.scrollTop = element.scrollHeight;
        }, 0);
      }
    }

    // 웹 소켓 연결 성공 시, 콜백 함수
    async function onConnected(){
      await messageList()
      fetchList()
    }

    function connect() {
      // 배포
      const serverURL = `${process.env.VUE_APP_BASE_URL_DEV}/api/ws`
      // const serverURL = 'http://localhost:8080/api/ws/'
      socket = new SockJS(serverURL, { transports: ['websocket', 'xhr-streaming', 'xhr-polling']});
      stompClient = Stomp.over(socket);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          stompClient.connected = true;
          console.log('소켓 연결 성공', frame);

          onConnected()
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          stompClient.connected = false;
        }
      );
    }

    function fetchList(){
      // 서버의 메시지 전송 endpoint를 구독합니다.
      // 이런형태를 pub sub 구조라고 합니다.
      stompClient.subscribe("/send/" + route.params.studyId, res => {
        // console.log('구독으로 받은 메시지 입니다.', res.body);

        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        state.recvList.unshift(JSON.parse(res.body))
        state.chatList.push(JSON.parse(res.body))
        state.recv = true

        setTimeout(() => {
          const element = document.getElementById('chat-body');
          element.scrollTop = element.scrollHeight;
        }, 0);
      });
    }

    connect()

    return {
      state,
      // messageList,
      scrollMove,
      scrollInit,
      sendMessage,
      loadingCall,
    }
  },
  created() {
    // console.log('사이드바 생성 ~')
    this.init = true

    this.loadingCall()
  },
}
</script>
<style scoped>
.chatDiv{
  /* float: right; */
  width: 100%;
  text-align: left;
  display: inline-flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100vh;
  display: flex;

}
.row{
  margin-bottom: 20px;
  border: 1px solid;
}
.title{
  font-size: 3vh;
  font-weight:500;
  margin: 5.5vh 0 1vh 1vw;
}
.chat-input{
  display: flex;
  margin: 1vh 1vw 1vh 0.5vw;
}
.inputText{
  display: flex;
  width: 100%;
  vertical-align: top;
}
#message{
  display: flex;
  height: 10vh;
  width: 100%;
  border: none;
  border-radius: 10px;
  padding: 2vh;
  margin-bottom: 1vh;
  background-color: #F8F8F8;
  flex-grow: 1;

  /* input 클릭 시, 테두리 없애기 */
  outline: none;
}
/*  */
.chat-body{
  flex-grow: 1;
  /* overflow: auto; */
  height: 77vh;
  padding-right: 1vw;
  padding-bottom: 0;
  margin-left: 1vw;
  overflow-y: scroll;
  scroll-behavior: auto;

  /* z-index:1; */
}
hr {
  margin-top: 2vh;
  margin-bottom: 2vh;
  margin: 2vh 1vw 2vh 0.5vw;
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

.chat-top{
  font-size: 13px;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  padding: 1px;
  line-break: anywhere;
  background-color: rgba(185, 175, 207, 0.2);
  border-radius: 10px;
}
.init-btn{
  display: flex;
  justify-content: right;
  position: sticky;
  z-index: 2;
  bottom: 0;

}
.init-btn > button{
  font-size: 20px;
  font-weight: 800;
  background-color: rgba(255, 224, 224, 0.4);
  border-radius: 50%;
  border: 0px;
  height: 40px;
  width: 40px;
}

.button:hover {
  transition-duration: 0.1s;
  background-color: rgba(230, 196, 196, 0.7);
}

.vld-overlay{
  margin-left: 60px;
}
</style>
