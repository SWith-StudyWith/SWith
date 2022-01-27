<template>
  <div class="study">
    <Sidebar />
    <div :style="{ 'margin-left': sidebarWidth }">
    <h1>스터디 기본 페이지</h1>
    <router-view />
    <h1>여기에 컴포넌트 넣으면 됨</h1>
    </div>
  </div>
</template>

<script>
import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { sidebarWidth } from '@/views/studies/components/sidebar/state.js';

export default {
  name: 'Studies',
  components: {
    Sidebar,
  },
  setup() {
    return { sidebarWidth }
  }
}
</script>

<style>
</style>
