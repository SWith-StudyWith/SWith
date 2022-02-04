<template>
  <div class="not-found" style="padding-bottom: 0px">
    <!-- 404 Not Found -->
    <div class="container">
      <div class="row">
        <div class="col-12">
          <img class="d-inline-block" src="@/assets/img/error_page/404.png">
        </div>
        <div class="col-12 ">
          <router-link :to="{ name: 'Main' }">
            <button type="button" class="btn" style="color:white; width:150px">Go Back Main</button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: '404Page',
}
</script>
<style scoped>
.not-found {
  font-size: 10rem;
  font-weight: bold;
  height: 100vh;
  background: #F1F6FF;
}
img{
  margin-top: 250px;
}
button{
  background-color: #3F4966;
  box-shadow: 0px 4px blur #000000;
}
.app{
  padding-bottom: 0px;
}
</style>
