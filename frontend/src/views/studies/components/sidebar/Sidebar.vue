<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <h1>
      <span v-if="collapsed">
        <div>V</div>
        <div>S</div>
      </span>
      <span v-else>Vue Sidebar</span>
    </h1>
    <!-- <SidebarLink/> -->
    <!-- <SidebarLink to="/" icon="fas fa-home">Home</SidebarLink>
    <SidebarLink to="/dashboard" icon="fas fa-columns">Dashboard</SidebarLink>
    <SidebarLink to="/analytics" icon="fas fa-chart-bar">Analytics</SidebarLink>
    <SidebarLink to="/friends" icon="fas fa-users">Friends</SidebarLink>
    <SidebarLink to="/image" icon="fas fa-image">Images</SidebarLink> -->
    <SidebarLink to="/studies/main" icon="fas fa-sign-out-alt">메인으로가기</SidebarLink>
    <SidebarLink to="/studies/11" icon="fas fa-microphone">기본화면이라쳐</SidebarLink>
    <SidebarLink to="/studies/11/kanbanboard" icon="fas fa-chalkboard">칸반보드</SidebarLink>
    <SidebarLink to="/studies/11/screenshare" icon="fas fa-desktop">화면공유</SidebarLink>
    <SidebarLink to="/studies/11/whiteboard" icon="fas fa-pencil-alt">화이트보드</SidebarLink>
    <!-- <img src="@/assets/img/icon_study_page/Pen_gray.png"> -->
    <!-- <SidebarLink to="/friends" icon="fas fa-users">Friends</SidebarLink> -->
    <!-- <SidebarLink to="/image" icon="fas fa-image">Images</SidebarLink> -->

    <span
      class="collapse-icon"
      :class="{ 'rotate-180': collapsed }"
      @click="toggleSidebar"
    >
      <i class="fas fa-angle-double-left" />
    </span>

  </div>
</template>

<script>
import SidebarLink from '@/views/studies/components/sidebar/SidebarLink.vue';
import { collapsed, toggleSidebar, sidebarWidth } from '@/views/studies/components/sidebar/state.js';

export default {
  name: 'Sidebar',
  components: {
    SidebarLink,
  },
  props: {},
  setup() {
    return { collapsed, toggleSidebar, sidebarWidth };
  }
}
</script>

<style scoped>
.sidebar {
  color: black;
  background-color: #1E304F;

  float:left;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  bottom: 0;
  padding: 0.5em;

  transition: 0.3s ease;

  display: flex;
  flex-direction: column;
}

.sidebar h1 {
  height: 2.5em;
}
.collapse-icon {
  position: absolute;
  bottom: 0;
  padding: 0.75em;
  color: rgba(255, 255, 255, 0.7);
  transition: 0.2s linear;
}
.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}
</style>
