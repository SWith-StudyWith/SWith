<template>
  <h1>The sidebar is {{ collapsed ? 'closed' : 'open' }}</h1>
  <h1>화이트보드</h1>
  <button @click="toggleSidebar">Toggle Sidebar</button>
</template>

<script>
import { collapsed, toggleSidebar } from '@/views/studies/components/sidebar/state.js';
export default {
  setup() {
    return { collapsed, toggleSidebar }
  }
}
</script>
