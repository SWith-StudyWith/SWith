<template>
  side bar
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <h1>
      <span v-if="collapsed">
        <div>V</div>
        <div>S</div>
      </span>
      <span v-else>Vue Sidebar</span>
    </h1>

    <!-- <SidebarLink to="/" icon="fas fa-home">Home</SidebarLink>
    <SidebarLink to="/dashboard" icon="fas fa-columns">Dashboard</SidebarLink>
    <SidebarLink to="/analytics" icon="fas fa-chart-bar">Analytics</SidebarLink>
    <SidebarLink to="/friends" icon="fas fa-users">Friends</SidebarLink>
    <SidebarLink to="/image" icon="fas fa-image">Images</SidebarLink> -->
    <SidebarLink to="/studies/:member_id/room" icon="fas fa-home">Home</SidebarLink>
    <SidebarLink to="/studies/:member_id" icon="fas fa-columns">MainPage</SidebarLink>
    <SidebarLink to="/studies/:member_id/room" icon="fas fa-chart-bar">Rooms</SidebarLink>
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
import SidebarLink from './SidebarLink.vue';
import { collapsed, toggleSidebar, sidebarWidth } from './state.js';

export default {
  name: 'Sidebar',
  props: {},
  setup() {
    return { collapsed, toggleSidebar, sidebarWidth };
  }
}
</script>

<style scoped>
:root {
  --sidebar-bg-color: #1E304F;
  --sidebar-item-hover: #38a169;
  --sidebar-item-active: #276749;
}
.sidebar {
  color: white;
  background-color: var(--sidebar-bg-color);

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
