package com.swith.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.swith.db.entity.Member;
import com.swith.db.repository.MemberRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member loginByKakao(String authCode) {
        // 인가코드 -> 엑세스 토큰
        String accessToken = getAccessTokenByKakao(authCode);

        // 엑세스 토큰 -> 카카오 사용자 정보
        Member kakaoUser = getMemberInfoByKakaoToken(accessToken);

        Member member = memberRepository.findByKakaoId(kakaoUser.getKakaoId()).orElse(null);
        if (member == null) {
            member = Member.builder()
                    .kakaoId(kakaoUser.getKakaoId())
                    .googleId(null)
                    .email(kakaoUser.getEmail())
                    .password(passwordEncoder.encode(kakaoUser.getKakaoId()))
                    .nickname(kakaoUser.getNickname())
                    .role(Member.Role.MEMBER)
                    .isDeleted("N")
                    .build();

            memberRepository.save(member);
        }

        return member;
    }

    @Override
    public Member loginByGoogle(String authCode) {
        // 인가 코드 -> 아이디 토큰
        String idToken = getIdTokenByGoogle(authCode);

        // 아이디 토큰 -> 구글 사용자 정보
        Member googleUser = getMemberInfoByGoogleToken(idToken);

        Member member = memberRepository.findByGoogleId(googleUser.getGoogleId()).orElse(null);
        if (member == null) {
            member = Member.builder()
                    .kakaoId(null)
                    .googleId(googleUser.getGoogleId())
                    .email(googleUser.getEmail())
                    .password(passwordEncoder.encode(googleUser.getGoogleId()))
                    .nickname(googleUser.getNickname())
                    .role(Member.Role.MEMBER)
                    .isDeleted("N")
                    .build();

            memberRepository.save(member);
        }

        return member;
    }

    private String getAccessTokenByKakao(String authCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "b87b2face727a7093e3816185ab2697c");
        params.add("code", authCode);
        params.add("redirect_uri", "http://localhost:8081/members/kakao/callback");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoTokenReq = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenReq,
                String.class
        );

        String tokenJson = response.getBody();
        JSONObject jsObject = new JSONObject(tokenJson);
        String accessToken = jsObject.getString("access_token");

        return accessToken;
    }

    private Member getMemberInfoByKakaoToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileReq = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileReq,
                String.class
        );

        JSONObject body = new JSONObject(response.getBody());

        Long id = body.getLong("id");
        String socialId = String.valueOf(id);
        String nickname = body.getJSONObject("properties").getString("nickname");

        String email = "";
        if (body.getJSONObject("kakao_account").getBoolean("has_email")) {
            if (!body.getJSONObject("kakao_account").getBoolean("email_needs_agreement")) {
                email = body.getJSONObject("kakao_account").getString("email");
            }
        }

        return Member.builder().kakaoId(socialId)
                .googleId(null).email(email).password(null)
                .nickname(nickname).role(Member.Role.MEMBER).isDeleted("N").build();
    }

    public String getIdTokenByGoogle(String authCode) {
        String idToken = "";
        String reqURL = "https://oauth2.googleapis.com/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=343513890539-mvk01v00kfnp5vdcvfu95hrnh970mtsl.apps.googleusercontent.com");
            sb.append("&redirect_uri=http://localhost:8081/login/oauth2/client/google");
            sb.append("&client_secret=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");
            sb.append("&code=" + authCode);
            bw.write(sb.toString());
            bw.flush();
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            idToken = element.getAsJsonObject().get("id_token").getAsString();
            System.out.println(idToken);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return idToken;
    }

    public Member getMemberInfoByGoogleToken(String idToken) {
        String reqURL = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
        String email = "";
        String nickname = "";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            email = element.getAsJsonObject().get("email").getAsString();
            nickname = element.getAsJsonObject().get("name").getAsString();

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Member.builder().kakaoId(null)
                .googleId(email).email(email).password(null)
                .nickname(nickname).role(Member.Role.MEMBER).isDeleted("N").build();
    }
}
