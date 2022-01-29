<template>
  <div class="study-base-page">
    <Sidebar @show-screenmode="showScreenMode"/>
    <div :style="{ 'margin-left': sidebarWidth }">



      <!-- 메인 컨테이너 -->
      <h2>카메라 들어가는 부분</h2>
      <div id="main-container" class="container">

        <audio preload="none" id="audioContainer">
          <source id="audioSource" src="" type="audio/mpeg" />
        </audio>

        <div id="session" v-if="session">
          <div id="session-header">
            <!-- <h1 id="session-title">{{ mySessionId }}</h1> -->
          </div>
          <div id="main-video">
            <user-video
              v-if="mainOnOff"
              :stream-manager="mainStreamManager"
              :mainStream="true"
              v-on:dblclick="deleteMainVideoStreamManager"
            />
          </div>
          <div id="video-container" class="col-md-6">
            <div id="myvideo">
              <user-video
                id="vid"
                :stream-manager="publisher"
                v-on:dblclick="updateMainVideoStreamManager(publisher)"
              />
            </div>
          </div>
        </div>
      </div>  <!-- main container end -->

      <!-- buttons -->
      <div id="btngroup">
        <button
          v-if="vOnOff"
          icon="fas fa-video"
          @click="videoOnOff()"
        ></button>
        <button
          v-else
          icon="fas fa-video-slash"
          @click="videoOnOff()"
        ></button>
        <button
          v-if="aOnOff"
          icon="fas fa-microphone"
          @click="audioOnOff()"
        ></button>
        <button
          v-else
          icon="fas fa-microphone-slash"
          @click="audioOnOff()"
        ></button>
        <button
          icon="fas fa-desktop"
          @click="toggleScreanshare()"
        ></button>
        <button
          icon="fas fa-sign-out-alt"
          @click="leaveSession()"
        ></button>
      </div>


















      <!-- 화면 모드 -->
      <KanbanBoard v-show="isKanbanBoard"/>
      <ScreenShare v-show="isScreenShare"/>
      <WhiteBoard v-show="isWhiteBoard"/>
    </div>
    <!-- sidebar -->
  </div>
</template>

