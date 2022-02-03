<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <h1>
      <span v-if="collapsed">
        <div>S</div>
        <div>S</div>
      </span>
      <span v-else>Swith Sidebar</span>
    </h1>
    <!-- <SidebarLink to="/studies/main" icon="fas fa-sign-out-alt">나가기</SidebarLink>
    <SidebarLink to="/studies/11" icon="fas fa-microphone">기본화면이라쳐</SidebarLink>
    <SidebarLink to="/studies/main" icon="fas fa-video">기본화면</SidebarLink>
    <SidebarLink to="/studies/11/kanbanboard" icon="fas fa-chalkboard">칸반보드</SidebarLink>
    <SidebarLink to="/studies/11/screenshare" icon="fas fa-desktop">화면공유</SidebarLink>
    <SidebarLink to="/studies/11/whiteboard" icon="fas fa-pencil-alt">화이트보드</SidebarLink> -->

    <SidebarLink to="SidebarMemberView" icon="fas fa-pencil-alt">화이트보드</SidebarLink>

    <div class="control-bottons">
      <p>
      <font-awesome-icon @click="this.onClickMuteIcon" :icon="['fas', this.mutedIcon]" />
      </p>
      <p>
      <font-awesome-icon @click="this.onClickCameraIcon" :icon="['fas', this.cameraIcon]" />
      </p>
      <p>
      <font-awesome-icon @click="this.onClickKanbanBoardIcon" :icon="['fas', this.kanbanboardIcon]" />
      </p>
      <p>
      <font-awesome-icon @click="this.onClickScreenShareIcon" :icon="['fas', this.screenshareIcon]" />
      </p>
      <p>
      <font-awesome-icon @click="this.onClickWhiteBoardIcon" :icon="['fas', this.whiteboardIcon]" />
      </p>
      <p>
      <font-awesome-icon @click="this.onClickChatIcon" :icon="['fas', this.chatIcon]" />
      </p>
      <p>
      <font-awesome-icon @click="this.onClickMemberIcon" :icon="['fas', this.memberIcon]"
      :style="{ color: state.isMemberList ? '#F5CEC7': 'rgba(255, 255, 255, 0.7)' }"/>
      </p>

    </div>

    <!-- toggle button -->
    <span
      class="collapse-icon"
      :class="{ 'rotate-180': collapsed }"
      @click="toggleSidebar"
    >
      <i class="fas fa-angle-double-left" />
    </span>
    <SidebarChat/>
  </div>
</template>

<script>
// import SidebarLink from '@/views/studies/components/sidebar/SidebarLink.vue';
import { collapsed, toggleSidebar, sidebarWidth } from '@/views/studies/components/sidebar/state.js';
import SidebarChat from '@/views/studies/components/sidebar/SidebarChat.vue';
import { ref, computed, reactive } from 'vue';

