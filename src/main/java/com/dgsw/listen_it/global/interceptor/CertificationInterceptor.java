package com.dgsw.listen_it.global.interceptor;

import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.domain.user.repository.UserRepository;
import com.dgsw.listen_it.global.annotations.Certification;
import com.dgsw.listen_it.global.jwt.JwtTokenProvider;
import com.dgsw.listen_it.global.utils.AuthorizationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class CertificationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        log.info("[{}] request uri: {}", request.getMethod(), request.getRequestURI());
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        if (!handlerMethod.getMethod().isAnnotationPresent(Certification.class)) {
            return true;
        }

        String token = AuthorizationUtils.extract(request, "Bearer");
        if (token.equals("")) {
            throw new User.AuthenticationException();
        }

        long id = Long.parseLong(tokenProvider.extractLoginFromToken(token));
        User user = userRepository.findById(id)
                .orElseThrow(User.AuthenticationException::new);
        request.setAttribute("user", user);
        return true;
    }
}
