(function(t){function e(e){for(var o,a,u=e[0],s=e[1],d=e[2],f=e[3]||[],m=0,p=[];m<u.length;m++)a=u[m],Object.prototype.hasOwnProperty.call(c,a)&&c[a]&&p.push(c[a][0]),c[a]=0;for(o in s)Object.prototype.hasOwnProperty.call(s,o)&&(t[o]=s[o]);l&&l(e),r.push.apply(r,f);while(p.length)p.shift()();return i.push.apply(i,d||[]),n()}function n(){for(var t,e=0;e<i.length;e++){for(var n=i[e],o=!0,a=1;a<n.length;a++){var d=n[a];0!==c[d]&&(o=!1)}o&&(i.splice(e--,1),t=s(s.s=n[0]))}return 0===i.length&&(r.forEach((function(t){if(void 0===c[t]){c[t]=null;var e=document.createElement("link");s.nc&&e.setAttribute("nonce",s.nc),e.rel="prefetch",e.as="script",e.href=u(t),document.head.appendChild(e)}})),r.length=0),t}var o={},a={app:0},c={app:0},i=[],r=[];function u(t){return s.p+"js/"+({OAuthGoogle:"OAuthGoogle",OAuthKakao:"OAuthKakao","about~mypage":"about~mypage",about:"about",mypage:"mypage",findpassword:"findpassword",landing:"landing",login:"login",main:"main",signup:"signup",studydetail:"studydetail"}[t]||t)+"."+{OAuthGoogle:"d1a8a740",OAuthKakao:"a826f6c2","about~mypage":"e300866b",about:"42411b52",mypage:"00ba8205",findpassword:"daf06089",landing:"379e3b8b",login:"959f7cfa",main:"bc3ef297",signup:"e468f488",studydetail:"2f0804bd"}[t]+".js"}function s(e){if(o[e])return o[e].exports;var n=o[e]={i:e,l:!1,exports:{}};return t[e].call(n.exports,n,n.exports,s),n.l=!0,n.exports}s.e=function(t){var e=[],n={OAuthGoogle:1,"about~mypage":1,about:1,mypage:1,findpassword:1,landing:1,login:1,main:1,signup:1,studydetail:1};a[t]?e.push(a[t]):0!==a[t]&&n[t]&&e.push(a[t]=new Promise((function(e,n){for(var o="css/"+({OAuthGoogle:"OAuthGoogle",OAuthKakao:"OAuthKakao","about~mypage":"about~mypage",about:"about",mypage:"mypage",findpassword:"findpassword",landing:"landing",login:"login",main:"main",signup:"signup",studydetail:"studydetail"}[t]||t)+"."+{OAuthGoogle:"fcaa950b",OAuthKakao:"31d6cfe0","about~mypage":"6dbc0659",about:"0ae77c96",mypage:"65bd2249",findpassword:"98d4a1e2",landing:"b5590e46",login:"9b341df0",main:"33dcaa30",signup:"bbc6963a",studydetail:"51b41b89"}[t]+".css",c=s.p+o,i=document.getElementsByTagName("link"),r=0;r<i.length;r++){var u=i[r],d=u.getAttribute("data-href")||u.getAttribute("href");if("stylesheet"===u.rel&&(d===o||d===c))return e()}var f=document.getElementsByTagName("style");for(r=0;r<f.length;r++){u=f[r],d=u.getAttribute("data-href");if(d===o||d===c)return e()}var m=document.createElement("link");m.rel="stylesheet",m.type="text/css",m.onload=e,m.onerror=function(e){var o=e&&e.target&&e.target.src||c,i=new Error("Loading CSS chunk "+t+" failed.\n("+o+")");i.code="CSS_CHUNK_LOAD_FAILED",i.request=o,delete a[t],m.parentNode.removeChild(m),n(i)},m.href=c;var l=document.getElementsByTagName("head")[0];l.appendChild(m)})).then((function(){a[t]=0})));var o=c[t];if(0!==o)if(o)e.push(o[2]);else{var i=new Promise((function(e,n){o=c[t]=[e,n]}));e.push(o[2]=i);var r,d=document.createElement("script");d.charset="utf-8",d.timeout=120,s.nc&&d.setAttribute("nonce",s.nc),d.src=u(t);var f=new Error;r=function(e){d.onerror=d.onload=null,clearTimeout(m);var n=c[t];if(0!==n){if(n){var o=e&&("load"===e.type?"missing":e.type),a=e&&e.target&&e.target.src;f.message="Loading chunk "+t+" failed.\n("+o+": "+a+")",f.name="ChunkLoadError",f.type=o,f.request=a,n[1](f)}c[t]=void 0}};var m=setTimeout((function(){r({type:"timeout",target:d})}),12e4);d.onerror=d.onload=r,document.head.appendChild(d)}return Promise.all(e)},s.m=t,s.c=o,s.d=function(t,e,n){s.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},s.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},s.t=function(t,e){if(1&e&&(t=s(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(s.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var o in t)s.d(n,o,function(e){return t[e]}.bind(null,o));return n},s.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return s.d(e,"a",e),e},s.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},s.p="/",s.oe=function(t){throw console.error(t),t};var d=window["webpackJsonp"]=window["webpackJsonp"]||[],f=d.push.bind(d);d.push=e,d=d.slice();for(var m=0;m<d.length;m++)e(d[m]);var l=f,p=function(){return i.push([0,"chunk-vendors"]),n()}();e([[],{},0,["main","studydetail"]])})({0:function(t,e,n){t.exports=n("56d7")},2808:function(t,e,n){"use strict";n("baba")},"365c":function(t,e,n){"use strict";n.d(e,"a",(function(){return u}));var o=n("bc3a"),a=n.n(o);n("d3b7");function c(t){return t.interceptors.request.use((function(t){return sessionStorage.getItem("accessToken")&&(t.headers.Authorization="Bearer ".concat(sessionStorage.getItem("accessToken"))),t}),(function(t){return Promise.reject(t)})),t.interceptors.response.use((function(t){return t}),(function(t){return Promise.reject(t)})),t}var i=c;function r(){var t=a.a.create({baseURL:"https://i6a501.p.ssafy.io",headers:{"Content-Type":"application/json"}});return i(t)}var u=r()},"4bfb":function(t,e,n){"use strict";n.d(e,"b",(function(){return c})),n.d(e,"m",(function(){return i})),n.d(e,"h",(function(){return r})),n.d(e,"i",(function(){return u})),n.d(e,"j",(function(){return s})),n.d(e,"d",(function(){return d})),n.d(e,"l",(function(){return p})),n.d(e,"k",(function(){return m})),n.d(e,"a",(function(){return f})),n.d(e,"g",(function(){return h})),n.d(e,"e",(function(){return b})),n.d(e,"f",(function(){return g})),n.d(e,"c",(function(){return E}));var o=n("1da1"),a=(n("96cf"),n("99af"),n("365c"));function c(t,e,n){a["a"].post("/api/studies",t).then(e).catch(n)}function i(t,e,n,o){a["a"].put("api/studies/".concat(t),e).then(n).catch(o)}function r(t,e,n){a["a"].get("/api/studies/".concat(t)).then(e).catch(n)}function u(t,e){a["a"].get("api/studies").then(t).catch(e)}function s(t,e,n){a["a"].post("api/studies/join",t).then(e).catch(n)}function d(t,e,n){a["a"].delete("api/studies/".concat(t)).then(e).catch(n)}function f(t,e,n){a["a"].get("api/studies/".concat(t,"/kanbans")).then(e).catch(n)}function m(t,e,n){return l.apply(this,arguments)}function l(){return l=Object(o["a"])(regeneratorRuntime.mark((function t(e,n,o){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,a["a"].patch("api//studies/".concat(e,"/kanbans/right")).then(n).catch(o);case 2:case"end":return t.stop()}}),t)}))),l.apply(this,arguments)}function p(t,e,n,o){a["a"].put("api/studies/".concat(e,"/kanbans"),t).then(n).catch(o)}function h(t,e,n){a["a"].get("api/studies/".concat(t,"/members")).then(e).catch(n)}function b(t,e,n,o){a["a"].get("api/studies/".concat(t,"/chat?index=").concat(e)).then(n).catch(o)}function g(t,e,n){a["a"].get("api/studies/".concat(t,"/files")).then(e).catch(n)}function E(t,e,n,o){a["a"].delete("api/studies/".concat(t,"/files/").concat(e)).then(n).catch(o)}},"56d7":function(t,e,n){"use strict";n.r(e);n("e260"),n("e6cf"),n("cca6"),n("a79d");var o=n("7a23"),a={class:"app"};function c(t,e){var n=Object(o["resolveComponent"])("router-link"),c=Object(o["resolveComponent"])("router-view");return Object(o["openBlock"])(),Object(o["createElementBlock"])("div",a,[Object(o["createVNode"])(n,{to:"/test"}),Object(o["createVNode"])(n,{to:"/"}),Object(o["createVNode"])(c)])}n("2808");var i=n("6b0d"),r=n.n(i);const u={},s=r()(u,[["render",c]]);var d=s,f=(n("d3b7"),n("3ca3"),n("ddb0"),n("6c02")),m=n("ec16"),l=Object(m["a"])(),p=l.notifySuccessDescription,h=[{path:"/",name:"Landing",component:function(){return n.e("landing").then(n.bind(null,"bede"))},meta:{authRequired:!1},beforeEnter:function(t,e,n){var o=sessionStorage.getItem("accessToken");o?n("/Main"):n()}},{path:"/main",name:"Main",component:function(){return n.e("main").then(n.bind(null,"6b61"))},meta:{authRequired:!0}},{path:"/members/login",name:"Login",component:function(){return n.e("login").then(n.bind(null,"4a90"))},meta:{authRequired:!1},beforeEnter:function(t,e,n){var o=sessionStorage.getItem("accessToken");o?n("/Main"):n()}},{path:"/members/signup",name:"SignUp",component:function(){return n.e("signup").then(n.bind(null,"6cec"))},meta:{authRequired:!1},beforeEnter:function(t,e,n){var o=sessionStorage.getItem("accessToken");o?n("/Main"):n()}},{path:"/members/mypage",name:"MyPage",component:function(){return Promise.all([n.e("about~mypage"),n.e("mypage")]).then(n.bind(null,"f250"))},meta:{authRequired:!0}},{path:"/members/findpassword",name:"FindPassword",component:function(){return n.e("findpassword").then(n.bind(null,"59b4"))},meta:{authRequired:!1},beforeEnter:function(t,e,n){var o=sessionStorage.getItem("accessToken");o?n("/Main"):n()}},{path:"/studies/create",name:"StudyCreate",component:function(){return Promise.all([n.e("about~mypage"),n.e("about")]).then(n.bind(null,"5560"))},meta:{authRequired:!0}},{path:"/studies/:studyId/update",name:"StudyUpdate",component:function(){return Promise.all([n.e("about~mypage"),n.e("about")]).then(n.bind(null,"0636"))},meta:{authRequired:!0}},{path:"/studies/:studyId",name:"StudyDetail",component:function(){return n.e("studydetail").then(n.bind(null,"9b7b"))},meta:{authRequired:!0}},{path:"/studies/:studyId/room",name:"Studies",component:function(){return Promise.all([n.e("about~mypage"),n.e("about")]).then(n.bind(null,"9084"))},meta:{authRequired:!0},props:!0},{path:"/members/kakao/callback",name:"OAuthKakao",component:function(){return n.e("OAuthKakao").then(n.bind(null,"7c4f"))}},{path:"/login/oauth2/client/google",name:"OAuthGoogle",component:function(){return n.e("OAuthGoogle").then(n.bind(null,"f7ec"))}},{path:"/404",name:"404",component:function(){return n.e("OAuthGoogle").then(n.bind(null,"4b0a"))}},{path:"/:pathMatch(.*)*",redirect:"/404"}],b=Object(f["a"])({scrollBehavior:function(){return{el:"html",top:0}},history:Object(f["b"])(),routes:h});b.beforeEach((function(t,e,n){var o=sessionStorage.getItem("accessToken");t.matched.some((function(t){return t.meta.authRequired}))&&(""==o||void 0==o)?(p("로그인이 필요한 페이지입니다!😎","로그인 페이지로 이동합니다."),n("/members/login")):n()}));var g=b,E=n("0e44"),O=n("5502"),_=n("5530"),T=n("c24f"),S=Object(m["a"])(),y=S.notifySuccess,I=S.notifyDanger,v=function(){return{userInfo:{}}},L={getUserInfo:function(t){return t.userInfo}},j={GET_USER_INFO:function(t){var e=t.commit;Object(T["c"])((function(t){e("UPDATE_USER_INFO",t.data.data)}),(function(){I("서버에 문제가 발생했습니다.😰")}))},LOGIN:function(t,e){var n=t.dispatch,o=t.commit;Object(T["d"])(e,(function(t){200===t.data.code?(sessionStorage.setItem("accessToken",t.data.data.accessToken),o("SET_USER_ACCESS_INFO",t.data.data),n("GET_USER_INFO"),g.push({name:"Main"})):404===t.data.code&&I("존재하지 않는 이메일입니다.😦")}),(function(){I("로그인 정보가 맞지 않습니다.😅")}))},LOGIN_KAKAO:function(t,e){var n=t.dispatch,o=t.commit;Object(T["f"])(e,(function(t){sessionStorage.setItem("accessToken",t.data.data.accessToken),o("SET_USER_ACCESS_INFO",t.data.data),n("GET_USER_INFO"),g.push({name:"Main"})}),(function(t){console.log(t),I("서버에 문제가 발생했습니다.😰")}))},LOGIN_GOOGLE:function(t,e){var n=t.dispatch,o=t.commit;Object(T["e"])(e,(function(t){sessionStorage.setItem("accessToken",t.data.data.accessToken),o("SET_USER_ACCESS_INFO",t.data.data),n("GET_USER_INFO"),g.push({name:"Main"})}),(function(t){console.log(t),I("서버에 문제가 발생했습니다.😰")}))},updateUserInfo:function(t,e){var n=t.commit;Object(T["l"])(e,(function(t){switch(t.data.code){case 200:n("UPDATE_USER_INFO",t.data.data),g.push({name:"Main"}),y("회원정보 수정 성공!😇");break;case 400:I("회원 인증 실패😰");break;case 404:I("회원정보 수정 실패😰")}}),(function(){I("서버에 문제가 발생했습니다.😰")}))},LOGOUT:function(t){var e=t.commit;e("LOGOUT")},SIGNOUT:function(t){var e=t.dispatch;Object(T["i"])((function(t){200===t.data.code?setTimeout((function(){e("LOGOUT"),g.push({name:"Login"}),y("떠나신다니 아쉽습니다.🥺")}),1200):404===t.data.code&&I("회원 탈퇴 실패.😰")}))}},k={SET_USER_ACCESS_INFO:function(t,e){t.userInfo.path=e.path},UPDATE_USER_INFO:function(t,e){t.userInfo=Object(_["a"])(Object(_["a"])({},t.userInfo),e)},LOGOUT:function(t){t.userInfo={},sessionStorage.removeItem("accessToken"),sessionStorage.clear()}},A={state:v,getters:L,actions:j,mutations:k},U=(n("99af"),n("4bfb")),M=n("365c"),P=Object(m["a"])(),D=P.notifyDanger,C=function(){return{studyInfo:{},studyList:[],memberList:[],fileList:[],chatList:[],fileUploadPercent:100}},w={},N={GET_STUDY_INFO:function(t,e){var n=t.commit;Object(U["h"])(e,(function(t){200===t.data.code?n("SET_STUDY_INFO",t.data.data):401===t.data.code&&(D("접근 권한이 없습니다.⛔"),history.back())}),(function(t){console.log(t)}))},GET_STUDY_LIST:function(t){var e=t.commit;Object(U["i"])((function(t){200===t.data.code&&e("SET_STUDY_LIST",t.data.data)}),(function(t){console.log(t)}))},GET_MEMBER_LIST:function(t,e){var n=t.commit;Object(U["g"])(e,(function(t){200===t.data.code&&n("SET_MEMBER_LIST",t.data.data)}),(function(t){console.log(t)}))},GET_CHAT_LIST:function(t,e){var n=t.commit,o=e.studyId,a=e.index;Object(U["e"])(o,a,(function(t){200===t.data.code&&n("SET_CHAT_LIST",t.data.data)}),(function(t){console.log("채팅 불러오기 실패"),console.log(t)}))},updateStudyInfo:function(t,e){var n=t.commit,o=e.studyId,a=e.payload;Object(U["m"])(o,a,(function(t){n("UPDATE_STUDY_INFO",t.data.data)}),(function(){}))},GET_FILE_LIST:function(t,e){var n=t.commit;Object(U["f"])(e,(function(t){200===t.data.code&&n("SET_FILE_LIST",t.data.data)}),(function(t){console.log(t)}))},UPLOAD_FILE:function(t,e){var n=t.commit,o=e.studyId,a=e.uploadFileData,c=e.success,i=e.fail;M["a"].post("api/studies/".concat(o,"/files"),a,{onUploadProgress:function(t){var e=100*t.loaded/t.total,o=Math.round(e);n("SET_FILE_UPLOAD_PERCENT",o)}}).then(c).catch(i)},DOWNLOAD_FILE:function(t,e){var n=t.commit,o=e.studyId,a=e.fileId,c=e.success,i=e.fail;M["a"].get("api/studies/".concat(o,"/files/").concat(a),{onDownloadProgress:function(t){var e=100*t.loaded/t.total,o=Math.round(e);n("SET_FILE_UPLOAD_PERCENT",o)},responseType:"blob"}).then(c).catch(i)}},F={SET_STUDY_INFO:function(t,e){t.studyInfo=e},SET_STUDY_LIST:function(t,e){t.studyList=e},SET_MEMBER_LIST:function(t,e){t.memberList=e},SET_CHAT_LIST:function(t,e){t.chatList=e},UPDATE_STUDY_INFO:function(t,e){console.log("update_study_info"),t.studyInfo=Object(_["a"])(Object(_["a"])({},t.studyInfo),e)},SET_FILE_LIST:function(t,e){t.fileList=e},SET_FILE_UPLOAD_PERCENT:function(t,e){t.fileUploadPercent=e},SET_FILE_UPLOAD_PERCENT_INIT:function(t){t.fileUploadPercent=0}},R={state:C,getters:w,actions:N,mutations:F};n("159b"),n("a434");function G(t,e,n,o){M["a"].put("/api/studies/".concat(t,"/memos"),e).then(n).catch(o)}function x(t,e,n){M["a"].get("/api/studies/".concat(t,"/memos")).then(e).catch(n)}var B=Object(m["a"])(),q=B.notifySuccess,K=function(){return{memoList:[],memo:{},selectedIdx:-1,zIndexCount:1}},Y={selectedMemo:function(t){return t.selectedIdx<0?null:t.memoList.selectedIdx}},z={GET_MEMO_LIST:function(t,e){var n=t.commit;x(e,(function(t){n("SET_MEMO_LIST",t.data.data)}),(function(t){console.log(t)}))},UPDATE_MEMO_LIST:function(t,e){var n=t.commit,o=e.studyId,a=e.memoList,c=[];a.forEach((function(t){var e=t.content,n=t.color,o=t.transform;c.push({content:e,color:n,transform:o})})),G(o,c,(function(t){console.log(t.data),x(o,(function(t){n("SET_MEMO_LIST",t.data.data),q("메모 저장 완료!")}),(function(t){console.log(t)}))}),(function(t){console.log(t)}))}},H={SET_MEMO_LIST:function(t,e){t.memoList=e},ADD_MEMO:function(t,e){t.memoList.push(e)},SET_SELECTED_MEMO_INDEX:function(t,e){t.selectedIdx=e,t.zIndexCount++,t.memoList[e].zIndex=t.zIndexCount},SET_MEMO_BY_INDEX:function(t,e){var n=e.index,o=e.memo;t.memoList[n]=o},UPDATE_SELECTED_MEMO_CONTENT:function(t,e){var n=e.index,o=e.content;t.memoList[n]=Object(_["a"])(Object(_["a"])({},t.memoList[n]),{content:o})},UPDATE_SELECTED_MEMO_COLOR:function(t,e){var n=e.index,o=e.color;t.memoList[n]=Object(_["a"])(Object(_["a"])({},t.memoList[n]),{color:o})},DELETE_SELECTED_MEMO:function(t,e){t.memoList.splice(e,1),t.selectedIdx=-1}},V={state:K,getters:Y,actions:z,mutations:H},J=Object(O["a"])({modules:{user:A,study:R,memo:V},plugins:[Object(E["a"])({paths:["user"]})]}),X=J,W=n("34f3"),Q=(n("7b17"),n("7051"),n("ad3d")),Z=Object(o["createApp"])(d);Z.use(X),Z.use(g),Z.mount("#app"),Z.directive("dragscroll",W["a"]),Z.component("font-awesome-icon",Q["a"]),Z.config.globalProperties.canvas=null},baba:function(t,e,n){},c24f:function(t,e,n){"use strict";n.d(e,"j",(function(){return a})),n.d(e,"g",(function(){return c})),n.d(e,"a",(function(){return i})),n.d(e,"d",(function(){return r})),n.d(e,"c",(function(){return s})),n.d(e,"f",(function(){return d})),n.d(e,"l",(function(){return m})),n.d(e,"e",(function(){return f})),n.d(e,"k",(function(){return l})),n.d(e,"b",(function(){return p})),n.d(e,"h",(function(){return u})),n.d(e,"i",(function(){return h}));var o=n("365c");function a(t,e,n){o["a"].post("/api/members",t).then(e).catch(n)}function c(t,e,n){o["a"].post("/api/members/auth/email",t).then(e).catch(n)}function i(t,e,n){o["a"].post("/api/members/auth/email/check",t).then(e,n).catch(n)}function r(t,e,n){o["a"].post("/api/members/login",t).then(e).catch(n)}function u(t,e,n){o["a"].post("/api/members/auth/email/password",t).then(e).catch(n)}function s(t,e){o["a"].get("/api/members").then(t).catch(e)}function d(t,e,n){o["a"].post("/api/members/login/kakao",{},{params:{code:t}}).then(e).catch(n)}function f(t,e,n){o["a"].post("/api/members/login/google",{},{params:{code:t}}).then(e).catch(n)}function m(t,e,n){o["a"].put("/api/members",t).then(e).catch(n)}function l(t,e,n){o["a"].patch("/api/members/password",t).then(e).catch(n)}function p(t,e,n){o["a"].post("/api/members/password",t).then(e).catch(n)}function h(t,e){o["a"].delete("/api/members").then(t).catch(e)}},ec16:function(t,e,n){"use strict";var o=n("98a0"),a=(n("c4bb"),function(){var t=function(t){Object(o["a"])(t,{showIcon:"true",position:"bottom-right",type:"success",transition:"bounce",hideProgressBar:"true",toastBackgroundColor:"#334666"})},e=function(t,e){Object(o["a"])({title:t,description:e},{showIcon:"true",position:"bottom-right",type:"success",transition:"bounce",hideProgressBar:"true",toastBackgroundColor:"#334666"})},n=function(t){Object(o["a"])(t,{showIcon:"true",position:"bottom-right",type:"danger",transition:"bounce",hideProgressBar:"true",toastBackgroundColor:"#FF9A9F"})},a=function(t,e){Object(o["a"])({title:t,description:e},{showIcon:"true",position:"bottom-right",type:"danger",transition:"bounce",hideProgressBar:"true",toastBackgroundColor:"#FF9A9F"})};return{notifySuccess:t,notifySuccessDescription:e,notifyDanger:n,notifyDangerDescription:a}});e["a"]=a}});
//# sourceMappingURL=app.93a89a0e.js.map