export default {
  name: 'Sidebar',
  components: {
    // SidebarLink,
    SidebarChat,
  },
  // props: {},
  setup( props, context ) {
    const isMuted = ref(true);
    const isCameraOn = ref(false);
    const isKanbanBoard = ref(false);
    const isScreenShare = ref(false);
    const isWhiteBoard = ref(false);
    const isChat = ref(false);

    const state = reactive({
      isMemberList : false
    })

    const onClickMuteIcon = () => {
      isMuted.value = !isMuted.value;
    };
    const onClickCameraIcon = () => {
      isCameraOn.value = !isCameraOn.value;
    };
    const onClickKanbanBoardIcon = () => {
      isKanbanBoard.value = !isKanbanBoard.value;
      if ( isKanbanBoard.value) {
        context.emit('show-screenmode', 1)
      }
    };
    const onClickScreenShareIcon = () => {
      isScreenShare.value = !isScreenShare.value;
      if ( isScreenShare.value) {
          context.emit('show-screenmode', 2)
      }
    };
    const onClickWhiteBoardIcon = () => {
      isWhiteBoard.value = !isWhiteBoard.value;
      if ( isWhiteBoard.value) {
          context.emit('show-screenmode', 3)
      }
    };
    const onClickChatIcon = () => {
      isChat.value = !isChat.value;
    };
    const onClickMemberIcon = () => {
      state.isMemberList = !state.isMemberList;
    }

    const mutedIcon = computed(() => {
      return isMuted.value ? 'microphone-slash' : 'microphone';
    });
    const cameraIcon = computed(() => {
      return isCameraOn.value ? 'video' : 'video-slash';
    });
    const kanbanboardIcon = computed(() => {
      return isKanbanBoard.value ? 'edit' : 'chalkboard';
    });
    const screenshareIcon = computed(() => {
      return isScreenShare.value ? 'desktop' : 'tv';
    });
    const whiteboardIcon = computed(() => {
      return isWhiteBoard.value ? 'pencil-alt' : 'pen';
    });
    const chatIcon = computed(() => {
      if(isChat.value){
        return 'comment-dots';
      }
      else return 'comment';
    });
    const memberIcon = computed(() => {
      if(state.isMemberList){
        return 'user-friends';
      }
      else return 'user-friends';
    });


    return { state, collapsed, toggleSidebar, sidebarWidth,
              isMuted, isCameraOn, isWhiteBoard, isScreenShare, isKanbanBoard,
              onClickMuteIcon, onClickCameraIcon, onClickScreenShareIcon, onClickWhiteBoardIcon, onClickKanbanBoardIcon,
              mutedIcon, cameraIcon, screenshareIcon, whiteboardIcon, kanbanboardIcon, chatIcon, onClickChatIcon, memberIcon, onClickMemberIcon
    };
  },
  // data() {
  //   return {
  //     myVideo: null,
  //     sampleData: '',
  //     isMuted: false,
  //     isCameraOn: false,
  //     isScreenShare: false,
  //     isWhiteBoard: false,
  //     isKanbanBoard: true,
  //   }
  // },
  // methods: {
  //   onClickMuteIcon: function () {
  //     this.isMuted = !this.isMuted;
  //   },
  //   onClickCameraIcon: function () {
  //     this.isCameraOn = !this.isCameraOn;
  //   },
  //   onClickScreenShareIcon: function () {
  //     this.isScreenShare = !this.isScreenShare;
  //   },
  //   onClickWhiteBoardIcon: function () {
  //     this.isWhiteBoard = !this.isWhiteBoard;
  //   },
  //   onClickKanbanBoardIcon: function () {
  //     this.isKanbanBoard = !this.isKanbanBoard;
  //   },

  // },
  // computed: {
  //   mutedIcon() {
  //     return this.isMuted ? 'microphone-slash' : 'microphone'
  //   },
  //   cameraIcon() {
  //     return this.isCameraOn ? 'video' : 'video-slash'
  //   },
  //   screenshareIcon() {
  //     return this.isScreenShare ? 'desktop' : 'tv'
  //   },
  //   whiteboardIcon() {
  //     return this.isWhiteBoard ? 'pencil-alt' : 'pen'
  //   },
  //   kanbanboardIcon() {
  //     return this.isKanbanBoard ? 'edit' : 'chalkboard'
  //   },
  // }
}
</script>

<style scoped>
.sidebar {
  color: rgb(255, 211, 91);
  background-color: #1E304F;
  box-shadow:  2px 1px 10px 0 rgb(0 0 0 / 0.7);

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

  cursor: pointer;
}
.sidebar h1 {
  height: 2.5em;
}
.collapse-icon {
  position: absolute;
  bottom: 0;
  /* padding: 0.75em; */
  color: rgba(255, 255, 255, 0.7);
  transition: 0.2s linear;
  font-size: 30px;
}
.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}

.fas {
  color: rgba(255, 255, 255, 0.7);
}
.fa-desktop {
  color: #F5CEC7;
}
.fa-microphone {
  color: #F5CEC7;
}
.fa-video {
  color: #F5CEC7;
}
.fa-pencil-alt {
  color: #F5CEC7;
}
.fa-edit {
  color: #F5CEC7
}
.fa-comment-dots{
  color: #F5CEC7;
}
/* .fa-user-friends{
  color: #F5CEC7;
} */
</style>
