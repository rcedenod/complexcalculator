package com.complexcalc.complexcalc.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.complexcalc.complexcalc.util.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebServlet(name = "ComplexCalculatorServlet", urlPatterns = {"/api/complex/operacion"})
public class ComplexCalculatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.getWriter().write("No tiene sesion");
            return;
        }

        String tipoOperacion = req.getParameter("tipoOperacion");
        String outputFormat = req.getParameter("outputFormat");

        double valor1 = Double.parseDouble(req.getParameter("valor1"));
        double valor2 = Double.parseDouble(req.getParameter("valor2"));
        double valor3 = Double.parseDouble(req.getParameter("valor3"));
        double valor4 = Double.parseDouble(req.getParameter("valor4"));

        ComplexNumber result;
        ComplexNumber num1 = new ComplexNumber(valor1, valor2);
        ComplexNumber num2 = new ComplexNumber(valor3, valor4);

        switch (tipoOperacion) {
            case "suma":
                result = num1.sumar(num2);
                break;
            case "resta":
                result = num1.restar(num2);
                break;
            case "multiplicacion":
                result = num1.multiplicar(num2);
                break;
            case "division":
                result = num1.dividir(num2);
                break;
            default:
                resp.getWriter().write("Operación invalida");
                return;
        }

        if (outputFormat.equals("polar")) {
            resp.getWriter().write(result.toPolar());
        } else {
            resp.getWriter().write(result.toRectangular());
        }
    }
}
