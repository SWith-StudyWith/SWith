<template>
  <div class="screenshare">
    <h1>화면공유 컴포넌트</h1>
  </div>
</template>

<script>
import { collapsed, toggleSidebar } from '@/views/studies/components/sidebar/state.js';
export default {
  setup() {
    return { collapsed, toggleSidebar }
  }
}
</script>
