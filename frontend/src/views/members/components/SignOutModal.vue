<template>
    <loading v-model:active="state.loading"
          :can-cancel="false"
          :is-full-page="true"
          :height="height"
          :width="width"
          :color="color"
          :loader="loader"
          :background-color="bgColor"
          :opacity="opacity"
          :lock-scroll="false"
          class="vld-overlay"
          :style="state.loading ? '-webkit-backdrop-filter: blur(2px); backdrop-filter: blur(2px);' : ''"
  ></loading>
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
          <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">뒤로 가기</button>
          <button type="button" class="btn btn-primary btn-sm" data-bs-dismiss="modal" @click="onClickSignOut">탈퇴 하기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import Loading from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/vue-loading.css';

export default {
  name: 'SignOut',
    data() {
    return {
      loader: 'dots',
      color: '#334466',
      bgColor: 'white',
      height: 120,
      width: 120,
      opacity: 0.2,
      lockScroll: true,
    }
  },
  components: {
    Loading,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const state = ref({
      loading: false,
    })
    const onClickSignOut = function (e) {
      e.preventDefault();
      loadingCall()
      store.dispatch('SIGNOUT')
      router.push({ name: 'Login' })
    }

    function loadingCall(){
      state.value.loading = true
      setTimeout(() => {
        state.value.loading = false
      }, 1200)
    }
    return { state, onClickSignOut, loadingCall }
  }
}

</script>
<style scoped>
</style>
