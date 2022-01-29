<template>
  <div class="kanbanboard">
    <!-- <Sidebar />
    <div :style="{ 'margin-left': sidebarWidth }">
    <router-view />
  <h1>The sidebar is {{ collapsed ? 'closed' : 'open' }}</h1>
  <button @click="toggleSidebar">Toggle Sidebar</button>
    </div> -->
    <h1>칸반보드컴포넌트</h1>
    <div>
      <div class="py-12 d-flex justify-content-center">
        <div
          v-for="tasks in kanbanBoard"
          :key="tasks.taskId"
          class="p-1 me-4 w-100"
        >
          <p class="">{{ tasks.taskName }}</p>
          <KanbanBoardCard
            v-for="(task) in tasks.kanban"
            :key="task.kanbanId"
            :task="task"
            class="mt-3 align-items-center"
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


export default {
  name: 'KanbanBoard',
  components: {
    // Sidebar,
    KanbanBoardCard,
  },
  setup() {
    const store = useStore();
    const kanbanBoard = store.state.study.studyInfo.kanbanBoard;
    return { collapsed, toggleSidebar, sidebarWidth,
            kanbanBoard,
    }
  }
}
</script>
