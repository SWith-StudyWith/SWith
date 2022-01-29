<template>
  <div class="h-100">
    <h2 class="text-start px-4">StudyDetailKanbanBoard</h2>
    <div class="p-3 d-flex justify-content-center h-90">
      <div
        v-for="tasks in kanbanBoard"
        :key="tasks.taskId"
        class="mx-2 w-100 bg-grey p-2 rounded-3"
      >
        <p class="text-start mb-0">{{ tasks.taskName }}</p>
        <StudyDetailKanbanBoardCard
          v-for="(task) in tasks.kanban"
          :key="task.kanbanId"
          :task="task"
          class="my-2 align-items-center"
        />
      </div>
    </div>
  </div>
</template>
<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import StudyDetailKanbanBoardCard from '@/views/studies/components/StudyDetailKanbanBoardCard.vue';

export default {
  name: 'StudyDetailKanbanBoard',
  components: {
    StudyDetailKanbanBoardCard,
  },
  setup() {
    const store = useStore();
    const kanbanBoard = computed(() => {
      return store.state.study.studyInfo.kanbanBoard;
    })

    return { kanbanBoard }
  },
}
</script>
<style scoped>
.bg-grey {
  background-color: #f7fafc;
}
</style>
