package db_groupwork.hotel.mapper;

import db_groupwork.hotel.entity.Deposit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface DepositMapper {
    //生成（充值）订单，返回订单id
//    @Insert("insert into deposit(customer_id,deposit_fee) values(#{customerId},#{depositFee})")
//    @Options(useGeneratedKeys = true,keyProperty = "deposit_id")
    int genDeposit(Deposit deposit);

    //充值加费
//    @Update("update customer set charge=charge+#{cost} where customer_id=#{id}")
    void charge(int id,double cost);

}