<script>
import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { sidebarWidth } from '@/views/studies/components/sidebar/state.js';
import KanbanBoard from '@/views/studies/components/screen/KanbanBoard.vue';
import ScreenShare from '@/views/studies/components/screen/ScreenShare.vue';
import WhiteBoard from '@/views/studies/components/screen/WhiteBoard.vue';
import { ref } from 'vue';
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./meeting-components/UserVideo.vue";
import axios from "axios";
// import ConnectionUserList from "@/views/studies/components/room/video/ConnectionUserList";
// import { h } from "vue";


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

  beforeRouteLeave(to, from, next) {
    console.log(to.fullPath);
    // if (to.fullPath.indexOf("board") != -1) {
    //   console.log("게시판갈거고 bid는1 " + to.params.bid);
    //   this.bid = to.params.bid;
    //   // console.log(this.bid);
    // } else {
    //   console.log("게시판안갈거고 id는2" + this.bid);
    //   // console.log(typeof this.bid);
    //   this.bid = false; //재할당
    // }
    // if (to.fullPath.indexOf("rollbook") != -1) {
    //   console.log("출석부가야함" + to.params.bid);
    //   if (this.session) {
    //     this.session.disconnect();
    //   }
    //   return next();
    // }
    if (to.fullPath == `/conference/${this.$route.params.conferenceId}`) {
      if (this.session) {
        this.session.disconnect();
      }
      return next();
    } else {
      if (this.session) {
        this.session.disconnect();
      }
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
      return next();
    }
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



  },  // data end











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





  // computed: {
  //   minutes: function() {
  //     const minutes = Math.floor(this.totalTime / 60);
  //     return this.padTime(minutes);
  //   },
  //   seconds: function() {
  //     const seconds = this.totalTime - this.minutes * 60;
  //     return this.padTime(seconds);
  //   }
  // },




  watch: {
    // sendtime: function() {
    //   if (this.sendtime >= 3600) {
    //     console.log("이상 이상");
    //     this.sendtime = 3600;
    //   }
    // },
    publisher() {
      // deep: true;
      // console.log(deep);
      console.log("중복 아디 체크");
      console.log("현재 나");
      console.log(this.publisher);
      if (this.publisher === undefined) {
        this.leaveSession();
      } else {
        console.log(this.publisher.stream.connection.data);
        const { clientData } = JSON.parse(
          this.publisher.stream.connection.data
        );
        const { idData } = JSON.parse(this.publisher.stream.connection.data);
        const nickname1 = clientData;
        const id1 = idData;
        console.log("접속자");
        this.subscribers.forEach(sub => {
          console.log(sub.stream.connection.data);
          // console.log(JSON.parse(sub.stream.connection.data));
          const { clientData } = JSON.parse(sub.stream.connection.data);
          const { idData } = JSON.parse(sub.stream.connection.data);
          console.log(nickname1);
          console.log(clientData);
          if (nickname1 == clientData && id1 == idData) {
            alert("같은 사용자가 존재합니다");
            this.$router.push({
              name: "conference-detail"
            });
            setTimeout("location.reload()", 10);
          }
        });
      }
    }
  },  // watch end








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
    },  // join session end





    playLoading() {
      const source = document.getElementById("audioSource");
      console.log(source);
      source.src = this.music;
      const audioContainer = document.getElementById("audioContainer");
      console.log("오디오");
      audioContainer.load();
      this.playAudio(audioContainer);
    },
    playAudio(audioContainer) {
      // const source = document.getElementById("audioSource");
      // console.log(source);
      // source.src = this.music;
      // const audioContainer = document.getElementById("audioContainer");
      // console.log("오디오");
      // audioContainer.load();
      audioContainer.volume = 0.3;
      console.log(audioContainer);
      audioContainer.play();
      // setTimeout(() => {
      // }, 1000);
      // const playPromise = audioContainer.play();
      // if (playPromise !== undefined) {
      //   playPromise
      //     .then(_ => {})
      //     .catch(error => {
      //       console.log("에러");
      //       console.log(playPromise);
      //       console.log(error);
      //     });
      // }
    },

    updateMainVideoStreamManager(stream) {
      this.mainOnOff = true;
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
      this.mainStreamManager.stream.videoDimensions = {
        width: 960,
        height: 600
      };
      console.log("바뀐 메인스트림정보");
      console.log(this.mainStreamManager);
      console.log(this.mainStreamManager.stream.videoDimensions);
    },
    deleteMainVideoStreamManager() {
      this.mainOnOff = false;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server   (POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then(sessionId =>
        this.createToken(sessionId)
      );
    },
    getSession(mySessionId) {
      axios
        .get(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${mySessionId}`)
        .then(response => {
          console.log("가져온 세션");
          console.log(response);
          console.log(response.data);
        })
        .catch(err => {
          console.log("못가져옴");
          console.log(err);
        });
    },
    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET
              }
            }
          )
          .then(response => response.data)
          .then(data => resolve(data.id))
          .catch(error => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET
              }
            }
          )
          .then(response => response.data)
          .then(data => resolve(data.token))
          .catch(error => reject(error.response));
      });
    },







// 화면 공유 기능 구현

    toggleScreanshare(publisher) {
      if (!this.tg) {
        this.tg = true;
        var newPublisher = this.OV.initPublisher(
          "user-video",
          {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: "screen",
            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "1920x1080", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false // Whether to mirror your local video or not
          },

          error => {
            // Function to be executed when the method finishes
            if (error) {
              console.error(
                "Error while initializing publisher: =====================================뉴퍼블리셔 게시하는도중에 에러가 발생했다",
                error
              );
              this.tg = false;
              // this.session.unpublish(this.publisher);
              this.publisher = this.tempPublisher;
              this.session.publish(this.publisher);
            } else {
              console.log("Publisher successfully initialized");
            }
          }
        );

        newPublisher.once("accessAllowed", event => {
          newPublisher.stream
            .getMediaStream()
            .getVideoTracks()[0]
            .addEventListener("ended", () => {
              this.toggleScreanshare();
              this.mainOnOff = false;
            });
        });
        this.width = "1920";
        this.height = "1080";
        // this.updateMainVideoStreamManager(newPublisher);
        newPublisher.once("accessAllowed", () => {
          try {
            newPublisher.stream
              .getMediaStream()
              .getVideoTracks()[0]
              .applyConstraints({
                width: this.width,
                height: this.height
              });
          } catch (error) {
            console.error("Error applying constraints: ", error);
          }
        });

        this.session.unpublish(this.publisher);
        this.tempPublisher = this.publisher;
        this.publisher = newPublisher;
        this.mainStreamManager = this.publisher;
        this.session.publish(this.publisher);

        // this.updateMainVideoStreamManager(this.publisher);
      } else {
        this.tg = false;
        this.session.unpublish(this.publisher);
        this.publisher = this.tempPublisher;
        this.session.publish(this.publisher);
      }
    },









    connectionUserOnOff() {
      this.connectionUser = !this.connectionUser;
    },
    audioOnOff() {
      this.publisher.publishAudio(!this.aOnOff);
      this.aOnOff = !this.aOnOff;
    },
    videoOnOff() {
      this.publisher.publishVideo(!this.vOnOff);
      this.vOnOff = !this.vOnOff;
    },





// leave Session
    leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
		},

		// updateMainVideoStreamManager (stream) {
		// 	if (this.mainStreamManager === stream) return;
		// 	this.mainStreamManager = stream;
    //   // this.$router.push({
    //   //     name: "conference-detail"
    //   // });
		// },





  },  //methods end


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
