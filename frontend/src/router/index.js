import { createRouter, createWebHistory } from 'vue-router';
// import Home from '../views/Home.vue'
// // main
// import Landing from "@/views/Landing.vue";
// import Main from "@/views/main/components/Main.vue";
// // members
// import Login from "@/views/members/components/Login.vue";
// import SignUp from "@/views/members/components/SignUp.vue";
// import MyPage from "@/views/members/components/MyPage.vue";
// import ChangePassword from "@/views/members/components/ChangePassword.vue";
// import FindPassword from "@/views/members/components/FindPassword.vue";
// // studies
// import StudyCreate from "@/views/studies/components/StudyCreate.vue";
// import StudyUpdate from "@/views/studies/components/StudyUpdate.vue";
// import StudyMain from "@/views/studies/components/StudyMain.vue";
// import StudyRoom from "@/views/studies/components/StudyRoom.vue";
import { useStore } from 'vuex';

// 전역 가드로 페이지 인증하기 위함 meta: {authRequired : true},
const routes = [
  {
    path: '/',
    name: 'Landing',
    component: () => import(/* webpackChunkName: "landing" */ '../views/Landing.vue'),
    meta: { authRequired: false },
  },
  {
    path: '/main',
    name: 'Main',
    component: () => import(/* webpackChunkName: "main", webpackPrefetch:true  */ '../views/main/components/Main.vue'),
    meta: {authRequired : true},
  },
  {
    path: '/members/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "login" */ '../views/members/components/Login.vue'),
    meta: { authRequired: false },
  },
  {
    path: '/members/signup',
    name: 'SignUp',
    component: () => import(/* webpackChunkName: "signup" */ '../views/members/components/SignUp.vue'),
    meta: { authRequired: false },
  },
  {
    path: '/members/mypage',
    name: 'MyPage',
    component: () => import(/* webpackChunkName: "mypage" */ '../views/members/components/MyPage.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/members/findpassword',
    name: 'FindPassword',
    component: () => import(/* webpackChunkName: "findpassword" */ '../views/members/components/FindPassword.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/members/changepassword',
    name: 'ChangePassword',
    component: () => import(/* webpackChunkName: "changepassword" */ '../views/members/components/ChangePassword.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/create',
    name: 'StudyCreate',
    component: () => import(/* webpackChunkName: "about" */ '../views/studies/components/StudyCreate.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/:studyCode/update',
    name: 'StudyUpdate',
    component: () => import(/* webpackChunkName: "about" */ '../views/studies/components/StudyUpdate.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/11',
    name: 'Studies',
    component: () => import(/* webpackChunkName: "about" */ '@/views/studies/Studies.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/11/kanbanboard',
    name: 'KanbanBoard',
    component: () => import(/* webpackChunkName: "about" */ '@/views/studies/components/screen/KanbanBoard.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/11/screenshare',
    name: 'ScreenShare',
    component: () => import(/* webpackChunkName: "about" */ '@/views/studies/components/screen/ScreenShare.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/11/whiteboard',
    name: 'WhiteBoard',
    component: () => import(/* webpackChunkName: "about" */ '@/views/studies/components/screen/WhiteBoard.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/:studyCode',
    name: 'StudyMain',
    component: () => import(/* webpackChunkName: "studymain", webpackPrefetch:true  */ '../views/studies/components/StudyMain.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/:studyCode/room',
    name: 'StudyRoom',
    component: () => import(/* webpackChunkName: "studyroom", webpackPrefetch:true  */ '../views/studies/components/StudyRoom.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/studies/:studyCode/page',
    name: 'StudyPage',
    component: () => import(/* webpackChunkName: "about" */ '@/views/studies/StudyPage.vue'),
    meta: { authRequired: true },
  },
  {
    path: '/footer',
    name: 'Footer',
    component: () => import(/* webpackChunkName: "footer" */ '../views/common/Footer.vue'),
    meta: { authRequired: false },
  },
  {
    path: '/test',
    name: 'Test',
    component: () => import(/* webpackChunkName: "test" */ '../components/test.vue'),
  },
  {
    path: '/members/kakao/callback',
    name: 'OAuthKakao',
    component: () => import(/* webpackChunkName: "test" */ '../views/members/components/OAuthKakao.vue'),
  },
  {
    path: '/login/oauth2/client/google',
    name: 'OAuthGoogle',
    component: () => import(/* webpackChunkName: "test" */ '../views/members/components/OAuthGoogle.vue'),
  },
];

const store = useStore();

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach(function (to, from, next) {
  // to : 이동할 URL 정보가 담긴 라우터 객체
  // from : 현재 URL 정보가 담긴 라우터 객체
  // next : to에서 지정한 URL로 이동하기 위해 반드시 호출해야하는 함수

  // meta 속성의 authRequired : false 이면 라우팅 이동 허용
  // true 면 라우팅 이동 차단하며 로그인 시도하라는 alert 띄움

  const token = localStorage.getItem('accessToken');

  if (to.matched.some(function (routeInfo) {
    return routeInfo.meta.authRequired;
  })) {
    if (token == '' || token == undefined) {
      alert('로그인이 필요한 페이지입니다.');
      next('/members/login');
    } else {
      next()
    }
  } else {
    // console.log('routing success : ' + to.path );

    // next()로 to로 이동하지 않으면 라우팅이 되지 않음
    next();
  }
})
export default router;
