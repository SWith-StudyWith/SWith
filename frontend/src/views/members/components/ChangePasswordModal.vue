<template>
  <!-- Button trigger modal -->
<button type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#exampleModal">
  비밀번호 변경
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <!-- <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">비밀번호 변경</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div> -->
      <div class="modal-body">
        <div class="row modalHeader">
            <h5 class="modal-title" id="exampleModalLabel">비밀번호 변경</h5>
        </div>
        <form action="" class="row g-3 needs-validation"  style="text-align: left;" novalidate>
          <div class="col-12" >
            <label for="inputAddress" class="form-label">현재 비밀번호</label>
            <div class="input-group has-validation">
              <input type="password" class="form-control" id="inputAddress" placeholder="현재 비밀번호 입력" required>
              <div class="invalid-feedback">필수 입력 사항입니다.</div>
            </div>
          </div>
          <div class="col-12">
            <label for="inputAddress2" class="form-label">변경할 비밀번호</label>
            <input type="password" class="form-control" id="inputAddress2" placeholder="변경할 비밀번호 입력">
          </div>
          <div class="col-12">
            <label for="inputAddress2" class="form-label">변경할 비밀번호 확인</label>
            <input type="password" class="form-control" id="inputAddress2" placeholder="변경할 비밀번호 확인">
          </div>
        </form>
        <div class="row modalFooter">
          <div class="col" style="margin: 10px;">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            <button button type="button" class="btn btn-light">비밀번호 변경</button>
          </div>
        </div>
      </div>
      <!-- <div class="modal-footer" >
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
        <button type="button" class="btn btn-primary">비밀번호 변경</button>
      </div> -->
    </div>
  </div>
</div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
export default {

}
</script>

<style>
input[type="password"]{
  background-color: #F4F5F4;
  border: none;
  font-size: 13px;
  padding: 10px 15px;
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

button{
  margin: 10px;
}
</style>
