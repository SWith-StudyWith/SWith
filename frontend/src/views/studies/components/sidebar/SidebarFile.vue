<template>
  <div class="fileDiv">
    <p class="title">📑 첨부 파일 목록</p>
    <!-- <SidebarFileList /> -->
    <div class="file-body">
      <!-- <p>SidebarFile.vue - file-body</p> -->

      <div class="card">
        <div v-if="state.fileList" >
                  <div class="card-body" v-for="file in state.fileList" :key="file.memberId">
                    <div class="card-body-data">
                      <p class="card-body-data-name"><b>{{ file.originName }}</b></p>
                      <p class="card-body-data-size">size | {{ file.fileSize }}</p>
                      <p class="card-body-data-createdAt">createdAt | {{ file.createdAt }}</p>
                    </div>
                    <!-- <button>다운로드</button> -->
                    <!-- <img class="file-type" @click="onClickCancelFile(index)" src="@/assets/img/icon_sidebar/file/trash-1E304F.svg" alt=""> -->
                    <div class="card-body-buttons">
                      <img
                        src="@/assets/img/icon_sidebar/file/download_1E304F.svg"
                        @click="onClickDownloadFile(file.fileId, file.originName)">
                      <img
                        src="@/assets/img/icon_sidebar/file/trash-1E304F.svg"
                        @click="onClickDeleteFile(file.fileId)">
                    </div>
                  </div>
        </div>
        <div v-else>
          <p>등록된 파일이 없습니다.</p>
        </div>
      </div>

    </div>
    <form enctype="multipart/form-data">
      <!-- <span>fileList : {{ state.fileList.createdAt }}</span> -->
      <!-- <span>dropzoneFiles : {{ dropzoneFiles }}</span> -->
      <div v-for="(dropzoneFile, index) in dropzoneFiles" v-bind:key="dropzoneFile.id" class="file-item">
        <span class="file-info"><img class="file-type" src="@/assets/img/icon_sidebar/file/clip_light.svg" alt=""> {{dropzoneFile.name}} </span>
        <img class="file-type" @click="onClickCancelFile(index)" src="@/assets/img/icon_sidebar/file/trash-DEE8F9.svg" alt="">
      </div>
      <!-- <button @click="uploadFile"><img src="@/assets/img/icon_sidebar/file/check-DEE8F9.svg" alt="">전송</button> -->
      <div class="file-submit">
        <img class="file-submit-icon" @click="onClickUploadFile" src="@/assets/img/icon_sidebar/file/check-DEE8F9.svg" alt="">
        <span @click="onClickUploadFile"> submit</span>
      </div>
      <DropZone @drop.prevent="drop" @change="selectedFile" />
    </form>
  </div>
</template>

<script>
import { ref } from "vue";
import { uploadFile, downloadFile, deleteFile } from '@/api/study';
import { computed, reactive } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import notifications from '@/composables/notifications'
import DropZone from '@/views/studies/components/sidebar/SidebarFileDropzone.vue';
// import SidebarFileList from '@/views/studies/components/sidebar/SidebarFileList.vue';

export default {
  name: "SidebarFile",
  components: {
    DropZone,
    // SidebarFileList,
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

    const onClickCancelFile = (index) => {
      dropzoneFiles.value.splice(index, 1);
    }

    const onClickUploadFile = (e) => {
      e.preventDefault();
      const uploadFileData = new FormData();
      console.log('여기까지 오나');
        console.log(dropzoneFiles.value.length);
      for (let i = 0; i < dropzoneFiles.value.length; i++) {
        uploadFileData.append("files", dropzoneFiles.value[i]);
      }

      uploadFile(
        route.params.studyId,
        uploadFileData,
        (res) => {
          console.log(res.data)
          switch (res.data.code) {
            case 200:
              alert('스터디 파일 업로드 성공')
              break;
            case 400:
              alert('스터디 파일 업로드 실패')
              break;
          }
          dropzoneFiles.value = [];
          store.dispatch('GET_FILE_LIST', route.params.studyId);
        },
        (err) => {
          console.log(err)
          notifyWarning('서버가 아파요.😰')
        },
      )
    }

    const onClickDownloadFile = (fileId, fileName) => {
      downloadFile(
        route.params.studyId,
        fileId,
        (res) => {
          console.log(res.data)
          const url = window.URL.createObjectURL(new Blob([res.data]));
          const link = document.createElement('a');
          link.href = url;
          link.setAttribute('download', fileName);
          document.body.appendChild(link);
          link.click();
        },
        (err) => {
          console.log(err)
          notifyWarning('서버가 아파요.😰')
        },
      )
    }

    const onClickDeleteFile = (fileId) => {
      deleteFile(
        route.params.studyId,
        fileId,
        (res) => {
          console.log(res.data);
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
      onClickCancelFile,
      onClickUploadFile,
      onClickDownloadFile,
      onClickDeleteFile,
      notifyWarning,
      notifySuccess,
    };
  },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Mulish:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Alef&display=swap');

/* .home {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f1f1f1;
  font-family:  'Noto Sans KR', 'Mulish';
} */

.fileDiv {
  height: 100vh;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  /* align-items: center; */
  /* background-color: #f1f1f1; */
  font-family:  'Noto Sans KR', 'Mulish';
  width: 100%;
  text-align: left;


  /* scroll */
}

.title{
  font-size: 25px;
  font-weight:500;
  margin-top: 40px;
  margin-bottom: 30px;
  /* position: fixed; */
}

/* .home h1 {
  font-size: 40px;
  margin-bottom: 32px;
} */

.file-body {
  flex-grow: 1;
  /* overflow: auto; */
  /* padding: 1rem; */
  overflow-x: hidden;
  overflow-y: scroll;
  scroll-behavior: smooth;
  width: 100%;
}
/* .file-body::-webkit-scrollbar {
  display: none;
} */
::-webkit-scrollbar{
  /* opacity: 0.7; */
  width: 12px;
}
::-webkit-scrollbar-thumb{
    background-color: #999;
    border-radius: 10px;
}
::-webkit-scrollbar-track{
    background-color: #1E304F;
}
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

.card {
  display: flex;
  /* align-items: row; */
  color: black;
  /* background-color: antiquewhite; */
  background-color: #1E304F;
  margin-top: 2px;
  /* opacity: 0.7; */
}

.card-body {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  /* padding: 10px; */
  /* margin-bottom: 5px; */
  background-color: antiquewhite;
  border: solid 4px #ffffff;
  margin-top: 5px;
  border-radius: 24px;
  opacity: 0.85;

  font-family: 'Mulish', 'Alef', 'Noto Sans KR';
  color: 1E304F;
  font-size: 13px;
}

.card-body-buttons {
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-items: center;
}

.card-body-data {
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  align-items: flex-start;
  /* margin-bottom: 0px; */
}

.card-body-data-name {
  font-size: 14px;
  margin: 0;
}

.card-body-data-size {
  /* color: #9c9c9c; */
  margin: 0;
  font-size: 12px;
}

.card-body-data-createdAt {
  /* color: #9c9c9c; */
  margin: 0;
  font-size: 12px;
}
</style>
