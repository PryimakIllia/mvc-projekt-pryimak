package pl.merito.studyplanner.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.merito.studyplanner.util.HtmlUtil;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/tasks/added")
public class TaskAddedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        if (title == null || title.isBlank() || title.strip().length() > 120) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Brak poprawnej nazwy zadania.");
            return;
        }
        PrintWriter out = HtmlUtil.begin(req, resp, "Formularz przyjęty");
        out.println("<p>Zadanie: <strong>" + HtmlUtil.escape(title) + "</strong></p>");
        out.println("<p>Walidacja zakończyła się pomyślnie. To odpowiedź GET po przekierowaniu.</p>");
        out.println("<p class='note'>To demonstracja PRG, bez trwałego zapisu zadania.</p>");
        out.println("<a class='button' href='" + HtmlUtil.escape(req.getContextPath() + "/tasks/add")
                + "'>Dodaj kolejne zadanie</a>");
        HtmlUtil.end(req, out);
    }
}
