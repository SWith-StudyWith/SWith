<template>
  <div
    @dragenter.prevent="toggleActive"
    @dragleave.prevent="toggleActive"
    @dragover.prevent
    @drop.prevent="toggleActive"
    :class="{ 'active-dropzone': active }"
    class="dropzone"
  >
    <span id="title">Drag & Drop</span>
    <!-- <span id="detail">or select files from device</span> -->
    <label for="dropzoneFile">or select files from device</label>
    <input type="file" multiple id="dropzoneFile" class="dropzoneFile" />
    <label for="dropzoneFile"><img src="@/assets/img/icon_sidebar/file/upload_icon.svg" alt=""></label>
    <input type="file" multiple id="dropzoneFile" class="dropzoneFile" />
    <!-- <img src="@/assets/img/icon_sidebar/upload_icon.svg" alt=""> -->
    <!-- <span id="detail">max. size : 30MB / upload : 5</span> -->
    <label for="dropzoneFile">max. size : 30MB / upload : 5</label>
    <input type="file" multiple id="dropzoneFile" class="dropzoneFile" />
  </div>
</template>

<script>
import { ref } from "vue";
export default {
  name: "SidebarFileDropzone",
  setup() {
    const active = ref(false);

    const toggleActive = () => {
      active.value = !active.value;
    };

    return { active, toggleActive }
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Alef&display=swap');
.dropzone {
  width: 100%;
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  row-gap: 1px;
  border: 5px dashed #ffffff;
  border-radius: 24px;
  background-color: #9EABCB;
  opacity: 0.7;
  transition: 0.3s ease all;
  font-family: 'Alef';
  color: #1E304F;
}

.dropzone #title {
  font-size: 24px;
}

.dropzone #detail {
  font-size: 13px;
  color: #474747;
}

.dropzone label {
  padding: 8px 12px;
  font-size: 13px;
  color: #474747;
  /* background-color: #7285A6; */
  transition: 0.3s ease all;
  cursor: pointer;
}
.dropzone input {
  display: none;
}

.active-dropzone {
  color: #fff;
  border-color: #fff;
  background-color: #7285A6;
}

.active-dropzone label {
  background-color: #fff;
  color: #7285A6;;
}

</style>
