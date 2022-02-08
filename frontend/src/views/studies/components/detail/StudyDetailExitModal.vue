<template>
  <!-- Modal -->
  <div class="modal fade" id="exitModal" tabindex="-1" aria-labelledby="exitModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">스터디 탈퇴</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          정말로 스터디를 탈퇴하시겠습니까?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-danger btn-sm" data-bs-dismiss="modal" @click="onClickExitConfirmBtn">탈퇴하기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { reactive, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router'
import { exitStudy } from '@/api/study';
import notifications from '@/composables/notifications'

export default {
  name: 'StudyDetailExitModal',
  components: {},
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      studyId: computed(() => {
        return store.state.study.studyInfo.studyId
      })
    })
    const { notifySuccess, notifyDanger } = notifications();

    const onClickExitConfirmBtn = function () {
      exitStudy(
        state.studyId,
        (res) => {
          console.log(res.data)
          if (res.data.code === 200) {
            notifySuccess('스터디를 탈퇴했습니다.🤤')
            router.push({ name: 'Main' })
          }
        },
        (err) => {
          console.log(err)
          notifyDanger('서버가 아파요 😰')
        }
      )
    }
    return { state, onClickExitConfirmBtn }
  },
}
</script>
<style scoped>

</style>
