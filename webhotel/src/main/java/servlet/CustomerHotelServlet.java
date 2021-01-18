package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.BillDao;
import dao.HotelDao;
import entity.Bill;
import entity.Hotel;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "CustomerHotelServlet",urlPatterns = {"/CustomerHotelServlet"})
public class CustomerHotelServlet extends HttpServlet {

    // req: none
    // 找到: {"hotel_name": ...}
    // 找不到: {"code": 0}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            Utils.sendJsonFailure(resp, 0);
            return;
        }
        int id = (Integer) session.getAttribute("id");
        List<Bill> bills = BillDao.findBillsByCustomerId(id);
        if (bills.isEmpty()) {
            Utils.sendJsonFailure(resp, 0);
            return;
        }
        bills.sort(Comparator.comparing(Bill::getDate_in));
        Bill bill = bills.get(bills.size() - 1);
        Hotel hotel = HotelDao.findHotel(bill.getHotel_id());
        if (hotel == null) {
            Utils.sendJsonFailure(resp, 0);
            return;
        }
        JsonObject jso = new JsonObject();
        jso.addProperty("hotel_name", hotel.getHotel_id());
        Utils.sendJsonResp(resp, new Gson().toJson(jso));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
