<template>
  <div class="screenshare">
    <div id="main-video">
      <user-video
        :stream-manager="streamManager"
        :mainStream="true"
        @dblclick="$emit('deleteMainVideoStreamManager')"
      />
    </div>
  </div>
  <!-- 빈 공간 채우기 -->
  <div v-if="!streamManager" class="background">
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
}
</script>
<style scoped>
.background {
  position: fixed;
  height: 100vh;
  width: 100vw;
  background-color: #aebed4;
}
</style>


