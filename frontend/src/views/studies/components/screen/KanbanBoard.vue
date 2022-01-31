<template>
  <div class="kanbanboard">
    <div class="h-100">
      <div class="p-3 d-flex justify-content-center h-90">
        <div
          v-for="column in kanbanBoard"
          :key="column.taskId"
          class="mx-2 w-100 bg-light-grey p-2 rounded-3"
        >
          <p class="text-start mb-1">
              <span
                class="taskname px-2 py-1 "
                :class="{ 'bg-grey' : column.taskId === 1, 'bg-pink' : column.taskId === 2, 'bg-purple' : column.taskId === 3 }"
              >
                {{ column.taskName }}
              </span>
          </p>
          <div class="overflow-auto" style="max-height: 30rem;">
            <draggable
              class="list-group"
              :list="column.kanban"
              item-key="kanbanId"
              group="task"
              ghost-class="ghost"
            >
              <template #item="{ element }">
                <div class="list-group-item rounded mt-1 p-3">
                  <KanbanBoardCard
                    :task="element"
                    :taskName="column.taskName"
                    class="align-items-center text-start"
                    @onClickCard="selectedTask.value=$event"
                  />
                </div>
              </template>
            </draggable>
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
import { ref, computed, reactive } from 'vue';
import draggable from 'vuedraggable'

export default {
  name: 'KanbanBoard',
  components: {
    // Sidebar,
    KanbanBoardCard,
    KanbanBoardModal,
    draggable,
  },
  setup() {
    const store = useStore();
    const kanbanBoard = computed(() => {
      return store.state.study.studyInfo.kanbanBoard;
    })
    const columns = reactive({
      todo: kanbanBoard.value[0],
      inProgress: kanbanBoard.value[1],
      done: kanbanBoard.value[2]
    })

    const selectedTask = ref({})

    return {
      collapsed, toggleSidebar, sidebarWidth,
      kanbanBoard, selectedTask, columns
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
.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
</style>
