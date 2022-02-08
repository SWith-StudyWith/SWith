<template>
  <div class="app">
    <router-link to="/test"></router-link>
    <router-link to="/"></router-link>
    <router-view/>
  </div>
</template>

<script>

</script>

<style lang="scss">
$primary: #334666;
$secondary: #D9D8D8;
$success: #F5CEC7;
@import 'bootstrap/scss/bootstrap';
@import url('https://fonts.googleapis.com/css2?family=Mulish:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Noto+Sans+KR:wght@100;300;400;500;700;900&family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');

#app {
  font-family:  'Noto Sans KR', 'Mulish', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
::-webkit-scrollbar{
    width: 12px;
}
::-webkit-scrollbar-thumb{
    background-color: #999;
    border-radius: 10px;
}
::-webkit-scrollbar-track{
    background-color: #ccc;
}
.popover {
  font-family:  'Noto Sans KR', 'Mulish', sans-serif;
  font-size: 0.7rem;
}
.popover-body {
  padding: 0.3rem;
}
.app {
  min-height: 100%;
  position: relative;
  // padding-bottom: 170px;
}
</style>
