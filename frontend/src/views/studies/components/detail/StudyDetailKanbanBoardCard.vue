<template>
  <div @click="onClickCard"
    class="StudyDetailKanbanBoardCard user-select-none "
    data-bs-toggle="modal" data-bs-target="#kanbanCardModal"
  >
    <p class="card-body-data-content font-small mb-0">{{ task.content }}</p>
  </div>
</template>
<script>
export default {
  name: 'StudyDetailKanbanBoard',
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
.StudyDetailKanbanBoardCard {
  background-color: white;
  padding: 8px;
  margin: 0 8px 8px 0;
  text-align: start;
  border: solid 1.5px #DDDDDD;
  border-radius: 0.4rem;
  box-shadow: 1px 1px .4em #DDDDDD;
  overflow : hidden;
  /* white-space: nowrap; */
  /* cursor: pointer; */
}
.StudyDetailKanbanBoardCard:hover {
  transition-duration: 0.1s;
  background-color: rgba(238, 238, 238, 0.301);

}

.card-body-data-content {
  overflow : hidden;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  /* min-height: 2vh; */
  word-break: break-all;
  line-height: 1.3rem;
  max-height: 3.9rem;
}

.font-small {
  font-size: 0.9rem;
}

</style>
