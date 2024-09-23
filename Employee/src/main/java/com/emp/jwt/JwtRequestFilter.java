package com.emp.jwt;

import com.emp.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // Bypass JWT authentication for specific paths (Swagger, getToken)
        if (requestURI.startsWith("/zindgi/swagger-ui") ||
                requestURI.startsWith("/zindgi/v3/api-docs") ||
                requestURI.startsWith("/zindgi/swagger-resources") ||
                requestURI.startsWith("/zindgi/webjars/") ||
                requestURI.startsWith("/zindgi/swagger-ui.html") ||
                requestURI.equals("/getToken")) {
            return true;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is missing or doesn't start with "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Missing Token");
            return false;
        }

        final String token = authorizationHeader.substring(7); // Extract token from header

        try {
            jwtUtil.validateToken(token); // Validate the token

            // Optionally, you can set the username or other information in the request attributes if needed
            String username = jwtUtil.extractUsername(token);
            // request.setAttribute("username", username);

        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
            return false;
        } catch (JwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            return false;
        }

        return true;
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
