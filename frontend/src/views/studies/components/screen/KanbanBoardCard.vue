<template>
  <div class="bg-white shadow rounded p-2">
    <p>{{task.title}}제목</p>
    <div>
      <span>{{task.content}}</span>
    </div>
  </div>
</template>

<script>
export default {
  components: {
  },
  props: {
    task: {
      type: Object,
      default: () => ({})
    }
  },
};
</script>
