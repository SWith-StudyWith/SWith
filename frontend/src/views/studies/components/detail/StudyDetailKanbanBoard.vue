<template>
  <div style="height: 28rem;">
    <div class="p-3 d-flex justify-content-center h-100">
      <div
        v-for="tasks in state.kanbanBoard"
        :key="tasks.taskId"
        class="mx-2 w-100 bg-light-grey p-2 rounded-3"
      >
        <p class="text-start mb-2">
          <span class="taskname px-2 py-1"
            :class="{ 'bg-grey' : tasks.taskId === 1, 'bg-pink' : tasks.taskId === 2, 'bg-purple' : tasks.taskId === 3 }">
            {{ tasks.taskName }}
          </span>
          </p>
        <div class="overflow-auto" style="max-height: 23rem">
          <StudyDetailKanbanBoardCard
            v-for="(task) in tasks.kanban"
            :key="task.kanbanId"
            :task="task"
            class="mb-2 align-items-center"
            @onClickCard="selectedTask.value=$event"
          />
        </div>
      </div>
    </div>
  </div>
  <StudyDetailKanbanBoardModal :selectedTask="selectedTask"/>
</template>
<script>
import { ref, reactive, computed } from 'vue'
import StudyDetailKanbanBoardCard from '@/views/studies/components/detail/StudyDetailKanbanBoardCard.vue';
import StudyDetailKanbanBoardModal from '@/views/studies/components/detail/StudyDetailKanbanBoardModal.vue';

export default {
  name: 'StudyDetailKanbanBoard',
  components: {
    StudyDetailKanbanBoardCard,
    StudyDetailKanbanBoardModal,
  },
  props: {
    studyInfo: Object,
  },
  setup(props) {
    const state = reactive({
      kanbanBoard: computed(() => {
        return props.studyInfo.kanbanBoard
      }),
    })
    const selectedTask = ref({})

    return { state, selectedTask }
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
