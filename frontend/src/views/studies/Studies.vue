<template>
  <div class="study-base-page">
    <Sidebar
      @show-screenmode="showScreenMode($event)"
      @startScreenSharing="startScreenSharing"
      @stopScreenSharing="stopScreenSharing"
      @toggleSidebar="toggleSidebar"
      :screenMode="screenMode"
      :isScreenShared="isScreenShared"
    />
    <div :style="{ 'margin-left': sidebarWidth }">
      <!-- main container start -->
      <div id="main-container" class="mx-4" >
        <!-- session start -->
        <div id="session" v-if="session">
          <!-- session header -->
          <div id="session-header">
            <!-- leave session button -->
          </div>
          <!-- video-container start -->
          <div id="video-container">
            <div class="user-video-wrapper d-flex overflow-auto">
              <!-- publisher -->
              <div class="video-box position-relative">
                <div v-if="publisher">
                  <user-video id="my-video" :stream-manager="publisher"
                    :isSpeak="isSpeakList.includes(publisher.stream.connection.connectionId)"/>
                </div>
                <div class="stream-btn-container" @click.self="updateMainVideoStreamManager(publisher)">
                  <button class="btn btn-primary mx-1 stream-onoff-btn" @click="videoOnOff(publisher)">
                    <font-awesome-icon
                      :icon="['fas', publisher&&publisher.stream.videoActive ? 'video' : 'video-slash' ]"
                      :class="{'font-red' : !(publisher&&publisher.stream.videoActive)}"
                    />
                  </button>
                  <button class="btn btn-primary mx-1 stream-onoff-btn" @click="audioOnOff(sub)">
                    <font-awesome-icon
                      :icon="['fas', publisher&&publisher.stream.audioActive ? 'microphone' : 'microphone-slash']"
                      :class="{'font-red' : !(publisher&&publisher.stream.audioActive)}"
                    />
                  </button>
                </div>
              </div>
              <!-- subcribers -->
              <div v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub">
                <div class="video-box position-relative">
                  <user-video :stream-manager="sub" :isSpeak="isSpeakList.includes(sub.stream.connection.connectionId)"/>
                  <div
                    class="mute-icon-container text-start"
                    v-if="sub.stream.typeOfVideo !== 'SCREEN'"
                  >
                    <font-awesome-icon
                      v-if="!sub.stream.videoActive"
                      :icon="['fas', 'video-slash']"
                      class="font-red ms-2 mt-2"
                    />
                    <font-awesome-icon
                      v-if="!sub.stream.audioActive"
                      :icon="['fas', 'microphone-slash']"
                      class="font-red ms-2 mt-2"
                    />
                  </div>
                  <div v-if="sub" class="stream-btn-container" @click.self="updateMainVideoStreamManager(sub)">
                    <button
                      class="btn btn-primary mx-1 stream-onoff-btn" @click="toggleVideoSub(sub)"
                      v-if="sub.stream.typeOfVideo !== 'SCREEN'"
                    >
                      <font-awesome-icon
                        :icon="['fas', !sub.muteVideo ? 'video' : 'video-slash' ]"
                        :class="{'font-red': sub.muteVideo}"
                      />
                    </button>
                    <button
                      class="btn btn-primary mx-1 stream-onoff-btn" @click="toggleAudioSub(sub)"
                      v-if="sub.stream.typeOfVideo !== 'SCREEN'"
                    >
                      <font-awesome-icon
                        :icon="['fas', !sub.muteAudio ? 'microphone' : 'microphone-slash']"
                        :class="{'font-red': sub.muteAudio}"
                      />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- session div end -->
      </div>
      <!-- main container end -->
      <!-- 화면 모드 -->
      <KanbanBoard v-if="screenMode === 0" @isEditPermit="isEditPermit($event)" :editPermit="editPermit" ref="kanbanBoard"/>
      <MainScreen
        v-else-if="screenMode === 1"
        :streamManager="mainStreamManager"
        @deleteMainVideoStreamManager="deleteMainVideoStreamManager"
      />
      <WhiteBoard v-else-if="screenMode === 2"/>
      <Memo v-else-if="screenMode === 3"/>
    </div>
    <!-- sidebar end -->
  </div>
</template>
<script>
import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import KanbanBoard from '@/views/studies/components/screen/KanbanBoard.vue';
import MainScreen from '@/views/studies/components/screen/MainScreen.vue';
import WhiteBoard from '@/views/studies/components/screen/WhiteBoard.vue';
import Memo from '@/views/studies/components/screen/Memo.vue';
import UserVideo from "@/views/studies/components/room/video//UserVideo.vue";
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import { OpenVidu } from "openvidu-browser";
import { ref } from 'vue';
import axios from "axios";

axios.defaults.headers.post["Content-Type"] = "application/json";
const OPENVIDU_SERVER_URL = "https://i6a501.p.ssafy.io:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const parsing = (string) => !(string === 'false')

