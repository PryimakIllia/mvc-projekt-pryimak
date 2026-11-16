package pl.merito.studyplanner.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/tasks/add")
public class TaskFormServlet extends HttpServlet {
    private static final String VIEW = "/WEB-INF/views/tasks/form.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String title = clean(req.getParameter("title"));
        String subject = clean(req.getParameter("subject"));
        String minutes = clean(req.getParameter("estimatedMinutes"));
        Map<String, String> errors = validate(title, subject, minutes);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        // Potwierdzenie i licznik należą do sesji, a nie do bazy danych.
        HttpSession session = req.getSession();
        synchronized (session) {
            Integer count = (Integer) session.getAttribute("addedCount");
            session.setAttribute("lastTitle", title);
            session.setAttribute("addedCount", count == null ? 1 : count + 1);
        }
        resp.sendRedirect(req.getContextPath() + "/tasks/added");
    }

    private static String clean(String value) {
        return value == null ? "" : value.strip();
    }

    private static Map<String, String> validate(String title, String subject, String minutes) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (title.isEmpty()) {
            errors.put("title", "Nazwa zadania jest wymagana.");
        } else if (title.length() > 120) {
            errors.put("title", "Nazwa zadania może mieć maksymalnie 120 znaków.");
        }
        if (subject.isEmpty()) {
            errors.put("subject", "Przedmiot jest wymagany.");
        } else if (subject.length() > 80) {
            errors.put("subject", "Przedmiot może mieć maksymalnie 80 znaków.");
        }
        try {
            int duration = Integer.parseInt(minutes);
            if (duration < 1 || duration > 1440) {
                errors.put("estimatedMinutes", "Czas wykonania musi mieścić się w zakresie 1–1440 minut.");
            }
        } catch (NumberFormatException e) {
            errors.put("estimatedMinutes", "Czas wykonania musi być liczbą całkowitą.");
        }
        return errors;
    }
}
