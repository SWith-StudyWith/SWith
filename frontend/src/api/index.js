// api 기본 axios
import axios from 'axios';
import setInterceptors from './common/interceptors';

// user instance
function createUserInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL_DEV,
    headers: {
      'Content-Type': 'application/json',
    }
  })
  return setInterceptors(instance);
}
export const userInstance = createUserInstance();

// study instance
function createStudyInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL_DEV,
    headers: {
      'Content-Type': 'application/json',
    }
  })
  return setInterceptors(instance);
}
export const studyInstance = createStudyInstance();
