package pl.merito.studyplanner.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.merito.studyplanner.util.HtmlUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet("/info")
public class RequestInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = HtmlUtil.begin(req, resp, "Informacje o żądaniu");
        out.println("<ul>");
        item(out, "Metoda", req.getMethod());
        item(out, "URI", req.getRequestURI());
        item(out, "Kontekst aplikacji", req.getContextPath());
        item(out, "Ścieżka servletu", req.getServletPath());
        item(out, "Query string", req.getQueryString());
        item(out, "Adres klienta", req.getRemoteAddr());
        item(out, "User-Agent", req.getHeader("User-Agent"));
        item(out, "Dispatcher type", req.getDispatcherType().name());
        Object note = req.getAttribute("forwardNote");
        item(out, "Atrybut forwardNote", note == null ? "brak" : note.toString());
        out.println("</ul><h2>Parametry</h2><ul>");
        req.getParameterMap().forEach((name, values) ->
                item(out, name, Arrays.toString(values)));
        out.println("</ul>");
        String[] tags = req.getParameterValues("tag");
        out.println("<p>getParameterValues(\"tag\"): <code>"
                + HtmlUtil.escape(tags == null ? "brak" : Arrays.toString(tags)) + "</code></p>");
        HtmlUtil.end(req, out);
    }

    private void item(PrintWriter out, String label, String value) {
        out.println("<li><strong>" + HtmlUtil.escape(label) + ":</strong> "
                + HtmlUtil.escape(value == null ? "brak" : value) + "</li>");
    }
}
