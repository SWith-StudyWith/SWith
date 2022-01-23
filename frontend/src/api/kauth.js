const CLIENT_ID = 'b87b2face727a7093e3816185ab2697c'; // 앱 REST API 키
const REDIRECT_URI = 'http://localhost:8081/members/kakao/callback';  // redirect 될 URI, kakao dev에서 설정
const KAKAO_GET_AUTH_CODE_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`

export {
  KAKAO_GET_AUTH_CODE_URL,
}
