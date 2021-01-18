package servlet;

import com.google.gson.JsonObject;
import dao.CustomerDao;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UnsubscribeServlet", urlPatterns = { "/unsubscribe" })
public class UnsubscribeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        JsonObject json = Utils.req2json(req);
        int id = json.get("order_id").getAsInt();
        boolean result = CustomerDao.Unsubscribe(id);
        resp.sendRedirect("http://127.0.0.1:3000/homepage.html");
//        if (list != null) {
//            String customerInfoStr = Utils.attachCodeAsJson(list);
//            Utils.sendJsonResp(resp, customerInfoStr);
//        }
//        else {
//            System.err.println("error");
////            Utils.sendJsonFailure(resp, 0);
//        }
    }
}