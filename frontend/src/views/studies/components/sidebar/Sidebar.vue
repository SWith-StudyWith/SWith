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
      <div class="icon-container exit-btn" @click="onClickExitIcon">
        <font-awesome-icon class="m-2" :icon="['fas', 'sign-out-alt']" />
      </div>
      <div class="control-bottons">
        <div class="icon-container" @click="onClickKanbanBoardIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="칸반보드">
          <font-awesome-icon class="m-2" :class="{ 'font-active': screenMode === 0 }" :icon="['fas', 'chalkboard']" />
        </div>
        <div class="icon-container" @click="onClickScreenShareIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="화면공유">
          <font-awesome-icon class="m-2" :class="{ 'font-active': isScreenShared }"  :icon="['fas', 'tv']" />
        </div>
        <div class="icon-container" @click="onClickWhiteBoardIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="화이트보드">
          <font-awesome-icon class="m-2" :class="{ 'font-active': screenMode === 2 }"  :icon="['fas', 'pen']" />
        </div>
        <div class="icon-container" @click="onClickFileIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="파일공유">
          <font-awesome-icon class="m-2" :class="{ 'font-active': state.isFile }"  :icon="['fas', 'file-upload']" />
        </div>
        <div class="icon-container" @click="onClickChatIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="채팅">
          <font-awesome-icon class="m-2" :class="{ 'font-active': state.isChat }" :icon="['fas', 'comment']" />
        </div>
        <div class="icon-container" @click="onClickMemberIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="right" data-bs-content="유저목록">
          <font-awesome-icon class="m-2" :class="{ 'font-active': state.isMemberList }" :icon="['fas', 'user-friends']"/>
        </div>
          <!-- toggle button -->
        <div class="icon-container">
          <span
            class="collapse-icon"
            :class="{ 'rotate-180': collapsed }"
            @click="toggleSidebar"
          >
            <i class="fas fa-angle-double-left" />
          </span>
        </div>
      </div>
      <div v-if="!collapsed" class="col-10 sidebar-main" >
        <div >
          <SidebarFile v-if="state.isFile"/>
          <SidebarChat v-if="state.isChat"/>
          <SidebarMemberView :members="state.memberList" v-if="state.isMemberList"/>
        </div>
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
  setup( props, context ) {
    // const isChat = ref(false);

    // 스터디 회원 목록 조회
    const store = useStore();
    const route = useRoute();
    const router = useRouter();
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
      if (!state.isFile) {
        state.isChat = false;
        state.isMemberList = false;
      }
      state.isFile = !state.isFile;
      // if(!collapsed.value){
      //   state.isFile = !state.isFile;
      // } else{
      //   collapsed.value = !collapsed.value;
      // }
    }
    const onClickChatIcon = () => {
      if (!state.isChat) {
        state.isFile = false;
        state.isMemberList = false;
      }
      state.isChat = !state.isChat;
    };
    const onClickMemberIcon = () => {
      if (!state.isMemberList) {
        state.isFile = false;
        state.isChat = false;
      }
      state.isMemberList = !state.isMemberList;
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
      state, collapsed, toggleSidebar, sidebarWidth,
      onClickScreenShareIcon, onClickWhiteBoardIcon, onClickKanbanBoardIcon,
      onClickChatIcon, onClickMemberIcon, onClickFileIcon, onClickExitIcon
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
  width: 38px;
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
  margin-top: 0.4rem;
  border-radius: 0.5rem;
  width: 38px;
  cursor: pointer;
}
.icon-container:hover {
  background-color: #334564;
}
.exit-btn {
  position: fixed;
  top: 8px;
  left: 8px;
  transform: rotate(180deg);
  font-size: 23px;
}

.sidebar-main{
  margin-left: 60px;
  margin-bottom: 20px;
  margin-top: 20px;
}
</style>
