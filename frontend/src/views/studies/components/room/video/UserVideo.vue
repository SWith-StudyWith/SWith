<template>
  <div v-if="streamManager" id="UserVideo">
    <OvVideo :stream-manager="streamManager" :isSpeak="isSpeak" :mainStream="mainStream"/>
    <p class="user-name">{{ clientData }}</p>
  </div>
</template>

<script>
import OvVideo from "@/views/studies/components/room/video/OvVideo.vue";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
    mainStream: Boolean,
    isSpeak: Boolean
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    }
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      // console.log("메인 비디오로 변경!!");
      // console.log(connection);
      return JSON.parse(connection.data);
    }
  }
};
</script>

<style scoped>
#UserVideo {
  margin: 0;
}
.user-name{
  margin-bottom: 0;
  font-size: 1.8vh;
}
</style>
