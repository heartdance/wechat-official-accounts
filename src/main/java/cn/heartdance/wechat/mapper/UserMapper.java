package cn.heartdance.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.heartdance.wechat.entity.po.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户信息
     */
    @Select("select * from user where open_id = #{openId}")
    User getUserByOpenId(String openId);

    /**
     * 新添加一个用户
     */
    @Insert("insert into user (open_id, money, prize_time) values (#{openId}, #{money}, #{prizeTime})")
    int addUser(User user);

    /**
     * 更新用户金钱
     * 通过判断执行之后money大于0 来应对并发的情况
     * 使用时要对返回值进行判断 确定更新条数>1 再进行后续操作
     * @param moneyInc 金钱增量
     */
    @Update("update user set money = money + #{moneyInc} where open_id = #{openId} " +
            "and money + #{moneyInc} > 0")
    int updateUserMoney(@Param("openId") String openId, @Param("moneyInc") double moneyInc);

    /**
     * 更新用户的金钱和领奖时间
     * 通过判断上次领奖时间相等来保证同步
     */
    @Update("update user set money = money + #{moneyInc}, prize_time = #{prizeTime} " +
            "where open_id = #{openId} and prize_time = #{lastPrizeTime}")
    int updateUserMoneyAndPrizeTime(@Param("openId") String openId,
                                    @Param("moneyInc") double money,
                                    @Param("prizeTime") double prizeTime,
                                    @Param("lastPrizeTime") double lastPrizeTime);

    /**
     * 判断用户是否如存在
     */
    @Select("select count(1) from user where open_id = #{openId}")
    int getUserCountByOpenId(String openId);
}
