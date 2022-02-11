<template>
  <div class="modal fade" id="memoModal" tabindex="-1" aria-labelledby="memoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <!-- <h5 class="modal-title" id="memoModalLabel">Modal title</h5> -->
          <input id="red-radio" class="form-check-input d-none" name="color" type="radio" value="0" @change="handleChangeColor">
          <label for="red-radio" class="red"></label>
          <input id="blue-radio" class="form-check-input d-none" name="color" type="radio" value="1" @change="handleChangeColor">
          <label for="blue-radio" class="blue"></label>
          <input id="yellow-radio" class="form-check-input d-none" name="color" type="radio" value="2" @change="handleChangeColor">
          <label for="yellow-radio" class="yellow"></label>

          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <textarea name="memoText" cols="30" rows="10" v-model="memo.content"></textarea>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" @click="onClickSaveBtn">Save changes</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex';
import _ from 'lodash';

export default {
  name: 'MemoModal',
  components: {},
  data() {
    return {
    }
  },
  unmounted() {},
  methods: {
    handleChangeColor(e) {
      this.memo.color = Number(e.target.value);
    },
    onClickSaveBtn() {
      const index = this.selectedIdx;
      const memo = this.memo;
      this.$store.commit('SET_MEMO_BY_INDEX', { index, memo })
    }
  },
  computed: {
    ...mapState({
      memoList: (state) => state.memo.memoList,
      selectedIdx: (state) => state.memo.selectedIdx,
      memo: (state) => state.memo.memo,
    }),

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
