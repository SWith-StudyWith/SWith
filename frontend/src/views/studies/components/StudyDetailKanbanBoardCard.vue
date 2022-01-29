<template>
  <div class="bg-white card-shadow rounded p-2 text-start align-top border border-white">
    <span class="font-small">{{ task.content }}</span>
  </div>
</template>
<script>
export default {
  name: 'StudyDetail',
  components: {
  },
  props: {
    task: Object
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
