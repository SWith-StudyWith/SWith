<template>
  <div>
    <Navbar />
    <div class="container">
      <h1 class="form-title">스터디룸 만들기</h1>
      <div class="row d-flex justify-content-center">
        <div class="col-md-6 col-lg-5 col-xl-4">
          <section>
            <form class="studyInfo-wrapper" enctype="multipart/form-data">
              <div>
                <label for="studyName" class="form-label">스터디 이름</label>
                <input type="text" class="form-control" id="studyName" v-model="state.studyName" required placeholder="스터디 이름" maxlength="20">
                <div :style="{ visibility: (state.isValidStudyName || !state.wasInputed.studyName )? 'hidden' : 'visible' }"
                  class="invalid-feedback">
                  2~20자 사이로 작성해주세요.
                </div>
              </div>
              <div class="mb-3">
                <label for="goal" class="form-label">스터디 목표</label>
                <textarea
                  class="form-control form-goal"
                  id="studyGoal" rows="3"
                  v-model="state.studyGoal"
                  placeholder="스터디 목표를 한 줄로 표현해 보세요!"
                  maxlength="40"
                >
                </textarea>
              </div>
              <div class="d-flex justify-content-start mb-4">
                <div class="dropend">
                  <div class="image-wrapper scale" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false" >
                    <img class="study-img" :src="state.studyImgSrc">
                  </div>
                  <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <li>
                      <label for="changeStudyImg" class="dropdown-item img-form-label">스터디 이미지 선택</label>
                    </li>
                    <li>
                      <span @click="onClickDefaultImg" class="dropdown-item img-revert-default">기본 이미지로 변경</span>
                    </li>
                  </ul>
                </div>
                <div class="form-group">
                  <input @change="onClickUploadFile" id="changeStudyImg" type="file" ref="file" class="form-control" accept="image/*" style="display: none;" />
                </div>
              </div>
              <button @click="onClickCreateStudy" class="btn btn-primary col-12">스터디 만들기</button>
            </form>
          </section>
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { createStudy } from '@/api/study';
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';
import notifications from '@/composables/notifications'

export default {
  name: '',
  components: { Navbar, Footer },
  setup() {
    const router = useRouter();
    const state = ref({
      studyName: '',
      studyGoal: '',
      studyImgURL: '', // studyInfo 안에 포함
      studyImage: '',
      studyImgSrc: computed(()=> {
        if (state.value.studyImgURL) {
          return state.value.studyImgURL
        } else {
          return require(`@/assets/img/study_room/studyroom.png`)
        }
      }),
      wasInputed: {
        studyName: false,
      },
      isValidStudyName: computed(() => {
        if (state.value.studyName !== '') {
          state.value.wasInputed.studyName = true;
        }
        if (state.value.studyName && validateStudyName(state.value.studyName)) {
          return true;
        }
        return false;
      }),
    });
    const { notifyDanger, notifySuccess, notifyDangerDescription } = notifications();

    const onClickUploadFile = (e) => {
      const file = e.target.files[0]
      if (file.size > 209715) {
        e.preventDefault();
        notifyDangerDescription('파일 사이즈가 너무 큽니다.😯', '최대 2MB')
        return;
      } else {
        state.value.studyImgURL = URL.createObjectURL(file);
        state.value.studyImage = file;
      }
    };
    const onClickDefaultImg = (e) => {
      e.preventDefault();
      state.value.studyImgURL = '';
      state.value.studyImage = '';
    };
    const onClickCreateStudy = (e) => {
      e.preventDefault();
      if (state.value.studyName === '') {
        state.value.wasInputed.studyName = true;
        return;
      }
      if (!state.value.isValidStudyName) {
        return;
      }
      const createStudyData = new FormData();
      createStudyData.append("studyName", state.value.studyName)
      createStudyData.append("studyGoal", state.value.studyGoal)
      createStudyData.append("studyImage", state.value.studyImage)

      createStudy(
        createStudyData,
        (res) => {
          console.log(res.data)
          switch (res.data.code) {
            case 200:
              notifySuccess('스터디룸 생성 완료!🔨')
              break;
            case 400:
              console.log('실패')
              notifyDanger('스터디룸 생성 실패😥')
              break;
          }
        },
        (err) => {
          console.log(err)
          notifyDanger('서버에 문제가 발생했습니다.😰')
          },
          router.push({ name: 'Main'})
      )
    }

    const validateStudyName = function (studyName) {
      if (studyName.length >= 2 && studyName.length <= 20) {
        return true;
      }
      return false;
    };

    return {
      state,
      onClickUploadFile,
      onClickDefaultImg,
      onClickCreateStudy,
    }
  },

}
</script>

<style scoped>
.container{
  margin-top: 50px;
  justify-content: center;
}
form{
  text-align: left;
}
p{
  font-size: 18px;
  font-weight: 700;
  display: flex;
}
.btn-change-pw {
  text-align: center;
  font-size: 0.7rem;
}
.uploadImage{
  margin-bottom: 20px;
}
.profile-img{
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.base-url{
  font-size: 14px;
  font-weight: 400;
  color: #737B7D;
  margin-bottom: 5px;
}
.studyroom-img p{
  font-size: 16px;
  font-weight: 400;
  margin-bottom: 5px;
}
.invalid-feedback {
  display: block;
  font-size: 0.75rem;
  margin-top: 0;
  margin-bottom: 0;
}
/* basic setting */
button{
  font-size: 14px;
  text-align: center;
}
.form-label{
  margin-bottom: 3px;
}
.form-title {
  font-size: 24px;
  text-align: center;
  margin-bottom: 30px;
}
.container {
  margin-top: 100px;
  /* margin-bottom: 120px; */
}
input::placeholder {
  font-size: 12px;
  padding: auto;
  vertical-align: middle;
}
input{
  background-color: #F4F5F4;
  vertical-align: middle;
}
textarea{
  background-color: #F4F5F4;
  vertical-align: middle;
}
.img-form-label{
  margin-bottom: 3px;
}
.image-wrapper{
  width: 200px;
  height: 150px;
  border-radius: 4%;
  overflow: hidden;
  justify-content: center;
  cursor: pointer;
  display: block;
}
.study-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  background: #fff;
  border: 1px solid rgba(0, 0, 0, 0.125);
  border-radius: 4%;
}
.dropdown-menu{
  text-align: center;
}
.dropdown-item{
  cursor: pointer;
}
.scale {
  transform: scale(1);
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transition: all 0.3s ease-in-out;   /* 부드러운 모션을 위해 추가*/
}
.scale:hover {
  transform: scale(1.05);
  -webkit-transform: scale(1.05);
  -moz-transform: scale(1.05);
  -ms-transform: scale(1.05);
  -o-transform: scale(1.05);
}
</style>
