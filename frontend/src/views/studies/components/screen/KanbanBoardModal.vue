<template>
  <div class="modal fade" id="kanbanCardUpdateModal" tabindex="-1" aria-labelledby="kanbanCardUpdateModalLabel" aria-hidden="true" @click.self="closeInput">
    <div class="modal-dialog modal-dialog-centered modal-md">
      <div class="modal-content">
        <div v-if="!state.isEdit" class="modal-body text-start" @click="openInput">
          {{ selectedTask.value ? selectedTask.value.content : '' }}
        </div>
        <div v-else class="modal-body">
          <input type="text" class="form-control" v-model="state.task.value.content" @keyup.enter="closeInput" @blur="closeInput">
        </div>
        <div class="modal-footer">
          <button v-if="editPermit" type="button" class="btn btn-danger btn-sm">삭제하기</button>
          <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal" @click="closeInput">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { reactive } from 'vue'

export default {
  name: 'KanbanBoardModal',
  components: {},
  props: {
    selectedTask: Object,
    editPermit: Boolean,
  },
  emits: ['updateTask'],
  setup(props, { emit }) {
    const state = reactive({
      isEdit: false,
      task: props.selectedTask,
    })
    const closeInput = function() {
      state.isEdit = false;
      emit('updateTask', state.task)
    }
    const openInput = function() {
      if (!props.editPermit) {
        return
      }
      state.isEdit = true;
    }
    return { state, closeInput, openInput }
  },

}
</script>
<style scoped>
.modal-md {
  max-width: 400px;
}
</style>
