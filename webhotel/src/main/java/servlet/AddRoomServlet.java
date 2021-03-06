package servlet;

import com.google.gson.Gson;
import dao.ManagerDao;
import dao.TokenDao;
import entity.EditRoomInfo;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "/addroom",urlPatterns = {"/addroom"})
public class AddRoomServlet extends HttpServlet {

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
        Gson gson = new Gson();
        EditRoomInfo add_info = gson.fromJson(charReader((req)), EditRoomInfo.class);
        if(TokenDao.getId(add_info.token)==-1){
            Utils.sendJsonFailure(resp,0);
            return;
        }
        ManagerDao.addHotelRoom(
                Integer.valueOf(add_info.roommsg.hotel_id).intValue(),
                add_info.roommsg.room_type,
                add_info.roommsg.total,
                add_info.roommsg.total,
                add_info.roommsg.room_price);
        Utils.sendJsonFailure(resp, 1);//成功
    }

    String charReader(HttpServletRequest request) {
        String str, wholeStr = "";
        try{
            BufferedReader br = request.getReader();
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
            return wholeStr;
        } catch (IOException e) {
                e.printStackTrace();
        }
        return null;
    }
}

