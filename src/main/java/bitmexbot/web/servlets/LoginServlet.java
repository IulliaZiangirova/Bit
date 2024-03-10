package bitmexbot.web.servlets;

import bitmexbot.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import bitmexbot.model.User;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/log")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/login.jsp");
        requestDispatcher.forward(req, resp);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException, IOException {
        String userName = request.getParameter("name");
        String password = request.getParameter("pass");

        HttpSession session = request.getSession();

        UserService usersService = new UserService();
        boolean isExist = usersService.checkUser(userName, password);
        if (isExist) {
            User user = usersService.findByName(userName);
            session.setAttribute("userName", userName);
            session.setAttribute("user_id", user.getId());
            response.sendRedirect("/bot");
        }else
        {
            response.sendRedirect("views/faild.jsp");
        }
    }
}
