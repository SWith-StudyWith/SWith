<template>
  <div class="study-base-page">
    <Sidebar @show-screenmode="showScreenMode"/>
    <div :style="{ 'margin-left': sidebarWidth }">
    <h2>카메라 들어가는 부분</h2>
    <!-- <router-view /> -->
    <!-- <button @click="onClickKanbanBoard">칸반보드열어말어</button>
    <button @click="onClickScreenShare">화면공유열어말어</button>
    <button @click="onClickWhiteBoard">화이트보드열어말어</button> -->
    <KanbanBoard v-show="isKanbanBoard"/>
    <ScreenShare v-show="isScreenShare"/>
    <WhiteBoard v-show="isWhiteBoard"/>
    <!-- <KanbanBoard v-show="screen==1"/>
    <ScreenShare v-show="screen==2"/>
    <WhiteBoard v-show="screen==3"/> -->
    </div>
  </div>
</template>

<script>
import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { sidebarWidth } from '@/views/studies/components/sidebar/state.js';
import KanbanBoard from '@/views/studies/components/screen/KanbanBoard.vue';
import ScreenShare from '@/views/studies/components/screen/ScreenShare.vue';
import WhiteBoard from '@/views/studies/components/screen/WhiteBoard.vue';
import { ref, reactive } from 'vue';
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./meeting-components/UserVideo.vue";
import axios from "axios";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://i65101.p.ssafy.io:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";

export default {
  name: 'Studies',
  components: {
    Sidebar,
    KanbanBoard,
    ScreenShare,
    WhiteBoard,
    UserVideo,
  },
  setup() {
    const isKanbanBoard = ref(false);
    const isWhiteBoard = ref(false);
    const isScreenShare = ref(false);
    const screenMode = ref(1);
    const onClickKanbanBoard = () => {
      isKanbanBoard.value = !isKanbanBoard.value;
      console.log(isKanbanBoard);
    };
    const onClickWhiteBoard = () => {
      isWhiteBoard.value = !isWhiteBoard.value;
      console.log(isWhiteBoard);
    };
    const onClickScreenShare = () => {
      isScreenShare.value = !isScreenShare.value;
      console.log(isScreenShare);
    };
    const onClickScreenMode = () => {
      screenMode.value = screenMode.value*  (-1);
    };

    const showScreenMode = ( screen ) => {
      // switch (screen)
      if(screen==1){
        console.log('1 칸반보드 true 보여조라~');
        isKanbanBoard.value = !isKanbanBoard.value;
      } else if(screen==2){
        console.log('2 화면공유 true 보여조라~');
        isScreenShare.value = !isScreenShare.value;
      } else if( screen ==3) {
        console.log('3 화이트보드 true니깐 보여주라~');
        isWhiteBoard.value = !isWhiteBoard.value;
      }
    }

    // const state = reactive({
    //   OV: undefined,
    //   session: undefined,
    //   mainStreamManager: undefined,
    //   publisher: undefined,
    //   tempPublisher: undefined,
    //   subscribers: [],
    //   vOnOff: true,
    //   aOnOff: true,
    //   size: true,
    //   connectionUser: false,
    //   mainOnOff: false,
    //   myUserId: "",
    //   tg: false,
    //   width: "640",
    //   height: "400",

    //   // 사용자 정보
    //   mySessionId: "SessionA",
    //   myUserName: "Participant" + Math.floor(Math.random() * 100),

    //   // 화면 공유
		// 	OVForScreenShare: undefined,
		// 	sessionForScreenShare: undefined,
		// 	mainStreamManager2: undefined,
		// 	sharingPublisher: undefined,

    //   // 상태 관리 변수
		// 	//menu: false,			// 메뉴 오픈상태
		// 	isScreenShared: false,	// 화면공유 상태
		// 	screenShareName: "Screen Sharing",	// 화면 공유 스트림의 이름

    // });

    // const joinsession = () => {

    // };

    return { sidebarWidth,
              onClickScreenMode, screenMode,
              isWhiteBoard, isScreenShare, isKanbanBoard,
              onClickWhiteBoard, onClickScreenShare, onClickKanbanBoard,
              showScreenMode,
              // state,
    };
  },

  data () {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      tempPublisher: undefined,
      subscribers: [],
      vOnOff: true,
      aOnOff: true,
      size: true,
      connectionUser: false,
      mainOnOff: false,
      myUserId: "",
      tg: false,
      width: "640",
      height: "400",

      // 사용자 정보
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),

      // 화면 공유
			OVForScreenShare: undefined,
			sessionForScreenShare: undefined,
			mainStreamManager2: undefined,
			sharingPublisher: undefined,

      // 상태 관리 변수
			//menu: false,			// 메뉴 오픈상태
			isScreenShared: false,	// 화면공유 상태
			screenShareName: "Screen Sharing",	// 화면 공유 스트림의 이름
    }
  },
  async created() { // 스터디 방 접속 시 세션 id => 스터디 코드로
    let roomInfo = await this.$store.dispatch(
      "getRoomById",
      this.$route.params.conferenceId
    );
    this.mySessionId = roomInfo.code;
    this.myUserName = JSON.parse(sessionStorage.getItem("userInfo")).nickname;
    this.myUserId = JSON.parse(sessionStorage.getItem("userInfo")).id;
    console.log("세션 검색");
    console.log(this.getSession(this.mySessionId));
    this.joinSession();
    // this.host_id = roomInfo.host_id;
  },











  methods : {
    //  join session
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
        console.log("인원 변경이 감지되었다.");
        // this.checkNotice();
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

// 화면 공유 세션

			// On start screen share
			this.session.on('signal:startScreenSharing', ()=>{
				this.isScreenShared = true;
			})

			// On stop screen share
			this.session.on('signal:stopScreenSharing', ()=>{
				this.isScreenShared = false;
			})


// 화상회의 화면 틀 구성

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then(token => {
        this.session
          .connect(token, {
            clientData: this.myUserName,
            idData: this.myUserId
          })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher("user-video", {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: this.width + "x" + this.height, // "320x200", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false // Whether to mirror your local video or not
            });

            console.log("this publisher");
            console.log(publisher);
            // console.log(publisher.videoSource("screen"));
            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---
            this.session.publish(this.publisher);
            console.log("this publisher2");
            console.log(this.publisher);

            console.log(this.session);
          })
          .catch(error => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });
      window.addEventListener("beforeunload", this.leaveSession);
    },
  },


}
</script>

<style scoped>
.study-base-page {
  display: flex;
  justify-content: center;
  flex-wrap: nowrap;
  flex-direction: column;

  height: 100%;

  background-color: #7285A6;
}
</style>
