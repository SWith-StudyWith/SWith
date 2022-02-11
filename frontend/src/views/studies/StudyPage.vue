<template>
  <div class="study-base-page">
    <Sidebar @show-screenmode="showScreenMode"/>
    <div :style="{ 'margin-left': sidebarWidth }">
    <h2>카메라 들어가는 부분</h2>
    <!-- <router-view /> -->
    <!-- <button @click="onClickKanbanBoard">칸반보드열어말어</button>
    <button @click="onClickScreenShare">화면공유열어말어</button>
    <button @click="onClickWhiteBoard">화이트보드열어말어</button> -->
    <KanbanBoard v-show="isKanbanBoard"/>
    <!-- <ScreenShare v-show="isScreenShare"/> -->
    <WhiteBoard v-show="isWhiteBoard"/>
    <!-- <KanbanBoard v-show="screen==1"/>
    <ScreenShare v-show="screen==2"/>
    <WhiteBoard v-show="screen==3"/> -->
    </div>
  </div>
</template>

<script>
import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { sidebarWidth } from '@/views/studies/components/sidebar/state.js';
import KanbanBoard from '@/views/studies/components/screen/KanbanBoard.vue';
// import ScreenShare from '@/views/studies/components/screen/ScreenShare.vue';
import WhiteBoard from '@/views/studies/components/screen/WhiteBoard.vue';
import { ref } from 'vue';

export default {
  name: 'Studies',
  components: {
    Sidebar,
    KanbanBoard,
    // ScreenShare,
    WhiteBoard,
  },
  setup() {
    const isKanbanBoard = ref(false);
    const isWhiteBoard = ref(false);
    const isScreenShare = ref(false);
    const screenMode = ref(1);
    const onClickKanbanBoard = () => {
      isKanbanBoard.value = !isKanbanBoard.value;
      // console.log(isKanbanBoard);
    };
    const onClickWhiteBoard = () => {
      isWhiteBoard.value = !isWhiteBoard.value;
      // console.log(isWhiteBoard);
    };
    const onClickScreenShare = () => {
      isScreenShare.value = !isScreenShare.value;
      // console.log(isScreenShare);
    };
    const onClickScreenMode = () => {
      // screenMode.value = screenMode.value*  (-1);
    };

    const showScreenMode = ( screen ) => {
      // switch (screen)
      if(screen==1){
        // console.log('1 칸반보드 true 보여조라~');
        isKanbanBoard.value = !isKanbanBoard.value;
      } else if(screen==2){
        // console.log('2 화면공유 true 보여조라~');
        isScreenShare.value = !isScreenShare.value;
      } else if( screen ==3) {
        // console.log('3 화이트보드 true니깐 보여주라~');
        isWhiteBoard.value = !isWhiteBoard.value;
      }
    }

    return { sidebarWidth,
              onClickScreenMode, screenMode,
              isWhiteBoard, isScreenShare, isKanbanBoard,
              onClickWhiteBoard, onClickScreenShare, onClickKanbanBoard,
              showScreenMode,
    };
  }
}
</script>

<style scoped>
.study-base-page {
  display: flex;
  justify-content: center;
  flex-wrap: nowrap;
  flex-direction: column;

  height: 100%;

  background-color: #7285A6;
}
</style>
