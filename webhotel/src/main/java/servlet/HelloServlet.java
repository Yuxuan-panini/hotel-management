package servlet;

import com.google.gson.JsonObject;
import global.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 测试用Servlet接口
@WebServlet(name = "HelloServlet",urlPatterns = {"/HelloServlet"})
public class HelloServlet extends HttpServlet{

    public HelloServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("Hello, doGet() called");
        writer.flush();
    } //get请求方法

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");

        JsonObject json = Utils.req2json(req);
        System.out.println(json.get("name"));
        System.out.println(json.get("pwd"));
        System.out.println(json.getAsJsonArray("list").get(0));

        PrintWriter writer = resp.getWriter();
        writer.write("Hello, doPost() called");
        writer.flush();
    } //post请求方法
}
