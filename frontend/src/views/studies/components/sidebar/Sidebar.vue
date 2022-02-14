<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <!-- <h1>
      <span v-if="collapsed">
        <div>S</div>
        <div>S</div>
      </span>
      <span v-else>Swith Sidebar</span>
    </h1> -->
    <div class="row">
      <div>
        <div class="icon-container icon" @click="onClickExitIcon">
          <font-awesome-icon class="exit-btn" :icon="['fas', 'home']" />
        </div>
      </div>
      <div class="control-bottons">
        <!-- <div class="icon-container" @click="onClickKanbanBoardIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="칸반보드">
          <font-awesome-icon class="icon" :class="{ 'font-active': screenMode === 0 }" :icon="['fas', 'tasks']" />
        </div>
        <div class="icon-container" @click="onClickScreenShareIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="화면공유">
          <font-awesome-icon class="icon" :class="{ 'font-active': isScreenShared }"  :icon="['fas', 'chalkboard-teacher']" />
        </div>
        <div class="icon-container" @click="onClickWhiteBoardIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="화이트보드">
          <font-awesome-icon class="icon" :class="{ 'font-active': screenMode === 2 }"  :icon="['fas', 'highlighter']" />
        </div>
        <div class="icon-container" @click="onClickPostItIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="포스트잇">
          <font-awesome-icon class="icon" :class="{ 'font-active': screenMode === 3 }" :icon="['fas', 'sticky-note']"/>
        </div> -->
        <div class="icon-container" @click="onClickFileIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="파일공유">
          <font-awesome-icon class="icon" :class="{ 'font-active': state.isFile }"  :icon="['fas', 'paperclip']" />
        </div>
        <div class="icon-container" @click="onClickChatIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="채팅">
          <font-awesome-icon class="icon" :class="{ 'font-active': state.isChat }" :icon="['fas', 'comment']" />
        </div>
        <div class="icon-container" @click="onClickMemberIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="유저목록">
          <font-awesome-icon class="icon" :class="{ 'font-active': state.isMemberList }" :icon="['fas', 'user-friends']"/>
        </div>
          <!-- toggle button -->
        <div class="icon-container">
          <span
            class="collapse-icon"
            :class="{ 'rotate-180': state.collapsed }"
            @click="toggleSidebar"
          >
            <i class="fas fa-angle-double-left icon" />
          </span>
        </div>
      </div>
      <div v-if="!state.collapsed" class="col-10 sidebar-main" >
        <div >
          <SidebarFile v-if="state.isFile"/>
          <SidebarChat v-if="state.isChat"/>
          <!-- <SidebarChat v-if="state.isChat" :chatLog="state.chatLog"/> -->
          <SidebarMemberView :members="state.memberList" v-if="state.isMemberList"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import SidebarLink from '@/views/studies/components/sidebar/SidebarLink.vue';
// import { collapsed, toggleSidebar, sidebarWidth } from '@/views/studies/components/sidebar/state.js';
import SidebarChat from '@/views/studies/components/sidebar/SidebarChat.vue';
import SidebarFile from '@/views/studies/components/sidebar/SidebarFile.vue';
import SidebarMemberView from '@/views/studies/components/sidebar/SidebarMemberView.vue'
import { computed, reactive } from 'vue';
import { useStore } from 'vuex';
import { useRoute, useRouter } from 'vue-router';
import { Popover } from 'bootstrap';

