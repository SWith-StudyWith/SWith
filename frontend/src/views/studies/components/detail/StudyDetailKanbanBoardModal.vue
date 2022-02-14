<template>
  <div>
    <div class="modal fade" id="kanbanCardModal" tabindex="-1" aria-labelledby="kanbanCardUpdateModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-sm">
        <div class="modal-content">
          <div class="modal-body text-break">
            {{ selectedTask.value ? selectedTask.value.content : '' }}
          </div>
          <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'StudyDetailKanbanBoardModal',
  components: {},
  props: {
    selectedTask: Object
  }
}
</script>
<style scoped>

</style>
