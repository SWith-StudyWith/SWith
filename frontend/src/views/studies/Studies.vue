<template>
    <div class="study">
    <h1>This is a study page</h1>
    <Sidebar />
    <div :style="{ 'margin-left': sidebarWidth }">
    <router-view />
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
