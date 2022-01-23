import store from '../../store';

function setInterceptors(instance) {
  // 요청 인터셉터 추가
  instance.interceptors.request.use(
    function (config) {
      if (store.state.user.authToken) {
        config.headers.Authorization = `Bearer ${store.state.user.authToken}`
      }
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
  );
  // 응답 인터셉터 추가
  instance.interceptors.response.use(
    function (response) {
      return response;
    },
    function (error) {
      return Promise.reject(error);
    }
  );
  return instance;
};
export default setInterceptors;
