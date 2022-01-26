<template>
  <div class="videoInput">
    <video class="myVideo" autoplay style="width: 300px"></video>
  </div>
  <div class="text-center">
    <button class="btn btn-primary  mx-3" @click="this.onClickCameraBtn">
      <font-awesome-icon :icon="['fas', this.cameraIcon]" />
    </button>
    <select name="cameras" id="cameraSelect" @change="onChangeCamera" v-model="this.currentCamera">
      <option :value="camera.deviceId" :key="camera.deviceId" v-for="camera in this.cameraDevices">
        {{ camera.label }}
      </option>
    </select>
    <button class="btn btn-primary mx-3"  @click="this.onClickMuteBtn">
      <font-awesome-icon :icon="['fas', this.mutedIcon]" />
    </button>
  </div>
</template>
<script>

export default {
  name: '',
  components: {},
  data() {
    return {
      myStream: null,
      myVideo: null,
      cameraDevices: [],
      currentCamera: null,
      isMuted: false,
      isCameraOn: true,
    }
  },
  mounted() {
    this.myVideo = document.querySelector('.myVideo')
    this.cameraSelect = document.querySelector('#cameraSelect')
    this.getMedia()
    this.getCameras()
  },
  unmounted() {
    this.closeMedia()
  },
  methods: {
    // 카메라 장치 가져오기
    getCameras: async function () {
      try {
        const devices = await navigator.mediaDevices.enumerateDevices()
        const cameras = devices.filter(device => device.kind === 'videoinput')
        this.currentCamera = this.myStream ? await this.myStream.getVideoTracks()[0].id : null
        this.cameraDevices = cameras
      } catch(err) {
        console.log(err)
      }
    },
    // 장치 연결
    getMedia: async function (deviceId) {
      const initialConstraints = {
        audio: true,
        video: { facingMode: 'user' }
      }
      const cameraConstraints = {
        audio: true,
        video: { deviceId }
      }
      console.log(deviceId)
      try {
        this.myStream = await navigator.mediaDevices.getUserMedia(
          deviceId ? cameraConstraints : initialConstraints
        );
        this.myVideo.srcObject = this.myStream;
        if (!deviceId) {
          await this.getCameras();
        }
      } catch(err) {
        console.log(err)
      }
    },
    onClickMuteBtn: function () {
      this.isMuted = !this.isMuted;
      console.log(this.isMuted)
      this.myStream.getAudioTracks().forEach(track => {
        track.enabled = !track.enabled;
      })
    },
    onClickCameraBtn: function () {
      this.isCameraOn = !this.isCameraOn;
      this.myStream.getVideoTracks().forEach(track => {
        track.enabled = !track.enabled;
      });
    },
    onChangeCamera: async function (e) {
      console.log(e.target.value)
      await this.getMedia(e.target.value)
      this.isCameraOn = true;
    },
    // 미디어 장치 종료
    closeMedia: function () {
      this.myStream.getTracks().forEach(track => {
        track.stop();
      })
    }
  },
  computed: {
    mutedIcon() {
      return this.isMuted ? 'microphone-slash' : 'microphone'
    },
    cameraIcon() {
      return this.isCameraOn ? 'video' : 'video-slash'
    }
  }
}
</script>
<style scoped>
</style>
