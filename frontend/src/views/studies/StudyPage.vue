<template>
    <div class="study">
    <Sidebar />
    <h1>This is a study page</h1>
    <div :style="{ 'margin-left': sidebarWidth }">
    <router-view />
  </div>
  </div>
</template>

<script>
import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { sidebarWidth } from '@/views/studies/components/sidebar/state.js';

export default {
  name: 'StudyPage',
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
