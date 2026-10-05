package pl.merito.studyplanner.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/start")
public class StartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        renderPage(req, resp,
                "Witaj w StudyPlanner!",
                "Tutaj będziesz planować zadania, śledzić terminy i organizować naukę.",
                "GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // Ustawiamy kodowanie przed odczytaniem danych z formularza.
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");

        if (name == null || name.isBlank() || name.strip().length() > 80) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            renderPage(req, resp,
                    "Sprawdź swoje imię",
                    "Wpisz imię zawierające od 1 do 80 znaków.",
                    "POST");
            return;
        }

        renderPage(req, resp,
                "Cześć, " + name.strip() + "!",
                "Witaj w swoim planerze nauki. Formularz został odebrany poprawnie.",
                "POST");
    }

    private void renderPage(HttpServletRequest req, HttpServletResponse resp,
                            String heading, String message, String method)
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String contextPath = req.getContextPath();
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='pl'><head><meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<title>StudyPlanner — odpowiedź</title>");
        out.println("<link rel='stylesheet' href='" + escapeHtml(contextPath + "/style.css") + "'>");
        out.println("</head><body><main class='card'>");
        out.println("<p class='eyebrow'>StudyPlanner · etap 1</p>");
        out.println("<h1>" + escapeHtml(heading) + "</h1>");
        out.println("<p>" + escapeHtml(message) + "</p>");
        out.println("<p class='method'>Metoda żądania: " + escapeHtml(method) + "</p>");
        out.println("<a class='button' href='" + escapeHtml(contextPath + "/index.html")
                + "'>Powrót do strony głównej</a>");
        out.println("</main></body></html>");
    }

    // Tekst z formularza wyświetlamy jako tekst, a nie jako kod HTML.
    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