export default {
  name: 'Studies',
  props: {
    initVideoId: String,
    initAudioId: String,
    initVideoOn: Boolean,
    initAudioOn: Boolean,
  },
  components: {
    Sidebar,
    KanbanBoard,
    MainScreen,
    WhiteBoard,
    UserVideo,
    Memo,
  },
  beforeRouteLeave(to, from, next) {
    console.log(to.fullPath);
    if (!this.canLeave) {
      this.$refs.kanbanBoard.onClickSaveBtn()
      return next();
    }
    if (to.fullPath == `/studies/${this.$route.params.studyId}`) {
      if (this.session) {
        this.session.disconnect();
      }
      if (this.sessionForScreenShare) {
        this.sessionForScreenShare.disconnect();
      }
      return next();
    } else {
      if (this.session) {
        this.session.disconnect();
      }
      if (this.sessionForScreenShare) {
        this.sessionForScreenShare.disconnect();
      }
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      this.OVForScreenShare = undefined,
			this.sessionForScreenShare = undefined,
			this.mainStreamManager2 = undefined,
			this.sharingPublisher = undefined,
      window.removeEventListener("beforeunload", this.unloadEvent);
      // window.removeEventListener("beforeunload", this.leaveSession);
      return next();
    }

  },
  setup() {
    const store = useStore();
    const route = useRoute();
    store.dispatch('GET_STUDY_INFO', route.params.studyId);
    // const screenMode = ref(0);
    const sidebarWidth = ref('54px');
    const toggleSidebar = function (width) {
      sidebarWidth.value = width;
    };
    return { toggleSidebar, sidebarWidth };
  },
  data () {
    return {
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
      videoOn: parsing(this.initVideoOn),
      audioOn: parsing(this.initAudioOn),
      // initVideoId: JSON.parse(this.initDeviceSetting).videoId,
      // initAudioId: JSON.parse(this.initDeviceSetting).audioId,
      connectionUser: false,
      mainOnOff: false,
      myUserId: "",
      tg: false,
      screenMode: 0,
      editPermit: false,
      canLeave: true,

			// 사용자 정보
			// mySessionId: 'SessionA',
			// myUserName: 'Participant' + Math.floor(Math.random() * 100),

			// 화면 공유
			OVForScreenShare: undefined,
			sessionForScreenShare: undefined,
			mainStreamManager2: undefined,
			sharingPublisher: undefined,

			// 상태 관리 변수
			//menu: false,			// 메뉴 오픈상태
			isScreenShared: false,	// 화면공유 상태
			screenShareName: "Screen Sharing",	// 화면 공유 스트림의 이름

      // 발언 확인
      isSpeakList: [],
      isSpeak : false,
    }
  },  // data end
  mounted() {
    this.joinSession(this.initVideoOn, this.initAudioOn)
    window.addEventListener('beforeunload', this.unloadEvent)
  },
  unmounted() {
    window.removeEventListener('beforeunload', this.unloadEvent)
  },
  computed: {
    mySessionId() {
      return this.$route.params.studyId;
    },
    myUserName() {
      return this.$store.state.user.userInfo.nickname;
    },
  },
  methods : {
    unloadEvent(e) {
      if (this.canLeave) {
        this.leaveSession()
        this.leaveSessionForScreenSharing()
        return
      }
      e.preventDefault();
      e.returnValue = '';
    },
    isEditPermit (permit) {
      console.log(permit)
      this.editPermit = permit;
      this.canLeave = !permit;
    },
    showScreenMode ( mode ) {
      // switch (screen)
      console.log(mode)
      if(mode === 0){
        console.log('0 칸반보드 true 보여조라~');
      } else if(mode === 1){
        console.log('1 화면공유 true 보여조라~');
      } else if(mode === 2) {
        console.log('2 화이트보드 true니깐 보여주라~');
      }
      this.screenMode = mode;
    },
    toggleVideoSub(sub) {
      const now = sub.stream.videoActive;
      sub.subscribeToVideo(sub.muteVideo);
      sub.muteVideo = !sub.muteVideo;
      sub.stream.videoActive = now;
    },
    toggleAudioSub(sub) {
      const now = sub.stream.audioActive;
      sub.subscribeToAudio(sub.muteAudio);
      sub.muteAudio = !sub.muteAudio;
      sub.stream.audioActive = now;
    },
    joinSession (initVideoOn, initAudioOn) {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on('streamCreated', ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        subscriber.muteVideo = false;
        subscriber.muteAudio = false;
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

      // Speech Start Detection
      this.session.on("publisherStartSpeaking", (event) => {
        console.log(event);
        console.log("User " + event.connection.data + " start speaking");
        this.isSpeakList.push(event.connection.connectionId);
        this.isSpeak = !this.isSpeak;
        console.log(event.connection.connectionId);
        console.log("isSpeak 상태 : " + this.isSpeak);
        // alert("발언자 리스트 : " + this.isSpeakList);
        // this.$store.dispatch('startSpeaking')
        // this.$store.dispatch(
        //   "addSpeaker",
        //   JSON.parse(event.connection.data).clientData
        // );
      });

      // Speech Stop Detection
      this.session.on("publisherStopSpeaking", (event) => {
        console.log("User " + event.connection.connectionId + " stop speaking");
        let temp = this.isSpeakList;
        let index = temp.indexOf(event.connection.connectionId, 0);
        if (index >= 0) {
          temp.splice(index, 1);
          this.isSpeakList = temp;
        }
        this.isSpeak = !this.isSpeak;
        console.log("isSpeak 상태 : " + this.isSpeak);
        // this.$store.dispatch('stopSpeaking')
        // this.$store.dispatch(
        //   "removeSpeaker",
        //   JSON.parse(event.connection.data).clientData
        // );
      });
			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {
						// --- Get your own camera stream with the desired properties ---
						let publisher = this.OV.initPublisher(undefined, {
							audioSource: this.initAudioId, // The source of audio. If undefined default microphone
							videoSource: this.initVideoId, // The source of video. If undefined default webcam
							publishAudio: parsing(initAudioOn),  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: parsing(initVideoOn),  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						this.publisher = publisher;

						// --- Publish your stream ---
						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});
			// window.addEventListener('beforeunload', this.leaveSession)
		},
    // speechDetectIconOn() {
    //   if(this.isSpeak) {

    //   } else {

    //   }
    // },
    // speechDetectIconOff() {
    //   if(!this.isSpeak) {

    //   }
    //   this.connectionUser = !this.connectionUser;
    // },
    connectionUserOnOff() {
      this.connectionUser = !this.connectionUser;
    },
    audioOnOff() {
      this.publisher.publishAudio(!this.audioOn);
      this.audioOn = !this.audioOn;
    },
    videoOnOff() {
      this.publisher.publishVideo(!this.videoOn);
      this.videoOn = !this.videoOn;
    },

    leaveSession() {
      this.leaveSessionForScreenSharing();
      this.isScreenShared = false;
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			// window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager (stream) {
      // this.mainOnOff = false;
      this.screenMode = 1;
			this.mainStreamManager = stream;
      this.mainStreamManager.stream.videoDimensions = {
        width: 960,
        height: 600
      };
      console.log("바뀐 메인스트림정보");
      console.log(this.mainStreamManager);
      console.log(this.mainStreamManager.stream.videoDimensions);
      // this.mainOnOff = true;
		},
    deleteMainVideoStreamManager() {
      // this.mainOnOff = false;
      this.mainStreamManager = undefined;
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
				this.sessionForScreenShare.connect(token, { clientData: this.myUserName + ' 님의 화면공유' })
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
					this.mainStreamManager = publisher;
          this.sharingPublisher = publisher;
          this.sessionForScreenShare.publish(this.sharingPublisher);
				}).catch((error => {
					console.warn('There was an error connecting to the session:', error.code, error.message);
				}));
			});
			// window.addEventListener('beforeunload', this.leaveSessionForScreenSharing)
		},
    stopScreenSharing() {
      this.isScreenShared = false;
      this.leaveSessionForScreenSharing()
    },
		leaveSessionForScreenSharing () {
			if (this.sessionForScreenShare) {
        this.sessionForScreenShare.disconnect();
      }
      this.sessionForScreenShare = undefined;
      this.mainStreamManager = undefined;
      this.sharingPublisher = undefined;
      this.OVForScreenShare = undefined;
      // window.removeEventListener('beforeunload', this.leaveSessionForScreenSharing);
		},
		checkScreenShared () {
			var buf = 0;
			this.subscribers.forEach((sub)=>{
				if(sub.stream.typeOfVideo==="SCREEN") {
					buf+=1;
				}
			});
			// if (buf) {
			if (buf===0) {
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
  display: block;
  justify-content: center;
  flex-wrap: nowrap;
  flex-direction: column;
  height: 100vh;
  /* width: 100vw; */
  background-color: #aebed4;
}
#main-container {
  height: 25vh;
  margin-bottom: 1vh;
}
.video-container{
  height: 25vh;
}
.user-video-wrapper{
  height: 25vh
}
.video-box{
  height: 20vh
}
.stream-btn-container {
  position: absolute;
  width: 29vh;
  height: 18vh;
  top: 0;
  visibility: collapse;
  border-radius: 1rem;
}
.stream-onoff-btn {
  width: 2.5vw;
  font-size: 2vh;
  border-radius: 70%;
  margin-top: 15vh;
}
.video-box {
  margin: 1.5vh;
}
.video-box:hover .stream-btn-container {
  visibility: visible;
  background-color: rgba(0, 0, 0, 0.6);
}
.stream-btn-container:hover{
  cursor: pointer;
}
.mute-icon-container {
  position: absolute;
  width: 100%;
  height: 18vh;
  top: 0;
  font-size: 0.8rem;
}
.font-red {
  color: red;
}
::-webkit-scrollbar {
  height: 12px;
}
::-webkit-scrollbar-thumb:hover {
  background-color: #777;
}
::-webkit-scrollbar-track{
  /* background-color: #aebed4; */
}
</style>
