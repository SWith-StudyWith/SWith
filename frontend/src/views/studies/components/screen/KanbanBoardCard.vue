<template>
  <div class="bg-white card-shadow rounded p-2 text-start align-top border border-white">
    <!-- <p>{{task.title}}제목</p> -->
    <div>
      <span class="font-small">{{ task.content }}</span>
    </div>
  </div>
</template>

<script>
export default {
  components: {
  },
  props: {
    task: Object
    // task: {
    //   type: Object,
    //   default: () => ({})
    // }
  },
};
</script>

<style scoped>
.card-shadow {
  box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
}
.font-small {
  font-size: 0.9rem;
}
</style>
