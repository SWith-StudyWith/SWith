import $axios from 'axios';

export function requestLogin ({ state }, payload) {
  console.log('Login!', state, payload)
  const url = `${process.env.VUE_APP_LOCAL_URI}/members/login`
  return $axios.post(url, payload)
}

export function requestMember ({ state }, payload) {
  console.log('Member!', state, payload)
  const url = `${process.env.VUE_APP_LOCAL_URI}/members`
  const accessToken = payload.data.accessToken
  const headers = {
    Authorization: `Bearer ${accessToken}`
  }
  return $axios.get(url, headers)
}
