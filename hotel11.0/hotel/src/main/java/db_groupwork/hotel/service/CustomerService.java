package db_groupwork.hotel.service;

import db_groupwork.hotel.entity.Customer;
import db_groupwork.hotel.entity.Deposit;
import db_groupwork.hotel.mapper.CustomerMapper;
import db_groupwork.hotel.mapper.DepositMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.sql.SQLException;

@Service
public class CustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    DepositMapper depositMapper;

    //登录
    public int login(String customerName, String password){
        int result = customerMapper.getCustomerCount(customerName, password);
        return (result == 1) ? 1 : 0;
    }

    //注册
    public boolean regist(String customerName, String password){
        Customer newcustomer = new Customer(customerName, password);

        if(customerMapper.ifExist(customerName)==0) {
            int result = customerMapper.register(newcustomer);
            System.out.println("result" + result);
            return (result == 1) ? true : false;
        }
        return false;
    }

    //充值
    public boolean charge(Deposit deposit){
        depositMapper.charge(deposit.getCustomerId(),deposit.getDepositFee());
        depositMapper.genDeposit(deposit);
        return true;
    }

    //通过用户name获取id
    public Customer findCustomerByName(String customerName){
        return customerMapper.getCustomer(customerName);
    }
}
