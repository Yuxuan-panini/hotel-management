package servlet;

import com.google.gson.JsonObject;
import dao.ReceptionDao;
import dao.TokenDao;
import entity.CheckMsg;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "/getcheckinmsg",urlPatterns = {"/getcheckinmsg"})
public class CheckInMsgServlet extends HttpServlet {

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
        int consumer_id=json.get("consumer_id").getAsInt();

        if(TokenDao.getId(token)==-1){
            Utils.sendJsonFailure(resp,0);
            return;
        }
        List<String> result= ReceptionDao.watchCheckinBill(consumer_id);
        if (!result.isEmpty()) {
            CheckMsg checkmsg=new CheckMsg(result);
            String checkInfoStr = Utils.attachCodeAsJson(checkmsg, 1);
            Utils.sendJsonResp(resp, checkInfoStr);
        }
        else {
            System.err.println("Check in msg not exist");
            Utils.sendJsonFailure(resp, 1);
        }
    }
}

