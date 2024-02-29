package bitmexbot.servlets;

import bitmexbot.model.User;
import bitmexbot.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/log")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException, IOException {
        String userName = request.getParameter("login");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        UserService usersService = new UserService();
        boolean isExist = usersService.checkUser(userName, password);
        if (isExist) {
            session.setAttribute("userName", userName);
            //session.setAttribute("id_user", id);
            response.sendRedirect("views/bot.jsp");
        }else
        {
            response.sendRedirect("views/faild.jsp");
        }
    }
}
