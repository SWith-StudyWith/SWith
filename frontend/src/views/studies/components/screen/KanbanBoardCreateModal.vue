<template>
  <div class="modal fade" id="kanbanCardCreateModal" tabindex="-1" aria-labelledby="kanbanCardCreateModalLabel" aria-hidden="true" @click.self="onCloseModal">
    <div class="modal-dialog modal-dialog-centered modal-md">
      <div class="modal-content">
        <div class="modal-body">
          <input type="text" ref="inputForm" v-model="state.content" @keypress.enter="onClickCreate" class="form-control" maxlength="100" minlength="1">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary btn-sm" @click="onClickCreate" data-bs-dismiss="modal">
            <font-awesome-icon :icon="['fas', 'check']"></font-awesome-icon>
          </button>
          <button type="button" class="btn btn-primary btn-sm" data-bs-dismiss="modal" @click="onCloseModal">
            <font-awesome-icon :icon="['fas', 'times']"></font-awesome-icon>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { reactive } from 'vue';
import { Modal } from 'bootstrap';
import notifications from '@/composables/notifications'

export default {
  name: 'KanbanBoardCreateModal',
  components: {},
  props: {
    taskId: Number,
  },
  emits: ['createTask'],
  setup(props, { emit }) {
    const { notifyDanger } = notifications();
    const state = reactive({
      content: '',
    })
    const onCloseModal = function() {
      state.content = '';
    }
    const onClickCreate = function () {
      if (state.content) {
        emit('createTask', { taskId: props.taskId, content: state.content, kanbanId: Date.now() })
      } else {
        notifyDanger('입력 값이 없습니다.😥')
      }
      state.content = '';
      let myModalEl = document.getElementById('kanbanCardCreateModal')
      let modal = Modal.getInstance(myModalEl)
      modal.hide()
    }
    return { state, onCloseModal, onClickCreate }
  },
}
</script>
<style scoped>
.modal-md {
  max-width: 400px;
}
</style>
