package servlet;

import com.google.gson.JsonObject;
import dao.ReceptionDao;
import dao.TokenDao;
import entity.RoomList;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "/getroommsg",urlPatterns = {"/getroommsg"})
public class GetRoomMsgServlet extends HttpServlet {

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
        int rcpid=TokenDao.getId(token);
        if(rcpid==-1){
            Utils.sendJsonFailure(resp,0);
            return;
        }
        int hotel_id= ReceptionDao.getHotelId(rcpid);
        System.out.println(hotel_id);
        RoomList result= ReceptionDao.watchRooms(hotel_id);
        if (result!=null) {
            Utils.sendJsonResp(resp,Utils.attachCodeAsJson(result, 1));
        }
        else {
            System.err.println("Check room failed");
            Utils.sendJsonFailure(resp, 1);
        }
    }
}

