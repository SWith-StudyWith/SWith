<template>
<div class="study-base-page">
<Sidebar @show-screenmode="showScreenMode"/>
<div :style="{ 'margin-left': sidebarWidth }">


<!-- main container start -->
<div id="main-container" class="container">
  <!-- join session page -->
  <div id="join" v-if="!session">
    <div id="img-div">
      <img src="@/assets/img/icon_logo/logo.png" />
    </div>
    <div id="join-dialog" class="jumbotron vertical-center">
      <h1>Join a video session</h1>
      <div class="form-group">
        <p>
          <label>Participant</label>
          <input v-model="myUserName" class="form-control" type="text" required>
        </p>
        <p>
          <label>Session</label>
          <input v-model="mySessionId" class="form-control" type="text" required>
        </p>
        <p class="text-center">
          <button class="btn btn-lg btn-success" @click="joinSession()">Join!</button>
        </p>
      </div>
    </div>
  </div>

  <!-- session start -->
  <div id="session" v-if="session">
    <!-- session header -->
    <div id="session-header">
      <!-- session header - title -->
      <!-- <h1 id="session-title">{{ mySessionId }}</h1> -->

      <!-- screen share icon -->
      <!-- session header - functions -->
      <div class="my-function">
        <div class="function" id="screen-sharing" @click="startScreenSharing">
          <img src="@/assets/img/icon_logo/logo.png" alt="">
        </div>
      </div>

      <!-- <div class="session-title"></div> -->
      <!-- leave session button -->
      <input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
    </div>




    <!-- main-video start -->
    <!-- <div id="main-video" class="col-md-6">
      <user-video :stream-manager="mainStreamManager"/>
    </div>
    <div id="video-container" class="col-md-6">
      <user-video :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)"/>
      <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
    </div>-->
    <div id="main-video">
      <user-video
        v-if="mainOnOff"
        :stream-manager="mainStreamManager"
        :mainStream="true"
        v-on:dblclick="deleteMainVideoStreamManager"
      />
    </div>

    <!-- video-container start -->
    <div id="video-container" class="col-md-6">
      <div id="myvideo">
        <user-video
          id="vid"
          :stream-manager="publisher"
          v-on:dblclick="updateMainVideoStreamManager(publisher)"
        />
      </div>
    </div>




    <!-- 화면 공유할 때의 비디오 컨테이너 -->
    <div id="video-container" v-if="isScreenShared">
      <!-- <user-video :stream-manager="teacher" @click="updateMainVideoStreamManager(teacher)" v-if="teacher"/> -->
      <div class="video-wrapper" style="width:100%;">
        <div class="user-video-wrapper" id="user-video-wrapper" style="left:0;">
          <user-video id="my-video" style="width:10%;" :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)"/>
          <div id="user-video-while-shared" style="width:10%;" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub">
            <div class="test" v-if="JSON.parse(sub.stream.connection.data).clientData !== 'Screen Sharing'">
              <user-video :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
            </div>
          </div>
        </div>
        <div style="width:100%;" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub">
          <div class="video-screen-sharing" v-if="JSON.parse(sub.stream.connection.data).clientData === 'Screen Sharing'">
            <user-video style="width: 100%; " :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
          </div>
        </div>
      </div>
    </div>

    <!-- 화면 공유 안 할 때의 비디오 컨테이너 -->
    <div id="video-container" v-else>
      <!-- <user-video :stream-manager="teacher" @click="updateMainVideoStreamManager(teacher)" v-if="teacher"/> -->
      <div class="user-video" style="" >
        <user-video id="my-video" :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)"/>
      </div>

      <div class="user-video" id="user-video" style="width:30%;" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub">
        <div class="test">
          <user-video :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
        </div>
      </div>
    </div>


  </div>
  <!-- session div end -->
</div>
<!-- main container end -->

