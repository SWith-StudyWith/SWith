<template>
  <div @click="onClickCard"
    class="StudyDetailKanbanBoardCard user-select-none"
    data-bs-toggle="modal" data-bs-target="#kanbanCardModal"
  >
    <span class="card-body-data-content font-small" text-truncate>{{ task.content }}</span>
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
  /* cursor: pointer; */
}
.StudyDetailKanbanBoardCard:hover {
  transition-duration: 0.1s;
  background-color: rgba(238, 238, 238, 0.301);
  display: flex;
  flex-wrap: nowrap;
}

.card-body-data-content {
  overflow : hidden;
  text-overflow : ellipsis;
  word-wrap : brek-word;
  display : -webkit-box;
  -webkit-line-clamp : 2;
  -webkit-box-orient: vertical;
  min-height: 2vh;
}

.font-small {
  font-size: 0.9rem;
}

</style>
