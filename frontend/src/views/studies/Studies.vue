<template>
  <div class="study-base-page">
    <Sidebar @show-screenmode="showScreenMode"/>
    <div :style="{ 'margin-left': sidebarWidth }">
    <h1>스터디 기본 페이지</h1>
    <router-view />
    <h1>여기에 컴포넌트 넣으면 됨</h1>
    <button @click="onClickKanbanBoard">칸반보드열어말어</button>
    <button @click="onClickScreenShare">화면공유열어말어</button>
    <button @click="onClickWhiteBoard">화이트보드열어말어</button>
    <KanbanBoard v-show="isKanbanBoard"/>
    <ScreenShare v-show="isScreenShare"/>
    <WhiteBoard v-show="isWhiteBoard"/>
    </div>
  </div>
</template>

<script>
import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { sidebarWidth } from '@/views/studies/components/sidebar/state.js';
import KanbanBoard from '@/views/studies/components/screen/KanbanBoard.vue';
import ScreenShare from '@/views/studies/components/screen/ScreenShare.vue';
import WhiteBoard from '@/views/studies/components/screen/WhiteBoard.vue';
import { ref } from 'vue';


export default {
  name: 'Studies',
  components: {
    Sidebar,
    KanbanBoard,
    ScreenShare,
    WhiteBoard,
  },
  setup() {
    const isKanbanBoard = ref(false);
    const isWhiteBoard = ref(false);
    const isScreenShare = ref(false);
    const screenMode = ref(1);
    const onClickKanbanBoard = () => {
      isKanbanBoard.value = !isKanbanBoard.value;
      console.log(isKanbanBoard);
    };
    const onClickWhiteBoard = () => {
      isWhiteBoard.value = !isWhiteBoard.value;
      console.log(isWhiteBoard);
    };
    const onClickScreenShare = () => {
      isScreenShare.value = !isScreenShare.value;
      console.log(isScreenShare);
    };
    const onClickScreenMode = () => {
      screenMode.value = screenMode.value*  (-1);
    };

    const showScreenMode = ( screen ) => {
      // switch (screen)
      if(screen==1){
        console.log('1 칸반보드 true 보여조라~');
      } else if(screen==2){
        console.log('2 화면공유 true 보여조라~');
      } else if( screen ==3) {
        console.log('3 화이트보드 true니깐 보여주라~');
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
}
</style>
