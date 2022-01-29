<template>
  <div @click="onClickCard"
    class="bg-white card-shadow rounded p-2 text-start align-top border border-white"
    data-bs-toggle="modal" data-bs-target="#kanbanCardUpdateModal"
  >
    <span class="d-inline-block font-small">{{ task.content }}</span>
  </div>
</template>
<script>
export default {
  name: 'StudyDetail',
  components: {
  },
  props: {
    task: Object
  },
  emits: ['onClickCard'],
  setup(props, { emit }) {
    const onClickCard = function() {
      emit('onClickCard', props.task)
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
.font-small {
  font-size: 0.9rem;
}
</style>
