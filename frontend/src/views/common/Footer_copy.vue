<template>
  <footer class="footer">
    <div id="footer-content">
      <div class="footer-content-section-1">
        <img class="footer-logo" src="@/assets/img/icon_logo/SWith_logo2.png" alt=""><p>Github</p>
        <h6>SWith</h6>
        <p>sdfsdf</p>
      </div>
      <div class="footer-content-section-2">

      </div>
      <div class="footer-content-section-3">
        <h6>CONTACT</h6>
        <img src="@/assets/img/footer/media/github.svg" alt=""><p>Github</p>
        <img src="@/assets/img/footer/media/notion.svg" alt=""><p>Notion</p>

      </div>
      <div class="footer-content-section-4">
        <h6>ABOUT US</h6>
        <div class="footer-content-s4-aboutus">
          <img src="@/assets/img/footer/media/instagram_color.svg" alt=""><p>Notion</p>
          <img src="@/assets/img/footer/media/instagram_2.svg" alt=""><p>Notion</p>
          <img src="@/assets/img/footer/media/notion.svg" alt=""><p>Notion</p>
        </div>
      </div>
    </div>
    <div id="footer-content-bottom">
      <p>(주) <b>SWith</b> 주소 : 서울특별시 강남구 역삼동 테헤란로 212</p>
      <p>이메일 : official.swith@gmail.com</p>
      <p>© SWith. All rights reserved.</p>

    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer',
};
</script>

<style scoped>
footer {
  text-align : center;
  font-size : 13px;
  color : #C3CBCD;
  /* margin : 100px 0 0 0; */
  /* display: flex; */
  /* justify-content: center; */
  /* flex-direction: row; */
  display: flex;
  flex-direction: column;
  background-color : #373F41;
  /* height: 200px; */
  position : relative;
  clear : right;

  /* position: fixed; */
  transform : translateY(100%);
}

#footer-content {
  display: flex;
  margin : auto;
  align-content: center;
}

.footer-logo {
  max-height: 10vh;
  min-height: 6vh;
  box-shadow: #C3CBCD;
}

#footer-content-bottom {
  display: flex;
  margin : auto;
  /* align-content: center; */
}
</style>
