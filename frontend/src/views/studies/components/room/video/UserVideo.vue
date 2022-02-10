<template>
  <div v-if="streamManager" id="UserVideo">
    <!-- <OvVideo :stream-manager="streamManager" :mainStream="mainStream" :is-speak="isSpeak"/> -->
    <OvVideo :stream-manager="streamManager" :isSpeak="isSpeak" :mainStream="mainStream"/>
    <p class="user-name">{{ clientData }}</p>
  </div>
<!-- <div id="UserVideo" v-if="streamManager">
	<ov-video :stream-manager="streamManager"/>
	<div id="UserName"><p>{{ clientData }}</p></div>
</div> -->
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
      console.log("메인 비디오로 변경!!");
      console.log(connection);
      return JSON.parse(connection.data);
    }
  }
};
</script>

<style>
/* #UserVideo {
  box-shadow:  0px 0px 10px rgb(0 255 0 / 1.0);
  border-radius: 1rem;
} */
#UserVideo {
  margin: 0;
}
.user-name{
  margin-bottom: 0;
  font-size: 1.8vh;
}
</style>
