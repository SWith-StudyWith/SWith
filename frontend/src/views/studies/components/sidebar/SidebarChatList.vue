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
/*  */
.chat-body{
  flex-grow: 1;
  /* overflow: auto; */
  padding: 1rem;

  overflow-y: scroll;
  scroll-behavior: smooth;
}
.chat-body::-webkit-scrollbar {
  /* display: none; */
}
::-webkit-scrollbar{
    width: 12px;
}
::-webkit-scrollbar-thumb{
    background-color: #999;
    border-radius: 10px;
}
::-webkit-scrollbar-track{
    background-color: #1E304F;
}
</style>
