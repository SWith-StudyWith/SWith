<template>
  <video v-if="mainStream" class="main" autoplay />
  <video v-else class="sub" :class="{'video-isSpeak': isSpeak}" autoplay />
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
    mainStream: Boolean,
    isSpeak: Boolean,
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
  border-radius: 0.7rem;
}
.sub {
  width: 300px;
  height: 200px;
  overflow: hidden;
  object-fit: cover;
  border-radius: 1rem;
}
.video-isSpeak {
  border: solid 2.5px rgb(0, 242, 250);
  border-radius: 1rem;
}
</style>
