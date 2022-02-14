<template>
  <!-- <div class="back"> -->
    <div v-dragscroll:nochilddrag class="memo-board position-relative overflow-hidden" id="memoContainer">
      <span>타인에게 보이지 않는 개인 공간입니다.</span>
      <span v-if="isEditting" class="btn-container">
        <button class="btn btn-primary ms-2 shadow" @click="onClickCreateBtn">
          <font-awesome-icon :icon="['fas', 'plus']"></font-awesome-icon>
        </button>
        <button class="btn btn-primary ms-2 shadow" @click="onClickDelete">
          <font-awesome-icon :icon="['fas', 'trash-alt']"></font-awesome-icon>
        </button>
        <button class="btn btn-primary ms-2 shadow" @click="onClickSaveBtn">
          <font-awesome-icon :icon="['fas', 'save']"></font-awesome-icon>
        </button>
      </span>
      <span v-else class="btn-container">
        <button class="btn btn-primary shadow" @click="isEditting = true">
          <font-awesome-icon :icon="['fas', 'edit']"></font-awesome-icon>
        </button>
      </span>
      <div
        v-for="(memo, idx) in memoList"
        :key="idx" class="postit user-select-none position-absolute m-0 py-3 px-3 text-start"
        :class="['target'+ idx, colorList[memo.color]]"
        :style="{ 'z-index': memo.zIndex, transform : memo.transform }"
        @dblclick="onDblClick"
        @click="handleSelectedMemo(idx)"
      >
        {{ memo.content }}
      </div>
      <Moveable
        v-if="selectedIdx !== -1 && isEditting"
        className="moveable"
        v-bind:draggable="true"
        :target="['.target'+selectedIdx]"
        v-bind:rotatable="true"
        v-bind:scalable="true"
        v-bind:snappable="true"
        v-bind:origin="false"
        v-bind:bounds="{position: 'absolute', left: 10, right: containerWidth-10, top: 10, bottom: containerHeight-10}"
        @drag="onDrag"
        @scale="onScale"
        @rotate="onRotate"
        @renderEnd="handleRenderEnd(selectedIdx, $event)"
      />
      </div>
  <!-- </div> -->
  <MemoModal v-if="isEditting" />
</template>
<script>
import Moveable from "vue3-moveable";
import MemoModal from '@/views/studies/components/screen/MemoModal.vue'
import { mapState } from 'vuex';
import { Modal } from 'bootstrap';

export default {
  name: 'Memo',
  components: {
    Moveable,
    MemoModal
  },
  data() {
    return {
      colorList: ['red', 'blue', 'yellow'],
      imgSrc: require('@/assets/img/landing/icon_download.png'),
      isEditting: false,
      memoContainer: null,
      initZIndex: 0,
      resizeObserver: new ResizeObserver(
        () => {
          this.containerWidth = this.memoContainer.offsetWidth;
          this.containerHeight = this.memoContainer.offsetHeight;
        }
      ),
      containerWidth: 0,
      containerHeight: 0,
    }
  },
  setup() {},
  created() {
    this.$store.dispatch('GET_MEMO_LIST', this.$route.params.studyId)
  },
  mounted() {
    this.memoContainer = document.getElementById('memoContainer')
    this.resizeObserver.observe(this.memoContainer)
  },
  beforeUnmount() {
    if (this.isEditting) {
      this.onClickSaveBtn()
    }
  },
  methods: {
    handleSelectedMemo(idx) {
      this.$store.commit('SET_SELECTED_MEMO_INDEX', idx)
    },
    onDblClick() {
      let myModalEl = document.getElementById('memoModal');
      let modal = Modal.getOrCreateInstance(myModalEl);
      modal.show();
    },
    onDrag({ target, transform }) {
      target.style.transform = transform;
    },
    onScale({ target, drag }) {
      target.style.transform = drag.transform;
    },
    onRotate({ target, drag }) {
      target.style.transform = drag.transform;
    },
    onClick(idx) {
      this.$store.commit('SET_SELECTED_MEMO_INDEX', idx)
    },
    onClickDelete() {
      this.$store.commit('DELETE_SELECTED_MEMO', this.selectedIdx)
    },
    handleRenderEnd(idx, event) {
      this.memoList[idx].transform = event.target.style.transform;
    },
    onClickCreateBtn() {
      console.log('새 메모 만들기')
      this.$store.commit(
        'ADD_MEMO',
        {
          content: '',
          color: 0,
          zIndex: this.zIndexCount++,
        }
      )
    },
    onClickSaveBtn() {
      console.log('저장하기')
      this.$store.dispatch(
        'UPDATE_MEMO_LIST',
        {
          studyId: this.$route.params.studyId,
          memoList: this.memoList
        }
      )
      this.isEditting = false;
    }
  },
  computed: {
    ...mapState({
      memoList: (state) => state.memo.memoList,
      zIndexCount: (state) => state.memo.zIndexCount,
      selectedIdx: (state) => state.memo.selectedIdx,
    }),
  },
}
</script>
<style scoped>
.memo-board {
  /* position: fixed; */
  background-color: #aebed4;
  height: 72vh;
  background-image: url("../../../../assets/img/study_room/paper_texture.png");
  cursor: grab;
  border-radius: 0.8rem;
  margin: 1vh 0.7vw 0 2vw ;
}
.postit {
  width: 13rem;
  height: 13rem;
  background-color: burlywood;
  box-shadow: 0 5px 5px 0 rgb(0 0 0 / 0.1), 0 5px 5px -1px rgb(0 0 0 / 0.1);
  font-size: 0.8rem;
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
.btn-container {
  z-index: 4000;
  position: absolute;
  right: 12px;
  top: 12px;
}
.shadow {
  box-shadow: 0 3px 3px 0 rgb(0 0 0 / 0.8), 0 5px 5px -1px rgb(0 0 0 / 0.1);
}
</style>
