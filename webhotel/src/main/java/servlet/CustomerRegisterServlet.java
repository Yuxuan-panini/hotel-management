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
import java.io.IOException;

@WebServlet(name = "CustomerRegisterServlet",urlPatterns = "/CustomerRegisterServlet")
public class CustomerRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    // POST参数: username, userid, password
    // 成功注册，Json回应: {code: 1}
    // 注册失败，Json回应: {code: 0}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("registerUsername");
        int id = Integer.parseInt(req.getParameter("registerID"));
        String pwd = req.getParameter("registerPassword");
        Customer customer = CustomerDao.findCustomer(name, pwd);
        if (customer != null) {
            System.err.println("Customer already exists");
            Utils.sendJsonFailure(resp, 0);
            resp.sendRedirect("http://127.0.0.1:3000/register.html");
            return;
        }
        boolean res = CustomerDao.addCustomer(name, id, pwd);
        if (!res) {
            System.err.println("Failed to insert new customer record into database");

            resp.sendRedirect("http://127.0.0.1:3000/register.html");
            Utils.sendJsonFailure(resp, 0);
            return;
        }
        String jsonResp = Utils.attachCodeAsJson(new JsonObject(), 1);

        resp.sendRedirect("http://127.0.0.1:3000");
        Utils.sendJsonResp(resp, jsonResp);
    }
}
