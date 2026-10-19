package pl.merito.studyplanner.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/lifecycle", loadOnStartup = 1)
public class LifecycleServlet extends HttpServlet {
    // Wspólny, bezpieczny wątkowo licznik; nie przechowuje danych użytkownika.
    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public void init() {
        log("init(): LifecycleServlet utworzony");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log("service(): " + req.getMethod() + " " + req.getRequestURI());
        // Zachowujemy standardowe kierowanie do doGet/doPost.
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int n = counter.incrementAndGet();
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().println("Obsłużono żądań GET od uruchomienia: " + n);
    }

    @Override
    public void destroy() {
        log("destroy(): LifecycleServlet usuwany, obsłużono " + counter.get() + " żądań GET");
    }
}
