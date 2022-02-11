<template>
  <div class="modal fade" id="memoModal" tabindex="-1" aria-labelledby="memoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <!-- <h5 class="modal-title" id="memoModalLabel">Modal title</h5> -->
          <input id="red-radio" class="form-check-input d-none" name="color" type="radio" v-model="memoColor" value="0" @change="handleChangeColor">
          <label for="red-radio" class="red"></label>
          <input id="blue-radio" class="form-check-input d-none" name="color" type="radio" v-model="memoColor" value="1" @change="handleChangeColor">
          <label for="blue-radio" class="blue"></label>
          <input id="yellow-radio" class="form-check-input d-none" name="color" type="radio" v-model="memoColor" value="2" @change="handleChangeColor">
          <label for="yellow-radio" class="yellow"></label>

          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <textarea name="memoText" cols="30" rows="10" v-model="memoContent"></textarea>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary btn-sm" @click="onClickDelete" data-bs-dismiss="modal">
            <font-awesome-icon :icon="['fas', 'trash-alt']"></font-awesome-icon>
          </button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex';

export default {
  name: 'MemoModal',
  components: {},
  data() {
    return {
    }
  },
  unmounted() {},
  methods: {
    onClickDelete() {
      this.$store.commit('DELETE_SELECTED_MEMO', this.selectedIdx)
    }
  },
  computed: {
    ...mapState({
      selectedIdx: (state) => state.memo.selectedIdx,
    }),
    memoContent: {
      get () {
        if (!this.$store.state.memo.memoList || this.$store.state.memo.selectedIdx===undefined || this.$store.state.memo.selectedIdx === -1) {
          return null;
        }
        return this.$store.state.memo.memoList[this.$store.state.memo.selectedIdx].content;
      },
      set (content) {
        const index = this.$store.state.memo.selectedIdx;
        this.$store.commit('UPDATE_SELECTED_MEMO_CONTENT', { index, content })
      }
    },
    memoColor: {
      get () {
        if (!this.$store.state.memo.memoList || !this.$store.state.memo.selectedIdx || this.$store.state.memo.selectedIdx === -1) {
          return null;
        }
        return this.$store.state.memo.memoList[this.$store.state.memo.selectedIdx].color;
      },
      set (color) {
        const index = this.$store.state.memo.selectedIdx;
        this.$store.commit('UPDATE_SELECTED_MEMO_COLOR', { index, color })
      }
    }
  }
}
</script>
<style scoped>
.modal {
  z-index: 5000;
}
label {
  width: 1rem;
  height: 1rem;
}

.red {
  background-color: #F1D4D4;
}
.blue {
  background-color: #D4EAF1;
}
.yellow {
  background-color: #FFF9BE;
}
</style>
