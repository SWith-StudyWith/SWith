<template>
  <div class="kanbanboard">
    <div class="kanbanboard-header text-end mx-3">
      <div class="kanbanboard-title">
        <h1>Todo List</h1>
      </div>
      <div v-if="!editPermit" class="kanban-button">
        <button class="btn btn-primary" @click="onClickEditBtn">
          수정하기
          <font-awesome-icon class="icon" :icon="['fas', 'edit']"></font-awesome-icon>
        </button>
        <button  class="btn btn-primary" @click="onClickRefreshBtn">
          불러오기
          <font-awesome-icon class="icon" :icon="['fas', 'sync-alt']"></font-awesome-icon>
        </button>
      </div>
      <div v-else class="d-flex flex-row-reverse align-items-center kanban-button">
        <button class="btn btn-primary" @click="onClickSaveBtn">
          저장하기
          <font-awesome-icon class="icon" :icon="['fas', 'save']"></font-awesome-icon>
        </button>
        <Timer @timeStopped="timeOver"/>
      </div>
    </div>
    <div>
      <div class="kanban-wrapper d-flex justify-content-center">
        <div
          v-for="column in kanbanBoard"
          :key="column.taskId"
          class="task-column"
        >
          <p class="text-start mb-1">
              <span
                class="taskname px-2 py-1 user-select-none"
                :class="{ 'bg-grey' : column.taskId === 1, 'bg-pink' : column.taskId === 2, 'bg-purple' : column.taskId === 3 }"
              >
                {{ column.taskName }}
              </span>
          </p>
          <div class="overflow-auto my-2" style="height: 40vh">
            <div v-if="editPermit">
              <draggable
                class="list-group"
                :list="column.kanban"
                item-key="kanbanId"
                group="task"
                ghost-class="ghost"
              >
                <template #item="{ element }">
                  <div class="list-group-item rounded mt-1 p-3 kanban-card">
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
              <div v-for="task in column.kanban" :key="task.kanbanId" class="list-group-item rounded mt-1 p-3 kanban-card">
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
import KanbanBoardCard from '@/views/studies/components/screen/KanbanBoardCard.vue';
import KanbanBoardModal from '@/views/studies/components/screen/KanbanBoardModal.vue';
import KanbanBoardCreateModal from '@/views/studies/components/screen/KanbanBoardCreateModal.vue';
import KanbanWarningModal from '@/views/studies/components/screen/KanbanWarningModal.vue';
import Timer from '@/views/studies/components/screen/Timer.vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import { ref, computed, onBeforeUnmount } from 'vue';
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
    const route = useRoute();
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
    const onClickEditBtn = async function() {
      console.log('수정할래!')
      const getStudyInfo = () => {
        return new Promise((resolve) => {
          // store.dispatch('GET_STUDY_INFO', store.state.study.studyInfo.studyId);
          store.dispatch('GET_STUDY_INFO', route.params.studyId);
          console.log('불러오기 완료!')
          resolve();
        })
      }
      await getStudyInfo();
      checkKanban(
        route.params.studyId,
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
      const studyId = route.params.studyId;
      console.log(studyId)
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
        studyId,
        (res) => {
          console.log(res.data)
          if (res.data.code === 200) {
            store.dispatch('GET_STUDY_INFO', studyId)
          }
        },
        (err) => {
          console.log(err)
        }
      )
      emit('isEditPermit', false);
    };
    const onClickRefreshBtn = function () {
      store.dispatch('GET_STUDY_INFO', route.params.studyId)
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
    onBeforeUnmount(() => {
      if (props.editPermit) {
        onClickSaveBtn()
      }
    })
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
.kanbanboard {
  background-color: white;
  margin: 1vh 1vw 0 1vw ;
  height: 72vh;
  border-radius: 0.8rem;
}
.kanban-wrapper {
  margin: 0 10vh 0;
  border-radius: 0.7rem;
  padding: 1.4vh 0.7vh 1.4vh 0.7vh;
  height: 58vh;
}
.taskname {
  border-radius: 3rem;
  font-size: 0.8rem;
}
.kanbanboard-header{
  display: flex;
  justify-content: space-between;
  height: 10vh;
  padding: 2vh;
}
.kanbanboard-header > * {
  font-size: 7vh;
}
h1 {
  font-family: 'Mulish';
  font-size: 4vh;
  font-weight: 700;
  margin-left: 4vw;
  margin-top: 1vh;
  display: flex;
  text-align: start;
}
.kanban-button{
  display: flex;
  margin-top: 1vh;
  margin-right: 3vw;
}
.btn-primary {
  height: 4.5vh;
  width: 7vw;
  margin: 0 0.3vw;
  font-size: .8vmax;
}
.btn-primary > .icon {
  height: 2vh;
  width: 2vh;
}
.task-column{
  /* mx-2 w-100 bg-light-grey p-2 rounded-3 d-flex flex-column */
  display: flex;
  flex-direction: column;
  width: 100%;
  border-radius: 0.5rem;
  background-color: #F8F8F8;
  padding: 1vh;
  margin-inline: 0.5vw;
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
.kanban-card {
  cursor: pointer;
}
.kanban-card:hover {
  transition-duration: 0.1s;
  background-color: rgba(238, 238, 238, 0.301);
}
</style>
