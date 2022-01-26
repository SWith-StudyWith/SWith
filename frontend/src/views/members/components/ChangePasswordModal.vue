<template>
<!-- Modal -->
<div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-body">
        <div class="row modalHeader">
            <h5 class="modal-title" id="exampleModalLabel">비밀번호 변경</h5>
        </div>
        <form action="" class="row g-3 needs-validation"  style="text-align: left;">
          <div class="col-12" >

            <!-- nowPassword -->
            <label for="inputAddress" class="form-label">현재 비밀번호</label>
            <div class="input-group has-validation">
              <input type="password" class="form-control" id="nowPassword" v-model="state.nowPassword"
              placeholder="현재 비밀번호 입력" required>
            </div>
            <div
            :style="{ visibility: (state.isValidNowPassword || !state.wasInputed.nowPassword)? 'hidden' : 'visible' }"
            class="invalid-feedback">
              유효하지 않은 비밀번호입니다.
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
              숫자, 영문 소/대문자, 특수문자를 모두 포함해주세요.
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
            <div v-else-if="(state.passwordConfirm !== '') && state.isValidPasswordConfirm"         class="valid-feedback">일치합니다.</div>
            <div v-else style="height: 1.3rem;"></div>
          </div>
        </form>
        <div class="row modalFooter">
          <div class="col" style="margin: 10px;">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="onClickCancle">취소</button>
            <button button type="button" class="btn btn-primary" @click="onClickChange">비밀번호 변경</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
/* eslint-disable */
import { updatePassword } from '@/api/user';
import { reactive, computed } from 'vue';
import { useRouter } from 'vue-router';

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
      isValidNowPassword: computed(() => {
        if (state.nowPassword !== '') {
          state.wasInputed.nowPassword = true
        }
        if (state.nowPassword && validatePassword(state.nowPassword)) {
          return true;
        }
        return false;
      }),
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
      updatePassword(
        { password: state.newPassword },
        (res) => {
          console.log(res.data)
          switch (res.data.code) {
            case 200:
              alert('비밀번호 수정 성공!')
              break;
          }
        },
        (err) => {
          console.log(err)
          alert('서버가 아파요.')
        }
      )
      router.push({ name: 'Login' })
    }

    const onClickCancle = function(e){
      state.nowPassword = ''
      state.newPassword = ''
      state.passwordConfirm = ''
      state.wasInputed.nowPassword = false
      state.wasInputed.newPassword = false
      state.wasInputed.passwordConfirm = false
    }

    return {
      state, onClickChange
    };

  },
}
</script>

<style>
input[type="password"]{
  background-color: #F4F5F4;
  border: none;
  font-size: 13px;
  padding: 10px 15px;
  margin-bottom: 3px;
}

input::placeholder{
  color: #737B7D;
  font-size: 10 px;
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
  color: rgb(24, 190, 18);
}
button{
  margin: 10px;
}
</style>
