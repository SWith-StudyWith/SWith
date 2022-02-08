<template>
  <span class="bg-primary fs-4 px-2 rounded text-light align-middle user-select-none">
    <font-awesome-icon class="fs-5" :icon="['fas', 'hourglass-half']"></font-awesome-icon>
    {{ parsingTime }}
  </span>
</template>
<script>
import { ref, computed, reactive } from 'vue';

export default {
  name: 'Timer',
  setup(props, { emit }) {
    const time = ref(300);
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

</style>
