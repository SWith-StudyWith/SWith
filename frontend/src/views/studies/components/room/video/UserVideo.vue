<template>
  <div v-if="streamManager" id="UserVideo">
    <!-- <OvVideo :stream-manager="streamManager" :mainStream="mainStream" :is-speak="isSpeak"/> -->
    <OvVideo :stream-manager="streamManager" :is-speak="isSpeak" :mainStream="mainStream"/>
    <p style="margin:0px;">{{ clientData }}</p>
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
</style>
