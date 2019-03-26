package com.cherlshall.wechat.mapper;

import com.cherlshall.wechat.entity.mysql.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user where open_id = #{openId}")
    User getUserByOpenId(String openId);

    @Insert("insert into user (open_id, money, prize_time) values (#{openId}, #{money}, #{prizeTime})")
    int addUser(User user);

    @Update("update user set money = #{money} where open_id = #{openId}")
    int updateUserMoney(@Param("openId") String openId, @Param("money") double money);

    @Update("update user set money = #{money}, prize_time = #{prizeTime} where open_id = #{openId}")
    int updateUserMoneyAndPrizeTime(@Param("openId") String openId,
                                    @Param("money") double money,
                                    @Param("prizeTime") double prizeTime);

    @Select("select count(1) from user where open_id = #{openId}")
    int getUserCountByOpenId(String openId);
}
