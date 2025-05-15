//package com.example.WebApp.config;
//
//import com.example.WebApp.dto.JwtResponseDto;
//import com.example.WebApp.dto.RefreshTokenDto;
//import com.example.WebApp.service.BlackListService;
//import com.example.WebApp.service.CustomUserDetailsService;
//import com.example.WebApp.service.RefreshTokenService;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.security.sasl.AuthenticationException;
//import java.io.IOException;
//@Component
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class JwtFilter extends OncePerRequestFilter {
//
//    JwtProvider jwtProvider;
//    CustomUserDetailsService userDetailsService;
//    BlackListService blackListService;
//    RefreshTokenService refreshTokenService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String token = extractToken(request);
//        System.out.println(">>> JwtFilter called. Token: " + token);
//
//        if (token != null) {
//            if (blackListService.isTokenBlacklisted(token)) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
//                return;
//            }
//
//            try {
//                if (jwtProvider.validateToken(token)) {
//                    // Токен валиден, устанавливаем аутентификацию
//                    String username = jwtProvider.extractUsername(token);
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                    System.out.println(">>> Authentication set for user: " + username);
//                } else {
//                    // Токен невалиден, пробуем обновить
//                    attemptTokenRefresh(request, response, filterChain);
//                }
//            } catch (Exception e) {
//                // В случае ошибки при валидации токена
//                SecurityContextHolder.clearContext();
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void attemptTokenRefresh(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
//        // Получаем refresh token из куков
//        String refreshToken = null;
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("refresh_token".equals(cookie.getName())) {
//                    refreshToken = cookie.getValue();
//                    break;
//                }
//            }
//        }
//
//        if (refreshToken != null) {
//            try {
//                // Пытаемся обновить токен
//                JwtResponseDto newTokens = refreshTokenService.refresh(new RefreshTokenDto(refreshToken));
//
//                // Обновляем куки
//                Cookie newAccessTokenCookie = new Cookie("access_token", newTokens.getAccessToken());
//                newAccessTokenCookie.setPath("/");
//                newAccessTokenCookie.setHttpOnly(true);
//                response.addCookie(newAccessTokenCookie);
//
//                Cookie newRefreshTokenCookie = new Cookie("refresh_token", newTokens.getRefreshToken());
//                newRefreshTokenCookie.setPath("/");
//                newRefreshTokenCookie.setHttpOnly(true);
//                response.addCookie(newRefreshTokenCookie);
//
//                // Устанавливаем новую аутентификацию
//                String username = jwtProvider.extractUsername(newTokens.getAccessToken());
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, newTokens.getAccessToken(), userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//
//                // Перенаправляем запрос с новым токеном
//                String newRequestUrl = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
////                response.sendRedirect(newRequestUrl);
//                filterChain.doFilter(request, response);
//                return;
//            } catch (Exception e) {
//                // Не удалось обновить токен - очищаем контекст и куки
//                SecurityContextHolder.clearContext();
//                clearAuthCookies(response);
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired. Please login again.");
//                return;
//            }
//        }
//    }
//
//    private void clearAuthCookies(HttpServletResponse response) {
//        Cookie accessTokenCookie = new Cookie("access_token", null);
//        accessTokenCookie.setPath("/");
//        accessTokenCookie.setHttpOnly(true);
//        accessTokenCookie.setMaxAge(0);
//        response.addCookie(accessTokenCookie);
//
//        Cookie refreshTokenCookie = new Cookie("refresh_token", null);
//        refreshTokenCookie.setPath("/");
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setMaxAge(0);
//        response.addCookie(refreshTokenCookie);
//    }
//
//    private String extractToken(HttpServletRequest request) {
//        String header = request.getHeader("Authorization");
//        if (header != null && header.startsWith("Bearer ")) {
//            return header.substring(7);
//        }
//
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("access_token".equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//
//        return null;
//    }
//}


package com.example.WebApp.config;

import com.example.WebApp.dto.JwtResponseDto;
import com.example.WebApp.dto.RefreshTokenDto;
import com.example.WebApp.service.BlackListService;
import com.example.WebApp.service.CustomUserDetailsService;
import com.example.WebApp.service.RefreshTokenService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtFilter extends OncePerRequestFilter {

    JwtProvider jwtProvider;
    CustomUserDetailsService userDetailsService;
    BlackListService blackListService;
    RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);
        System.out.println(">>> JwtFilter called. Token: " + token);

        if (token != null) {
            if (blackListService.isTokenBlacklisted(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                return;
            }

            try {
                if (jwtProvider.validateToken(token)) {
                    String username = jwtProvider.extractUsername(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println(">>> Authentication set for user: " + username);
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    attemptTokenRefresh(request, response, filterChain);
                    return;
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                clearAuthCookies(response);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            // Попытка обновления, если access_token отсутствует, но есть refresh_token
            attemptTokenRefresh(request, response, filterChain);
            return;
        }
    }

    private void attemptTokenRefresh(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("REFRESH");
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken != null) {
            try {
                JwtResponseDto newTokens = refreshTokenService.refresh(new RefreshTokenDto(refreshToken));

                Cookie newAccessTokenCookie = new Cookie("access_token", newTokens.getAccessToken());
                newAccessTokenCookie.setPath("/");
                newAccessTokenCookie.setHttpOnly(true);
//                newAccessTokenCookie.setSecure(true); // если используется HTTPS
                response.addCookie(newAccessTokenCookie);

                Cookie newRefreshTokenCookie = new Cookie("refresh_token", newTokens.getRefreshToken());
                newRefreshTokenCookie.setPath("/");
                newRefreshTokenCookie.setHttpOnly(true);
//                newRefreshTokenCookie.setSecure(true); // если используется HTTPS
                response.addCookie(newRefreshTokenCookie);

                String username = jwtProvider.extractUsername(newTokens.getAccessToken());
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, newTokens.getAccessToken(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);

                filterChain.doFilter(request, response);
                System.out.println("SUCCESS");

            } catch (Exception e) {
                System.out.println("FAILED");
                System.out.println(e.getMessage());
                System.out.println(e.getCause().toString());

                SecurityContextHolder.clearContext();
                clearAuthCookies(response);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired. Please login again.");
            }
        } else {
            System.out.println("&&&");
            filterChain.doFilter(request, response); // Нет refresh токена — просто продолжаем
        }
    }

    private void clearAuthCookies(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("access_token", null);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refresh_token", null);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
