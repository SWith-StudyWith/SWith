<template>
  <div class="kanbanboard">
    <Sidebar />
    <div :style="{ 'margin-left': sidebarWidth }">
    <router-view />
  <h1>The sidebar is {{ collapsed ? 'closed' : 'open' }}</h1>
  <h1>칸반보드~~~~ 고기반찬~~~</h1>
  <button @click="toggleSidebar">Toggle Sidebar</button>
    </div>
  </div>
</template>

<script>
import { collapsed, toggleSidebar, sidebarWidth } from '@/views/studies/components/sidebar/state.js';
import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';

export default {
  components: {
    Sidebar,
  },
  setup() {
    return { collapsed, toggleSidebar, sidebarWidth }
  }
}
</script>
