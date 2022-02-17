<template>
<!-- Modal -->
<div class="modal fade" id="changePasswordModal" data-bs-backdrop="static" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-body">
        <div class="row modalHeader">
            <h5 class="modal-title" id="exampleModalLabel">비밀번호 변경</h5>
        </div>
        <form action="" class="row g-3" style="text-align: left;">
          <div class="col-12" >
            <!-- nowPassword -->
            <div class="row p-0 mb-0 justify-content-between">
              <div class="col-8">
                <label for="inputAddress" class="form-label">현재 비밀번호</label>
                <div class="input-group has-validation">
                  <input type="password" class="form-control" id="nowPassword"
                  v-model="state.nowPassword"
                  placeholder="현재 비밀번호 입력"
                  :disabled="state.isValidNowPassword"
                  required
                  autofocus>
                </div>
              </div>
              <div class="col-4 d-flex justify-content-end">
                <button class="btn btn-success text-black  btn-sm"
                  :disabled="!state.nowPassword || state.isValidNowPassword"
                  @click="onClickConfirmNowPassword"
                >비밀번호 확인
                </button>
              </div>
              <div
              :style="{ visibility: (!state.isValidNowPassword )? 'hidden' : 'visible' }"
              class="valid-feedback">
                비밀번호가 확인되었습니다.
              </div>
            </div>
          </div>

          <!-- newPassword -->
          <div class="col-12">
            <label for="inputAddress2" class="form-label">변경할 비밀번호</label>
            <input type="password" class="form-control" id="newPassword" v-model="state.newPassword"
              placeholder="변경할 비밀번호 입력" required>
            <div
            :style="{ visibility: (state.isValidNewPassword || !state.wasInputed.newPassword)? 'hidden' : 'visible' }"
            class="invalid-feedback">
              숫자, 영문, 특수문자를 모두 포함해 8 ~ 16 글자를 입력해주세요.
            </div>
          </div>

          <!-- passwordConfirm -->
          <div class="col-12">
            <label for="inputAddress2" class="form-label">변경할 비밀번호 확인</label>
            <input type="password" class="form-control" id="passwordConfirm" v-model="state.passwordConfirm"
              placeholder="변경할 비밀번호 확인" required>
            <div
              v-if="!state.isValidPasswordConfirm && state.wasInputed.passwordConfirm"
              class="invalid-feedback">
              비밀번호와 일치하지 않습니다.
            </div>
            <div v-else-if="(state.passwordConfirm !== '') && state.isValidPasswordConfirm" class="valid-feedback">일치합니다.</div>
            <div v-else style="height: 1.3rem;"></div>
          </div>
        </form>
        <div class="row modalFooter">
          <div class="col" style="margin: 10px;">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="onClickCancle">취소</button>
            <button type="button" class="btn btn-primary" @click="onClickChange">비밀번호 변경</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
/* eslint-disable */
import { updatePassword, confirmpassword } from '@/api/user';
import { reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import notifications from '@/composables/notifications'

export default {
  name: '',
  setup(){
    const state = reactive({
      nowPassword: '',
      newPassword: '',
      passwordConfirm: '',
      wasInputed: {
        nowPassword: false,
        newPassword: false,
        passwordConfirm: false,
      },
      isValidNowPassword: false,

      isValidNewPassword: computed(() => {
        if (state.newPassword !== '') {
          state.wasInputed.newPassword = true
        }
        if (state.newPassword && validatePassword(state.newPassword)) {
          return true;
        }
        return false;
      }),
      isValidPasswordConfirm: computed(() => {
        if (state.passwordConfirm !== '') {
          state.wasInputed.passwordConfirm = true
        }
        if (state.passwordConfirm && (state.newPassword === state.passwordConfirm)) {
          return true;
        }
        return false;
      }),
    })
    const { notifyDanger, notifySuccess } = notifications();

    const validatePassword = function (password) {
      const numberChar = /[0-9]/;
      const specialChar = /[`~!@#$%^&*\\\'\";:\/?]/;
      const alphabetChar = /[a-zA-Z]/;
      if (password.length >= 8 && password.length <= 16) {
        if (numberChar.test(password) && specialChar.test(password) && alphabetChar.test(password)) {
          return true;
        }
      } return false;
    };

    const router = useRouter();

// 비밀번호 확인
    const onClickConfirmNowPassword = function(e) {
      e.preventDefault()
      confirmpassword(
        { password: state.nowPassword },
        (res) => {
          switch (res.data.code) {
            case 200:
              state.isValidNowPassword = true;
              notifySuccess('비밀번호 확인 완료!😀')
              break;
            case 400:
              state.isValidNowPassword = false;
              notifyDanger('비밀번호 확인 실패😰')
              break;
          }
        },
        (err) => {
          console.log(err)
          notifyDanger('비밀번호 불일치😰')
          state.nowPassword = ''
          state.wasInputed.nowPassword = false
          state.isValidNowPassword = false
        },
      )
    }

    const onClickChange = function(e){
      e.preventDefault();
      if (state.nowPassword === '' || state.newPassword === '' || state.passwordConfirm === '' ) {
        state.wasInputed.nowPassword = true;
        state.wasInputed.newPassword = true;
        state.wasInputed.passwordConfirm = true;
        return;
      }
      if (!state.isValidNewPassword || !state.isValidPasswordConfirm || !state.isValidNowPassword){
        return;
      }


      // 비밀번호 수정
      updatePassword(
        { password: state.passwordConfirm },
        (res) => {
          switch (res.data.code) {
            case 200:
              router.go({ name: 'MyPage' })
              notifySuccess('비밀번호 수정 성공!🤗')
              break;
            case 404:
              notifyDanger('비밀번호 수정 실패😰')
              break;
            case 400:
              notifyDanger('회원 인증 실패😰');
              break;
          }

        },
        (err) => {
          console.log(err)
          notifyDanger('서버에 문제가 발생했습니다.😰')
        }
      )
    }

    const onClickCancle = function(e){
      e.preventDefault()
      state.nowPassword = ''
      state.newPassword = ''
      state.passwordConfirm = ''
      state.isValidNowPassword = false
      state.wasInputed.nowPassword = false
      state.wasInputed.newPassword = false
      state.wasInputed.passwordConfirm = false
    }

    return {
      state,
      onClickConfirmNowPassword,
      onClickChange,
      onClickCancle,
    };
  }
}
</script>

<style scoped>
input::placeholder {
  font-size: 12px;
  padding: auto;
  vertical-align: middle;

}
input[type="password"]{
  background-color: #F4F5F4;
  vertical-align: middle;
}
.form-label{
  margin-bottom: 3px;
}
.modal-title {
  font-size: 24px;
  text-align: center;
  margin-bottom: 30px;
}

.row{
  margin-bottom: 10px;
  padding: 10px 15px;
}

.modalHeader, .modalFooter{
  margin: 20px;
}

.invalid-feedback {
  display: block;
  font-size: 0.75rem;
  margin-top: 0;
  margin-bottom: 0.5rem;
}
.valid-feedback {
  display: block;
  font-size: 0.75rem;
  margin-top: 0;
  margin-bottom: 0.5rem;
  color: green;
}
.btn-success{
  margin-top: 1.7rem;
  height: 2.4rem;
}
.modalFooter{
  padding: 0px;
  margin-top: 0px;

}
.modalFooter button {
  margin-inline: 0.5rem
}
</style>
