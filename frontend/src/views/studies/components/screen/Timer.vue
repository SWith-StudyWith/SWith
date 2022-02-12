<template>
  <span class="btn btn-primary">
    <font-awesome-icon class="time" :icon="['fas', 'hourglass-half']"></font-awesome-icon>
    {{ parsingTime }}
  </span>
</template>
<script>
import { ref, computed, reactive, onBeforeUnmount } from 'vue';

export default {
  name: 'Timer',
  setup(props, { emit }) {
    const time = ref(10);
    const timer = reactive({
      timer: null,
    })
    const startTimer = function () {
      timer.timer = setInterval(
        () => countDown(),
        1000
      )
    }
    const countDown = function () {
      if (time.value >= 1) {
        time.value--;
      } else {
        time.value = 0;
        stopTimer()
      }
    }
    const stopTimer = function () {
      console.log('끝나따!')
      emit('timeStopped')
      clearInterval(timer.timer)
    }
    onBeforeUnmount(() => clearInterval(timer.timer))
    const parsingTime = computed(
      () => {
        const minute = parseInt(time.value / 60);
        const second = time.value % 60;
        const parsing = (num) => (num < 10) ? `0${num}` : `${num}`
        return `${parsing(minute)} : ${parsing(second)}`;
      }
    )
    startTimer()
    return {
      time,
      startTimer,
      stopTimer,
      parsingTime,
     }
  },
}
</script>
<style scoped>
/* fs-5 px-2 py-1 rounded text-light align-middle user-select-none */
/* .bg-primary {
  height: 4.5vh;
  width: 7vw;
  font-size: .8vmax;
  padding: 1.1vh 0vw ;
  border-radius: 0.5vh;
  color: white;
  text-align: center;
  margin-right: 0.6vw;
} */

.btn-primary {
  height: 4.5vh;
  width: 7vw;
  font-size: .8vmax;
  cursor:text;
  margin-left: 0.3vw;
  margin-right: 0.3vw;
}
.btn-primary > .icon {
  height: 2vh;
  width: 2vh;
  padding: 1vh;
}
</style>
