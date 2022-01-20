<template>
  <div class="study-list">
    <div v-for="study in studies" :key="study.id">
      <StudyListItem :study="study" />
    </div>
  </div>
</template>

<script>
import StudyListItem from './StudyListItem.vue';

export default {
  props: ['studies'],
  components: { StudyListItem },
  setup(props) {
    console.log(props.studies);
  },
};
</script>
