package miniblog.controller;

import miniblog.model.BlogMessage;

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
        if(id == null){
            req.getRequestDispatcher("miniblog/blog.jsp").forward(req, resp);
        } else {
            removeMessageFromSessionMessageList(id);
        }
        req.getRequestDispatcher("/miniblog/blog.jsp").forward(req, resp);
    }

    private void removeMessageFromSessionMessageList(Long id) {
        List<BlogMessage> blogMessages = (List<BlogMessage>) getServletContext().getAttribute("messagesList");

        if(blogMessages == null || blogMessages.isEmpty()) {
            return;
        }

        List<BlogMessage> updatedMessages = blogMessages
                .stream()
                .filter(x -> !x.getId().equals(id))
                .collect(Collectors.toList());

        getServletContext().setAttribute("messagesList", updatedMessages);
    }
}
