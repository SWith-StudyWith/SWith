<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <h1>
      <span v-if="collapsed">
        <div>S</div>
        <div>S</div>
      </span>
      <span v-else>Swith Sidebar</span>
    </h1>
    <div class="control-bottons">
      <font-awesome-icon class="m-2" :class="{ 'font-active': screenMode === 0 }" @click="onClickKanbanBoardIcon" :icon="['fas', 'chalkboard']" />
      <font-awesome-icon class="m-2" :class="{ 'font-active': isScreenShared }" @click="onClickScreenShareIcon" :icon="['fas', 'tv']" />
      <font-awesome-icon class="m-2" :class="{ 'font-active': screenMode === 2 }" @click="onClickWhiteBoardIcon" :icon="['fas', 'pen']" />
      <font-awesome-icon class="m-2" :class="{ 'font-active': false }" @click="toggleSidebar" :icon="['fas', 'file-upload']" />
      <font-awesome-icon class="m-2" :class="{ 'font-active': state.isChat }" @click="onClickChatIcon" :icon="['fas', 'comment']" />
      <font-awesome-icon class="m-2" :class="{ 'font-active': state.isMemberList }" @click="onClickMemberIcon" :icon="['fas', 'user-friends']"/>
    </div>
    <!-- toggle button -->
    <span
      class="collapse-icon"
      :class="{ 'rotate-180': collapsed }"
      @click="toggleSidebar"
    >
      <i class="fas fa-angle-double-left" />
    </span>
    <SidebarFile/>
    <SidebarChat v-if="state.isChat"/>
    <SidebarMemberView :members="state.memberList" v-if="state.isMemberList"/>
  </div>
</template>

<script>
// import SidebarLink from '@/views/studies/components/sidebar/SidebarLink.vue';
import { collapsed, toggleSidebar, sidebarWidth } from '@/views/studies/components/sidebar/state.js';
import SidebarChat from '@/views/studies/components/sidebar/SidebarChat.vue';
import SidebarFile from '@/views/studies/components/sidebar/SidebarFile.vue';
import SidebarMemberView from '@/views/studies/components/sidebar/SidebarMemberView.vue'
import { computed, reactive } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';

export default {
  name: 'Sidebar',
  components: {
    // SidebarLink,
    SidebarChat,
    SidebarFile,
    SidebarMemberView,
  },
  props: {
    screenMode: Number,
    isScreenShared: Boolean,
  },
  setup( props, context ) {
    // const isChat = ref(false);

    // 스터디 회원 목록 조회
    const store = useStore();
    const route = useRoute();
    store.dispatch('GET_MEMBER_LIST', route.params.studyId);
    const state = reactive({
      memberList : computed(() => {
        return store.state.study.memberList;
      }),
      isChat : false,
      isMemberList : false,
    })
    const onClickKanbanBoardIcon = () => {
      context.emit('show-screenmode', 0)
    };
    const onClickScreenShareIcon = () => {
      if (props.isScreenShared) {
        context.emit('stopScreenSharing')
      } else {
        context.emit('startScreenSharing')
      }
    };
    const onClickWhiteBoardIcon = () => {
      context.emit('show-screenmode', 2)
    };
    const onClickChatIcon = () => {
      state.isChat = !state.isChat;
    };
    const onClickMemberIcon = () => {
      state.isMemberList = !state.isMemberList;
    }

    return {
      state, collapsed, toggleSidebar, sidebarWidth,
      onClickScreenShareIcon, onClickWhiteBoardIcon, onClickKanbanBoardIcon,
      onClickChatIcon, onClickMemberIcon
    };
  },
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

  /* scroll */
  overflow-x: hidden;
    overflow-y: auto;
}
.control-bottons {
  text-align: center;
  color: rgba(255, 255, 255, 0.7);

  float:left;
  position: fixed;
  z-index: 1;
  top: 10;
  left: 0;
  bottom: 40px;
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
/* .fa-desktop {
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
} */
/* .fa-user-friends{
  color: #F5CEC7;
} */
.font-active {
  color: #F5CEC7;
}
</style>
