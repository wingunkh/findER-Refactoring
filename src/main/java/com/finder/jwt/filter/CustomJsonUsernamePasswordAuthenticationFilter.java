package com.finder.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

// 스프링 시큐리티의 폼 기반의 UsernamePasswordAuthenticationFilter를 참고하여 만든 커스텀 필터
public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/api/login"; // 로그인 URI
    private static final String HTTP_METHOD = "POST"; // 로그인 HTTP 메소드
    private static final String CONTENT_TYPE = "application/json; charset=utf-8"; // 콘텐츠 타입
    private static final String USERNAME_KEY = "email"; // username Key
    private static final String PASSWORD_KEY = "password"; // password Key
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD); // URI 패턴과 HTTP 메소드를 비교하는 객체 생성
    private final ObjectMapper objectMapper;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER); // "/api/login" + POST로 온 요청을 처리
        this.objectMapper = objectMapper;
    }

    // 인증 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        String email = usernamePasswordMap.get(USERNAME_KEY);
        String password = usernamePasswordMap.get(PASSWORD_KEY);

        // 스프링 시큐리티에서 사용자의 아이디와 비밀번호를 나타내는 UsernamePasswordAuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);

        // AuthenticationManager로 인증을 처리하고 인증의 결과로 Authentication 객체를 반환
        // Authentication 객체를 반환하면 스프링 시큐리티 내부에서 자동으로 SecurityContextHolder에 저장
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

