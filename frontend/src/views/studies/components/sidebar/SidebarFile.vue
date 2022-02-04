<template>
  <div>
    <h1>DropZone</h1>
    <DropZone @drop.prevent="drop" @change="selectedFile" />
    <div v-for="dropzoneFile in dropzoneFiles" v-bind:key="dropzoneFile.id">
      <span class="file-info">File: {{dropzoneFile.name}}</span>
    </div>

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
    let dropzoneFiles = ref("");

    const drop = (e) => {
      dropzoneFiles.value = e.dataTransfer.files;
    };

    const selectedFile = () => {
      dropzoneFiles.value = document.querySelector('.dropzoneFile').files;
      console.log(dropzoneFiles.value);
    }

    return { dropzoneFiles, drop, selectedFile };
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
