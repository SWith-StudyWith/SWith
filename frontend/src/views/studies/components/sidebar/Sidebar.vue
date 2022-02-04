<template>
  <div class="sidebar container" :style="{ width: sidebarWidth }">
    <!-- <h1>
      <span v-if="collapsed">
        <div>S</div>
        <div>S</div>
      </span>
      <span v-else>Swith Sidebar</span>
    </h1> -->
    <div class="row">
      <div class="sidebar-left control-bottons">
          <font-awesome-icon class="m-2" :class="{ 'font-active': screenMode === 0 }" @click="onClickKanbanBoardIcon" :icon="['fas', 'chalkboard']" />
          <font-awesome-icon class="m-2" :class="{ 'font-active': isScreenShared }" @click="onClickScreenShareIcon" :icon="['fas', 'tv']" />
          <font-awesome-icon class="m-2" :class="{ 'font-active': screenMode === 2 }" @click="onClickWhiteBoardIcon" :icon="['fas', 'pen']" />
          <font-awesome-icon class="m-2" :class="{ 'font-active': state.isFile }" @click="onClickFileIcon()" :icon="['fas', 'file-upload']" />
          <font-awesome-icon class="m-2" :class="{ 'font-active': state.isChat }" @click="onClickChatIcon" :icon="['fas', 'comment']" />
          <font-awesome-icon class="m-2" :class="{ 'font-active': state.isMemberList }" @click="onClickMemberIcon" :icon="['fas', 'user-friends']"/>
          <!-- toggle button -->
          <span
            class="collapse-icon"
            :class="{ 'rotate-180': collapsed }"
            @click="toggleSidebar"
          >
            <i class="fas fa-angle-double-left" />
          </span>
      </div>

      <div class="col-2">
        <!-- 사이드바 컨트롤 버튼들 자리차지 ? 가 안되서 만들어준 col...입니다 -->
      </div>
      <div class="col-10 sidebar-main">
        <!-- <div class="sidebar-main"> -->
          <SidebarFile v-if="state.isFile"/>
          <SidebarChat v-if="state.isChat"/>
          <SidebarMemberView :members="state.memberList" v-if="state.isMemberList"/>
        <!-- </div> -->
      </div>

    </div>

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
      isFile : false,
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
    const onClickFileIcon = () => {
      state.isFile = !state.isFile;
    }
    const onClickChatIcon = () => {
      state.isChat = !state.isChat;
    };
    const onClickMemberIcon = () => {
      state.isMemberList = !state.isMemberList;
    }

    return {
      state, collapsed, toggleSidebar, sidebarWidth,
      onClickScreenShareIcon, onClickWhiteBoardIcon, onClickKanbanBoardIcon,
      onClickChatIcon, onClickMemberIcon, onClickFileIcon
    };
  },
}
</script>

<style scoped>
.sidebar {
  color: white;
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
  /* overflow-x: hidden; */
  /* overflow-y: auto; */
}
/* .sidebar-left{
  height: 50%;
  width: 54px;
} */
.control-bottons {
  /* text-align: center; */
  color: rgba(255, 255, 255, 0.7);

  /* float:left;
  position: fixed;
  z-index: 1;
  top: 10;
  left: 0;
  bottom: 40px;
  padding: 0.5em;

  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  align-items: center; */

  position: fixed;
  left: 0x;
  bottom: 0px;
  width: 54px;
  text-align: left;
  font-size: 23px;
  cursor: pointer;
}
.sidebar h1 {
  height: 2.5em;
}
.collapse-icon {
  /* position: absolute; */
  /* bottom: 0; */
  /* padding: 0.75em; */
  /* color: rgba(255, 255, 255, 0.7); */
  transition: 0.2s linear;
  font-size: 30px;
  margin-left: 7px;
}
.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}
.sidebar-main{
  /* background-color: #9EABCB; */
  height: 100%;
}
.font-active {
  color: #F5CEC7;
}
</style>
