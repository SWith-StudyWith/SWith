<template>
  <footer class="footer">
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
  text-align : center;
  font-size : 13px;
  color : #C3CBCD;
  display: flex;
  background-color : #373F41;
  height: 200px;
  /* position : relative; */
  clear : right;


  /* transform : translateY(100%); */


  /* margin : 100px 0 0 0; */
  /* display: flex; */
  /* justify-content: center; */
  /* flex-direction: row; */

  /* position: fixed; */
  position: absolute;
bottom: 0;
left: 0;
right: 0;
/* color: white; */
/* background-color: #16806e; */
}

#footer-content {
  margin : auto;
  align-content: center;
}
</style>
