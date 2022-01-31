<template>
  <div class="kanbanboard">
    <!-- <Sidebar />
    <div :style="{ 'margin-left': sidebarWidth }">
    <router-view />
  <h1>The sidebar is {{ collapsed ? 'closed' : 'open' }}</h1>
  <button @click="toggleSidebar">Toggle Sidebar</button>
    </div> -->
  <div class="h-100">
    <div class="p-3 d-flex justify-content-center h-90">
      <div
        v-for="tasks in kanbanBoard"
        :key="tasks.taskId"
        class="mx-2 w-100 bg-light-grey p-2 rounded-3"
      >
        <p class="text-start mb-0">
          <span class="taskname px-2 py-1"
            :class="{ 'bg-grey' : tasks.taskId === 1, 'bg-pink' : tasks.taskId === 2, 'bg-purple' : tasks.taskId === 3 }">
            {{ tasks.taskName }}
          </span>
        </p>
        <div class="overflow-auto" style="max-height: 20rem;">
          <KanbanBoardCard
            v-for="(task) in tasks.kanban"
            :key="task.kanbanId"
            :task="task"
            class="my-2 align-items-center list-group-item"
            @onClickCard="selectedTask.value=$event"
          />
        </div>
      </div>
    </div>
  </div>
  </div>
  <KanbanBoardModal :selectedTask="selectedTask" />
</template>

<script>
import { collapsed, toggleSidebar, sidebarWidth } from '@/views/studies/components/sidebar/state.js';
// import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { useStore } from 'vuex';
import KanbanBoardCard from '@/views/studies/components/screen/KanbanBoardCard.vue';
import KanbanBoardModal from '@/views/studies/components/screen/KanbanBoardModal.vue';
import { ref, computed } from 'vue';

export default {
  name: 'KanbanBoard',
  components: {
    // Sidebar,
    KanbanBoardCard,
    KanbanBoardModal,
  },
  setup() {
    const store = useStore();
    const kanbanBoard = computed(() => {
      return store.state.study.studyInfo.kanbanBoard;
    })
    const selectedTask = ref({})

    return {
      collapsed, toggleSidebar, sidebarWidth,
      kanbanBoard, selectedTask
    }
  },
}
</script>
<style scoped>
.taskname {
  border-radius: 3rem;
  font-size: 0.8rem;
}
.bg-grey {
  background-color: #E1E4E8;
}
.bg-purple {
  background-color: #F0E7F6;
}
.bg-pink {
  background-color: #FFDCE0;
}
.bg-light-grey {
  background-color: #F8F8F8;
}
::-webkit-scrollbar{
  width: 6px;
}
</style>
