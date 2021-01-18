package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.LoginDao;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

//管理员登录
@WebServlet(name = "/login",urlPatterns = {"/login"})
public class ManagerLogInServlet extends HttpServlet {
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

        int id = json.get("name").getAsInt();
        String password= json.get("pwd").getAsString();
//        Customer customer = CustomerDao.findCustomer(id);
        List<String> result= LoginDao.login(id,password);

        if (!result.isEmpty()) {
            Iterator<String> ite=result.iterator();
            JsonObject jso = new JsonObject();
            jso.addProperty("code", 1);
            jso.addProperty("token", ite.next());
            jso.addProperty("permission", ite.next());
            Utils.sendJsonResp(resp, new Gson().toJson(jso));
        }
        else {
            System.err.println("Unregistered staff");
            Utils.sendJsonFailure(resp, 0);
        }
    }
}
