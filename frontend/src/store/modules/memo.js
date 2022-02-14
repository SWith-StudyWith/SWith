import { setMemoList, getMemoList } from "../../api/memo";
import notifications from '@/composables/notifications'

const { notifySuccess } = notifications();
const state = () => ({
  memoList: [],
  memo: {},
  selectedIdx: -1,
  zIndexCount: 1,
});

const getters = {
  selectedMemo(state) {
    if (state.selectedIdx < 0) {
      return null;
    }
    return state.memoList.selectedIdx;
  }
};

const actions = {
  GET_MEMO_LIST({ commit }, studyId) {
    getMemoList(
      studyId,
      (res) => {
        commit('SET_MEMO_LIST', res.data.data)
      },
      (err) => {
        console.log(err)
      }
    )
  },
  UPDATE_MEMO_LIST({ commit }, { studyId, memoList }) {
    console.log(studyId, memoList)
    const payload = [];
    memoList.forEach((memo) => {
      const { content, color, transform } = memo;
      payload.push({
        content,
        color,
        transform,
      })
    })
    console.log(payload)
    setMemoList(
      studyId,
      payload,
      (res) => {
        console.log(res.data)
        getMemoList(
          studyId,
          (res) => {
            commit('SET_MEMO_LIST', res.data.data)
            notifySuccess('메모 저장 완료!')
          },
          (err) => {
            console.log(err)
          }
        )
      },
      (err) => {
        console.log(err)
      }
    )
  },
};
const mutations = {
  SET_MEMO_LIST(state, memoList) {
    state.memoList = memoList
  },
  ADD_MEMO(state, memo) {
    state.memoList.push(memo)
  },
  SET_SELECTED_MEMO_INDEX(state, index) {
    state.selectedIdx = index;
    state.zIndexCount++;
    state.memoList[index].zIndex = state.zIndexCount;
  },
  SET_MEMO_BY_INDEX(state, { index, memo }) {
    state.memoList[index] = memo;
  },
  UPDATE_SELECTED_MEMO_CONTENT(state, { index, content }) {
    state.memoList[index] = { ...state.memoList[index], ...{ content } };
  },
  UPDATE_SELECTED_MEMO_COLOR(state, { index, color }) {
    state.memoList[index] = { ...state.memoList[index], ...{ color } };
  },
  DELETE_SELECTED_MEMO(state, index) {
    state.memoList.splice(index, 1);
    state.selectedIdx = -1;
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
