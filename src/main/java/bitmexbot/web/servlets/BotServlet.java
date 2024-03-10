package bitmexbot.web.servlets;


import bitmexbot.model.Bot;
import bitmexbot.service.BotService;
import bitmexbot.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
;


@WebServlet(name = "BotServlet", value = "/bot")
public class BotServlet extends HttpServlet {
    private final BotService botService = new BotService();
    private final UserService userService = new UserService();
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
        if (req.getParameter("choose") != null){
            HttpSession session = req.getSession();
            session.setAttribute("bot", req.getParameter("choose"));
            resp.sendRedirect("/bot/trade");
        }else if (req.getParameter("delete") != null){
            botService.delete(botService.findById(req.getParameter("delete")));
            resp.sendRedirect("/bot");
        }else if (req.getParameter("create") != null) {
            double step = Double.parseDouble(req.getParameter("step"));
            int level = Integer.parseInt(req.getParameter("level"));
            double coefficient = Double.parseDouble(req.getParameter("coefficient"));
            HttpSession session = req.getSession();
            Bot bot = Bot.builder().
                    step(step).
                    level(level).
                    coefficient(coefficient).
                    workingIndicator(false).
                    user(userService.findById(session.getAttribute("user_id").toString())).
                    build();
            botService.create(bot);
            resp.sendRedirect("/bot");
        }
        //doGet(req, resp);
        //resp.sendRedirect("/bot");
    }
}
