<template>
  <!-- Modal -->
  <div class="modal fade" id="exitModal" tabindex="-1" aria-labelledby="exitModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm modal-dialog-centered">
      <div class="modal-content">
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
import { reactive } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router'
import { exitStudy } from '@/api/study';

export default {
  name: 'StudyMainExitModal',
  components: {},
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      studyId: store.state.study.studyInfo.studyId
    })
    const onClickExitConfirmBtn = function () {
      exitStudy(
        state.studyId,
        (res) => {
          console.log(res.data)
          if (res.data.data.code === 200) {
            router.push({ name: 'Main' })
          }
        },
        (err) => {
          console.log(err)
        }
      )
    }
    return { state, onClickExitConfirmBtn }
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
<style scoped>

</style>
