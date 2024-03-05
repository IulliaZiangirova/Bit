package bitmexbot.web.servlets;

import bitmexbot.dao.BotDao;
import bitmexbot.model.Bot;
import bitmexbot.service.BotService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@WebServlet(name = "BotServlet", value = "/bot")
public class BotServlet extends HttpServlet {
    private final BotService botService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Bot> bots = botService.findByUserId(session.getAttribute("user_id").toString());
        req.setAttribute("bots", bots);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/bot.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double step = Double.parseDouble(req.getParameter("step"));
        int level = Integer.parseInt(req.getParameter("level"));
        double coefficient = Double.parseDouble(req.getParameter("coefficient"));
        HttpSession session = req.getSession();

        botService.create




    }
}
