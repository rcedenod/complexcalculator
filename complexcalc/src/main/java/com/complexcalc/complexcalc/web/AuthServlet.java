package com.complexcalc.complexcalc.web;

import com.complexcalc.complexcalc.model.User;
import com.complexcalc.complexcalc.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@WebServlet(name = "AuthServlet", urlPatterns = {"/api/auth/login", "/api/auth/logout", "/api/auth/register"})
public class AuthServlet extends HttpServlet {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if (path.equals("/api/auth/register")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (userRepository.findByUsername(username).isPresent()) {
                resp.getWriter().write("Usuario ya registrado");
                return;
            }

            userRepository.save(new User(username, password));
            resp.getWriter().write("Registro exitoso");

        } else if (path.equals("/api/auth/login")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                req.getSession().setAttribute("user", username);
                req.getSession().setMaxInactiveInterval(10);
                resp.getWriter().write("Login exitoso");
                
            } else {
                resp.getWriter().write("Credenciales invalidas");
            }
            
        } else if (path.equals("/api/auth/logout")) {
            req.getSession().invalidate();
            resp.getWriter().write("Sesion cerrada");
        }
    }
}
