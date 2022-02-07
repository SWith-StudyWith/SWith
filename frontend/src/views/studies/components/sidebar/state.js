// contain the status of sidebar
import { ref, computed } from 'vue';

export const collapsed = ref(true);
export const toggleSidebar = () => (collapsed.value = !collapsed.value);

export const SIDEBAR_WIDTH = 400;
export const SIDEBAR_WIDTH_COLLAPSED = 54;
export const sidebarWidth = computed(
  () => `${collapsed.value ? SIDEBAR_WIDTH_COLLAPSED : SIDEBAR_WIDTH}px`
);
