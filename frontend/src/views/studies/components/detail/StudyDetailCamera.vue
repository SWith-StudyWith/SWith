<template>
  <div class="videoInput">
    <video class="myVideo" autoplay style="width: 100%"></video>
  </div>
  <div class="text-center mt-3">
    <button class="btn btn-primary mx-3" @click="this.onClickCameraBtn">
      <font-awesome-icon :icon="['fas', this.cameraIcon]" />
    </button>
    <button class="btn btn-primary mx-3"  @click="this.onClickMuteBtn">
      <font-awesome-icon :icon="['fas', this.mutedIcon]" />
    </button>
    <button class="btn btn-primary mx-3" @click="this.selectsOn = !this.selectsOn">
      <font-awesome-icon :icon="['fas', 'cog']" />
    </button>
    <div v-if="this.selectsOn" class="select-container my-2">
      <select name="cameras" id="cameraSelect" class="form-select text-truncate" @change="onChangeCamera" v-model="this.deviceSetting.currentVideoId">
        <option value="0" disabled>카메라 선택</option>
        <option :value="camera.deviceId" :key="camera.deviceId" v-for="camera in this.cameraDevices">
          {{ camera.label }}
        </option>
      </select>
      <select name="mics" id="micSelect" class="form-select text-truncate" @change="onChangeCamera" v-model="this.deviceSetting.currentAudioId">
        <option value="0" disabled>마이크 선택</option>
        <option :value="mic.deviceId" :key="mic.deviceId" v-for="mic in this.micDevices">
          {{ mic.label }}
        </option>
      </select>
    </div>
  </div>
</template>
<script>

export default {
  name: 'StudyDetailCamera',
  components: {},
  data() {
    return {
      myStream: null,
      myVideo: null,
      cameraDevices: [],
      initCamera: 0,
      micDevices: [],
      initMic: 0,
      selectsOn: false,
      deviceSetting: {
        currentVideoId: '',
        currentAudioId: '',
        isCameraOn: false,
        isMuted: true,
      }
    }
  },
  mounted() {
    this.myVideo = document.querySelector('.myVideo')
    this.getMedia()
    this.getDevices()
  },
  unmounted() {
    this.closeMedia()
  },
  watch: {
    deviceSetting: {
      deep: true,
      handler () {
        this.$emit(
        'setDevice',
        {
          videoId: this.deviceSetting.currentVideoId,
          audioId: this.deviceSetting.currentAudioId,
          videoOn: this.deviceSetting.isCameraOn,
          audioOn: !this.deviceSetting.isMuted
        }
      )
    }
    }
  },
  methods: {
    // 장치 가져오기
    getDevices: async function () {
      try {
        const devices = await navigator.mediaDevices.enumerateDevices()
        const cameras = devices.filter(device => device.kind === 'videoinput')
        const mics = devices.filter(device => device.kind === 'audioinput')
        // this.currentCamera = this.myStream ? await this.myStream.getVideoTracks()[0].id : null
        // this.currentMic = this.myStream ? await this.myStream.getAudioTracks()[0].id: null
        this.cameraDevices = cameras
        this.micDevices = mics
        this.deviceSetting.currentVideoId = this.myStream ? await this.myStream.getVideoTracks()[0].getSettings().deviceId : null;
        this.deviceSetting.currentAudioId = this.myStream ? await this.myStream.getAudioTracks()[0].getSettings().deviceId : null;
      } catch(err) {
        console.log(err)
      }
    },
    // 장치 연결
    getMedia: async function (cameraDeviceId, micDeviceId) {
      // 초기 세팅
      const initialConstraints = {
        audio: true,
        video: true,
      }
      // 사용자 입력 세팅
      const exactConstraints = {
        audio: { deviceId: micDeviceId },
        video: { deviceId: cameraDeviceId }
      }
      try {
        // 스트림
        this.myStream = await navigator.mediaDevices.getUserMedia(
          cameraDeviceId ? exactConstraints : initialConstraints
        );
        this.myVideo.srcObject = this.myStream;
        if (this.deviceSetting.isMuted) {
          this.onClickMuteBtn()
          this.deviceSetting.isMuted = true
        }
        if (!this.deviceSetting.isCameraOn) {
          this.onClickCameraBtn()
          this.deviceSetting.isCameraOn = false
        }
        if (!cameraDeviceId) {
          await this.getDevices();
        }
      } catch(err) {
        console.log(err)
      }
    },
    onClickMuteBtn: function () {
      this.deviceSetting.isMuted = !this.deviceSetting.isMuted;
      this.myStream.getAudioTracks().forEach(track => {
        track.enabled = !track.enabled;
      })
    },
    onClickCameraBtn: function () {
      this.deviceSetting.isCameraOn = !this.deviceSetting.isCameraOn;
      this.myStream.getVideoTracks().forEach(track => {
        track.enabled = !track.enabled;
      });
    },
    onChangeCamera: async function (e) {
      // 현재 카메라 끄고, 타겟 카메라와 현재 마이크 불러옴.
      console.log(e.target.value)
      this.myStream.getVideoTracks().forEach(track => {
        track.stop();
      });
      await this.getMedia(this.deviceSetting.currentVideoId, this.deviceSetting.currentAudioId)
    },
    onChangeMic: async function () {
      // 현재 마이크 끄고, 타겟 마이크와 현재 카메라
      this.myStream.getAudioTracks().forEach(track => {
        track.stop();
      });
      await this.getMedia(this.deviceSetting.currentVideoId, this.deviceSetting.currentAudioId)
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
      return this.deviceSetting.isMuted ? 'microphone-slash' : 'microphone'
    },
    cameraIcon() {
      return this.deviceSetting.isCameraOn ? 'video' : 'video-slash'
    }
  }
}
</script>
<style scoped>
</style>
