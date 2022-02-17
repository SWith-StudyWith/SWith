<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <div class="row">
      <div class="home-buttons">
        <div class="icon-container" @click="onClickExitIcon">
          <font-awesome-icon class="icon" :icon="['fas', 'home']" />
        </div>
      </div>
      <div class="control-buttons">
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
          <SidebarMemberView :members="state.memberList" v-if="state.isMemberList"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
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
    SidebarChat,
    SidebarFile,
    SidebarMemberView,
  },
  setup( props, { emit } ) {
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
      collapsed: true,
      recvList: [],
      chatLog: [],
    })

    const SIDEBAR_WIDTH = 22;
    const SIDEBAR_WIDTH_COLLAPSED = 4;
    const sidebarWidth = computed(
      () => `${state.collapsed ? SIDEBAR_WIDTH_COLLAPSED : SIDEBAR_WIDTH}vw`
    );
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
      getMemberList(
        route.params.studyId,
        (res) => {
          if (res.data.code === 200) {
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
      state, toggleSidebar, sidebarWidth, onClickChatIcon,
      onClickMemberIcon, onClickFileIcon, onClickExitIcon,
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
  padding: 0.5vw;
  transition: 0.3s ease;
  display: flex;
  flex-direction: column-reverse;
  min-width: 4vh;
}
.home-buttons {
  color: rgba(255, 255, 255, 0.7);
  width: 3vw;
  top: 1vh;
  position: fixed;
  left: 0.5rem;
  /* text-align: left; */
  font-size: 22px;
  z-index: 500;
  padding: 0;
}
.control-buttons {
  /* text-align: center; */
  color: rgba(255, 255, 255, 0.7);
  position: fixed;
  left: 0.5rem;
  bottom: 2vh;
  width: 3vw;
  text-align: left;
  font-size: 22px;
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
  /* top: 2vh; */
}
.icon-container:hover {
  background-color: #334564;
}
.icon {
  /* width: 2.5vw; */
  height: 3vh;
  margin: 1vh;
}
/* .exit-btn { */
  /* height: 3vh; */
  /* margin: 1vh; */
  /* position: fixed; */
  /* top: 8px; */
  /* left: 8px; */
  /* transform: rotate(180deg); */
  /* font-size: 22px; */
  /* display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 0.5rem;
  cursor: pointer; */
  /* margin: 1vh 0 0 1vh; */
  /* color: rgba(255, 255, 255, 0.7); */
/* } */
.sidebar-main{
  margin-left: 4vw;
  margin-bottom: 2.8vh;
  margin-top: 2.8vh;
  padding-left: 0px;
}
</style>
