<template>
  <footer class="footer">
  <div class="container">
    <div class="row">
      <div class="footer-policy-box col-sm-3">
        <div class="footer-policy">
          <!-- <h3>Study With __ </h3> -->
          <img class="footer-logo" src="@/assets/img/icon_logo/SWith_logo_pink.png" alt="">
          <div class="footer-policy-content">
            <a href="mailto:sales@example.com" class="contact-link">이용약관</a> <span> | </span>
            <a href="mailto:support@example.com" class="contact-link">개인정보처리방침</a>
            <!-- <a href="tel:0121234" class="contact-link">(123) 456-789</a>
            <img src="@/assets/img/footer/contact/call.svg" alt="">
            <img src="@/assets/img/footer/contact/mail.svg" alt="">
            <img src="@/assets/img/footer/contact/location.svg" alt=""> -->
            <div class="footer-social">
              <!-- <a class="footer-social-icon" href="#"><img src="@/assets/img/footer/media/github.svg" alt=""></a>
              <a class="footer-social-icon" href="#"><img src="@/assets/img/footer/media/notion.svg" alt=""></a>
              <br> -->
              <!-- <ul>
                <a href="#"></a>
                <li><a href="#"><img src="@/assets/img/footer/media/github.svg" alt=""></a></li>
                <li><a href="#"><img src="@/assets/img/footer/media/notion.svg" alt=""></a></li>
              </ul> -->
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3">
      <div class="footer-contactus">
        <h3>CONTACT US</h3>
        <div class="footer-contactus-content">
          <div class="footer-contactus-content-media">
              <!-- <p><img src="@/assets/img/footer/contact/call.svg" alt=""><span><a href="tel:15883357" class="contact-link">(02) 1588 - 3357</a></span></p> -->
              <!-- <img src="@/assets/img/footer/contact/call.svg" alt=""><span><a href="tel:15883357" class="contact-link">(02) 1588 - 3357</a></span> -->
              <p><img class="contactus-icon" src="@/assets/img/footer/contact/call.svg" alt=""><span>(02) 1588 - 3357</span></p>
              <p><img class="contactus-icon" src="@/assets/img/footer/contact/mail.svg" alt=""><span>official.swith@gmail.com</span></p>
              <!-- <p>class="contactus-icon" <img src="@/assets/img/footer/contact/mail.svg" alt=""><span><a href="mailto:official.swith@gmail.com" class="contact-link">official.swith@gmail.com</a></span></p> -->
              <p><img class="contactus-icon" src="@/assets/img/footer/contact/location.svg" alt=""><span>서울특별시 강남구 역삼동 테헤란로 212</span></p>
              <p><img class="contactus-icon" src="@/assets/img/footer/contact/clock.svg" alt=""><span>평일 오전 8:00 - 오후 6:00</span><br><span>(주말/공휴일 휴무)</span></p>
              <p><img class="contactus-icon" src="@/assets/img/footer/media/github.svg" alt=""><span>Github</span></p>
              <p><img class="contactus-icon" src="@/assets/img/footer/media/notion.svg" alt=""><span>Notion</span></p>

              <!-- <ul>
              <a href="#"></a>
              <li><a href="#"><img src="@/assets/img/footer/media/github.svg" alt=""></a></li>
              <li><a href="#"><img src="@/assets/img/footer/media/notion.svg" alt=""></a></li>
              </ul> -->
            <!-- <img src="@/assets/img/footer/contact/mail.svg" alt="">
            <img src="@/assets/img/footer/contact/location.svg" alt=""> -->
          </div>
          <!-- <div class="media">
              <div class="media-left">
                <a href="#."><img class="media-object" src="http://placehold.it/60x60" alt="..."></a>
              </div>
              <div class="media-body">
                <p><a href="#">vulputate velit esse consequat. </a></p>
                <span>September 30, 2016 </span>
              </div>
          </div> -->
        </div>
        </div>
      </div>
      <!-- <div class="col-sm-3">
      <div class="footer-widget">
        <h3>Opening Hour</h3>
        <div class="footer-widget-content">
        <div class="open-time ">
          <ul class="opening-time">
            <li><span><i class="fa fa-times"></i></span><p class="clock-time"><strong>monday :</strong> closed</p>
            </li>
            <li><span><i class="fa fa-check"></i></span><p><strong>tue-fri :</strong> 8am - 12am</p></li>
            <li><span><i class="fa fa-check"></i></span><p><strong>sat-sun :</strong> 7am - 1am</p></li>
            <li><span><i class="fa fa-check"></i></span><p><strong>holydays :</strong> 12pm-12am</p></li>
          </ul>
          </div>
        </div>
        </div></div> -->

      <div class="col-sm-6">
      <div class="footer-weare">
        <h3>WE ARE</h3>
        <div class="footer-weare-content">
          <div class="images-gellary">
            <ul>
              <li><a href="#"><img src="http://placehold.it/85x85" alt="Instagram 01"></a></li>
              <li><a href="#"><img src="http://placehold.it/85x85" alt="Instagram 02"></a></li>
              <li><a href="#"><img src="http://placehold.it/85x85" alt="Instagram 03"></a></li>
              <li><a href="#"><img src="http://placehold.it/85x85" alt="Instagram 04"></a></li>
              <li><a href="#"><img src="http://placehold.it/85x85" alt="Instagram 05"></a></li>
              <li><a href="#"><img src="http://placehold.it/85x85" alt="Instagram 06"></a></li>
            </ul>
          </div>
        </div>
        </div>
      </div>
    </div>
  </div>
