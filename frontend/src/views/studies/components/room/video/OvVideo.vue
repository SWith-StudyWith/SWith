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
	updated () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>

<style scoped>
.main {
  width: 1080px;
  border-radius: 0.5rem;
}
.sub {
  width: 300px;
  height: 200px;
  overflow: hidden;
  object-fit: cover;
  border-radius: 1rem;
}
</style>
