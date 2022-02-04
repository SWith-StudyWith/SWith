<template>
  <div>
    <h1>DropZone</h1>
    <DropZone @drop.prevent="drop" @change="selectedFile" />
    <span class="file-info">File: {{dropzoneFile.name}}</span>
  </div>
</template>

<script>
import DropZone from '@/views/studies/components/sidebar/SidebarFileDropzone.vue';
import { ref } from "vue";

export default {
  name: "SidebarFile",
  components: {
    DropZone,
  },
  setup() {
    let dropzoneFile = ref("");

    const drop = (e) => {
      dropzoneFile.value = e.dataTransfer.files[0]
    };

    const selectedFile = () => {
      dropzoneFile.value = document.querySelector('.dropzoneFile').files[0];
    }

    return { dropzoneFile, drop, selectedFile };
  },
}
</script>

<style scoped>
.home {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f1f1f1;
}
.home h1 {
  font-size: 40px;
  margin-bottom: 32px;
}
.home .file-info {
  margin-top: 32px;
}
</style>
