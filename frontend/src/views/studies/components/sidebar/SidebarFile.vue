<template>
  <div>
    <h1>DropZone</h1>
    <DropZone @drop.prevent="drop" @change="selectedFile" />
    <div v-for="(dropzoneFile, index) in dropzoneFiles" v-bind:key="dropzoneFile.id">
      <span class="file-info">File: {{dropzoneFile.name}}</span>
      <button @click="deleteFile(index)">삭제</button>
    </div>
    <button @click="uploadFile">전송</button>
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
    let dropzoneFiles = ref([]);

    const drop = (e) => {
      // dropzoneFiles.value = e.dataTransfer.files;
      for (let i = 0; i < e.dataTransfer.files.length; i++) {
        dropzoneFiles.value.push(e.dataTransfer.files[i]);
      }
    };

    const selectedFile = () => {
      // dropzoneFiles.value = document.querySelector('.dropzoneFile').files;
      let files = document.querySelector('.dropzoneFile').files;
      for (let i = 0; i < files.length; i++) {
        dropzoneFiles.value.push(files[i]);
      }
    }

    const deleteFile = (index) => {
      dropzoneFiles.value.splice(index, 1);
    }

    const uploadFile = () => {

    }

    return { dropzoneFiles, drop, selectedFile, deleteFile, uploadFile };
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
