<template>
    <!-- class="bg-white card-shadow rounded p-2 text-start align-top border border-white" -->
  <div @click="onClickCard"
    data-bs-toggle="modal" data-bs-target="#kanbanCardUpdateModal"
  >
    <span class="d-inline-block">{{ task.content }}</span>
  </div>
</template>

<script>
export default {
  name: 'KanbanBoardCard',
  components: {
  },
  props: {
    task: Object,
    taskName: String,
  },
  emits: ['onClickCard'],
  setup(props, { emit }) {
    const onClickCard = function() {
      emit('onClickCard', { content: props.task.content, kanbanId: props.task.kanbanId, taskName: props.taskName })
    };
    return { onClickCard };
  }
};
</script>

<style scoped>
span {
  display: -webkit-box;
  text-overflow: ellipsis;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.2rem;
  max-height: 2.4rem;
}
.card-shadow {
  box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
}
</style>
