package servlet;

import com.google.gson.JsonObject;
import dao.CustomerDao;
import entity.Customer;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CustomerInfoServlet",urlPatterns = {"/CustomerInfoServlet"})
public class CustomerInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    // POST参数: username, password
    // 找到顾客，Json回应: {code: 1, userid: ...}
    // 找不到顾客，Json回应: {code: 0}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 如果不存在session会话，则创建一个session对象
        HttpSession session = req.getSession(true);
        String name = req.getParameter("username");
        String pwd = req.getParameter("password");
        Customer customer = CustomerDao.findCustomer(name, pwd);
        if (customer != null) {
            long id = customer.getCustomer_id();
            // 在session中存储costomer id
            session.setAttribute("id", id);
            JsonObject jso = new JsonObject();
            jso.addProperty("userid", id);
            String customerInfoStr = Utils.attachCodeAsJson(jso, 1);

            resp.sendRedirect("http://127.0.0.1:3000/homepage.html");
            Utils.sendJsonResp(resp, customerInfoStr);
        }
        else {
            System.err.println("Unregistered customer");

            resp.sendRedirect("http://127.0.0.1:3000");
            Utils.sendJsonFailure(resp, 0);
        }
    }
}
