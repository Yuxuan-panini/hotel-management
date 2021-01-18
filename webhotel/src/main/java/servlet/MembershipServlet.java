package servlet;

import dao.CustomerDao;
import entity.Customer;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MembershipServlet",urlPatterns = {"/MembershipServlet"})
public class MembershipServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    // POST参数: customer_id, membership_fee
    // 找到顾客，扣费成功，Json回应: {code: 1, customer_id: ..., customer_name: ..., customer_pwd: ..., membership: ..., balance: ...}
    // 找到顾客，扣费失败，Json回应：{code: 2, customer_id: ..., customer_name: ..., customer_pwd: ..., membership: ..., balance: ...}
    // 找不到顾客，Json回应: {code: 0}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("customer_id"));
        int fee = Integer.parseInt(req.getParameter("membership_fee"));
        Customer customer = CustomerDao.findCustomer(id);
        if (customer == null) {
            System.err.println("Unregistered customer");
            Utils.sendJsonFailure(resp, 0);
        }
        else {
            if (customer.getBalance() < fee) {
                String jsonResp = Utils.attachCodeAsJson(customer, 2);
                Utils.sendJsonResp(resp, jsonResp);
            }
            else {
                customer.setMembership(true);
                customer.setBalance(customer.getBalance() - fee);
                String jsonResp = Utils.attachCodeAsJson(customer, 1);
                Utils.sendJsonResp(resp, jsonResp);
            }
        }
    }
}