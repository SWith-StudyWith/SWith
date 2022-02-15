<template>
    <!-- class="bg-white card-shadow rounded p-2 text-start align-top border border-white" -->
  <div @click="onClickCard" class="user-select-none overflow-hidden"
    data-bs-toggle="modal" data-bs-target="#kanbanCardUpdateModal"
  >
    <p class="mb-0">{{ task.content }}</p>
  </div>
</template>

<script>
export default {
  name: 'KanbanBoardCard',
  components: {
  },
  props: {
    task: Object,
    taskId: Number,
  },
  emits: ['onClickCard'],
  setup(props, { emit }) {
    const onClickCard = function() {
      emit('onClickCard', { content: props.task.content, kanbanId: props.task.kanbanId, taskId: props.taskId })
    };
    return { onClickCard };
  }
};
</script>

<style scoped>
p {
  overflow : hidden;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  /* min-height: 2vh; */
  word-break: break-all;
  line-height: 1.3rem;
  max-height: 3.9rem;
}
.card-shadow {
  box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
}
</style>