//
import { getMemberList } from '@/api/study'
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
  setup( props, { emit } ) {
    // 스터디 회원 목록 조회
    const store = useStore();
    const route = useRoute();
    const router = useRouter();
    const state = reactive({
      memberList : computed(() => {
        return store.state.study.memberList;
      }),
      isChat : false,
      isMemberList : false,
      isFile : false,
      collapsed: true,
      recvList: [],
      chatLog: [],
    })

    const SIDEBAR_WIDTH = 22;
    const SIDEBAR_WIDTH_COLLAPSED = 4;
    const sidebarWidth = computed(
      () => `${state.collapsed ? SIDEBAR_WIDTH_COLLAPSED : SIDEBAR_WIDTH}vw`
    );
    const onClickKanbanBoardIcon = () => {
      emit('show-screenmode', 0)
    };
    const onClickScreenShareIcon = () => {
      if (props.isScreenShared) {
        emit('stopScreenSharing')
      } else {
        emit('startScreenSharing')
      }
    };
    const onClickWhiteBoardIcon = () => {
      emit('show-screenmode', 2)
    };
    const onClickPostItIcon = () => {
      emit('show-screenmode', 3)
    };
    const toggleSidebar = () => {
      state.collapsed = !state.collapsed;
      if (!state.collapsed) {
        onClickChatIcon()
      } else {
        state.isChat = false;
        state.isMemberList = false;
        state.isFile = false;
      }
      emit('toggleSidebar', sidebarWidth.value)
    }
    const onClickFileIcon = () => {
      if (!state.isFile) {
        state.isChat = false;
        state.isMemberList = false;
      }
      if (state.collapsed) {
        state.collapsed = false;
      }
      if (!state.collapsed && state.isFile) {
        state.collapsed = true;
      }
        state.isFile = !state.isFile;
      emit('toggleSidebar', sidebarWidth.value)
    }
    const onClickChatIcon = () => {
      if (!state.isChat) {
        state.isFile = false;
        state.isMemberList = false;
      }
      if (state.collapsed) {
        state.collapsed = false;
      }
      if (!state.collapsed && state.isChat) {
        state.collapsed = true;
      }
      state.isChat = !state.isChat;
      emit('toggleSidebar', sidebarWidth.value)

    };
    const onClickMemberIcon = () => {
      if (!state.isMemberList) {
        state.isFile = false;
        state.isChat = false;
      }
      if (state.collapsed) {
        state.collapsed = false;
      }
      if (!state.collapsed && state.isMemberList) {
        state.collapsed = true;
      }
      state.isMemberList = !state.isMemberList;
      emit('toggleSidebar', sidebarWidth.value)
      // test
      console.log('스터디 회원 목록 조회 테스트!');
      getMemberList(
        route.params.studyId,
        (res) => {
          console.log(res.data);
          if (res.data.code === 200) {
            // console.log('회원 목록 조회 성공!');
            store.dispatch('GET_MEMBER_LIST', route.params.studyId);
          }
        },
        (err) => {
          console.log(err);
        }
      );
    }
    const onClickExitIcon = () => {
      router.push({ name: 'StudyDetail', params: { studyId: route.params.studyId } })
    }

    return {
      state, toggleSidebar, sidebarWidth,
      onClickScreenShareIcon, onClickWhiteBoardIcon, onClickKanbanBoardIcon,
      onClickChatIcon, onClickMemberIcon, onClickFileIcon, onClickExitIcon, onClickPostItIcon,
    };
  },
  mounted() {
    Array.from(document.querySelectorAll('[data-bs-toggle="popover"]'))
    .forEach(popoverNode => new Popover(popoverNode))
  },
}
</script>

<style scoped>
.sidebar {
  color: white;
  background-color: #1E304F;
  box-shadow:  2px 1px 10px rgb(0 0 0 / 0.7);
  /* float: left; */
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  bottom: 0;
  padding: 0.5rem;
  transition: 0.3s ease;
  display: flex;
  flex-direction: column-reverse;
}
.control-bottons {
  /* text-align: center; */
  color: rgba(255, 255, 255, 0.7);
  position: fixed;
  left: 0.5rem;
  bottom: 0px;
  width: 3vw;
  text-align: left;
  font-size: 23px;
  z-index: 500;
  padding: 0;
}
.sidebar h1 {
  height: 2.5em;
}
.collapse-icon {
  transition: 0.2s linear;
  /* font-size: 30px; */
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
.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  border-radius: 0.5rem;
  cursor: pointer;
}
.icon-container:hover {
  background-color: #334564;
}
.icon {
  /* width: 2.5vw; */
  height: 3vh;
  margin: 1vh;
}
.exit-btn {
  position: fixed;
  top: 8px;
  left: 8px;
  /* transform: rotate(180deg); */
  font-size: 3vh;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 0.5rem;
  cursor: pointer;
  margin: 1vh 0 0 1vh;
  color: rgba(255, 255, 255, 0.7);
}
.sidebar-main{
  margin-left: 4vw;
  margin-bottom: 2.8vh;
  margin-top: 2.8vh;
  padding-left: 0px;
}
</style>
