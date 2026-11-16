package pl.merito.studyplanner.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/tasks/added")
public class TaskAddedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("lastTitle") == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        req.getRequestDispatcher("/WEB-INF/views/tasks/added.jsp").forward(req, resp);
    }
}
