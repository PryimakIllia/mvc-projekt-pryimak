package pl.merito.studyplanner.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.merito.studyplanner.util.HtmlUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/tasks/add")
public class TaskFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        renderForm(req, resp, List.of(), "", "", "");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        // Oryginalne wartości zachowujemy, aby odtworzyć formularz po błędzie.
        String title = value(req.getParameter("title"));
        String subject = value(req.getParameter("subject"));
        String minutes = value(req.getParameter("estimatedMinutes"));
        List<String> errors = new ArrayList<>();

        // Walidacja odbywa się na serwerze, także przy pominięciu przeglądarki.
        if (title.isBlank()) errors.add("Nazwa zadania jest wymagana.");
        else if (title.strip().length() > 120) errors.add("Nazwa zadania może mieć maksymalnie 120 znaków.");
        if (subject.isBlank()) errors.add("Przedmiot jest wymagany.");
        else if (subject.strip().length() > 80) errors.add("Przedmiot może mieć maksymalnie 80 znaków.");
        try {
            int duration = Integer.parseInt(minutes.strip());
            if (duration < 1 || duration > 1440)
                errors.add("Czas wykonania musi mieścić się w zakresie 1–1440 minut.");
        } catch (NumberFormatException e) {
            errors.add("Czas wykonania musi być liczbą całkowitą.");
        }

        if (!errors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            renderForm(req, resp, errors, title, subject, minutes);
            return;
        }

        // Etap 2 demonstruje obsługę formularza. Nie zapisujemy jeszcze danych w bazie.
        // POST -> 302 -> GET: odświeżenie potwierdzenia nie ponawia formularza.
        String encodedTitle = URLEncoder.encode(title.strip(), StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/tasks/added?title=" + encodedTitle);
        return;
    }

    private String value(String text) {
        return text == null ? "" : text;
    }

    private void renderForm(HttpServletRequest req, HttpServletResponse resp,
                            List<String> errors, String title, String subject,
                            String minutes) throws IOException {
        PrintWriter out = HtmlUtil.begin(req, resp, "Dodaj zadanie");
        if (!errors.isEmpty()) {
            out.println("<div class='errors' role='alert'><h2>Popraw dane</h2><ul>");
            for (String error : errors) out.println("<li>" + HtmlUtil.escape(error) + "</li>");
            out.println("</ul></div>");
        }
        out.println("<p>Podaj nazwę zadania, przedmiot i przewidywany czas wykonania.</p>");
        out.println("<form method='post' accept-charset='UTF-8' action='"
                + HtmlUtil.escape(req.getContextPath() + "/tasks/add") + "'>");
        field(out, "title", "Nazwa zadania", title);
        field(out, "subject", "Przedmiot", subject);
        field(out, "estimatedMinutes", "Czas wykonania w minutach (1–1440)", minutes);
        out.println("<button type='submit'>Zatwierdź zadanie</button></form>");
        out.println("<p class='note'>Etap 2: sprawdzamy formularz i przekierowanie. Dane nie są jeszcze zapisywane.</p>");
        HtmlUtil.end(req, out);
    }

    private void field(PrintWriter out, String name, String label, String value) {
        // type=text celowo pozwala sprawdzić walidację serwera także dla np. abc.
        out.println("<label for='" + name + "'>" + HtmlUtil.escape(label) + "</label>");
        out.println("<input type='text' id='" + name + "' name='" + name
                + "' value='" + HtmlUtil.escape(value) + "'>");
    }
}
