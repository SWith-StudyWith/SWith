<template>
  <div class="kanbanboard">
    <!-- <Sidebar />
    <div :style="{ 'margin-left': sidebarWidth }">
    <router-view />
  <h1>The sidebar is {{ collapsed ? 'closed' : 'open' }}</h1>
  <button @click="toggleSidebar">Toggle Sidebar</button>
    </div> -->
  <div class="h-100">
    <!-- <h2 class="text-start px-4">StudyDetailKanbanBoard</h2> -->
    <div class="p-3 d-flex justify-content-center h-90">
      <div
        v-for="tasks in kanbanBoard"
        :key="tasks.taskId"
        class="mx-2 w-100 bg-grey p-2 rounded-3"
      >
        <p class="text-start mb-0">{{ tasks.taskName }}</p>
        <KanbanBoardCard
          v-for="(task) in tasks.kanban"
          :key="task.kanbanId"
          :task="task"
          class="my-2 align-items-center"
        />
      </div>
    </div>
  </div>
  </div>
</template>

<script>
import { collapsed, toggleSidebar, sidebarWidth } from '@/views/studies/components/sidebar/state.js';
// import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { useStore } from 'vuex';
import KanbanBoardCard from '@/views/studies/components/screen/KanbanBoardCard.vue';
import { computed } from 'vue';

export default {
  name: 'KanbanBoard',
  components: {
    // Sidebar,
    KanbanBoardCard,
  },
  setup() {
    const store = useStore();
    const kanbanBoard = computed(() => {
      return store.state.study.studyInfo.kanbanBoard;
    })

    return {
      collapsed, toggleSidebar, sidebarWidth,
      kanbanBoard,
    }
  }
}
</script>
<style scoped>
.bg-grey {
  background-color: #f7fafc;
}
</style>
