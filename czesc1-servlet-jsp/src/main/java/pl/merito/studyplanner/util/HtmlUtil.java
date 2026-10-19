package pl.merito.studyplanner.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public final class HtmlUtil {
    private HtmlUtil() { }

    public static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                .replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    public static PrintWriter begin(HttpServletRequest req, HttpServletResponse resp,
                                    String title) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang='pl'><head><meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<title>" + escape(title) + " — StudyPlanner</title>");
        out.println("<link rel='stylesheet' href='" + escape(req.getContextPath() + "/style.css") + "'>");
        out.println("<style>.errors{color:#a51d20} code{overflow-wrap:anywhere} li{margin-bottom:8px}</style>");
        out.println("</head><body><main class='card'><p class='eyebrow'>StudyPlanner · etap 2</p>");
        out.println("<h1>" + escape(title) + "</h1>");
        return out;
    }

    public static void end(HttpServletRequest req, PrintWriter out) {
        out.println("<p><a href='" + escape(req.getContextPath() + "/index.html")
                + "'>Strona główna</a></p></main></body></html>");
    }
}
