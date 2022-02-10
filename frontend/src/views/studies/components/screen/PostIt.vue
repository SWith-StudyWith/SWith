<template>
  <!-- <div class="back"> -->
    <div class="container">
      <div class="target">Vue Moveable</div>
        <Moveable
          className="moveable"
          v-bind:target="['.target']"
          v-bind:draggable="true"
          v-bind:scalable="true"
          v-bind:rotatable="true"
          @drag="onDrag"
          @scale="onScale"
          @rotate="onRotate"
        />
    </div>
  <!-- </div> -->
</template>
<script>
import Moveable from "vue3-moveable";

export default {
  name: 'PostIt',
  components: {
    Moveable,
  },
  data() {
    return {
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    onDrag({ target, transform }) {
      target.style.transform = transform;
    },
    onScale({ target, drag }) {
      target.style.transform = drag.transform;
    },
    onRotate({ target, drag }) {
      target.style.transform = drag.transform;
    },
  }
}
</script>
<style scoped>
.back {
  position: fixed;
  background-color: #aebed4;
  height: 100vh;
  width: 100vw;
  background-image: url("../../../../assets/img/study_room/paper_texture.png")
}

</style>
