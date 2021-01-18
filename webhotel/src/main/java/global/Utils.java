package global;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Failure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Utils {
    public static void sendJsonFailure(HttpServletResponse resp, int code) throws IOException {
        Failure failure = new Failure(code);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(new Gson().toJson(failure));
        out.flush();
    }

    // 发送Json格式的HTTP Response
    public static void sendJsonResp(HttpServletResponse resp, String jsonResp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(jsonResp);
        out.flush();
    }

    public static String attachCodeAsJson(Object obj, int code) {
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(obj);
        element.getAsJsonObject().addProperty("code", code);
        return gson.toJson(element);
    }

    public static String attachCodeAsJson(Object obj) {
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(obj);
        return gson.toJson(element);
    }

    public static JsonObject mergeTwoJsonObject(JsonObject jso1, JsonObject jso2) {
        JsonObject merged = jso1.deepCopy();
        for (Map.Entry<String, JsonElement> entry : jso1.entrySet()) {
            merged.add(entry.getKey(), entry.getValue().deepCopy());
        }
        return merged;
    }

    public static JsonObject req2json(HttpServletRequest req) throws ServletException, IOException {
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputstr;
        BufferedReader streamReader = req.getReader();
        while((inputstr=streamReader.readLine())!=null)
            responseStrBuilder.append(inputstr);
        JsonObject json = new JsonParser().parse(responseStrBuilder.toString()).getAsJsonObject();
        return json;
    }

    public static void setRespHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept,"
                + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
        response.setHeader("Access-Control-Request-Headers", "Authorization,Origin, X-Requested-With,content-Type,Accept");
        response.setHeader("Access-Control-Expose-Headers", "*");
    }
}

