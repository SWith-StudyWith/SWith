<template>
  <!-- <div class="back"> -->
    <button @click="isEditting = !isEditting"> 버튼</button>
    <button class="btn btn-primary" @click="onClickCreateBtn">새 메모</button>
    <button class="btn btn-primary" @click="onClickSaveBtn">저장하기</button>
    <div v-dragscroll:nochilddrag class="container position-relative back overflow-hidden mb-4" id="memoContainer">
      <div
        v-for="(memo, idx) in memoList"
        :key="idx" class="postit position-absolute m-0"
        :class="['target'+ idx, memo.color]"
        :style="{ 'z-index': idx, transform : memo.transform }"
        data-bs-toggle="modal" data-bs-target="#memoModal"
      >
        {{ memo.content }}
      </div>
        <!-- :moveable="moveableState" -->
        <!-- v-bind:target="[isEditting ? '.target'+idx : null]" -->
      <Moveable
        v-for="(memo, idx) in memoList" :key="idx"
        v-if="isEditting"
        className="moveable"
        v-bind:draggable="true"
        v-bind:target="['.target'+idx]"
        v-bind:rotatable="true"
        v-bind:scalable="true"
        v-bind:snappable="true"
        v-bind:origin="false"
        v-bind:bounds="{position: 'absolute', left: 10, right: containerWidth-10, top: 10, bottom: containerHeight-10}"
        @drag="onDrag"
        @scale="onScale"
        @rotate="onRotate"
        @click="onClick(memo)"
        @renderEnd="handleRenderEnd(idx, $event)"
      />
          <!-- @dblclick="onDblClick(memo)" -->
      </div>
  <!-- </div> -->
  <MemoModal/>
</template>
<script>
import Moveable from "vue3-moveable";
// import { Modal } from 'bootstrap';
import MemoModal from '@/views/studies/components/screen/MemoModal.vue'
import { mapState } from 'vuex';

export default {
  name: 'Memo',
  components: {
    Moveable,
    MemoModal
  },
  data() {
    return {
      imgSrc: require('@/assets/img/landing/icon_download.png'),
      isEditting: true,
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
      // memoList: [
      //   {
      //     content: 'A',
      //     color: 'red',
      //     transform: 'matrix(1, 0, 0, 1, 0, 0) translate(162px, 0px)',
      //   },
      //   {
      //     content: 'B',
      //     color: 'blue',
      //     transform: 'matrix(1, 0, 0, 1, 0, 0) translate(162px, 80px)',
      //   },
      //   {
      //     content: 'C',
      //     color: 'green',
      //     transform: 'matrix(1, 0, 0, 1, 0, 0) translate(162px, 160px)',
      //   }
      // ],
    }
  },
  setup() {},
  created() {},
  mounted() {
    this.memoContainer = document.getElementById('memoContainer')
    this.resizeObserver.observe(this.memoContainer)
  },
  unmounted() {},
  methods: {
    onDrag({ target, transform }) {
      target.style.transform = transform;
    },
    onScale({ target, drag }) {
      target.style.transform = drag.transform;
    },
    onRotate({ target, drag }) {
      target.style.transform = drag.transform;
    },
    onClick(memo) {
      console.log(memo.color)
      console.log(memo.content)

    },
    handleRenderEnd(idx, event) {
      console.log(event.target.style.transform)
      this.memoList[idx].transform = event.target.style.transform;
    },
    onClickCreateBtn() {
      console.log('새 메모 만들기')
      this.$store.commit(
        'ADD_MEMO',
        {
          content: '',
          color: 'yellow',
          zIndex: ++this.zIndexCount,
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
    }
  },
  computed: {
    ...mapState({
      memoList: (state) => state.memo.memoList,
      zIndexCount:(state) => state.memo.zIndexCount,
    }),
  },
}
</script>
<style scoped>
.back {
  position: fixed;
  background-color: #aebed4;
  height: 80vh;
  width: 90%;
  background-image: url("../../../../assets/img/study_room/paper_texture.png");
  cursor: grab;
}
.postit {
  width: 10rem;
  height: 10rem;
  background-color: burlywood;
}
.red {
  background-color: rgb(211, 97, 97);
}
.blue {
  background-color: rgb(115, 115, 218);
}
.green {
  background-color: rgb(94, 187, 94);
}
.yellow {
  background-color: rgb(255, 255, 147);
}
</style>
