<template>
  <!-- Modal -->
  <div class="modal fade" id="signOutModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm modal-dialog-centered">
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
          <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="onClickSignOut">탈퇴 하기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  name: 'SignOut',
  setup() {
    const store = useStore()
    const router = useRouter()
    const onClickSignOut = function (e) {
      e.preventDefault();
      console.log(e)
      console.log('버튼 클릭')
      store.dispatch('SIGNOUT')

      router.push({ name: 'Login' })
    }
    return { onClickSignOut }
  }
}

</script>
<style scoped>
</style>
