<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <h1>
      <span v-if="collapsed">
        <div>S</div>
        <div>S</div>
      </span>
      <span v-else>Swith Sidebar</span>
    </h1>
    <!-- <SidebarLink/> -->
    <SidebarLink to="/studies/main" icon="fas fa-sign-out-alt">나가기</SidebarLink>
    <SidebarLink to="/studies/11" icon="fas fa-microphone">기본화면이라쳐</SidebarLink>
    <SidebarLink to="/studies/main" icon="fas fa-video">기본화면</SidebarLink>
    <SidebarLink to="/studies/11/kanbanboard" icon="fas fa-chalkboard">칸반보드</SidebarLink>
    <SidebarLink to="/studies/11/screenshare" icon="fas fa-desktop">화면공유</SidebarLink>
    <SidebarLink to="/studies/11/whiteboard" icon="fas fa-pencil-alt">화이트보드</SidebarLink>
    <!-- <img src="@/assets/img/icon_study_page/Pen_gray.png"> -->

    <div class="control-bottons">
      <p @click="this.onClickMundae">뭔데</p>
      <p>ㅎ ㅏ</p>
      <!-- <button class="btn btn-primary  mx-3" @click="this.onClickCameraBtn">
        <font-awesome-icon :icon="['fas', this.cameraIcon]" />
      </button>
      <button class="btn btn-primary mx-3"  @click="this.onClickMuteBtn">
        <font-awesome-icon :icon="['fas', this.mutedIcon]" />
      </button> -->
      <p>
      <font-awesome-icon @click="this.onClickCameraBtn" :icon="['fas', this.cameraIcon]" />
      </p>
      <p>
      <font-awesome-icon @click="this.onClickMuteBtn" :icon="['fas', this.mutedIcon]" />
      </p>
      <!-- <span
        class="collapse-icon"
        :class="{ 'rotate-180': collapsed }"
        @click="toggleSidebar"
      >
        <i class="fas fa-angle-double-left" />
      </span> -->
    </div>

    <!-- toggle button -->
    <span
      class="collapse-icon"
      :class="{ 'rotate-180': collapsed }"
      @click="toggleSidebar"
    >
      <i class="fas fa-angle-double-left" />
    </span>

  </div>
</template>

<script>
import SidebarLink from '@/views/studies/components/sidebar/SidebarLink.vue';
import { collapsed, toggleSidebar, sidebarWidth } from '@/views/studies/components/sidebar/state.js';

export default {
  name: 'Sidebar',
  components: {
    SidebarLink,
  },
  props: {},
  setup() {
    return { collapsed, toggleSidebar, sidebarWidth };
  },
  data() {
    return {
      myVideo: null,
      sampleData: '',
      isMuted: false,
      isCameraOn: true,
      isMundae: true,
    }
  },
  methods: {
    onClickMuteBtn: function () {
      this.isMuted = !this.isMuted;
    },
    onClickCameraBtn: function () {
      this.isCameraOn = !this.isCameraOn;
    },
    onClickMundae: function () {
      this.isMundae = !this.isMundae;
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
.sidebar {
  color: rgb(255, 211, 91);
  background-color: #1E304F;

  float:left;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  bottom: 0;
  padding: 0.5em;

  transition: 0.3s ease;

  display: flex;
  flex-direction: column;
}
.control-bottons {
  text-align: center;
  color: rgba(255, 255, 255, 0.7);

  float:left;
  position: fixed;
  z-index: 1;
  top: 10;
  left: 0;
  bottom: 70px;
  padding: 0.5em;

  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  align-items: center;
}
.sidebar h1 {
  height: 2.5em;
}
.collapse-icon {
  position: absolute;
  bottom: 0;
  padding: 0.75em;
  color: rgba(255, 255, 255, 0.7);
  transition: 0.2s linear;
}
.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}

.fas {
  color: rgba(255, 255, 255, 0.7);
}

</style>
