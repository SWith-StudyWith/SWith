<template>
  <!-- Modal -->
  <div class="modal fade" id="KanbanWarningModal" tabindex="-1" aria-labelledby="KanbanWarningModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="KanbanWarningModalLabel">칸반 보드 수정 불가!</h5>
        </div>
        <div class="modal-body">
          <span class="fs-5 fw-bold">{{ edittingUser }} </span>
          님이 수정 중입니다.
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary btn-sm" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'KanbanWarningModal',
  props: {
    edittingUser: String,
  },
  components: {},
  setup() {},
}
</script>
<style scoped>

</style>
