package db_groupwork.hotel.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db_groupwork.hotel.entity.Hotel;
import db_groupwork.hotel.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.rmi.server.ExportException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

@Controller
public class billController {
    @Autowired
    BillService billService;

    @GetMapping("/reserve")
    public String  reserve(){
        return "reserve";
    }

    @GetMapping("/order")
    public String  order(){
        return "order";
    }

    @ResponseBody
    @PostMapping("/order")
    public void history(HttpServletResponse response, HttpSession session) {
        int customerId = Integer.parseInt(session.getAttribute("customer_id").toString());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        JsonElement j = billService.getHisoryBill(customerId);
        try{
        response.getWriter().println(j);
        }catch (Exception e){
            e.printStackTrace();
        }
        //return billService.getHisoryBill(customerId);
    }

    @ResponseBody
    @GetMapping("/gethotels")
    public List<String> hotels(){
        List<String> hotels = billService.getHotelList();
        return hotels;
    }

    @ResponseBody
    @GetMapping("/getrooms")
    public List<String> rooms(){
        List<String> rooms = billService.getHotelList();
        return rooms;
    }

    @PostMapping("/hotel")
    public String hotel(HttpServletRequest request, HttpSession session){
        String hotelname = request.getParameter("select_hotel");
        System.out.println(hotelname);

        int hotelid = billService.getHotelid(hotelname);
        System.out.println(hotelid);

        Hotel hotel = new Hotel(hotelid, hotelname);
        hotel.setHotelId(hotelid);
        hotel.setHotelName(hotelname);
        System.out.println();


        session.setAttribute("hotel", hotel);
        List<String> hoteltypes = billService.getRoomType(hotelid);
        session.setAttribute("roomtype",hoteltypes);
        System.out.println(hoteltypes);

        return "booking";

    }

    @PostMapping("/room")
    public String room(HttpServletRequest request, HttpSession session){

        int customerid = Integer.parseInt(request.getParameter("customer_id"));
        System.out.println(customerid);
        int hotelid = Integer.parseInt(request.getParameter("hotel_id"));
        System.out.println(hotelid);

        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
        String date1 = request.getParameter("checkin");
        String date2 = request.getParameter("checkout");
        String room_type = request.getParameter("room_type");
        System.out.println("date1:"+date1);
        System.out.println("date2:"+date2);
        System.out.println("romm_type:"+room_type);



            //返回form表单数据
//            Date checkin = new Date();
//            Date checkout = new Date();
            Date checkin = java.sql.Date.valueOf(date1);
            Date checkout = java.sql.Date.valueOf(date2);

            System.out.println("checkin:"+checkin);
            System.out.println("checkout:"+checkout);

            boolean result = billService.makeOrder(customerid, hotelid, checkin, checkout, room_type);
            if(result == true){
                return "homepage";
            }
            else{
                return "booking";
            }



    }

    @PostMapping("/off")
    public String off(HttpServletRequest request, HttpSession session){

        int orderid = Integer.parseInt(request.getParameter("bill_id"));
        System.out.println(orderid);
        int customerid = Integer.parseInt(session.getAttribute("customer_id").toString());
        System.out.println(customerid);
        boolean res = billService.cancel(orderid,customerid);
        if(res)
            return "homepage";
        else
            return "order";
    }

}
