package db_groupwork.hotel.controller;

import db_groupwork.hotel.entity.Customer;
import db_groupwork.hotel.entity.Deposit;
import db_groupwork.hotel.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class customerController {
    @Autowired
    CustomerService customerService;

    // ========================================================================
    // 请求网页部分
    // ========================================================================
    @GetMapping("/")
    public String getindex(){
        return "index";
    }

    @GetMapping("/regist")
    public String getregist(){
        return "register";
    }

    @GetMapping("/homepage")
    public String gethomepage(){
        return "homepage";
    }

    @GetMapping("/recharge")
    public String getrecharge(){
        return "VIP_recharged";
    }

    // ========================================================================
    // 后端逻辑处理部分
    // ========================================================================

    @PostMapping("/regist")
    public String regist(HttpServletRequest request){

        String customername = request.getParameter("registerUsername");
        String password = request.getParameter("registerPassword");

        if(customerService .regist(customername,password)){
            return "index";
        }
        else{
            return "register";
        }
    }

    @PostMapping("/index")
    public String login(HttpServletRequest request, HttpSession session){

        String customername = request.getParameter("username");
        String password = request.getParameter("password");

        int login_result = customerService.login(customername, password);

        if(login_result == 0){
            return "index";
        }
        else{
            Customer customer = customerService.findCustomerByName(customername);
            System.out.println(customer.getCustomerId());
            //将数据存储到session中
            session.setAttribute("customer", customer);
            session.setAttribute("customer_id",customer.getCustomerId());
            return "homepage";
        }


    }

    @PostMapping("/recharge")
    public String recharge(HttpServletRequest request){
        System.out.println("recharge");
        int customerId = Integer.parseInt(request.getParameter("customer_id"));
        double fee = Double.parseDouble(request.getParameter("deposit_fee"));//todo;

        Deposit deposit = new Deposit(customerId, fee);
        customerService.charge(deposit);

        return "homepage";

    }



}
