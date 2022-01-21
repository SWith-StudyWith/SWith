<template>
  <footer>
    <div id="footer-content">
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
  font-family:  'Mulish', 'Noto Sans KR', sans-serif;
  text-align : center;
  font-size : 16px;
  color : #C3CBCD;
  /* margin : 100px 0 0 0; */
  display: flex;
  justify-content: center;
  flex-direction: row;
  background-color : #373F41;
  height : 273px;
}

#footer-content {
  margin : auto;
  align-content: center;
}
</style>
