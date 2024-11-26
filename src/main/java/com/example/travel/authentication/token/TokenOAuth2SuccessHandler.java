package com.example.travel.authentication.token;

import com.example.travel.authentication.component.UserComponent;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenOAuth2SuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    private final UserComponent userComponent;
    private final TokenUtils tokenUtils;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // This step will change to tokenService to get token
//        UserEntity user = webService.userLogin();
//
//        UserDto userDto = UserDto.dto(user);
//        String token = tokenUtils.generateToken(userDto);

        // return link to get token
        String targetUrl = String.format(
                "http://127.0.0.1:8080/travel/login"
//                "http://127.0.0.1:8080/token/oauth"
        );
        //redirect 한다
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