</footer>
</template>

<script>
export default {
  name: 'Footer',
};
</script>

<style lang="scss" scoped>
p{
  color: #C3CBCD;
}

h3 {
  color: #C3CBCD;
  margin-bottom: 30px;
}

.footer-logo {
  max-height: 10vh;
  min-height: 6vh;
  box-shadow: #C3CBCD;
}

.red{
  color: red !important;
}

footer{
  // background-color: #f5f5f5;
  background-color: #373F41;
  padding: 80px 0;
  .footer-widget{
    h3{
      color: #C3CBCD;
      margin-bottom: 30px;
    }
    align-items: baseline;
  }
  .contact-link{
    display:inline;
    width: 100%;
    color: #C3CBCD;
    text-decoration:none;
    font-size: 13px;
  }
  .footer-social{
    ul{
      padding-left: 0;
      li{
        list-style: none;
        float: left;
        padding: 0 10px;
        &:first-child{
          padding-left: 0;
        }
        a{
          font-size: 20px;
          color: #C3CBCD;
          &:hover{
            color: red;
          }
        }
      }
    }
  }

  .opening-time{
    padding-left:0;
    li{
      list-style: none;
      span{
        float:left;
        padding-right: 10px;
        .fa-times{
          color: red;
        }
      }
        strong{
          color: red;
        }
    }
  }

  .media-body{
    a{
      color: #C3CBCD;
      &:hover{
        color: red;
      }
    }
    span{
        color: #969696;
      }
  }

  .images-gellary{
    ul{
      padding-left: 0;
      li{
        list-style: none;
        float: left;
        margin: 0 3% 2% 0;
        width: 31%;
        position: relative;
        &:nth-child(3n) {
    margin: 0 0 1%;
}
        a{

        }
      }
    }
  }
}

.footer-policy-box {
  display: flex;
      flex-direction: column;
    justify-content: space-around;
    align-items: center;
}

.footer-policy {
  color: #C3CBCD;
  font-size: 13px;
}

.footer-policy-content {
  color: #C3CBCD;
  font-size: 13px;
}

.footer-social-icon {
  // float: left;
  // margin: 0 3% 2% 0;
  margin-right: 5px;
  // margin-top: 30px;
}

.footer-contactus {
  align-items: baseline;
  // float: left;
}

.footer-contactus-content-media {
  display: flex;
  flex-direction: column;
  align-items: baseline;
  color: #969696;
  font-size: 13px;
}

.contactus-icon {
  margin-right: 5px;
  height: 2vh;
}
</style>
