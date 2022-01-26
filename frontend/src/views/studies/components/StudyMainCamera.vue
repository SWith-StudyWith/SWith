<template>
  <div class="videoInput">
    <video class="myVideo" autoplay style="width: 300px"></video>
    <audio src="" class="myAudio" autoplay></audio>
  </div>
  <div class="text-center">
    <button class="btn btn-primary  mx-3" @click="this.onClickCameraBtn">
      <font-awesome-icon :icon="['fas', this.cameraIcon]" />
    </button>
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
      sampleData: '',
      isMuted: false,
      isCameraOn: true,
    }
  },
  mounted() {
    this.myVideo = document.querySelector('.myVideo')
    this.getMedia()
  },
  unmounted() {},
  methods: {
    getMedia: async function () {
      try {
        this.myStream = await navigator.mediaDevices.getUserMedia({
          audio: true,
          video: true,
        });
        this.myVideo.srcObject = this.myStream;

      } catch(err) {
        console.log(err)
      }
    },
    onClickMuteBtn: function () {
      this.isMuted = !this.isMuted;
      console.log(this.isMuted)
      this.myStream
        .getAudioTracks()
        .forEach(track => {
          track.enabled = !track.enabled;
        })
    },
    onClickCameraBtn: function () {
      this.isCameraOn = !this.isCameraOn;
      this.myStream
        .getVideoTracks()
        .forEach(track => {
          track.enabled = !track.enabled;
        });
    },
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
