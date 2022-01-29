<template>
  <div class="h-100">
    <h2 class="text-start px-4">StudyDetailKanbanBoard</h2>
    <div class="p-3 d-flex justify-content-center h-90">
      <div
        v-for="tasks in kanbanBoard"
        :key="tasks.taskId"
        class="mx-2 w-100 bg-grey p-2 rounded-3 overflow-auto"
        style="max-height: 20rem"
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
import { ref } from 'vue'
import StudyDetailKanbanBoardCard from '@/views/studies/components/detail/StudyDetailKanbanBoardCard.vue';

export default {
  name: 'StudyDetailKanbanBoard',
  components: {
    StudyDetailKanbanBoardCard,
  },
  props: {
    studyInfo: Object
  },
  setup(props) {
    const kanbanBoard = ref(props.studyInfo.kanbanBoard)

    return { kanbanBoard }
  },
}
</script>
<style scoped>
.bg-grey {
  background-color: #f7fafc;
}
</style>
