package servlet;

import com.google.gson.JsonObject;
import dao.ManagerDao;
import dao.TokenDao;
import entity.HotelRoomInfo;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/gethotelop",urlPatterns = {"/gethotelop"})
public class GetHotelOpServlet extends HttpServlet {

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

    //分经理获得所有他所管理的酒店的房间信息
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        JsonObject json = Utils.req2json(req);
        String token = json.get("token").getAsString();
        int id= TokenDao.getId(token);
        if(id==-1){
            Utils.sendJsonFailure(resp,0);
            return;
        }
        HotelRoomInfo result= ManagerDao.printRoom(id);
        if (result!=null) {
            String InfoStr = Utils.attachCodeAsJson(result, 1);
            Utils.sendJsonResp(resp, InfoStr);
        }
        else {
            System.err.println("error");
            Utils.sendJsonFailure(resp, 1);
        }
    }
}

