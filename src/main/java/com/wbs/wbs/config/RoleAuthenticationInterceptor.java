package com.wbs.wbs.config;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.wbs.wbs.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class RoleAuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        String path = request.getRequestURI();
        if (path.startsWith("/admin/") && (user == null || !"ADMIN".equalsIgnoreCase(user.getRole()))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        if (path.startsWith("/user/") && (user == null || !"USER".equalsIgnoreCase(user.getRole()))) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }
        return true;
    }
}
