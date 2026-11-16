package pl.merito.studyplanner.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/start")
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        show(req, resp, "Witaj w StudyPlanner!", "Zaplanuj zadania i uporządkuj naukę.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        if (name == null || name.isBlank() || name.strip().length() > 80) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            show(req, resp, "Sprawdź swoje imię", "Wpisz imię zawierające od 1 do 80 znaków.");
            return;
        }
        show(req, resp, "Cześć, " + name.strip() + "!", "Formularz został odebrany poprawnie.");
    }

    private void show(HttpServletRequest req, HttpServletResponse resp, String heading, String message)
            throws ServletException, IOException {
        req.setAttribute("heading", heading);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/WEB-INF/views/start.jsp").forward(req, resp);
    }
}
