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
        <p>
          수정 권한을 가져오면 수정 중이던 내용이 손상될 수 있습니다.
        </p>
        <p>
          그래도 가져오시겠습니까?
        </p>
        <div class="modal-footer">
          <button type="button" class="btn btn-danger btn-sm" data-bs-dismiss="modal" @click="onClickTakePermit">가져오기</button>
          <button type="button" class="btn btn-primary btn-sm" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { patchKanbanRight } from '@/api/study.js';
import { useRoute } from 'vue-router';

export default {
  name: 'KanbanWarningModal',
  props: {
    edittingUser: String,
  },
  components: {},
  setup(props, { emit }) {
    const route = useRoute();
    const onClickTakePermit = async function () {
      await patchKanbanRight(
        route.params.studyId,
        (res) => {
          console.log(res.data)
          if (res.data.code === 200) {
            emit('isEditPermit', true);
          } else {
            console.log('에러 발생')
          }
        },
        (err) => {
          console.log(err)
        }
      )
    }
    return { onClickTakePermit }
  },
}
</script>
<style scoped>

</style>
