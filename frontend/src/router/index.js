import { createRouter, createWebHistory } from 'vue-router';
import notifications from '@/composables/notifications'

const { notifySuccessDescription } = notifications();

// 전역 가드로 페이지 인증하기 위함 meta: {authRequired : true},
const routes = [
  {
    path: '/',
    name: 'Landing',
    component: () => import(/* webpackChunkName: "landing" */ '../views/Landing.vue'),
    meta: { authRequired: false },

    // 로그인 상태로 랜딩 페이지 접근 못하게
    beforeEnter: function (to, from, next) {
      const token = sessionStorage.getItem('accessToken');
      if (token) {
        next('/Main');
      } else next();
    }
  },
  {
    path: '/main',
    name: 'Main',
    component: () => import(/* webpackChunkName: "main", webpackPrefetch:true  */ '../views/main/Main.vue'),
    meta: {authRequired : true},
  },
  {
    path: '/members/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "login" */ '../views/members/Login.vue'),
    meta: { authRequired: false },
    beforeEnter: function (to, from, next) {
      const token = sessionStorage.getItem('accessToken');
      if (token) {
        next('/Main');
      } else next();
    }
  },
  {
    path: '/members/signup',
    name: 'SignUp',
    component: () => import(/* webpackChunkName: "signup" */ '../views/members/SignUp.vue'),
    meta: { authRequired: false },
    beforeEnter: function (to, from, next) {
      const token = sessionStorage.getItem('accessToken');
      if (token) {
        next('/Main');
      } else next();
    }
  },
  {
    path: '/members/mypage',
    name: 'MyPage',
    component: () => import(/* webpackChunkName: "mypage" */ '../views/members/MyPage.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/members/findpassword',
    name: 'FindPassword',
    component: () => import(/* webpackChunkName: "findpassword" */ '../views/members/components/FindPassword.vue'),
    meta: { authRequired: false },
    beforeEnter: function (to, from, next) {
      const token = sessionStorage.getItem('accessToken');
      if (token) {
        next('/Main');
      } else next();
    }
  },
  {
    path: '/studies/create',
    name: 'StudyCreate',
    component: () => import(/* webpackChunkName: "about" */ '../views/studies/components/StudyCreate.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/:studyId/update',
    name: 'StudyUpdate',
    component: () => import(/* webpackChunkName: "about" */ '../views/studies/components/StudyUpdate.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/:studyId',
    name: 'StudyDetail',
    component: () => import(/* webpackChunkName: "studydetail", webpackPrefetch:true  */ '../views/studies/StudyDetail.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/:studyId/room',
    name: 'Studies',
    component: () => import(/* webpackChunkName: "about" */ '@/views/studies/Studies.vue'),
    meta: { authRequired: true },
    props: true,
  },
  {
    path: '/members/kakao/callback',
    name: 'OAuthKakao',
    component: () => import(/* webpackChunkName: "OAuthKakao" */ '../views/members/components/OAuthKakao.vue'),
  },
  {
    path: '/login/oauth2/client/google',
    name: 'OAuthGoogle',
    component: () => import(/* webpackChunkName: "OAuthGoogle" */ '../views/members/components/OAuthGoogle.vue'),
  },
  {
    path: '/404',
    name: '404',
    component: () => import(/* webpackChunkName: "OAuthGoogle" */ '../views/common/404Page.vue'),
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404'
  },
];
const router = createRouter({
  scrollBehavior() {
    return {
      el: 'html',
      top: 0
    }
  },
  history: createWebHistory(),
  routes,
});

router.beforeEach(function (to, from, next) {
  // to : 이동할 URL 정보가 담긴 라우터 객체
  // from : 현재 URL 정보가 담긴 라우터 객체
  // next : to에서 지정한 URL로 이동하기 위해 반드시 호출해야하는 함수

  // meta 속성의 authRequired : false 이면 라우팅 이동 허용
  // true 면 라우팅 이동 차단하며 로그인 시도하라는 alert 띄움

  const token = sessionStorage.getItem('accessToken');

  if (to.matched.some(function (routeInfo) {
    return routeInfo.meta.authRequired;
  })) {
    if (token == '' || token == undefined) {
      notifySuccessDescription('로그인이 필요한 페이지입니다!😎', '로그인 페이지로 이동합니다.')
      next('/members/login');
    } else {
      next()
    }
  } else {
    // next()로 to로 이동하지 않으면 라우팅이 되지 않음
    next();
  }
})
export default router;
