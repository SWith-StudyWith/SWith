<template>
  <div class="kanbanboard">
    <div class="text-end mx-3">
      <div v-if="!editPermit">
        <button class="btn btn-primary mx-2" @click="onClickEditBtn">
          칸반보드 수정하기
          <font-awesome-icon :icon="['fas', 'edit']"></font-awesome-icon>
        </button>
        <button  class="btn btn-primary mx-2" @click="onClickRefreshBtn">
          칸반보드 불러오기
          <font-awesome-icon :icon="['fas', 'sync-alt']"></font-awesome-icon>
        </button>
      </div>
      <div v-else class="d-flex flex-row-reverse align-items-center">
        <button class="btn btn-primary mx-2" @click="onClickSaveBtn">
          수정내용 저장하기
          <font-awesome-icon :icon="['fas', 'save']"></font-awesome-icon>
        </button>
        <Timer @timeStopped="timeOver"/>
      </div>
    </div>
    <div class="h-100">
      <div class="p-3 d-flex justify-content-center h-100">
        <div
          v-for="column in kanbanBoard"
          :key="column.taskId"
          class="mx-2 w-100 bg-light-grey p-2 rounded-3 d-flex flex-column"
        >
          <p class="text-start mb-1">
              <span
                class="taskname px-2 py-1 user-select-none"
                :class="{ 'bg-grey' : column.taskId === 1, 'bg-pink' : column.taskId === 2, 'bg-purple' : column.taskId === 3 }"
              >
                {{ column.taskName }}
              </span>
          </p>
          <div class="overflow-auto my-2" style="height: 30rem;">
            <div v-if="editPermit">
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
                      :taskId="column.taskId"
                      class="align-items-center text-start"
                      @onClickCard="selectedTask.value=$event"
                    />
                  </div>
                </template>
              </draggable>
            </div>
            <div v-else>
              <div v-for="task in column.kanban" :key="task.kanbanId" class="list-group-item rounded mt-1 p-3">
                <KanbanBoardCard
                  :task="task"
                  :taskId="column.taskId"
                  class="align-items-center text-start"
                  @onClickCard="selectedTask.value=$event"
                />
              </div>
            </div>
          </div>
          <button
            class="mt-auto btn"
            :class="{ 'bg-grey' : column.taskId === 1, 'bg-pink' : column.taskId === 2, 'bg-purple' : column.taskId === 3 }"
            data-bs-toggle="modal" data-bs-target="#kanbanCardCreateModal"
            @click="statusId=column.taskId"
            :disabled="!editPermit"
          >
            추가하기
          </button>
        </div>
      </div>
    </div>
  </div>
  <KanbanBoardModal
    :selectedTask="selectedTask"
    :editPermit="editPermit"
    @updateTask="updateTask($event)"
    @deleteTask="deleteTask($event)"
  />
  <KanbanBoardCreateModal
    :taskId="statusId"
    @createTask="createTask($event)"
  />
  <KanbanWarningModal :edittingUser="edittingUser"/>
</template>

<script>
// import Sidebar from '@/views/studies/components/sidebar/Sidebar.vue';
import { useStore } from 'vuex';
import KanbanBoardCard from '@/views/studies/components/screen/KanbanBoardCard.vue';
import KanbanBoardModal from '@/views/studies/components/screen/KanbanBoardModal.vue';
import KanbanBoardCreateModal from '@/views/studies/components/screen/KanbanBoardCreateModal.vue';
import KanbanWarningModal from '@/views/studies/components/screen/KanbanWarningModal.vue';
import Timer from '@/views/studies/components/screen/Timer.vue';
import { ref, computed } from 'vue';
import draggable from 'vuedraggable'
import { checkKanban, putKanban } from '@/api/study'
import { Modal } from 'bootstrap';

export default {
  name: 'KanbanBoard',
  props: {
    editPermit: Boolean,
  },
  components: {
    // Sidebar,
    KanbanBoardCard,
    KanbanBoardModal,
    KanbanBoardCreateModal,
    draggable,
    Timer,
    KanbanWarningModal,
  },
  setup(props, { emit }) {
    const store = useStore();
    const kanbanBoard = computed(() => {
      return store.state.study.studyInfo.kanbanBoard;
    });
    const statusId = ref(null);
    const selectedTask = ref({});
    const updateTask = function(task) {
      kanbanBoard.value[task.value.taskId - 1].kanban.forEach((card) => {
        if (card.kanbanId === task.value.kanbanId) {
          card.content = task.value.content;
        }
      })
    };
    const deleteTask = function(task) {
      // 삭제할 인덱스 찾기
      const targetIndex = kanbanBoard.value[task.value.taskId - 1].kanban
        .findIndex((card) => card.kanbanId === task.value.kanbanId);
      // 삭제
      kanbanBoard.value[task.value.taskId - 1].kanban
        .splice(targetIndex, 1);
    };
    const edittingUser = ref('');
    const showWarningModal = function () {
      let myModalEl = document.getElementById('KanbanWarningModal');
      let modal = Modal.getOrCreateInstance(myModalEl);
      modal.show();
    }
    const onClickEditBtn = function() {
      console.log('수정할래!')
      store.dispatch('GET_STUDY_INFO', store.state.study.studyInfo.studyId)
      checkKanban(
        store.state.study.studyInfo.studyId,
        (res) => {
          console.log(res.data);
          if (res.data.code === 200) {
            console.log('수정 가능');
            emit('isEditPermit', true);
          } else if (res.data.code === 400) {
            console.log(`${res.data.data.nickname} 님이 수정 중입니다.`);
            edittingUser.value = res.data.data.nickname;
            // alert(`${res.data.data.nickname} 님이 수정 중입니다.`)
            showWarningModal();
            emit('isEditPermit', false);
          }
        },
        (err) => {
          console.log(err);
        }
      );
    }
    const onClickSaveBtn = function() {
      const studyId = store.state.study.studyInfo.studyId;
      console.log('저장할래!')
      // request payload 형태 만들기
      const payload = []
      kanbanBoard.value.forEach((column) => {
        let taskId = column.taskId
        column.kanban.forEach((task) => {
          payload.push({ studyId, taskId, content: task.content })
        })
      })
      putKanban(
        payload,
        store.state.study.studyInfo.studyId,
        (res) => {
          console.log(res.data)
          if (res.data.code === 200) {
            store.dispatch('GET_STUDY_INFO',studyId)
          }
        },
        (err) => {
          console.log(err)
        }
      )
      emit('isEditPermit', false);
    };
    const onClickRefreshBtn = function () {
      store.dispatch('GET_STUDY_INFO', store.state.study.studyInfo.studyId)
    };
    const createTask = function (task) {
      const taskId = task.taskId;
      kanbanBoard.value[taskId - 1].kanban.push({ content: task.content, kanbanId: task.kanbanId })
    };
    const timeOver = function () {
      console.log('시간 다되서 저장해버리기~')
      emit('isEditPermit', false);
      onClickSaveBtn()
    };
    return {
      kanbanBoard,
      selectedTask,
      updateTask,
      onClickEditBtn,
      onClickSaveBtn,
      onClickRefreshBtn,
      deleteTask,
      statusId,
      createTask,
      timeOver,
      edittingUser,
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
