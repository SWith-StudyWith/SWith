<template>
  <div class="control-buttons-container">
    <div class="control-buttons-frame">
      <div class="icon-container" @click="onClickKanbanBoardIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="bottom" data-bs-content="칸반보드">
          <font-awesome-icon class="icon" :class="{ 'font-active': screenMode === 4 }" :icon="['fas', 'tasks']" />
        </div>
        <div class="icon-container" @click="onClickWhiteBoardIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="bottom" data-bs-content="화이트보드">
          <font-awesome-icon class="icon" :class="{ 'font-active': screenMode === 2 }"  :icon="['fas', 'highlighter']" />
        </div>
        <div class="icon-container" @click="onClickPostItIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="bottom" data-bs-content="포스트잇">
          <font-awesome-icon class="icon" :class="{ 'font-active': screenMode === 3 }" :icon="['fas', 'sticky-note']"/>
        </div>
        <div class="icon-container" @click="onClickScreenShareIcon" tabindex="0" data-bs-container="body" data-bs-trigger="hover" data-bs-toggle="popover" data-bs-placement="bottom" data-bs-content="화면공유">
          <font-awesome-icon class="icon" :class="{ 'font-active': isScreenShared }"  :icon="['fas', 'chalkboard-teacher']" />
        </div>
    </div>
  </div>
</template>

<script>
import { computed, reactive } from 'vue';
import { useStore } from 'vuex';
import { Popover } from 'bootstrap';

export default {
  name: 'ControlButtons',
  props: {
    screenMode: Number,
    isScreenShared: Boolean,
  },
  setup( props, { emit } ) {
    // 스터디 회원 목록 조회
    const store = useStore();
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
      emit('show-screenmode', 4)
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


    return {
      state, sidebarWidth,
      onClickScreenShareIcon, onClickWhiteBoardIcon, onClickKanbanBoardIcon, onClickPostItIcon,
    };
  },
  mounted() {
    Array.from(document.querySelectorAll('[data-bs-toggle="popover"]'))
    .forEach(popoverNode => new Popover(popoverNode))
  },
}
</script>

<style>
.control-buttons-container {
  width: 16vw;
  /* display: flex; */
}
.control-buttons-frame {
  /* height: 8vh; */
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  opacity: 0.9;
  /* background-color: #F5CEC7; */
  /* background-color: #27355e; */
  background-color: #1E304F;
  /* background-color: antiquewhite; */
  /* padding: 5px; */

  /* display: flex; */
  /* flex-direction: row; */
  /* align-items: center; */
  justify-content: space-evenly;
  padding: 5px;
  /* margin-bottom: 5px; */
  /* background-color: #F5CEC7; */
  border: solid 4px #ffffff;
  /* margin-top: 5px; */
  border-radius: 15px;
  /* opacity: 0.5; */

  font-family: 'Mulish', 'Alef', 'Noto Sans KR';
  /* color: #b7c1d3; */
  color: #aebed4;
  font-size: 13px;
  z-index: 500;
  box-shadow:  2px 1px 10px rgb(0 0 0 / 0.6);
}
.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  border-radius: 0.5rem;
  cursor: pointer;
}
.icon {
  /* width: 2.5vw; */
  height: 2vh;
  margin: 1vh;
  font-size: 20px;
}
.icon-container:hover {
  background-color: #334564;
  /* background-color: #334564; */
  /* border-radius: 6px; */
}
.font-active {
  /* color: #F5CEC7; */
  color: #ffd5cd;
}
</style>
