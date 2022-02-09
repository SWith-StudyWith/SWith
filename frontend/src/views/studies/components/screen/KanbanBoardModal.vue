<template>
  <div class="modal fade" id="kanbanCardUpdateModal" tabindex="-1" aria-labelledby="kanbanCardUpdateModalLabel" aria-hidden="true" @click.self="closeInput">
    <div class="modal-dialog modal-dialog-centered modal-md">
      <div class="modal-content">
        <div v-if="!state.isEdit" class="modal-body text-start" @click="openInput">
          {{ selectedTask.value ? selectedTask.value.content : '' }}
        </div>
        <div v-else class="modal-body">
          <input type="text" ref="inputForm" class="form-control" maxlength="100" minlength="1" v-model="state.task.value.content" @keyup.enter="closeInput" @blur="closeInput">
        </div>
        <div class="modal-footer">
          <button v-if="editPermit" type="button" class="btn btn-primary btn-sm" @click="openInput">
            <font-awesome-icon  :icon="['fas', 'pen']"></font-awesome-icon>
          </button>
          <button v-if="editPermit" type="button" class="btn btn-primary btn-sm" @click="onClickDelete" data-bs-dismiss="modal">
            <font-awesome-icon :icon="['fas', 'trash-alt']"></font-awesome-icon>
          </button>
          <button type="button" class="btn btn-primary btn-sm" data-bs-dismiss="modal" @click="closeInput">
            <font-awesome-icon :icon="['fas', 'times']"></font-awesome-icon>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { reactive, ref } from 'vue';
import { Modal } from 'bootstrap';
import notifications from '@/composables/notifications'

export default {
  name: 'KanbanBoardModal',
  components: {},
  props: {
    selectedTask: Object,
    editPermit: Boolean,
  },
  emits: ['updateTask', 'deleteTask'],
  setup(props, { emit }) {
    const { notifyDanger } = notifications();
    const state = reactive({
      isEdit: false,
      task: props.selectedTask,
    })
    const closeInput = function() {
      state.isEdit = false;
      if (state.task.value.content) {
        emit('updateTask', state.task);
      } else {
        notifyDanger('입력 값이 없습니다.😥')
        return
      }
      let myModalEl = document.getElementById('kanbanCardUpdateModal');
      let modal = Modal.getInstance(myModalEl);
      modal.hide();
    }
    const inputForm = ref(null);
    const openInput = function() {
      if (!props.editPermit) {
        return;
      }
      state.isEdit = true;
      setTimeout(
        ()=>inputForm.value.focus(), 0
      )
    }
    const onClickDelete = function () {
      emit('deleteTask', state.task);
    }
    return { state, closeInput, openInput, onClickDelete, inputForm }
  },

}
</script>
<style scoped>
.modal-md {
  max-width: 400px;
}
</style>
