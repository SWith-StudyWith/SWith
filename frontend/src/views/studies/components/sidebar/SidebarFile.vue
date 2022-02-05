<template>
  <div>
    <div class="member-body">
    <div v-if="files.length" >
      <div class="row" v-for="file in files" :key="file.memberId">
        <div class="col-4">
        </div>
        <div class="col-8">
        </div>
      </div>
    </div>
    <h1>DropZone</h1>
    <DropZone @drop.prevent="drop" @change="selectedFile" />
    <div v-for="(dropzoneFile, index) in dropzoneFiles" v-bind:key="dropzoneFile.id">
      <span class="file-info">File: {{dropzoneFile.name}}</span>
      <button @click="onClickDeleteFile(index)">삭제</button>
    </div>
    <button @click="onClickUploadFile">전송</button>
  </div>
</template>

<script>
import DropZone from '@/views/studies/components/sidebar/SidebarFileDropzone.vue';
import { ref } from "vue";
import { uploadFile } from '@/api/study';

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

    const onClickDeleteFile = (index) => {
      dropzoneFiles.value.splice(index, 1);
    }

    const onClickUploadFile = (e) => {
      e.preventDefault();

      const uploadFileData = new FormData();
      for (let i = 0; i < dropzoneFiles.value.length; i++) {
        uploadFileData.append("studyFile", dropzoneFiles.value[i]);
      }

      uploadFile(
        uploadFileData,
        (res) => {
          console.log(res.data)
          switch (res.data.code) {
            case 200:
              alert('파일 업로드 완료')
              break;
            case 400:
              alert('파일 업로드 실패')
              break;
          }
        },
        (err) => {
          console.log(err)
          alert('서버가 아파유~')
        },
      )
    }

    return { dropzoneFiles, drop, selectedFile, onClickDeleteFile, onClickUploadFile };
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
