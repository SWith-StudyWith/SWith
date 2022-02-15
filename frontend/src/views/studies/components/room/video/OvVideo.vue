<template>
  <video v-if="mainStream" class="main" autoplay :poster="defaultPosterSrc"/>
  <video v-else class="sub" :class="{'video-isSpeak': isSpeak}" autoplay :poster="defaultPosterSrc"/>
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
    mainStream: Boolean,
    isSpeak: Boolean,
	},
  computed: {
    defaultPosterSrc() {
      if (this.streamManager.stream.typeOfVideo === 'SCREEN') {
        return ''
      }
      return this.streamManager.accessAllow ? '' : require('@/assets/img/navbar/profile.png')
    }
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
  height: 70vh;
  width: 100%;
  border-radius: 0.7rem;
}
.sub {
  width: 29vh;
  height: 18vh;
  overflow: hidden;
  object-fit: cover;
  border-radius: 1rem;
}
.video-isSpeak {
  border: solid 2.5px rgb(0, 242, 250);
  border-radius: 1rem;
}
</style>
