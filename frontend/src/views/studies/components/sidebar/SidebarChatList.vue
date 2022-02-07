<template>
  <div class="chat-body" id="chat-body">
    <SidebarChatMessage
      v-for="(msg, idx) in msgs"
      :key="idx"
      :msg = "msg"
      :prev="[idx == 0 ? null : msgs[idx-1]]"
    >
    </SidebarChatMessage>
  </div>
</template>

<script>
import SidebarChatMessage from '@/views/studies/components/sidebar/SidebarChatMessage.vue';

export default {
  nane: '',
  components: {
    SidebarChatMessage
  },
  props: ["msgs"],
  data() {
    return {
      sampleData: '',
    };
  },

}
</script>

<style>

</style>
