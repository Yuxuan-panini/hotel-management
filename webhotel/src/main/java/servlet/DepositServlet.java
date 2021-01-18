package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.CustomerDao;
import dao.DepositDao;
import entity.Customer;
import entity.Deposit;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DepositServlet",urlPatterns = {"/DepositServlet"})
public class DepositServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    // POST参数: customer_id, deposit_fee
    // 顾客存在，充值成功，Json回应: {code: 1, deposit_id: ..., deposit_fee: ..., deposit_time: ..., customer_id: ..., customer_name: ..., customer_pwd: ..., membership: ..., balance: ...}
    // 顾客存在，充值失败, Json回应: {code: 2, customer_id: ..., customer_name: ..., customer_pwd: ..., membership: ..., balance: ...}
    // 顾客不存在, Json回应: {code, 0}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("customer_id"));
        double fee = Double.parseDouble(req.getParameter("deposit_fee"));
        Customer customer = CustomerDao.findCustomer(id);

        if (customer == null) {
            Utils.sendJsonFailure(resp, 0);
            resp.sendRedirect("http://127.0.0.1:3000/homepage.html");
            System.err.println("Unregistered customer");
            return;
        }

        int depositId = DepositDao.addDeposit(id, fee);
        boolean suc = CustomerDao.setBalance(id, customer.getBalance() + fee);
        if (depositId == -1 || !suc) {
            String jsonResp = Utils.attachCodeAsJson(customer, 2);
            resp.sendRedirect("http://127.0.0.1:3000/homepage.html");
            Utils.sendJsonResp(resp, jsonResp);
            return;
        }

        Deposit deposit = DepositDao.findDeposit(depositId);
        Gson gson = new Gson();
        JsonObject customerJso = gson.toJsonTree(customer).getAsJsonObject();
        JsonObject depositJso = gson.toJsonTree(deposit).getAsJsonObject();
        JsonObject mergedJso = Utils.mergeTwoJsonObject(customerJso, depositJso);
        String jsonResp = Utils.attachCodeAsJson(mergedJso, 1);
        resp.sendRedirect("http://127.0.0.1:3000/homepage.html");
        Utils.sendJsonResp(resp, jsonResp);
    }
}