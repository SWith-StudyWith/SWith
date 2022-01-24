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
// import StudyView from "@/views/studies/components/StudyView.vue";

const routes = [
  {
    path: '/',
    name: 'Landing',
    component: () => import(/* webpackChunkName: "landing" */ '../views/Landing.vue'),
  },
  {
    path: '/main',
    name: 'Main',
    component: () => import(/* webpackChunkName: "main", webpackPrefetch:true  */ '../views/main/components/Main.vue'),
  },
  {
    path: '/members/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "login" */ '../views/members/components/Login.vue'),
  },
  {
    path: '/members/signup',
    name: 'SignUp',
    component: () => import(/* webpackChunkName: "signup" */ '../views/members/components/SignUp.vue'),
  },
  {
    path: '/members/mypage',
    name: 'MyPage',
    component: () => import(/* webpackChunkName: "mypage" */ '../views/members/components/MyPage.vue'),
  },
  {
    path: '/members/findpassword',
    name: 'FindPassword',
    component: () => import(/* webpackChunkName: "findpassword" */ '../views/members/components/FindPassword.vue'),
  },
  {
    path: '/members/changepassword',
    name: 'ChangePassword',
    component: () => import(/* webpackChunkName: "changepassword" */ '../views/members/components/ChangePassword.vue'),
  },
  {
    path: '/studies/create',
    name: 'StudyCreate',
    component: () => import(/* webpackChunkName: "about" */ '../views/studies/components/StudyCreate.vue'),
  },
  {
    path: '/studies/update',
    name: 'StudyUpdate',
    component: () => import(/* webpackChunkName: "about" */ '../views/studies/components/StudyUpdate.vue'),
  },
  {
    path: '/studies/:member_id',
    name: 'StudyMain',
    component: () => import(/* webpackChunkName: "studymain", webpackPrefetch:true  */ '../views/studies/components/StudyMain.vue'),
  },
  {
    path: '/studies/:member_id/room',
    name: 'StudyView',
    component: () => import(/* webpackChunkName: "studyview", webpackPrefetch:true  */ '../views/studies/components/StudyView.vue'),
  },
  {
    path: '/footer',
    name: 'Footer',
    component: () => import(/* webpackChunkName: "footer" */ '../views/common/Footer.vue'),
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
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
