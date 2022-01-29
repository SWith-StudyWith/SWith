<template>
  <video v-if="mainStream" class="main" autoplay />
  <video v-else class="sub" autoplay />
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
    mainStream: Boolean
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>

<style scoped>
.main {
  width: 960px;
  height: 600px;
}
.sub {
  width: 320px;
  height: 200px;
}
</style>
