<template>
  <div class="modal fade" id="memoModal" tabindex="-1" aria-labelledby="memoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <input id="red-radio" class="form-check-input d-none" name="color" type="radio" v-model="memoColor" value="0">
          <label for="red-radio" class="red"></label>
          <input id="blue-radio" class="form-check-input d-none" name="color" type="radio" v-model="memoColor" value="1">
          <label for="blue-radio" class="blue"></label>
          <input id="yellow-radio" class="form-check-input d-none" name="color" type="radio" v-model="memoColor" value="2">
          <label for="yellow-radio" class="yellow"></label>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body px-3">
          <textarea
            name="memoText"
            class="form-control"
            :class="colorList[memoColor]"
            cols="20" rows="10"
            v-model="memoContent"
            maxlength="140"
            style="resize: none;"
          >
          </textarea>
          <p class="text-limit text-end text-muted mb-0">{{ memoLength }} / 140</p>
        </div>
        <div class="modal-footer">
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
      colorList: ['red', 'blue', 'yellow'],
    }
  },
  unmounted() {},
  methods: {

  },
  computed: {
    ...mapState({
      selectedIdx: (state) => state.memo.selectedIdx,
    }),
    memoLength() {
      return this.memoContent ? this.memoContent.length : 0;
    },
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
        if (!this.$store.state.memo.memoList || this.$store.state.memo.selectedIdx===undefined || this.$store.state.memo.selectedIdx === -1) {
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
  width: 2rem;
  height: 2rem;
  cursor: pointer;
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
.text-limit {
  font-size: 0.7rem;
}
</style>
