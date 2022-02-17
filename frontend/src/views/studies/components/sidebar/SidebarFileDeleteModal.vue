<template>
  <!-- Modal -->
  <div class="modal fade" id="fileDeleteModal" tabindex="-1" aria-labelledby="fileDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">파일 삭제</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          정말로 파일을 삭제하시겠습니까?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">취소</button>
          <button type="button" class="btn btn-primary btn-sm" data-bs-dismiss="modal" @click="onClickDeleteConfirmBtn">삭제하기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { reactive, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router'
import { deleteFile } from '@/api/study';
import notifications from '@/composables/notifications'

export default {
  name: 'SidebarFileDeleteModal',
  components: {},
  props: {
    fileId: Number,
  },
  setup() {
    const store = useStore();
    const state = reactive({
      studyId: computed(() => {
        return store.state.study.studyInfo.studyId
      })
    })
    const { notifySuccess, notifyDanger } = notifications();

    const onClickDeleteConfirmBtn = function () {
      deleteFile(
        state.studyId,
        this.fileId,
        (res) => {
          switch (res.data.code) {
            case 200:
              notifySuccess('스터디 파일 삭제 성공')
              break;
            case 400:
              notifyDanger('스터디 파일 삭제 실패')
              break;
          }
          // store.dispatch('GET_FILE_LIST', route.params.studyId);
        },
        (err) => {
          console.log(err)
          notifyDanger('서버에 문제가 발생했습니다.😰')
        },
      )
    }
    return { state, onClickDeleteConfirmBtn }
  },
}
</script>
<style scoped>

</style>
