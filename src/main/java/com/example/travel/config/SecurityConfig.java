package com.example.travel.config;

import com.example.travel.authentication.token.TokenFilterHandler;
import com.example.travel.authentication.token.TokenOAuth2SuccessHandler;
import com.example.travel.authentication.token.TokenUtils;
import com.example.travel.authentication.user.oAuth2User.KakaoOAuth2UserService;
import com.example.travel.authentication.user.oAuth2User.NaverOAuth2UserService;
import com.example.travel.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final NaverOAuth2UserService naverService;
    private final KakaoOAuth2UserService kakaoService;
    private final TokenOAuth2SuccessHandler tokenOAuth2SuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers(HttpMethod.POST, "/users")
                            .permitAll();
                    auth.requestMatchers("/comments/list/**").permitAll();
                    auth.requestMatchers("/manage-booking/**", "/call/miss-call/admin/**",
                        "/messages/admin/**")
                            .hasRole("ADMIN");
                    auth.requestMatchers("/users/**", "/messages/**", "/booking/**", "/call/**",
                            "/messages/user/**", "/comments/**", "/like/**","/posting/**")
                            .hasRole("USER");
                    auth.anyRequest().permitAll();
                })
                .addFilterBefore(
                        new TokenFilterHandler(
                                tokenUtils, userService
                        ),
                        AuthorizationFilter.class
                )
                .oauth2Login(oauth2->oauth2
                        .userInfoEndpoint(userInfo-> userInfo
                                .userService(oAuth2UserServiceSelector()))
                        .successHandler(tokenOAuth2SuccessHandler)
                        .permitAll())
        ;
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserServiceSelector(){
        return userRequest -> {
            String registerId = userRequest.getClientRegistration().getRegistrationId();
            if ("naver".equals(registerId)){
                return naverService.loadUser(userRequest);
            }
            else if ("kakao".equals(registerId)){
                return kakaoService.loadUser(userRequest);
            }
            else {
                throw new OAuth2AuthenticationException("Unsupported provider"+ registerId);
            }
        };
    }
}
