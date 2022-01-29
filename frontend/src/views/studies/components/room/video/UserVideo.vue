<template>
  <div v-if="streamManager">
    <OvVideo :stream-manager="streamManager" :mainStream="mainStream" />
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
    mainStream: Boolean
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
  display: inline-block;
  border: 1px black solid;
  margin: 5px;

} */
</style>
