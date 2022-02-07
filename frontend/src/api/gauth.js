const CLIENT_ID = '343513890539-mvk01v00kfnp5vdcvfu95hrnh970mtsl.apps.googleusercontent.com'; // 앱 REST API 키
// const REDIRECT_URI = 'http://localhost:8081/login/oauth2/client/google';  // redirect 될 URI, kakao dev에서 설정
const REDIRECT_URI = 'https://i6a501.p.ssafy.io/login/oauth2/client/google';  // redirect 될 URI, kakao dev에서 설정
const GOOGLE_GET_AUTH_CODE_URL = `https://accounts.google.com/o/oauth2/auth?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&scope=email profile&response_type=code`
// https://accounts.google.com/o/oauth2/auth?client_id=343513890539-mvk01v00kfnp5vdcvfu95hrnh970mtsl.apps.googleusercontent.com&redirect_uri=http://localhost:8081/login/oauth2/client/google&scope=email profile&response_type=code
// `https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/analytics.readonly&
// access_type=offline&include_granted_scopes=true&state=state_parameter_passthrough_value&
// redirect_uri=${REDIRECT_URI}&response_type=code&client_id=${CLIENT_ID}`
// const GOOGLE_GET_AUTH_CODE_URL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${CLIENT_ID}&response_type=code&redirect_uri=${REDIRECT_URI}/google&scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+openid&access_type=offline&prompt=consent`;
// https://accounts.google.com/o/oauth2/auth?client_id=343513890539-mvk01v00kfnp5vdcvfu95hrnh970mtsl.apps.googleusercontent.com&redirect_uri=http://localhost:8081/login/oauth2/client/google&scope=email profile&response_type=code
export {
  GOOGLE_GET_AUTH_CODE_URL,
}
