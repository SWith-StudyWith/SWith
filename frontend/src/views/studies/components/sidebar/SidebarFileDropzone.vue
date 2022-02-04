<template>
  <div
    @dragenter.prevent="toggleActive"
    @dragleave.prevent="toggleActive"
    @dragover.prevent
    @drop.prevent="toggleActive"
    :class="{ 'active-dropzone': active }"
    class="dropzone"
  >
    <span>Drag or Drop File</span>
    <span>OR</span>
    <label for="dropzoneFile">Select File</label>
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
.dropzone {
  width: 100%;
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  row-gap: 16px;
  border: 2px dashed #7285A6;
  background-color: #fff;
  transition: 0.3s ease all;
}
.dropzone label {
  padding: 8px 12px;
  color: #fff;
  background-color: #7285A6;
  transition: 0.3s ease all;
}
.dropzone input {
  display: none;
}

.active-dropzone {
  color: #fff;
  border-color: #fff;
  background-color: #7285A6;;
}
.active-dropzone label {
  background-color: #fff;
  color: #7285A6;;
}
</style>
