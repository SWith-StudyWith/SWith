<template>
  <div class="screenshare">
    <h1>메인스크린 컴포넌트</h1>
    <div id="main-video">
      <user-video
        :stream-manager="streamManager"
        :mainStream="true"
        v-on:dblclick="deleteMainVideoStreamManager"
      />
    </div>
  </div>
</template>

<script>
import UserVideo from "@/views/studies/components/room/video//UserVideo.vue";

export default {
  components: {
    UserVideo,
  },
  props: {
    streamManager: Object,
  },
  setup() {
  }
}
</script>