<!-- buttons -->
<div id="btngroup">
  <button
    v-if="vOnOff"
    icon="fas fa-video"
    @click="videoOnOff()"
  >비디오 on</button>
  <button
    v-else
    icon="fas fa-video-slash"
    @click="videoOnOff()"
  >비디오 off</button>
  <button
    v-if="aOnOff"
    icon="fas fa-microphone"
    @click="audioOnOff"
  >오디오 on</button>
  <button
    v-else
    icon="fas fa-microphone-slash"
    @click="audioOnOff"
  >오디오 off</button>
  <button
    icon="fas fa-desktop"
    @click="toggleScreanshare()"
  >화면 공유 on</button>
  <button
    icon="fas fa-sign-out-alt"
    @click="leaveSession()"
  >세션 나가기</button>
</div>


<!-- 화면 모드 -->
<KanbanBoard v-show="isKanbanBoard"/>
<ScreenShare v-show="isScreenShare"/>
<WhiteBoard v-show="isWhiteBoard"/>
</div>
<!-- sidebar end -->
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
import UserVideo from "@/views/studies/components/room/video//UserVideo.vue";
import axios from "axios";

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = "https://i65101.p.ssafy.io:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
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
    if (to.fullPath == `/studies/${this.$route.params.studyId}`) {
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

    return { sidebarWidth,
              onClickScreenMode, screenMode,
              isWhiteBoard, isScreenShare, isKanbanBoard,
              onClickWhiteBoard, onClickScreenShare, onClickKanbanBoard,
              showScreenMode,
    };
  },

  data () {
    return {
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
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
			mySessionId: 'SessionA',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),

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









  methods : {

      joinSession () {
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});







			// On start screen share
			this.session.on('signal:startScreenSharing', ()=>{
				this.isScreenShared = true;
			})

			// On stop screen share
			this.session.on('signal:stopScreenSharing', ()=>{
				this.isScreenShared = false;
			})






			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
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

		updateMainVideoStreamManager (stream) {
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
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},





		startScreenSharing () {
			this.OVForScreenShare = new OpenVidu();

			this.sessionForScreenShare = this.OVForScreenShare.initSession();

			var mySessionId = this.mySessionId;
			this.getToken(mySessionId).then(token => {
				this.sessionForScreenShare.connect(token, { clientData: this.screenShareName })
				.then(() => {
					let publisher = this.OVForScreenShare.initPublisher("sharingvideo", {
						audioSource: false,
						videoSource: "screen",
                        publishVideo: true,
						resolution: "1920x1980",
						frameRate: 10,
                        insertMode: 'APPEND',
                        mirror: false
					});
					console.log("publisher",publisher);
					publisher.once('accessAllowed', () => {
						try {
							console.log("subscriber >>>>> ", this.subscribers);
							this.isScreenShared=true;
							this.session.signal({
								data: JSON.stringify(status),  // Any string (optional)
								to: [],
								type: 'startScreenSharing'             // The type of message (optional)
							})
							publisher.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
								console.log('User pressed the "Stop sharing" button');
								this.session.signal({
									data: JSON.stringify(status),  // Any string (optional)
									to: [],
									type: 'stopScreenSharing'             // The type of message (optional)
								})
								this.leaveSessionForScreenSharing()
								this.isScreenShared=false;
							});
						} catch (error) {
							console.error('Error applying constraints: ', error);
						}
					});

					publisher.once('accessDenied', () => {
						console.warn('ScreenShare: Access Denied');
					});

					this.mainStreamManager2 = publisher;
                    this.sharingPublisher = publisher;

                    this.sessionForScreenShare.publish(this.sharingPublisher);

				}).catch((error => {

					console.warn('There was an error connecting to the session:', error.code, error.message);

				}));
			});

			window.addEventListener('beforeunload', this.leaveSessionForScreenSharing)
		},

		leaveSessionForScreenSharing () {
			if (this.sessionForScreenShare) this.sessionForScreenShare.disconnect();

            this.sessionForScreenShare = undefined;
            this.mainStreamManager2 = undefined;
            this.sharingPublisher = undefined;
            this.OVForScreenShare = undefined;

            window.removeEventListener('beforeunload', this.leaveSessionForScreenSharing);
		},

		checkScreenShared () {
			var buf = 0;
			this.subscribers.forEach((sub)=>{
				if(JSON.parse(sub.stream.connection.data).clientData==="Screen Sharing") {
					buf+=1;
				}
			});
			if (buf) {
				this.isScreenShared=true;
			} else {
				this.isScreenShared = false;
			}
		},




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
