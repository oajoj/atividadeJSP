package miniblog.controller;

import miniblog.model.BlogMessage;
import miniblog.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/formControllerDelete")
public class FormControllerDelete extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        User user = (User) req.getSession().getAttribute("user");

        if(id == null){
            req.getRequestDispatcher("miniblog/blog.jsp").forward(req, resp);
        } else {
            removeMessageFromSessionMessageList(id, user);
        }
        req.getRequestDispatcher("/miniblog/blog.jsp").forward(req, resp);
    }

    private void removeMessageFromSessionMessageList(Long id, User user) {
        List<BlogMessage> blogMessages = (List<BlogMessage>) getServletContext().getAttribute("messagesList");
        if(blogMessages == null || blogMessages.isEmpty()) {
            return;
        }

        blogMessages.removeIf(obj -> obj.getId().equals(id) && obj.getUser().getName().equals(user.getName()));

        getServletContext().setAttribute("messagesList", blogMessages);
    }
}
