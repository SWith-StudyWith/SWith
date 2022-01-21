package com.swith.config;

import com.swith.common.jwt.JwtAccessDeniedHandler;
import com.swith.common.jwt.JwtAuthenticationEntryPoint;
import com.swith.common.jwt.JwtSecurityConfig;
import com.swith.common.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // method security 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
//            CustomOAuth2UserService customOAuth2UserService
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
//        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // httpBasic disable
                .cors()

                .and()
                .csrf().disable()   // rest api이므로 csrf 보안이 필요X, token 사용 -> disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // jwt token 인증 -> session 생성 설정 해제(stateless)

                .and()
                .authorizeHttpRequests()    // HttpServletRequest에 따라 접근을 제한
//                .antMatchers("").hasRole("")  role에 따라 해당 url 접근을 허용
                .antMatchers("/members", "/members/login", "/members/auth/email", "/members/auth/email/check").permitAll()  // 해당 url 접근을 모두 허용
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
