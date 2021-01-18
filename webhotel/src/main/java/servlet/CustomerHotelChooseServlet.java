package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.HotelDao;
import entity.Hotel;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CustomerHotelChooseServlet",urlPatterns = {"/CustomerHotelChooseServlet"})
public class CustomerHotelChooseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    // Post: id, hotel_name
    // 返回hotel所有属性
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject json=Utils.req2json(req);
        String hotelName =json.get("hotel_name").getAsString();
       // System.out.println(hotelName);
        Hotel hotel = HotelDao.findHotel(Integer.parseInt(hotelName));
        if (hotel == null) {
            Utils.sendJsonFailure(resp, 0);
            return;
        }
        Utils.sendJsonResp(resp, new Gson().toJson(hotel));
    }
}
