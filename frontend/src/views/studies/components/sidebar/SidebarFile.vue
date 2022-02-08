<template>
  <!-- <div> -->
    <!-- <div v-for="(dropzoneFile, index) in dropzoneFiles" v-bind:key="dropzoneFile.id" class="file-item">
      <span class="file-info"><img class="file-type" src="@/assets/img/icon_sidebar/file/file-type-img-DEE8F9.svg" alt=""> {{dropzoneFile.name}} </span>
      <img class="file-type" @click="deleteFile(index)" src="@/assets/img/icon_sidebar/file/trash-DEE8F9.svg" alt="">
    </div>
    <div class="file-submit">
      <img class="file-submit-icon" @click="uploadFile" src="@/assets/img/icon_sidebar/file/check-DEE8F9.svg" alt="">
      <span @click="uploadFile"> submit</span>
    </div>
    <DropZone @drop.prevent="drop" @change="selectedFile" /> -->

  <div class="fileDiv">
    <!-- <div v-if="files.length" >
      <div class="row" v-for="file in state.fileList" :key="file.memberId">
        <div class="col-4">
          <span>파일명</span>
          <span>파일크기</span>
          <span>생성일자</span>
        </div>
        <div class="col-8">
          <button>다운로드</button>
        </div>
      </div>
    </div> -->
    <!-- <h1>DropZone</h1> -->
    <div v-for="(dropzoneFile, index) in dropzoneFiles" v-bind:key="dropzoneFile.id" class="file-item">
      <span class="file-info"><img class="file-type" src="@/assets/img/icon_sidebar/file/file-type-img-DEE8F9.svg" alt=""> {{dropzoneFile.name}} </span>
      <img class="file-type" @click="deleteFile(index)" src="@/assets/img/icon_sidebar/file/trash-DEE8F9.svg" alt="">
    </div>
    <!-- <button @click="uploadFile"><img src="@/assets/img/icon_sidebar/file/check-DEE8F9.svg" alt="">전송</button> -->
    <div class="file-submit">
      <img class="file-submit-icon" @click="uploadFile" src="@/assets/img/icon_sidebar/file/check-DEE8F9.svg" alt="">
      <span @click="uploadFile"> submit</span>
    </div>
    <DropZone @drop.prevent="drop" @change="selectedFile" />
  </div>
</template>

<script>
import DropZone from '@/views/studies/components/sidebar/SidebarFileDropzone.vue';
import { ref } from "vue";
import { uploadFile } from '@/api/study';
import { computed, reactive } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import notifications from '@/composables/notifications'

export default {
  name: "SidebarFile",
  components: {
    DropZone,
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    store.dispatch('GET_FILE_LIST', route.params.studyId);
    const state = reactive({
      fileList : computed(() => {
        return store.state.study.fileList;
      }),
    })
    const { notifyWarning, notifySuccess } = notifications();

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
              notifySuccess('파일 업로드 완료')
              break;
            case 400:
              notifyWarning('파일 업로드 실패')
              break;
          }
          store.dispatch('GET_FILE_LIST', route.params.studyId);
        },
        (err) => {
          console.log(err)
          notifyWarning('서버가 아파요.😰')
        },
      )
    }

    return {
      state,
      dropzoneFiles,
      drop,
      selectedFile,
      onClickDeleteFile,
      onClickUploadFile,
      notifyWarning,
      notifySuccess,
    };
  },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Mulish:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Alef&display=swap');

.home {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f1f1f1;
  font-family:  'Noto Sans KR', 'Mulish';
}

.fileDiv {
  width: 100%
}

/* .home h1 {
  font-size: 40px;
  margin-bottom: 32px;
} */

.file-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;

  padding: 5px;
}

.file-info {
  font-size: 13px;
  padding: 2.5px;
  font-family: 'Mulish';

}

.file-submit {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  font-size: 13px;
  cursor: pointer;
  padding: 10px;
}

.file-submit-icon {
  margin-right: 3px;
}

</style>
