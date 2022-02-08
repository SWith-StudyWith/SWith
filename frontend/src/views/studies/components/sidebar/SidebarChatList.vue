<template>
  <div class="chat-body" id="chat-body">
    <SidebarChatMessage
      v-for="(chat, idx) in chatList"
      :key="idx"
      :chat = "chat"
      :prev="[idx == 0 ? null : chatList[idx-1]]"
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
  props: {
    chatList: Object
  },
  data() {
    return {
      sampleData: '',
    };
  },

}
</script>

<style>

</style>
