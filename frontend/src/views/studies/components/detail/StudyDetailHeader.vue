<template>
  <div>
    <div class="text-center">
      <span class="fs-2 me-2 fw-bold">{{ this.studyInfo.studyName }}</span>
      <router-link :to="{ name: 'StudyUpdate', params: this.$route.params }"><i class="fas fa-pen"></i></router-link>
    </div>
    <div class="align-middle">
      <span class="text-muted pe-3">목표 </span>
      <span class="fs-5">{{ this.studyInfo.studyGoal }}</span>
    </div>
    <div class="input-group-text p-0 mb-2">
      <div class="text-center w-100 bg-light py-1">
        {{ this.studyInfo.studyCode }}
      </div>
      <a tabindex="0" class="copy-btn text-center border border-top-0 border-end-0 border-bottom-0 px-2 fs-5"
        data-bs-container="body" data-bs-trigger="focus" data-bs-toggle="popover" data-bs-placement="top" data-bs-content="copyed!"
        @click="copyCode"
      >
        <i class="fas fa-copy"></i>
      </a>
    </div>
  </div>
</template>
<script>
import { Popover } from 'bootstrap';

export default {
  name: 'StudyDetailHeader',
  components: {},
  props: {
    studyInfo: Object,
  },
  setup(props) {
    // 스터디 url 복사
    const copyCode = function () {
      navigator.clipboard.writeText(props.studyInfo.studyCode)
    }
    return { copyCode };
  },
  created() {},
  mounted() {
    Array.from(document.querySelectorAll('[data-bs-toggle="popover"]'))
    .forEach(popoverNode => new Popover(popoverNode))
  },
  unmounted() {},
  methods: {}
}
</script>
<style scoped>
.code-container {
  border: solid #ced4da 1px;
  border-radius: 3px;
}
</style>
