<template>
<div>
  <label for="dropzoneFile">
  <div
    @dragenter.prevent="toggleActive"
    @dragleave.prevent="toggleActive"
    @dragover.prevent
    @drop.prevent="toggleActive"
    :class="{ 'active-dropzone': active }"
    class="dropzone"
  >
    <span id="title">Drag & Drop</span>
    <p>or select files from device</p>
    <img src="@/assets/img/icon_sidebar/file/upload_icon.svg" alt="">
    <p>max. size : 30MB / upload : 5</p>
  </div>
  </label>
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


    return { active, toggleActive,    }
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Alef&display=swap');
.dropzone {
  width: 100%;
  height: 29vh;
  margin: 0vh 0vw 1vh 0;
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
  cursor: pointer;
}
.dropzone > p {
  font-size: 1.5vh;
  margin: 1vh;
  color: #474747;
}
label {
  display: flex;
}
.dropzone #title {
  font-size: 3.5vh;
}

img {
  height: 3.5vh;
  padding: 0;
}

.dropzone label {
  padding: 8px 12px;
  font-size: 1.7vh;
  color: #474747;
  /* background-color: #7285A6; */
  transition: 0.3s ease all;
  cursor: pointer;
}
input {

  display: none;
}

.active-dropzone {
  color: #fff;
  border-color: #fff;
  background-color: #7285A6;
}

.active-dropzone label {
  /* background-color: #fff; */
  color: #474747;;
}


</style>
