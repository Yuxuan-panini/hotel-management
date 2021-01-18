package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.BillDao;
import dao.TokenDao;
import entity.Bill;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "/getcheckoutmsg",urlPatterns = {"/getcheckoutmsg"})

public class GetCheckOutMsgServlet extends HttpServlet {

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept,"
                + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
        response.setHeader("Access-Control-Request-Headers", "Authorization,Origin, X-Requested-With,content-Type,Accept");
        response.setHeader("Access-Control-Expose-Headers", "*");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        JsonObject json = Utils.req2json(req);
        String token = json.get("token").getAsString();
        int id = json.get("consumer_id").getAsInt();
        if (TokenDao.getId(token) == -1) {
            Utils.sendJsonFailure(resp, 0);
            return;
        }

        List<Bill> bills = BillDao.findBillsByCustomerIdOut(id);
        if (bills.isEmpty()) {
            Utils.sendJsonFailure(resp, 0);
            return;
        }
        bills.sort(Comparator.comparing(Bill::getOrder_date));

        List<JsonObject> orderList = new ArrayList<>();
        JsonObject orderListJso = new JsonObject();
        for (Bill bill : bills) {
            JsonObject orderJso = new JsonObject();
            orderJso.addProperty("id", bill.getBill_id());
            orderJso.addProperty("Stay_days", bill.getOrder_time());
            orderJso.addProperty("Date_in", bill.getDate_in().toString());
            orderJso.addProperty("Date_out", bill.getDate_out().toString());
            orderJso.addProperty("Order_day", bill.getOrder_date().toString());
            orderJso.addProperty("fee", bill.getBill_fee());
            orderJso.addProperty("room_type", bill.getRoom_type());
            orderList.add(orderJso);
        }
        orderListJso.add("orderlist", new Gson().toJsonTree(orderList));
        String jsonResp = Utils.attachCodeAsJson(orderListJso, 1);
        Utils.sendJsonResp(resp, jsonResp);
    }
}
