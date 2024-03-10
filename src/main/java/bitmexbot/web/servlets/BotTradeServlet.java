package bitmexbot.web.servlets;

import bitmexbot.client.BitmexWebSocketClientCache;
import bitmexbot.model.Bot;
import bitmexbot.service.BotService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BotTradeServlet", value = "/bot/trade")
public class BotTradeServlet extends HttpServlet {
    private final BotService botService = new BotService();
    private String botId;
    private final static BitmexWebSocketClientCache bitmexWebSocketClientCache = new BitmexWebSocketClientCache();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        botId = session.getAttribute("bot").toString();
        Bot bot = botService.findById(botId);
        req.setAttribute("bot", bot);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/bot.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("start") != null){
            if (botService.hasWorkingBot(session.getAttribute("user_id").toString())){
                resp.sendRedirect("views/failedstart.jsp");
            }
            else {botService.start(botId);
                resp.sendRedirect("/bot/trade");}
        }else if (req.getParameter("stop") != null){
            botService.stop(req.getParameter("stop"));
            resp.sendRedirect("/bot/trade");
        }
        //doGet(req, resp);

    }
}
