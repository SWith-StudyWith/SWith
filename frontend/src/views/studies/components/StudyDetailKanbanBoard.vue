<template>
  <div>
    StudyDetailKanbanBoard
    <div>
      <div class="py-12 d-flex justify-content-center">
        <div
          v-for="tasks in kanbanBoard"
          :key="tasks.taskId"
          class="p-1 me-4 w-100"
        >
          <p class="">{{ tasks.taskName }}</p>
          <StudyDetailKanbanBoardCard
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
import { useStore } from 'vuex'
import StudyDetailKanbanBoardCard from '@/views/studies/components/StudyDetailKanbanBoardCard.vue';

export default {
  name: 'StudyDetailKanbanBoard',
  components: {
    StudyDetailKanbanBoardCard,
  },
  setup() {
    const store = useStore();
    const kanbanBoard = store.state.study.studyInfo.kanbanBoard;

    return { kanbanBoard }
  },
}
</script>
<style scoped>

</style>
