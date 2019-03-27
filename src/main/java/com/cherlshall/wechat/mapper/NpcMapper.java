package com.cherlshall.wechat.mapper;

import com.cherlshall.wechat.entity.mysql.Npc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NpcMapper {

    /**
     * 查询NPC信息
     */
    @Select("select * from npc where id = #{id}")
    Npc getNpcById(int id);

    /**
     * 更新NPC血量
     */
    @Update("update npc set hp = hp + #{hpInc} where id = #{id}")
    int updateNpcHp(@Param("id") int id, @Param("hpInc") int hpInc);
}
