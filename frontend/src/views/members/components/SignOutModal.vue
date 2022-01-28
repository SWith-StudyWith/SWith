<template>
  <!-- Modal -->
  <div class="modal fade" id="signOutModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="signOutModalLabel">회원 탈퇴</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          정말 탈퇴하시겠어요? 😥
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">뒤로 가기</button>
          <button type="button" class="btn btn-primary" @click="onClickSignOut">탈퇴 하기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'SignOut',
}

</script>
<style scoped>
.modal-body{
  font-size: 20px;
}
</style>
